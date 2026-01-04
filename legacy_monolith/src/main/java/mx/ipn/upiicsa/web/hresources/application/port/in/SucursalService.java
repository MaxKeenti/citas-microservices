package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.SucursalJpa;
import java.util.List;

public interface SucursalService {
    List<SucursalJpa> findAll();

    SucursalJpa save(SucursalJpa sucursal);

    java.util.Optional<SucursalJpa> findById(Integer id);

    void deleteById(Integer id);
}
