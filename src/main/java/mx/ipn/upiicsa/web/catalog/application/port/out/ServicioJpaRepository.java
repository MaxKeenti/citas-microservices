package mx.ipn.upiicsa.web.catalog.application.port.out;

import mx.ipn.upiicsa.web.catalog.domain.ServicioJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioJpaRepository extends JpaRepository<ServicioJpa,Integer> {
}
