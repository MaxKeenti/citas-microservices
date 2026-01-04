package mx.ipn.upiicsa.web.catalog.application.service;

import mx.ipn.upiicsa.web.catalog.application.port.in.ServicioListaPrecioService;
import mx.ipn.upiicsa.web.catalog.application.port.out.ServicioListaPrecioJpaRepository;
import mx.ipn.upiicsa.web.catalog.domain.ServicioListaPrecioJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioListaPrecioServiceImpl implements ServicioListaPrecioService {

    @Autowired
    private ServicioListaPrecioJpaRepository repository;

    @Override
    public List<ServicioListaPrecioJpa> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ServicioListaPrecioJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return repository.findById(id);
    }

    @Override
    public ServicioListaPrecioJpa save(ServicioListaPrecioJpa servicioListaPrecio) {
        if (servicioListaPrecio == null) {
            throw new IllegalArgumentException("servicioListaPrecio cannot be null");
        }
        return repository.save(servicioListaPrecio);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        repository.deleteById(id);
    }
}
