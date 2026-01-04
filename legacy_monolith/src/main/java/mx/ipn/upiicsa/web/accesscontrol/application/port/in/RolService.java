package mx.ipn.upiicsa.web.accesscontrol.application.port.in;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.RolJpa;
import java.util.List;
import java.util.Optional;

public interface RolService {
    List<RolJpa> findAll();

    RolJpa save(RolJpa rol);

    Optional<RolJpa> findById(Integer id);

    void deleteById(Integer id);
}
