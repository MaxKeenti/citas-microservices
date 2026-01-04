package mx.ipn.upiicsa.web.hresources.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.DiaDescansoService;
import mx.ipn.upiicsa.web.hresources.application.port.out.DiaDescansoJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.DiaDescansoJpa;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaDescansoServiceImpl implements DiaDescansoService {
    private final DiaDescansoJpaRepository diaDescansoJpaRepository;

    @Override
    public List<DiaDescansoJpa> findAll() {
        return diaDescansoJpaRepository.findAll();
    }

    @Override
    public DiaDescansoJpa save(DiaDescansoJpa diaDescanso) {
        if (diaDescanso == null) {
            throw new IllegalArgumentException("diaDescanso cannot be null");
        }
        DiaDescansoJpa saved = diaDescansoJpaRepository.save(diaDescanso);
        return saved;
    }

    @Override
    public java.util.Optional<DiaDescansoJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return diaDescansoJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        diaDescansoJpaRepository.deleteById(id);
    }
}
