package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.SucursalJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalJpaRepository extends JpaRepository<SucursalJpa,Integer> {
}
