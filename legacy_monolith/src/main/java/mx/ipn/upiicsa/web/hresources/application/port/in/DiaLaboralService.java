package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.DiaLaboralJpa;
import java.util.List;

public interface DiaLaboralService {
    List<DiaLaboralJpa> findAll();

    DiaLaboralJpa save(DiaLaboralJpa diaLaboral);

    java.util.Optional<DiaLaboralJpa> findById(Integer id);

    void deleteById(Integer id);
}
