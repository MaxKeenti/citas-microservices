package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.PersonaJpaRepository;
import mx.ipn.upiicsa.web.hresources.application.port.in.EmpleadoService;
import mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa;
import mx.ipn.upiicsa.web.hresources.domain.SucursalJpa;
import mx.ipn.upiicsa.web.hresources.application.port.out.SucursalJpaRepository;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.EmpleadoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private PersonaJpaRepository personaJpaRepository;

    @Autowired
    private SucursalJpaRepository sucursalJpaRepository;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("empleadoForm", new EmpleadoForm());
        // personas that are not yet employees (simple approach: list all personas and
        // filter by existence of empleado)
        List<PersonaJpa> personas = personaJpaRepository.findAll();
        // leave filtering to user for now
        List<SucursalJpa> sucursales = sucursalJpaRepository.findAll();
        model.addAttribute("personas", personas);
        model.addAttribute("sucursales", sucursales);
        return "hresources/empleados/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("empleadoForm") EmpleadoForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("personas", personaJpaRepository.findAll());
            model.addAttribute("sucursales", sucursalJpaRepository.findAll());
            return "hresources/empleados/create";
        }
        EmpleadoJpa e = new EmpleadoJpa();
        saveEmpleado(e, form);
        return "redirect:/empleados/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return empleadoService.findById(java.util.Objects.requireNonNull(id))
                .map(empleado -> {
                    EmpleadoForm form = new EmpleadoForm();
                    form.setIdPersona(empleado.getIdEmpleado());
                    form.setIdSucursal(empleado.getFkIdSucursal());
                    model.addAttribute("empleadoForm", form);
                    model.addAttribute("personas", personaJpaRepository.findAll());
                    model.addAttribute("sucursales", sucursalJpaRepository.findAll());
                    return "hresources/empleados/edit";
                })
                .orElse("redirect:/empleados/list");
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("empleadoForm") EmpleadoForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("personas", personaJpaRepository.findAll());
            model.addAttribute("sucursales", sucursalJpaRepository.findAll());
            return "hresources/empleados/edit";
        }
        EmpleadoJpa e = new EmpleadoJpa();
        saveEmpleado(e, form);
        return "redirect:/empleados/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return empleadoService.findById(java.util.Objects.requireNonNull(id))
                .map(empleado -> {
                    model.addAttribute("empleado", empleado);
                    return "hresources/empleados/delete";
                })
                .orElse("redirect:/empleados/list");
    }

    @PostMapping("/delete")
    public String delete(@org.springframework.web.bind.annotation.RequestParam Integer id) {
        empleadoService.deleteById(java.util.Objects.requireNonNull(id));
        return "redirect:/empleados/list";
    }

    private void saveEmpleado(EmpleadoJpa e, EmpleadoForm form) {
        e.setIdEmpleado(form.getIdPersona()); // employee id equals persona id
        e.setFkIdSucursal(form.getIdSucursal());
        empleadoService.save(e);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("empleados", empleadoService.findAll());
        return "hresources/empleados/list";
    }
}
