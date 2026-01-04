package mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.hresources.domain.EstablecimientoJpa;
import mx.ipn.upiicsa.web.hresources.domain.SucursalJpa;
import mx.ipn.upiicsa.web.hresources.application.port.out.EstablecimientoJpaRepository;
import mx.ipn.upiicsa.web.hresources.application.port.in.SucursalService;
import mx.ipn.upiicsa.web.hresources.infrastructure.adapter.in.web.dto.SucursalForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;

import java.util.List;

@Controller
@RequestMapping("/sucursales")
@lombok.RequiredArgsConstructor
public class SucursalController {
    private final SucursalService sucursalService;
    private final EstablecimientoJpaRepository establecimientoJpaRepository;

    @GetMapping("/create")
    @RequiresRole("ADMIN")
    public String createForm(Model model) {
        model.addAttribute("sucursalForm", new SucursalForm());
        List<EstablecimientoJpa> establecimientos = establecimientoJpaRepository.findAll();
        model.addAttribute("establecimientos", establecimientos);
        return "hresources/sucursales/create";
    }

    @PostMapping("/create")
    @RequiresRole("ADMIN")
    public String create(@Valid @ModelAttribute("sucursalForm") SucursalForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("establecimientos", establecimientoJpaRepository.findAll());
            return "hresources/sucursales/create";
        }
        SucursalJpa s = new SucursalJpa();
        saveSucursal(s, form);
        return "redirect:/sucursales/list";
    }

    @GetMapping("/edit/{id}")
    @RequiresRole("ADMIN")
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return sucursalService.findById(id)
                .map(sucursal -> {
                    SucursalForm form = new SucursalForm();
                    form.setId(sucursal.getIdSucursal());
                    form.setIdEstablecimiento(sucursal.getFkIdEstablecimiento());
                    form.setNombre(sucursal.getTxNombre());
                    if (sucursal.getGmUbicacion() != null) {
                        form.setLatitud(sucursal.getGmUbicacion().getY());
                        form.setLongitud(sucursal.getGmUbicacion().getX());
                    }
                    model.addAttribute("sucursalForm", form);
                    model.addAttribute("establecimientos", establecimientoJpaRepository.findAll());
                    return "hresources/sucursales/edit";
                })
                .orElse("redirect:/sucursales/list");
    }

    @PostMapping("/update")
    @RequiresRole("ADMIN")
    public String update(@Valid @ModelAttribute("sucursalForm") SucursalForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("establecimientos", establecimientoJpaRepository.findAll());
            return "hresources/sucursales/edit";
        }
        SucursalJpa s = new SucursalJpa();
        s.setIdSucursal(form.getId());
        saveSucursal(s, form);
        return "redirect:/sucursales/list";
    }

    @GetMapping("/delete/{id}")
    @RequiresRole("ADMIN")
    public String deleteConfirmation(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model) {
        return sucursalService.findById(id)
                .map(sucursal -> {
                    model.addAttribute("sucursal", sucursal);
                    return "hresources/sucursales/delete";
                })
                .orElse("redirect:/sucursales/list");
    }

    @PostMapping("/delete")
    @RequiresRole("ADMIN")
    public String delete(@org.springframework.web.bind.annotation.RequestParam Integer id) {
        sucursalService.deleteById(id);
        return "redirect:/sucursales/list";
    }

    private void saveSucursal(SucursalJpa s, SucursalForm form) {
        s.setFkIdEstablecimiento(form.getIdEstablecimiento());
        s.setTxNombre(form.getNombre());

        org.locationtech.jts.geom.GeometryFactory gf = new org.locationtech.jts.geom.GeometryFactory();
        org.locationtech.jts.geom.Point point = gf
                .createPoint(new org.locationtech.jts.geom.Coordinate(form.getLongitud(), form.getLatitud()));
        point.setSRID(4326);
        s.setGmUbicacion(point);

        sucursalService.save(s);
    }

    @GetMapping("/list")
    @RequiresRole({ "ADMIN", "EMPLOYEE" })
    public String list(Model model) {
        model.addAttribute("sucursales", sucursalService.findAll());
        return "hresources/sucursales/list";
    }
}
