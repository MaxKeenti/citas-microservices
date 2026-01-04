package mx.ipn.upiicsa.web.accesscontrol.application.port.in;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.UsuarioJpa;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioJpa> findAll();

    UsuarioJpa save(UsuarioJpa usuario);

    Optional<UsuarioJpa> findById(Integer id);

    void deleteById(Integer id);
}
