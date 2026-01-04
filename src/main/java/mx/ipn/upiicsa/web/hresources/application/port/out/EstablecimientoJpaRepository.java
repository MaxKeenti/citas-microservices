package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.EstablecimientoJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablecimientoJpaRepository extends JpaRepository<EstablecimientoJpa,Integer> {
}
