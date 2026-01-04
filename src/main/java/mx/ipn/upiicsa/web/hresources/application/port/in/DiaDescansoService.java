package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.DiaDescansoJpa;
import java.util.List;

public interface DiaDescansoService {
    List<DiaDescansoJpa> findAll();

    DiaDescansoJpa save(DiaDescansoJpa diaDescanso);

    java.util.Optional<DiaDescansoJpa> findById(Integer id);

    void deleteById(Integer id);
}
