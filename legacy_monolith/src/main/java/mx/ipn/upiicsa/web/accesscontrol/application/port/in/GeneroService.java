package mx.ipn.upiicsa.web.accesscontrol.application.port.in;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.GeneroJpa;
import java.util.List;
import java.util.Optional;

public interface GeneroService {
    List<GeneroJpa> findAll();

    GeneroJpa save(GeneroJpa genero);

    Optional<GeneroJpa> findById(Integer id);

    void deleteById(Integer id);
}
