package mx.ipn.upiicsa.web.catalog.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.catalog.domain.ServicioJpa;
import mx.ipn.upiicsa.web.catalog.application.port.in.ServicioService;
import mx.ipn.upiicsa.web.catalog.infrastructure.adapter.in.web.dto.ServicioForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/servicios")
@lombok.RequiredArgsConstructor
public class ServicioController {
    private final ServicioService servicioService;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("servicioForm", new ServicioForm());
        return "catalog/servicios/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("servicioForm") ServicioForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "catalog/servicios/create";
        }
        ServicioJpa s = new ServicioJpa();
        saveServicio(s, form);
        return "redirect:/servicios/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return servicioService.findById(id)
                .map(servicio -> {
                    ServicioForm form = new ServicioForm();
                    form.setId(servicio.getIdServicio());
                    form.setNombre(servicio.getTxNombre());
                    form.setDescripcion(servicio.getDescripcion());
                    form.setDuracion(servicio.getNuDuracion());
                    form.setActivo(servicio.getStActivo());
                    model.addAttribute("servicioForm", form);
                    return "catalog/servicios/edit";
                })
                .orElse("redirect:/servicios/list");
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("servicioForm") ServicioForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "catalog/servicios/edit";
        }
        ServicioJpa s = new ServicioJpa();
        s.setIdServicio(form.getId());
        saveServicio(s, form);
        return "redirect:/servicios/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return servicioService.findById(id)
                .map(servicio -> {
                    model.addAttribute("servicio", servicio);
                    return "catalog/servicios/delete";
                })
                .orElse("redirect:/servicios/list");
    }

    @PostMapping("/delete")
    public String delete(@org.springframework.web.bind.annotation.RequestParam Integer id) {
        servicioService.deleteById(id);
        return "redirect:/servicios/list";
    }

    private void saveServicio(ServicioJpa s, ServicioForm form) {
        s.setTxNombre(form.getNombre());
        s.setDescripcion(form.getDescripcion());
        s.setNuDuracion(form.getDuracion());
        s.setStActivo(form.getActivo());
        servicioService.save(s);
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<ServicioJpa> servicios = servicioService.findAll();
        model.addAttribute("servicios", servicios);
        return "catalog/servicios/list";
    }
}
