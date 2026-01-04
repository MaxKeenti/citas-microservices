package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.DiaDescansoService;
import mx.ipn.upiicsa.web.hresources.domain.DiaDescansoJpa;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.DiaDescansoForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hresources/dia-descanso")
@RequiredArgsConstructor
public class DiaDescansoController {
    private final DiaDescansoService diaDescansoService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("diasDescanso", diaDescansoService.findAll());
        return "hresources/dia-descanso/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("diaDescansoForm", new DiaDescansoForm());
        return "hresources/dia-descanso/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute DiaDescansoForm form) {
        DiaDescansoJpa dia = new DiaDescansoJpa();
        saveDiaDescanso(dia, form);
        return "redirect:/hresources/dia-descanso/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        return diaDescansoService.findById(id)
                .map(dia -> {
                    DiaDescansoForm form = new DiaDescansoForm();
                    form.setId(dia.getId());
                    form.setIdEmpleado(dia.getIdEmpleado());
                    form.setFechaDescanso(dia.getFechaDescanso());
                    model.addAttribute("diaDescansoForm", form);
                    return "hresources/dia-descanso/edit";
                })
                .orElse("redirect:/hresources/dia-descanso/list");
    }

    @PostMapping("/update")
    public String update(@ModelAttribute DiaDescansoForm form) {
        DiaDescansoJpa dia = new DiaDescansoJpa();
        dia.setId(form.getId());
        saveDiaDescanso(dia, form);
        return "redirect:/hresources/dia-descanso/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(@PathVariable Integer id, Model model) {
        return diaDescansoService.findById(id)
                .map(dia -> {
                    model.addAttribute("dia", dia);
                    return "hresources/dia-descanso/delete";
                })
                .orElse("redirect:/hresources/dia-descanso/list");
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        diaDescansoService.deleteById(id);
        return "redirect:/hresources/dia-descanso/list";
    }

    private void saveDiaDescanso(DiaDescansoJpa dia, DiaDescansoForm form) {
        dia.setIdEmpleado(form.getIdEmpleado());
        dia.setFechaDescanso(form.getFechaDescanso());
        diaDescansoService.save(dia);
    }
}
