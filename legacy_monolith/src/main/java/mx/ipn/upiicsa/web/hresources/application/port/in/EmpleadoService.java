package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<EmpleadoJpa> findAll();

    Optional<EmpleadoJpa> findById(Integer id);

    EmpleadoJpa save(EmpleadoJpa empleado);

    void deleteById(Integer id);
}
