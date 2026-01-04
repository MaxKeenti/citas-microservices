package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoJpaRepository extends JpaRepository<EmpleadoJpa,Integer> {
}
