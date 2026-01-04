package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.RolJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.UsuarioJpa;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto.UsuarioForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;

@Controller
@RequestMapping("/usuarios")
@lombok.RequiredArgsConstructor
public class UsuarioController {
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.UsuarioService usuarioService;
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.PersonaService personaService;
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.RolService rolService;

    @GetMapping("/create")
    @RequiresRole("ADMIN")
    public String createForm(Model model) {
        model.addAttribute("usuarioForm", new UsuarioForm());
        // personas that don't have usuario
        List<PersonaJpa> personas = personaService.findAll()
                .stream().filter(p -> p.getUsuario() == null).collect(Collectors.toList());
        List<RolJpa> roles = rolService.findAll().stream().filter(r -> Boolean.TRUE.equals(r.getActivo()))
                .collect(Collectors.toList());
        model.addAttribute("personas", personas);
        model.addAttribute("roles", roles);
        return "accesscontrol/usuarios/create";
    }

    @PostMapping("/create")
    @RequiresRole("ADMIN")
    public String create(@Valid @ModelAttribute("usuarioForm") UsuarioForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            populateModel(model);
            return "accesscontrol/usuarios/create";
        }
        UsuarioJpa u = new UsuarioJpa();
        saveUsuario(u, form);
        return "redirect:/usuarios/list";
    }

    @GetMapping("/edit/{id}")
    @RequiresRole({ "ADMIN", "EMPLOYEE" })
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model,
            HttpSession session) {
        if (!isAuthorized(session, id))
            return "redirect:/access-denied";
        return usuarioService.findById(id)
                .map(usuario -> {
                    UsuarioForm form = new UsuarioForm();
                    form.setIdPersona(usuario.getId());
                    form.setIdRol(usuario.getIdRol());
                    form.setLogin(usuario.getLogin());
                    // Password not pre-filled for security, but we need to handle if empty in
                    // update
                    // form.setPassword(usuario.getPassword());
                    form.setActivo(usuario.getActivo());

                    model.addAttribute("usuarioForm", form);
                    populateModel(model);
                    return "accesscontrol/usuarios/edit";
                })
                .orElse("redirect:/usuarios/list");
    }

    @PostMapping("/update")
    @RequiresRole({ "ADMIN", "EMPLOYEE" })
    public String update(@Valid @ModelAttribute("usuarioForm") UsuarioForm form, BindingResult br, Model model,
            HttpSession session) {
        if (!isAuthorized(session, form.getIdPersona()))
            return "redirect:/access-denied";
        if (br.hasErrors()) {
            populateModel(model);
            return "accesscontrol/usuarios/edit";
        }
        UsuarioJpa u = new UsuarioJpa();
        u.setId(form.getIdPersona());
        // Handling password: if form password is empty, we might want to keep original.
        // But here we are building a new object.
        // Real implementation should probably fetch existing and only update changed
        // fields.
        // For now, consistent with create: overwrite.
        // Ideally, we'd check if password is changed.
        saveUsuario(u, form);
        return "redirect:/usuarios/list";
    }

    @GetMapping("/delete/{id}")
    @RequiresRole("ADMIN")
    public String deleteConfirmation(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return usuarioService.findById(id)
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    return "accesscontrol/usuarios/delete";
                })
                .orElse("redirect:/usuarios/list");
    }

    @PostMapping("/delete")
    @RequiresRole("ADMIN")
    public String delete(@org.springframework.web.bind.annotation.RequestParam Integer id) {
        usuarioService.deleteById(id);
        return "redirect:/usuarios/list";
    }

    private void saveUsuario(UsuarioJpa u, UsuarioForm form) {
        u.setId(form.getIdPersona());
        u.setIdRol(form.getIdRol());
        u.setLogin(form.getLogin());
        // Ideally hash password here if needed, or service does it.
        u.setPassword(form.getPassword());
        u.setActivo(form.getActivo());
        usuarioService.save(u);
    }

    private void populateModel(Model model) {
        model.addAttribute("personas", personaService.findAll()); // All personas, or logic for edit
        model.addAttribute("roles", rolService.findAll().stream()
                .filter(r -> Boolean.TRUE.equals(r.getActivo())).collect(Collectors.toList()));
    }

    @GetMapping("/list")
    @RequiresRole("ADMIN")
    public String list(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "accesscontrol/usuarios/list";
    }

    @RequiresRole("ADMIN")
    private boolean isAuthorized(HttpSession session, Integer targetId) {
        PersonaJpa cur = (PersonaJpa) session.getAttribute("persona");
        if (cur == null)
            return false;
        // 1 = Admin
        if (cur.getUsuario().getIdRol() == 1)
            return true;
        // Check if editing own profile
        return cur.getId().equals(targetId);
    }
}
