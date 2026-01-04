package mx.ipn.upiicsa.web.hresources.application.service;

import mx.ipn.upiicsa.web.hresources.application.port.in.EstablecimientoService;
import mx.ipn.upiicsa.web.hresources.application.port.out.EstablecimientoJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.EstablecimientoJpa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstablecimientoServiceImpl implements EstablecimientoService {

    private final EstablecimientoJpaRepository repository;

    public EstablecimientoServiceImpl(EstablecimientoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EstablecimientoJpa> findAll() {
        return repository.findAll();
    }

    @Override
    public EstablecimientoJpa save(EstablecimientoJpa establecimiento) {
        return repository.save(java.util.Objects.requireNonNull(establecimiento));
    }

    @Override
    public Optional<EstablecimientoJpa> findById(Integer id) {
        return repository.findById(java.util.Objects.requireNonNull(id));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(java.util.Objects.requireNonNull(id));
    }
}
