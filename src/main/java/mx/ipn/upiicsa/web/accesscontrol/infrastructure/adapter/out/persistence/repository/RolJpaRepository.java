package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.RolJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolJpaRepository extends JpaRepository<RolJpa, Integer> {
    java.util.Optional<RolJpa> findByNombre(String nombre);
}
