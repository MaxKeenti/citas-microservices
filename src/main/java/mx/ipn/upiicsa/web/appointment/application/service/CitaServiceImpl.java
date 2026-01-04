package mx.ipn.upiicsa.web.appointment.application.service;

import mx.ipn.upiicsa.web.appointment.application.port.in.CitaService;
import mx.ipn.upiicsa.web.appointment.application.port.out.CitaJpaRepository;
import mx.ipn.upiicsa.web.appointment.domain.CitaJpa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@lombok.RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaJpaRepository citaJpaRepository;
    private final mx.ipn.upiicsa.web.hresources.application.port.in.BloqueCitaService bloqueCitaService;
    private final mx.ipn.upiicsa.web.catalog.application.port.out.ServicioJpaRepository servicioJpaRepository;

    @Override
    public List<CitaJpa> findAll() {
        return citaJpaRepository.findAll();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public CitaJpa save(CitaJpa cita) {
        if (cita == null) {
            throw new IllegalArgumentException("cita cannot be null");
        }

        // 1. Calculate Duration early for validation
        Integer duration = cita.getCustomDuration();
        if (duration == null) {
            Integer serviceId = cita.getFkIdServicio();
            if (serviceId != null) {
                var servicio = servicioJpaRepository.findById(serviceId).orElse(null);
                if (servicio != null) {
                    duration = servicio.getNuDuracion();
                } else {
                    duration = 30; // Fallback
                }
            } else {
                duration = 30; // Fallback
            }
        }
        // Ensure duration is set on entity if it was calculated but not set (optional
        // but good)
        // actually we just use 'duration' var for logic

        java.time.LocalDateTime newStart = cita.getFechaHora();
        if (newStart == null) {
            throw new IllegalArgumentException("La fecha y hora de la cita son obligatorias.");
        }
        java.time.LocalDateTime newEnd = newStart.plusMinutes(duration);

        // 2. Validate Overlaps
        // Fetch all appointments for that employee on that day
        java.time.LocalDateTime dayStart = newStart.toLocalDate().atStartOfDay();
        java.time.LocalDateTime dayEnd = newStart.toLocalDate().atTime(23, 59, 59);

        List<CitaJpa> existingCitas = citaJpaRepository.findByFkIdEmpleadoAndFechaHoraBetween(cita.getFkIdEmpleado(),
                dayStart, dayEnd);

        for (CitaJpa existing : existingCitas) {
            // Skip self (for updates)
            if (existing.getIdCita() != null && existing.getIdCita().equals(cita.getIdCita()))
                continue;

            // Calculate existing duration/end
            Integer existingDuration = existing.getCustomDuration() != null ? existing.getCustomDuration()
                    : (existing.getServicio() != null ? existing.getServicio().getNuDuracion() : 30);
            java.time.LocalDateTime existingStart = existing.getFechaHora();
            java.time.LocalDateTime existingEnd = existingStart.plusMinutes(existingDuration);

            // Check overlap: start < existingEnd && end > existingStart
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                throw new IllegalStateException("El horario seleccionado ya está ocupado por otra cita.");
            }
        }

        // 3. Save
        CitaJpa savedCita = citaJpaRepository.save(cita);

        // 4. Create BloqueCita entries
        // If update, clear old blocks first
        if (cita.getIdCita() != null) {
            bloqueCitaService.deleteByCitaId(cita.getIdCita());
        }

        int blocks = (int) Math.ceil((double) duration / 30);
        java.time.LocalDateTime startTime = savedCita.getFechaHora(); // Use saved time

        for (int i = 0; i < blocks; i++) {
            java.time.LocalDateTime blockStart = startTime.plusMinutes(i * 30);
            java.time.LocalDateTime blockEnd = blockStart.plusMinutes(30);

            mx.ipn.upiicsa.web.hresources.domain.BloqueCitaJpa bloque = new mx.ipn.upiicsa.web.hresources.domain.BloqueCitaJpa();
            bloque.setIdSucursal(savedCita.getFkIdSucursal());
            bloque.setIdCita(savedCita.getIdCita());
            bloque.setFechaInicio(blockStart);
            bloque.setFechaFin(blockEnd);
            // Empleado ID missing in BloqueCitaJpa logic? Original code didn't set it.
            // Let's check BloqueCitaJpa definition later, but adhering to original logic
            // for now.

            bloqueCitaService.save(bloque);
        }

        return savedCita;
    }

    @Override
    public java.util.Optional<CitaJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return citaJpaRepository.findById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        // First delete associated blocks
        bloqueCitaService.deleteByCitaId(id);

        citaJpaRepository.deleteById(id);
    }

    @Override
    public List<CitaJpa> findByPersonaId(Integer idPersona) {
        if (idPersona == null) {
            throw new IllegalArgumentException("idPersona cannot be null");
        }
        return citaJpaRepository.findByFkIdPersona(idPersona);
    }

    @Override
    public List<CitaJpa> findByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        return citaJpaRepository.findByFechaHoraBetween(start, end);
    }

}
