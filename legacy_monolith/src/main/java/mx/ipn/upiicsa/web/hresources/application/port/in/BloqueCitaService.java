package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.BloqueCitaJpa;
import java.util.List;

public interface BloqueCitaService {
    List<BloqueCitaJpa> findAll();

    BloqueCitaJpa save(BloqueCitaJpa bloqueCita);

    java.util.Optional<BloqueCitaJpa> findById(mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id);

    void deleteById(mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id);

    void deleteByCitaId(Integer citaId);
}
