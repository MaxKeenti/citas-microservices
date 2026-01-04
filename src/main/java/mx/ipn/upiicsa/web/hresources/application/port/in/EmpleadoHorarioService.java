package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioJpa;
import java.util.List;

public interface EmpleadoHorarioService {
    List<EmpleadoHorarioJpa> findAll();

    EmpleadoHorarioJpa save(EmpleadoHorarioJpa empleadoHorario);

    java.util.Optional<EmpleadoHorarioJpa> findById(mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioId id);

    void deleteById(mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioId id);
}
