package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto.RegistrationForm;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.RolJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.UsuarioJpa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.PersonaService personaService;
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.UsuarioService usuarioService;
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.RolService rolService;
    private final mx.ipn.upiicsa.web.accesscontrol.application.port.in.GeneroService generoService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("generos", generoService.findAll());
        return "accesscontrol/login/register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm form, BindingResult br,
            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("generos", generoService.findAll());
            return "accesscontrol/login/register";
        }

        // Custom Validation
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            br.addError(new FieldError("registrationForm", "confirmPassword", "Las contraseñas no coinciden"));
        }
        if (form.getPassword().length() <= 8) {
            br.addError(new FieldError("registrationForm", "password", "La contraseña debe tener más de 8 caracteres"));
        }
        // Regex: at least one special character (non-alphanumeric)
        if (!form.getPassword().matches(".*[^a-zA-Z0-9].*")) {
            br.addError(new FieldError("registrationForm", "password",
                    "La contraseña debe contener al menos un carácter especial"));
        }

        if (br.hasErrors()) {
            model.addAttribute("generos", generoService.findAll());
            return "accesscontrol/login/register";
        }

        // 1. Create Persona
        PersonaJpa p = new PersonaJpa();
        p.setIdGenero(form.getIdGenero());
        p.setNombre(form.getNombre());
        p.setPrimerApellido(form.getPrimerApellido());
        p.setSegundoApellido(form.getSegundoApellido());
        p.setFechaNacimiento(form.getFechaNacimiento());
        p.setFechaNacimiento(form.getFechaNacimiento());
        p = personaService.save(p);

        // 2. Find Role 'cliente'
        RolJpa rolCliente = rolService.findById(3)
                .orElseThrow(() -> new RuntimeException("Role 'cliente' not found"));

        // 3. Create Usuario
        UsuarioJpa u = new UsuarioJpa();
        u.setId(p.getId()); // OneToOne shared PK
        u.setIdRol(rolCliente.getId());
        u.setLogin(form.getLogin());
        u.setPassword(encodePassword(form.getPassword()));
        u.setActivo(true);
        usuarioService.save(u);

        log.info("Registered new client: {}", form.getLogin());

        return "redirect:/?registered=true";
    }

    private String encodePassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }
}
