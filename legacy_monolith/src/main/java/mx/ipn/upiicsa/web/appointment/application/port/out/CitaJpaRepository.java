package mx.ipn.upiicsa.web.appointment.application.port.out;

import mx.ipn.upiicsa.web.appointment.domain.CitaJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaJpaRepository extends JpaRepository<CitaJpa, Integer> {
    List<CitaJpa> findByFkIdPersona(Integer fkIdPersona);

    List<CitaJpa> findByFechaHoraBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    List<CitaJpa> findByFkIdEmpleadoAndFechaHoraBetween(Integer fkIdEmpleado, java.time.LocalDateTime start,
            java.time.LocalDateTime end);

}
