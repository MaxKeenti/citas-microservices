package mx.ipn.upiicsa.web.hresources.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.BloqueCitaService;
import mx.ipn.upiicsa.web.hresources.application.port.out.BloqueCitaJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.BloqueCitaJpa;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BloqueCitaServiceImpl implements BloqueCitaService {
    private final BloqueCitaJpaRepository bloqueCitaJpaRepository;

    @Override
    public List<BloqueCitaJpa> findAll() {
        return bloqueCitaJpaRepository.findAll();
    }

    @Override
    public BloqueCitaJpa save(BloqueCitaJpa bloqueCita) {
        if (bloqueCita == null) {
            throw new IllegalArgumentException("bloqueCita cannot be null");
        }
        BloqueCitaJpa saved = bloqueCitaJpaRepository.save(bloqueCita);
        return saved;
    }

    @Override
    public java.util.Optional<BloqueCitaJpa> findById(mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return bloqueCitaJpaRepository.findById(id);
    }

    @Override
    public void deleteById(mx.ipn.upiicsa.web.hresources.domain.BloqueCitaId id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        bloqueCitaJpaRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteByCitaId(Integer citaId) {
        if (citaId == null) {
            throw new IllegalArgumentException("citaId cannot be null");
        }
        bloqueCitaJpaRepository.deleteByIdCita(citaId);
    }
}
