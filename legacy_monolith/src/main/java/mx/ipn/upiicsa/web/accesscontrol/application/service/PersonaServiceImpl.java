package mx.ipn.upiicsa.web.accesscontrol.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.accesscontrol.application.port.in.PersonaService;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.PersonaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {

    private final PersonaJpaRepository personaJpaRepository;

    @Override
    public List<PersonaJpa> findAll() {
        return personaJpaRepository.findAll();
    }

    @Override
    public PersonaJpa save(PersonaJpa persona) {
        if (persona == null) {
            throw new IllegalArgumentException("persona cannot be null");
        }
        return personaJpaRepository.save(persona);
    }

    @Override
    public Optional<PersonaJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return personaJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        personaJpaRepository.deleteById(id);
    }
}
