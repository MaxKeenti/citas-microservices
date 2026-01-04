package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.DiaLaboralJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaLaboralJpaRepository extends JpaRepository<DiaLaboralJpa, Integer> {
}
