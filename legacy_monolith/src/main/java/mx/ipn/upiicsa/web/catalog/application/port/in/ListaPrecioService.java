package mx.ipn.upiicsa.web.catalog.application.port.in;

import mx.ipn.upiicsa.web.catalog.domain.ListaPrecioJpa;
import java.util.List;
import java.util.Optional;

public interface ListaPrecioService {
    List<ListaPrecioJpa> findAll();

    Optional<ListaPrecioJpa> findById(Integer id);

    ListaPrecioJpa save(ListaPrecioJpa listaPrecio);

    void deleteById(Integer id);
}
