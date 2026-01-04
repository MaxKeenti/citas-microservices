package mx.ipn.upiicsa.web.accesscontrol.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.accesscontrol.application.port.in.RolService;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.RolJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.RolJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolJpaRepository rolJpaRepository;

    @Override
    public List<RolJpa> findAll() {
        return rolJpaRepository.findAll();
    }

    @Override
    public RolJpa save(RolJpa rol) {
        if (rol == null) {
            throw new IllegalArgumentException("rol cannot be null");
        }
        return rolJpaRepository.save(rol);
    }

    @Override
    public Optional<RolJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return rolJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        rolJpaRepository.deleteById(id);
    }
}
