package mx.ipn.upiicsa.web.appointment.application.port.in;

import mx.ipn.upiicsa.web.appointment.domain.CitaJpa;
import java.util.List;

public interface CitaService {
    List<CitaJpa> findAll();

    CitaJpa save(CitaJpa cita);

    java.util.Optional<CitaJpa> findById(Integer id);

    void deleteById(Integer id);

    List<CitaJpa> findByPersonaId(Integer idPersona);

    List<CitaJpa> findByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end);

}
