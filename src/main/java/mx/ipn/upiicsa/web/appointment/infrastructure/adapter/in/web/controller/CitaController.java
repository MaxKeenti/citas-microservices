package mx.ipn.upiicsa.web.appointment.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import mx.ipn.upiicsa.web.appointment.domain.CitaJpa;
import mx.ipn.upiicsa.web.appointment.application.port.in.CitaService;
import java.util.Optional;

import mx.ipn.upiicsa.web.catalog.application.port.out.ServicioJpaRepository;
import mx.ipn.upiicsa.web.catalog.application.port.out.ServicioListaPrecioJpaRepository;
import mx.ipn.upiicsa.web.catalog.domain.ServicioListaPrecioJpa;

import mx.ipn.upiicsa.web.hresources.application.port.out.SucursalJpaRepository;
import mx.ipn.upiicsa.web.hresources.application.port.out.EmpleadoJpaRepository;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.PersonaJpaRepository;
import mx.ipn.upiicsa.web.appointment.infrastructure.adapter.in.web.dto.CitaForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;
import mx.ipn.upiicsa.web.accesscontrol.domain.Persona;

@Controller
@RequestMapping("/citas")
@lombok.RequiredArgsConstructor
public class CitaController {
    private final CitaService citaService;
    private final PersonaJpaRepository personaJpaRepository;
    private final ServicioJpaRepository servicioJpaRepository;
    private final ServicioListaPrecioJpaRepository servicioListaPrecioJpaRepository;

    private final SucursalJpaRepository sucursalJpaRepository;
    private final EmpleadoJpaRepository empleadoJpaRepository;

    @GetMapping("/create")
    @RequiresRole({ "ADMIN", "CLIENT", "EMPLOYEE" })

    public String createForm(Model model, HttpSession session,
            @RequestParam(required = false) java.time.LocalDateTime fechaHora,
            @RequestParam(required = false) Integer customDuration) {
        Persona persona = (Persona) session.getAttribute("persona");
        // Clients can only create for themselves
        if (isClient(persona)) {
            model.addAttribute("personas", List.of(persona));
        } else {
            model.addAttribute("personas", personaJpaRepository.findAll());
        }

        CitaForm form = new CitaForm();
        if (fechaHora != null) {
            form.setFechaHora(fechaHora);
        }
        if (customDuration != null) {
            form.setCustomDuration(customDuration);
        }

        model.addAttribute("citaForm", form);
        model.addAttribute("servicios", servicioJpaRepository.findAll());
        model.addAttribute("sucursales", sucursalJpaRepository.findAll());
        model.addAttribute("empleados", empleadoJpaRepository.findAll());
        // initially no lista precios loaded
        model.addAttribute("listas", List.of());
        return "appointment/citas/create";
    }

    @PostMapping("/create")
    @RequiresRole({ "ADMIN", "CLIENT", "EMPLOYEE" })

    public String create(@Valid @ModelAttribute("citaForm") CitaForm form, BindingResult br, Model model,
            HttpSession session) {
        Persona persona = (Persona) session.getAttribute("persona");
        // Enforce ownership for clients
        if (isClient(persona) && !form.getIdPersona().equals(persona.getId())) {
            return "redirect:/access-denied";
        }

        if (br.hasErrors()) {
            populateModel(model, form.getIdServicio());
            return "appointment/citas/create";
        }
        CitaJpa c = new CitaJpa();
        try {
            saveCita(c, form);
        } catch (IllegalStateException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Error al guardar cita";
            br.rejectValue("fechaHora", "error.fechaHora", java.util.Objects.requireNonNull(msg));
            populateModel(model, form.getIdServicio());
            return "appointment/citas/create";
        }
        return "redirect:/citas/list";
    }

