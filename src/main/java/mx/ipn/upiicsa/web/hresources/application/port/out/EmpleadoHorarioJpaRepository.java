package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioJpa;
import mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoHorarioJpaRepository extends JpaRepository<EmpleadoHorarioJpa, EmpleadoHorarioId> {
}
