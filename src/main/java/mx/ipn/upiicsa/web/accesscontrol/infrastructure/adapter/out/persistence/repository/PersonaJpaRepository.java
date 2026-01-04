package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJpaRepository extends JpaRepository<PersonaJpa,Integer> {
}