    @GetMapping("/edit/{id}")
    @RequiresRole({ "ADMIN", "CLIENT" })
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model,
            HttpSession session) {
        if (!isAuthorized(session, id))
            return "redirect:/access-denied";

        return citaService.findById(id)
                .map(cita -> {
                    CitaForm form = new CitaForm();
                    form.setId(cita.getIdCita());
                    form.setIdPersona(cita.getFkIdPersona());
                    form.setIdServicio(cita.getFkIdServicio());
                    form.setIdListaPrecio(cita.getFkIdListaPrecio());
                    form.setIdSucursal(cita.getFkIdSucursal());
                    form.setIdEmpleado(cita.getFkIdEmpleado());
                    form.setFechaHora(cita.getFechaHora());
                    form.setCustomDuration(cita.getCustomDuration()); // Map customDuration to form

                    model.addAttribute("citaForm", form);
                    populateModel(model, cita.getFkIdServicio());
                    return "appointment/citas/edit";
                })
                .orElse("redirect:/citas/list");
    }

    @PostMapping("/update")
    @RequiresRole({ "ADMIN", "CLIENT" })
    public String update(@Valid @ModelAttribute("citaForm") CitaForm form, BindingResult br, Model model,
            HttpSession session) {
        // Since ID is in the form, we need to check the existing Cita to verify
        // ownership,
        // OR rely on the fact that they loaded the form via edit/{id} which checked it.
        // Better to check again using the form ID.
        if (!isAuthorized(session, form.getId()))
            return "redirect:/access-denied";

        if (br.hasErrors()) {
            populateModel(model, form.getIdServicio());
            return "appointment/citas/edit";
        }

        Optional<CitaJpa> existingOpt = citaService.findById(form.getId());
        if (existingOpt.isEmpty()) {
            return "redirect:/citas/list";
        }
        CitaJpa c = existingOpt.get();

        try {
            saveCita(c, form);
        } catch (IllegalStateException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Error al guardar cita";
            br.rejectValue("fechaHora", "error.fechaHora", java.util.Objects.requireNonNull(msg));
            populateModel(model, form.getIdServicio());
            return "appointment/citas/edit";
        }
        return "redirect:/citas/list";
    }

    @GetMapping("/delete/{id}")
    @RequiresRole({ "ADMIN", "CLIENT" })
    public String deleteConfirmation(@org.springframework.web.bind.annotation.PathVariable Integer id, Model model,
            HttpSession session) {
        if (!isAuthorized(session, id))
            return "redirect:/access-denied";

        return citaService.findById(id)
                .map(cita -> {
                    model.addAttribute("cita", cita);
                    return "appointment/citas/delete";
                })
                .orElse("redirect:/citas/list");
    }

    @PostMapping("/delete")
    @RequiresRole({ "ADMIN", "CLIENT" })
    public String delete(@org.springframework.web.bind.annotation.RequestParam Integer id, HttpSession session) {
        if (!isAuthorized(session, id))
            return "redirect:/access-denied";
        citaService.deleteById(id);
        return "redirect:/citas/list";
    }

    private void saveCita(CitaJpa c, CitaForm form) {
        c.setFkIdPersona(form.getIdPersona());
        c.setFkIdServicio(form.getIdServicio());
        c.setFkIdListaPrecio(form.getIdListaPrecio());
        c.setFkIdSucursal(form.getIdSucursal());
        c.setFkIdEmpleado(form.getIdEmpleado());
        c.setFechaHora(form.getFechaHora());
        if (form.getCustomDuration() != null) {
            c.setCustomDuration(form.getCustomDuration());
        }
        citaService.save(c);

    }

    private void populateModel(Model model, Integer idServicio) {
        model.addAttribute("personas", personaJpaRepository.findAll());
        model.addAttribute("servicios", servicioJpaRepository.findAll());
        model.addAttribute("sucursales", sucursalJpaRepository.findAll());
        model.addAttribute("empleados", empleadoJpaRepository.findAll());

        if (idServicio != null) {
            model.addAttribute("listas",
                    servicioListaPrecioJpaRepository.findByServicioIdServicio(idServicio).stream()
                            .map(ServicioListaPrecioJpa::getListaPrecio)
                            .filter(Objects::nonNull)
                            .filter(lp -> Boolean.TRUE.equals(lp.getStActivo()))
                            .collect(Collectors.toList()));
        } else {
            model.addAttribute("listas", List.of());
        }
    }

    @GetMapping("/list")
    @RequiresRole({ "ADMIN", "CLIENT" })
    public String list(Model model, HttpSession session) {
        Persona persona = (Persona) session.getAttribute("persona");
        if (isClient(persona)) {
            model.addAttribute("citas", citaService.findByPersonaId(persona.getId()));
        } else {
            model.addAttribute("citas", citaService.findAll());
        }
        model.addAttribute("activeTab", "historial");
        model.addAttribute("activeModule", "appointment"); // Ensure consistent with nav
        return "appointment/citas/list";
    }

    private boolean isClient(Persona persona) {
        return persona != null && persona.getUsuario().getIdRol() == 3; // 3 = Cliente
    }

    private boolean isAuthorized(HttpSession session, Integer citaId) {
        Persona cur = (Persona) session.getAttribute("persona");
        if (cur == null)
            return false;
        // 1 = Admin - can do anything
        if (cur.getUsuario().getIdRol() == 1)
            return true;

        // Clients can only access their own appointments
        return citaService.findById(citaId)
                .map(c -> c.getFkIdPersona().equals(cur.getId()))
                .orElse(false);
    }

    // Endpoint to fetch active listas filtered by servicio (used by AJAX from the
    // form)
    @GetMapping(path = "/listas-by-servicio", produces = "application/json")
    @ResponseBody
    public List<?> listasByServicio(@RequestParam Integer servicioId) {
        // find servicio-lista entries for this servicio
        List<ServicioListaPrecioJpa> sps = servicioListaPrecioJpaRepository.findByServicioIdServicio(servicioId);
        // for each, load listaPrecio and filter by estado activo (we assume estado id
        // for active is known or Estado has 'st_activo')
        return sps.stream()
                .map(ServicioListaPrecioJpa::getListaPrecio)
                .filter(Objects::nonNull)
                .filter(lp -> Boolean.TRUE.equals(lp.getStActivo()))
                .collect(Collectors.toList());
    }
}
