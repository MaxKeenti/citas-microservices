package mx.ipn.upiicsa.web.hresources.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.HorarioService;
import mx.ipn.upiicsa.web.hresources.application.port.out.HorarioJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.HorarioJpa;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {
    private final HorarioJpaRepository horarioJpaRepository;

    @Override
    public List<HorarioJpa> findAll() {
        return horarioJpaRepository.findAll();
    }

    @Override
    public HorarioJpa save(HorarioJpa horario) {
        if (horario == null) {
            throw new IllegalArgumentException("horario cannot be null");
        }
        HorarioJpa saved = horarioJpaRepository.save(horario);
        return saved;
    }

    @Override
    public java.util.Optional<HorarioJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return horarioJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        horarioJpaRepository.deleteById(id);
    }
}
