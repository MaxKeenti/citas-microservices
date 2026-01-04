package mx.ipn.upiicsa.web.appointment.infrastructure.adapter.in.web.controller;

import mx.ipn.upiicsa.web.accesscontrol.domain.Persona;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.PersonaJpaRepository;
import mx.ipn.upiicsa.web.appointment.application.port.in.CitaService;
import mx.ipn.upiicsa.web.appointment.domain.CitaJpa;

import mx.ipn.upiicsa.web.catalog.application.port.out.ServicioJpaRepository;
import mx.ipn.upiicsa.web.hresources.application.port.out.EmpleadoJpaRepository;
import mx.ipn.upiicsa.web.hresources.application.port.out.SucursalJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.RequiresRole;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agenda")
@lombok.RequiredArgsConstructor
public class AgendaController {

    private final CitaService citaService;
    private final ServicioJpaRepository servicioJpaRepository;
    private final SucursalJpaRepository sucursalJpaRepository;
    private final EmpleadoJpaRepository empleadoJpaRepository;
    private final PersonaJpaRepository personaJpaRepository;

    @GetMapping
    @RequiresRole({ "ADMIN", "CLIENT", "EMPLOYEE" })
    public String viewAgenda(Model model, HttpSession session) {

        Persona persona = (Persona) session.getAttribute("persona");
        if (persona == null) {
            return "redirect:/login";
        }

        model.addAttribute("activeModule", "citas");
        model.addAttribute("activeTab", "agenda");

        // Load data meant for the modal
        model.addAttribute("servicios", servicioJpaRepository.findAll());
        model.addAttribute("sucursales", sucursalJpaRepository.findAll());
        model.addAttribute("empleados", empleadoJpaRepository.findAll());

        // If user is Admin, they can select who the appointment is for
        if (persona.getUsuario().getIdRol() == 1) { // 1 = Admin
            model.addAttribute("personas", personaJpaRepository.findAll());
        } else {
            model.addAttribute("personas", List.of(persona));
        }

        return "appointment/agenda/index";
    }

    @GetMapping("/data")
    @ResponseBody
    @RequiresRole({ "ADMIN", "CLIENT", "EMPLOYEE" })
    public List<?> getAgendaData(@RequestParam String date, HttpSession session) {

        Persona currentUser = (Persona) session.getAttribute("persona");
        if (currentUser == null)
            return List.of();

        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

        List<CitaJpa> citas = citaService.findByDateRange(startOfDay, endOfDay);

        return citas.stream().map(c -> {
            boolean isOwner = currentUser.getId().equals(c.getFkIdPersona());
            boolean isAdmin = currentUser.getUsuario().getIdRol() == 1;
            boolean canViewDetails = isOwner || isAdmin;

            return Map.of(
                    "id", c.getIdCita(),
                    "start", c.getFechaHora(),
                    "duration",
                    c.getCustomDuration() != null ? c.getCustomDuration()
                            : (c.getServicio() != null ? c.getServicio().getNuDuracion() : 30),
                    // null
                    "title",
                    canViewDetails ? (c.getServicio() != null ? c.getServicio().getTxNombre() : "Cita") : "Ocupado",
                    "isOwner", isOwner,
                    "canViewDetails", canViewDetails);
        }).collect(Collectors.toList());
    }
}
