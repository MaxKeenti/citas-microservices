package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto.LoginDto;
import mx.ipn.upiicsa.web.accesscontrol.application.port.in.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "index";
    }

    // @PostMapping("/")
    // public String login(@RequestParam String username, @RequestParam String
    // password, Model model){
    // System.out.println("USERNAME: " + username);
    // System.out.println("PASSWORD: " + password);
    // return "index";
    // }

    @PostMapping("/")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, Model model,
            HttpSession session) {
        for (ObjectError errorList : bindingResult.getAllErrors()) {
            log.info("ERROR: {} - {} - {}", errorList.getObjectName(), errorList.getCode(),
                    errorList.getDefaultMessage());
        }
        String resultado = null;
        var resultadoLogin = loginService.login(loginDto);
        if (resultadoLogin.isRight()) {
            var persona = resultadoLogin.get();
            log.info("LOGIN EXITOSO");
            log.info("Persona: {} {} {}", persona.getNombre(), persona.getPrimerApellido(),
                    persona.getSegundoApellido());
            log.info("Usuario: {} {} {}", persona.getUsuario().getLogin(), persona.getUsuario().getPassword(),
                    persona.getUsuario().getActivo());
            model.addAttribute("persona", persona);
            session.setAttribute("persona", persona);
            resultado = "welcome";
        } else {
            log.info("LOGIN NO ENCONTRADO: {}", resultadoLogin.getLeft());
            // ObjectError error = new ObjectError("peticion","Error, usuario y/o contraseña
            // incorrectos");
            // bindingResult.addError(error);
            if (resultadoLogin.getLeft() == 1) {
                model.addAttribute("errorLogin", "Error, usuario y/o contraseña incorrectos");
            } else {
                model.addAttribute("errorLogin", "El usuario está inactivo");
            }
            resultado = "index";
        }
        return resultado;
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        if (session.getAttribute("persona") == null) {
            return "redirect:/";
        }
        model.addAttribute("persona", session.getAttribute("persona"));
        return "welcome";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
