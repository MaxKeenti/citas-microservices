package mx.ipn.upiicsa.web.hresources.application.port.in;

import mx.ipn.upiicsa.web.hresources.domain.HorarioJpa;
import java.util.List;

public interface HorarioService {
    List<HorarioJpa> findAll();

    HorarioJpa save(HorarioJpa horario);

    java.util.Optional<HorarioJpa> findById(Integer id);

    void deleteById(Integer id);
}
