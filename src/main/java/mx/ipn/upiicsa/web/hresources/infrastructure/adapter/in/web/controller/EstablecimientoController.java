package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.hresources.application.port.in.EstablecimientoService;
import mx.ipn.upiicsa.web.hresources.domain.EstablecimientoJpa;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.EstablecimientoForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hresources/establecimiento")
public class EstablecimientoController {

    private final EstablecimientoService establecimientoService;

    public EstablecimientoController(EstablecimientoService establecimientoService) {
        this.establecimientoService = establecimientoService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("establecimientos", establecimientoService.findAll());
        return "hresources/establecimiento/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("establecimientoForm", new EstablecimientoForm());
        return "hresources/establecimiento/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("establecimientoForm") EstablecimientoForm form, BindingResult br,
            Model model) {
        if (br.hasErrors()) {
            return "hresources/establecimiento/create";
        }
        EstablecimientoJpa e = new EstablecimientoJpa();
        saveEstablecimiento(e, form);
        return "redirect:/hresources/establecimiento/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        return establecimientoService.findById(id)
                .map(establecimiento -> {
                    EstablecimientoForm form = new EstablecimientoForm();
                    form.setId(establecimiento.getId());
                    form.setTxNombre(establecimiento.getTxNombre());
                    model.addAttribute("establecimientoForm", form);
                    return "hresources/establecimiento/edit";
                })
                .orElse("redirect:/hresources/establecimiento/list");
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
            @Valid @ModelAttribute("establecimientoForm") EstablecimientoForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "hresources/establecimiento/edit";
        }
        EstablecimientoJpa e = new EstablecimientoJpa();
        e.setId(id);
        saveEstablecimiento(e, form);
        return "redirect:/hresources/establecimiento/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(@PathVariable Integer id, Model model) {
        return establecimientoService.findById(id)
                .map(establecimiento -> {
                    model.addAttribute("establecimiento", establecimiento);
                    return "hresources/establecimiento/delete";
                })
                .orElse("redirect:/hresources/establecimiento/list");
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        establecimientoService.deleteById(id);
        return "redirect:/hresources/establecimiento/list";
    }

    private void saveEstablecimiento(EstablecimientoJpa e, EstablecimientoForm form) {
        e.setTxNombre(form.getTxNombre());
        establecimientoService.save(e);
    }
}
