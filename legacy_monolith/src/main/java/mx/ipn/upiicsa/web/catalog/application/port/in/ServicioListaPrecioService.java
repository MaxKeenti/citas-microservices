package mx.ipn.upiicsa.web.catalog.application.port.in;

import mx.ipn.upiicsa.web.catalog.domain.ServicioListaPrecioJpa;
import java.util.List;
import java.util.Optional;

public interface ServicioListaPrecioService {
    List<ServicioListaPrecioJpa> findAll();

    Optional<ServicioListaPrecioJpa> findById(Integer id);

    ServicioListaPrecioJpa save(ServicioListaPrecioJpa servicioListaPrecio);

    void deleteById(Integer id);
}
