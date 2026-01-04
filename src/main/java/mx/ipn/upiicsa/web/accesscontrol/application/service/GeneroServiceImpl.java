package mx.ipn.upiicsa.web.accesscontrol.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.accesscontrol.application.port.in.GeneroService;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.GeneroJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.GeneroJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneroServiceImpl implements GeneroService {

    private final GeneroJpaRepository generoJpaRepository;

    @Override
    public List<GeneroJpa> findAll() {
        return generoJpaRepository.findAll();
    }

    @Override
    public GeneroJpa save(GeneroJpa genero) {
        if (genero == null) {
            throw new IllegalArgumentException("genero cannot be null");
        }
        return generoJpaRepository.save(genero);
    }

    @Override
    public Optional<GeneroJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return generoJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        generoJpaRepository.deleteById(id);
    }
}
