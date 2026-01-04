package mx.ipn.upiicsa.web.catalog.application.service;

import mx.ipn.upiicsa.web.catalog.application.port.in.ServicioService;
import mx.ipn.upiicsa.web.catalog.application.port.out.ServicioJpaRepository;
import mx.ipn.upiicsa.web.catalog.domain.ServicioJpa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@lombok.RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioJpaRepository servicioJpaRepository;

    @Override
    public List<ServicioJpa> findAll() {
        return servicioJpaRepository.findAll();
    }

    @Override
    public ServicioJpa save(ServicioJpa servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("servicio cannot be null");
        }
        ServicioJpa saved = servicioJpaRepository.save(servicio);
        return saved;
    }

    @Override
    public java.util.Optional<ServicioJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return servicioJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        servicioJpaRepository.deleteById(id);
    }
}
