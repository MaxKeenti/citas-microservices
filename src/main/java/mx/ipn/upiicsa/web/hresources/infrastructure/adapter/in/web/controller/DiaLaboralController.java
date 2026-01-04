package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.DiaLaboralService;
import mx.ipn.upiicsa.web.hresources.domain.DiaLaboralJpa;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.DiaLaboralForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;

@Controller
@RequestMapping("/hresources/dia-laboral")
@RequiredArgsConstructor
@RequiresRole("ADMIN")
public class DiaLaboralController {
    private final DiaLaboralService diaLaboralService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("diasLaborales", diaLaboralService.findAll());
        return "hresources/dia-laboral/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("diaLaboralForm", new DiaLaboralForm());
        return "hresources/dia-laboral/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute DiaLaboralForm form) {
        DiaLaboralJpa dia = new DiaLaboralJpa();
        dia.setNombre(form.getNombre());
        dia.setDescripcion(form.getDescripcion());
        dia.setActivo(form.getActivo());
        diaLaboralService.save(dia);
        return "redirect:/hresources/dia-laboral/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        return diaLaboralService.findById(id)
                .map(dia -> {
                    DiaLaboralForm form = new DiaLaboralForm();
                    form.setId(dia.getId());
                    form.setNombre(dia.getNombre());
                    form.setDescripcion(dia.getDescripcion());
                    form.setActivo(dia.getActivo());
                    model.addAttribute("diaLaboralForm", form);
                    return "hresources/dia-laboral/edit";
                })
                .orElse("redirect:/hresources/dia-laboral/list");
    }

    @PostMapping("/update")
    public String update(@ModelAttribute DiaLaboralForm form) {
        DiaLaboralJpa dia = new DiaLaboralJpa();
        dia.setId(form.getId());
        dia.setNombre(form.getNombre());
        dia.setDescripcion(form.getDescripcion());
        dia.setActivo(form.getActivo());
        diaLaboralService.save(dia);
        return "redirect:/hresources/dia-laboral/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(@PathVariable Integer id, Model model) {
        return diaLaboralService.findById(id)
                .map(dia -> {
                    model.addAttribute("dia", dia);
                    return "hresources/dia-laboral/delete";
                })
                .orElse("redirect:/hresources/dia-laboral/list");
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        diaLaboralService.deleteById(id);
        return "redirect:/hresources/dia-laboral/list";
    }
}
