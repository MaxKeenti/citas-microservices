package mx.ipn.upiicsa.web.catalog.application.port.in;

import mx.ipn.upiicsa.web.catalog.domain.EstadoListaPrecioJpa;
import java.util.List;

public interface EstadoListaPrecioService {
    List<EstadoListaPrecioJpa> findAll();

    EstadoListaPrecioJpa save(EstadoListaPrecioJpa estadoListaPrecio);

    java.util.Optional<EstadoListaPrecioJpa> findById(Integer id);

    void deleteById(Integer id);
}
