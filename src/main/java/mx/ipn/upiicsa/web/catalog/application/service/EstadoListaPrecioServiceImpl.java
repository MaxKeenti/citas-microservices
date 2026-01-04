package mx.ipn.upiicsa.web.catalog.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.catalog.application.port.in.EstadoListaPrecioService;
import mx.ipn.upiicsa.web.catalog.application.port.out.EstadoListaPrecioJpaRepository;
import mx.ipn.upiicsa.web.catalog.domain.EstadoListaPrecioJpa;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoListaPrecioServiceImpl implements EstadoListaPrecioService {
    private final EstadoListaPrecioJpaRepository estadoListaPrecioJpaRepository;

    @Override
    public List<EstadoListaPrecioJpa> findAll() {
        return estadoListaPrecioJpaRepository.findAll();
    }

    @Override
    public EstadoListaPrecioJpa save(EstadoListaPrecioJpa estadoListaPrecio) {
        if (estadoListaPrecio == null) {
            throw new IllegalArgumentException("estadoListaPrecio cannot be null");
        }
        EstadoListaPrecioJpa saved = estadoListaPrecioJpaRepository.save(estadoListaPrecio);
        return saved;
    }

    @Override
    public java.util.Optional<EstadoListaPrecioJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return estadoListaPrecioJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        estadoListaPrecioJpaRepository.deleteById(id);
    }
}
