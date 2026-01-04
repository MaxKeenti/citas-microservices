package mx.ipn.upiicsa.web.hresources.application.service;

import mx.ipn.upiicsa.web.hresources.application.port.in.EmpleadoService;
import mx.ipn.upiicsa.web.hresources.application.port.out.EmpleadoJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoJpaRepository empleadoRepository;

    @Override
    public List<EmpleadoJpa> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<EmpleadoJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return empleadoRepository.findById(id);
    }

    @Override
    public EmpleadoJpa save(EmpleadoJpa empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("empleado cannot be null");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        empleadoRepository.deleteById(id);
    }
}
