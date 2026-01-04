package mx.ipn.upiicsa.web.hresources.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.EmpleadoHorarioService;
import mx.ipn.upiicsa.web.hresources.application.port.out.EmpleadoHorarioJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioJpa;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoHorarioServiceImpl implements EmpleadoHorarioService {
    private final EmpleadoHorarioJpaRepository empleadoHorarioJpaRepository;

    @Override
    public List<EmpleadoHorarioJpa> findAll() {
        return empleadoHorarioJpaRepository.findAll();
    }

    @Override
    public EmpleadoHorarioJpa save(EmpleadoHorarioJpa empleadoHorario) {
        if (empleadoHorario == null) {
            throw new IllegalArgumentException("empleadoHorario cannot be null");
        }
        EmpleadoHorarioJpa saved = empleadoHorarioJpaRepository.save(empleadoHorario);
        return saved;
    }

    @Override
    public java.util.Optional<EmpleadoHorarioJpa> findById(mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioId id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return empleadoHorarioJpaRepository.findById(id);
    }

    @Override
    public void deleteById(mx.ipn.upiicsa.web.hresources.domain.EmpleadoHorarioId id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        empleadoHorarioJpaRepository.deleteById(id);
    }
}
