package mx.ipn.upiicsa.web.catalog.application.port.out;

import mx.ipn.upiicsa.web.catalog.domain.ServicioListaPrecioJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicioListaPrecioJpaRepository extends JpaRepository<ServicioListaPrecioJpa, Integer> {
    List<ServicioListaPrecioJpa> findByServicioIdServicio(Integer idServicio);
}
