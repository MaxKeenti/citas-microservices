package mx.ipn.upiicsa.web.accesscontrol.application.port.in;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;
import java.util.List;
import java.util.Optional;

public interface PersonaService {
    List<PersonaJpa> findAll();

    PersonaJpa save(PersonaJpa persona);

    Optional<PersonaJpa> findById(Integer id);

    void deleteById(Integer id);
}
