package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.BloqueCitaService;
import mx.ipn.upiicsa.web.hresources.domain.BloqueCitaJpa;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.BloqueCitaForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hresources/bloque-cita")
@RequiredArgsConstructor
public class BloqueCitaController {
    private final BloqueCitaService bloqueCitaService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("bloquesCita", bloqueCitaService.findAll());
        return "hresources/bloque-cita/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("bloqueCitaForm", new BloqueCitaForm());
        return "hresources/bloque-cita/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute BloqueCitaForm form) {
        BloqueCitaJpa bc = new BloqueCitaJpa();
        saveBloqueCita(bc, form);
        return "redirect:/hresources/bloque-cita/list";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Integer idSucursal,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fechaInicio,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fechaFin,
            Model model) {
        mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id = new mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId();
        id.setIdSucursal(idSucursal);
        id.setFechaInicio(fechaInicio);
        id.setFechaFin(fechaFin);

        return bloqueCitaService.findById(id)
                .map(bc -> {
                    BloqueCitaForm form = new BloqueCitaForm();
                    form.setIdSucursal(bc.getIdSucursal());
                    form.setIdCita(bc.getIdCita());
                    form.setFechaInicio(bc.getFechaInicio());
                    form.setFechaFin(bc.getFechaFin());
                    model.addAttribute("bloqueCitaForm", form);
                    return "hresources/bloque-cita/edit";
                })
                .orElse("redirect:/hresources/bloque-cita/list");
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BloqueCitaForm form) {
        BloqueCitaJpa bc = new BloqueCitaJpa();
        saveBloqueCita(bc, form);
        return "redirect:/hresources/bloque-cita/list";
    }

    @GetMapping("/delete")
    public String deleteConfirmation(@RequestParam Integer idSucursal,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fechaInicio,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fechaFin,
            Model model) {
        mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id = new mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId();
        id.setIdSucursal(idSucursal);
        id.setFechaInicio(fechaInicio);
        id.setFechaFin(fechaFin);

        return bloqueCitaService.findById(id)
                .map(bc -> {
                    model.addAttribute("bloque", bc);
                    return "hresources/bloque-cita/delete";
                })
                .orElse("redirect:/hresources/bloque-cita/list");
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer idSucursal,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fechaInicio,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fechaFin) {
        mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id = new mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId();
        id.setIdSucursal(idSucursal);
        id.setFechaInicio(fechaInicio);
        id.setFechaFin(fechaFin);

        bloqueCitaService.deleteById(id);
        return "redirect:/hresources/bloque-cita/list";
    }

    private void saveBloqueCita(BloqueCitaJpa bc, BloqueCitaForm form) {
        bc.setIdSucursal(form.getIdSucursal());
        bc.setIdCita(form.getIdCita());
        bc.setFechaInicio(form.getFechaInicio());
        bc.setFechaFin(form.getFechaFin());
        bloqueCitaService.save(bc);
    }
}
