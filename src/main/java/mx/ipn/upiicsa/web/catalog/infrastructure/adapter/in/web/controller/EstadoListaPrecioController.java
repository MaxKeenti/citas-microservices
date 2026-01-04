package mx.ipn.upiicsa.web.catalog.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.catalog.application.port.in.EstadoListaPrecioService;
import mx.ipn.upiicsa.web.catalog.domain.EstadoListaPrecioJpa;
import mx.ipn.upiicsa.web.catalog.infrastructure.adapter.in.web.dto.EstadoListaPrecioForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalog/estado-lista-precio")
@RequiredArgsConstructor
public class EstadoListaPrecioController {
    private final EstadoListaPrecioService estadoListaPrecioService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("estados", estadoListaPrecioService.findAll());
        return "catalog/estado-lista-precio/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("estadoListaPrecioForm", new EstadoListaPrecioForm());
        return "catalog/estado-lista-precio/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute EstadoListaPrecioForm form) {
        EstadoListaPrecioJpa estado = new EstadoListaPrecioJpa();
        saveEstado(estado, form);
        return "redirect:/catalog/estado-lista-precio/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        return estadoListaPrecioService.findById(id)
                .map(estado -> {
                    EstadoListaPrecioForm form = new EstadoListaPrecioForm();
                    form.setId(estado.getId());
                    form.setNombre(estado.getNombre());
                    model.addAttribute("estadoListaPrecioForm", form);
                    return "catalog/estado-lista-precio/edit";
                })
                .orElse("redirect:/catalog/estado-lista-precio/list");
    }

    @PostMapping("/update")
    public String update(@ModelAttribute EstadoListaPrecioForm form) {
        EstadoListaPrecioJpa estado = new EstadoListaPrecioJpa();
        estado.setId(form.getId());
        saveEstado(estado, form);
        return "redirect:/catalog/estado-lista-precio/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(@PathVariable Integer id, Model model) {
        return estadoListaPrecioService.findById(id)
                .map(estado -> {
                    model.addAttribute("estado", estado);
                    return "catalog/estado-lista-precio/delete";
                })
                .orElse("redirect:/catalog/estado-lista-precio/list");
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        estadoListaPrecioService.deleteById(id);
        return "redirect:/catalog/estado-lista-precio/list";
    }

    private void saveEstado(EstadoListaPrecioJpa estado, EstadoListaPrecioForm form) {
        estado.setNombre(form.getNombre());
        estadoListaPrecioService.save(estado);
    }
}
