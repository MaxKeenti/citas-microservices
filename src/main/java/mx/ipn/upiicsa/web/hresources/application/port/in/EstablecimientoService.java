package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.EstablecimientoJpa;
import java.util.List;
import java.util.Optional;

public interface EstablecimientoService {
    List<EstablecimientoJpa> findAll();

    EstablecimientoJpa save(EstablecimientoJpa establecimiento);

    Optional<EstablecimientoJpa> findById(Integer id);

    void deleteById(Integer id);
}
