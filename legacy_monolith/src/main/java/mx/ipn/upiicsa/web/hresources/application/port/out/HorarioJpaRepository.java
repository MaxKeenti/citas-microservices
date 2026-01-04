package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.HorarioJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioJpaRepository extends JpaRepository<HorarioJpa, Integer> {
}
