package mx.ipn.upiicsa.web.hresources.application.port.out;

import mx.ipn.upiicsa.web.hresources.domain.BloqueCitaJpa;
import mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloqueCitaJpaRepository extends JpaRepository<BloqueCitaJpa, BloqueCitaId> {
    void deleteByIdCita(Integer idCita);
}
