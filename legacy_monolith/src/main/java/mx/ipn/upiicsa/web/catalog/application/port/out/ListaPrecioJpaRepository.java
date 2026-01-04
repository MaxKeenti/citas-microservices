package mx.ipn.upiicsa.web.catalog.application.port.out;

import mx.ipn.upiicsa.web.catalog.domain.ListaPrecioJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaPrecioJpaRepository extends JpaRepository<ListaPrecioJpa,Integer> {
}
