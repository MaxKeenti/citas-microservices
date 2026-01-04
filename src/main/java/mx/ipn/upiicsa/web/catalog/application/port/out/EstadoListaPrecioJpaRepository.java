package mx.ipn.upiicsa.web.catalog.application.port.out;

import mx.ipn.upiicsa.web.catalog.domain.EstadoListaPrecioJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoListaPrecioJpaRepository extends JpaRepository<EstadoListaPrecioJpa, Integer> {
}
