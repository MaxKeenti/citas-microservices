package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.HorarioService;
import mx.ipn.upiicsa.web.hresources.domain.HorarioJpa;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.HorarioForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;

@Controller
@RequestMapping("/hresources/horario")
@RequiredArgsConstructor
public class HorarioController {
    private final HorarioService horarioService;

    @GetMapping("/list")
    @RequiresRole({ "ADMIN", "EMPLOYEE" })
    public String list(Model model) {
        model.addAttribute("horarios", horarioService.findAll());
        return "hresources/horario/list";
    }

    @GetMapping("/create")
    @RequiresRole("ADMIN")
    public String createForm(Model model) {
        model.addAttribute("horarioForm", new HorarioForm());
        return "hresources/horario/create";
    }

    @PostMapping("/create")
    @RequiresRole("ADMIN")
    public String create(@ModelAttribute HorarioForm form) {
        HorarioJpa horario = new HorarioJpa();
        saveHorario(horario, form);
        return "redirect:/hresources/horario/list";
    }

    @GetMapping("/edit/{id}")
    @RequiresRole("ADMIN")
    public String editForm(@PathVariable Integer id, Model model) {
        return horarioService.findById(id)
                .map(horario -> {
                    HorarioForm form = new HorarioForm();
                    form.setId(horario.getId());
                    form.setIdSucursal(horario.getIdSucursal());
                    form.setIdDia(horario.getIdDia());
                    form.setHoraInicio(horario.getHoraInicio());
                    form.setHoraFin(horario.getHoraFin());
                    model.addAttribute("horarioForm", form);
                    return "hresources/horario/edit";
                })
                .orElse("redirect:/hresources/horario/list");
    }

    @PostMapping("/update")
    @RequiresRole("ADMIN")
    public String update(@ModelAttribute HorarioForm form) {
        HorarioJpa horario = new HorarioJpa();
        horario.setId(form.getId());
        saveHorario(horario, form);
        return "redirect:/hresources/horario/list";
    }

    @GetMapping("/delete/{id}")
    @RequiresRole("ADMIN")
    public String deleteConfirmation(@PathVariable Integer id, Model model) {
        return horarioService.findById(id)
                .map(horario -> {
                    model.addAttribute("horario", horario);
                    return "hresources/horario/delete";
                })
                .orElse("redirect:/hresources/horario/list");
    }

    @PostMapping("/delete")
    @RequiresRole("ADMIN")
    public String delete(@RequestParam Integer id) {
        horarioService.deleteById(id);
        return "redirect:/hresources/horario/list";
    }

    private void saveHorario(HorarioJpa horario, HorarioForm form) {
        horario.setIdSucursal(form.getIdSucursal());
        horario.setIdDia(form.getIdDia());
        horario.setHoraInicio(form.getHoraInicio());
        horario.setHoraFin(form.getHoraFin());
        horarioService.save(horario);
    }
}
