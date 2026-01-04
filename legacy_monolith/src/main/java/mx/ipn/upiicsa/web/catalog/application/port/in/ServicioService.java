package mx.ipn.upiicsa.web.catalog.application.port.in;

import mx.ipn.upiicsa.web.catalog.domain.ServicioJpa;
import java.util.List;

public interface ServicioService {
    List<ServicioJpa> findAll();

    ServicioJpa save(ServicioJpa servicio);

    java.util.Optional<ServicioJpa> findById(Integer id);

    void deleteById(Integer id);
}
