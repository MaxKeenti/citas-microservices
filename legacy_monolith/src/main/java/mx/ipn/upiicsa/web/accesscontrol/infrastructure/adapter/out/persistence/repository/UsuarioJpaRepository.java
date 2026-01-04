package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.UsuarioJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, Integer> {
    java.util.List<UsuarioJpa> findByLoginAndPassword(String login, String password);
}
