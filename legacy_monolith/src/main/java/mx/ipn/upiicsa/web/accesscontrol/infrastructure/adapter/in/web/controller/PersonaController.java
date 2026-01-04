package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.GeneroJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto.PersonaForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;

@Controller
@RequestMapping("/personas")
@lombok.RequiredArgsConstructor
public class PersonaController {
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.PersonaService personaService;
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.GeneroService generoService;

    @GetMapping("/create")
    @RequiresRole("ADMIN")
    public String createForm(Model model) {
        model.addAttribute("personaForm", new PersonaForm());
        List<GeneroJpa> generos = generoService.findAll();
        model.addAttribute("generos", generos);
        return "personas/create";
    }

    @PostMapping("/create")
    @RequiresRole("ADMIN")
    public String create(@Valid @ModelAttribute("personaForm") PersonaForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("generos", generoService.findAll());
            return "personas/create";
        }
        PersonaJpa p = new PersonaJpa();
        savePersona(p, form);
        return "redirect:/personas/list";
    }

    @GetMapping("/edit/{id}")
    @RequiresRole({ "ADMIN", "EMPLOYEE" })
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model,
            HttpSession session) {
        if (!isAuthorized(session, id))
            return "redirect:/access-denied";
        return personaService.findById(id)
                .map(persona -> {
                    PersonaForm form = new PersonaForm();
                    form.setIdGenero(persona.getIdGenero());
                    form.setNombre(persona.getNombre());
                    form.setPrimerApellido(persona.getPrimerApellido());
                    form.setSegundoApellido(persona.getSegundoApellido());
                    form.setFechaNacimiento(persona.getFechaNacimiento());

                    model.addAttribute("personaForm", form);
                    model.addAttribute("generos", generoService.findAll());
                    model.addAttribute("personaId", id); // Add ID to model for form action
                    return "personas/edit";
                })
                .orElse("redirect:/personas/list");
    }

    @PostMapping("/update/{id}")
    @RequiresRole({ "ADMIN", "EMPLOYEE" })
    public String update(@org.springframework.web.bind.annotation.PathVariable Integer id,
            @Valid @ModelAttribute("personaForm") PersonaForm form, BindingResult br, Model model,
            HttpSession session) {
        if (!isAuthorized(session, id))
            return "redirect:/access-denied";
        if (br.hasErrors()) {
            model.addAttribute("generos", generoService.findAll());
            model.addAttribute("personaId", id);
            return "personas/edit";
        }
        // Ideally we fetch existing persona and update it
        // simplified here:
        PersonaJpa p = new PersonaJpa();
        p.setId(id);
        savePersona(p, form);
        return "redirect:/personas/list";
    }

    @GetMapping("/delete/{id}")
    @RequiresRole("ADMIN")
    public String deleteConfirmation(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return personaService.findById(id)
                .map(persona -> {
                    model.addAttribute("persona", persona);
                    return "personas/delete";
                })
                .orElse("redirect:/personas/list");
    }

    @PostMapping("/delete")
    @RequiresRole("ADMIN")
    public String delete(@org.springframework.web.bind.annotation.RequestParam Integer id) {
        personaService.deleteById(id);
        return "redirect:/personas/list";
    }

    private void savePersona(PersonaJpa p, PersonaForm form) {
        p.setIdGenero(form.getIdGenero());
        p.setNombre(form.getNombre());
        p.setPrimerApellido(form.getPrimerApellido());
        p.setSegundoApellido(form.getSegundoApellido());
        p.setFechaNacimiento(form.getFechaNacimiento());
        personaService.save(p);
    }

    @GetMapping("/list")
    @RequiresRole("ADMIN")
    public String list(Model model) {
        model.addAttribute("personas", personaService.findAll());
        return "personas/list";
    }

    private boolean isAuthorized(HttpSession session, Integer targetId) {
        PersonaJpa cur = (PersonaJpa) session.getAttribute("persona");
        if (cur == null)
            return false;
        if (cur.getUsuario() != null && cur.getUsuario().getIdRol() == 1)
            return true; // Admin
        return cur.getId().equals(targetId);
    }
}
