package mx.ipn.upiicsa.web.catalog.application.service;

import mx.ipn.upiicsa.web.catalog.application.port.in.ListaPrecioService;
import mx.ipn.upiicsa.web.catalog.application.port.out.ListaPrecioJpaRepository;
import mx.ipn.upiicsa.web.catalog.domain.ListaPrecioJpa;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaPrecioServiceImpl implements ListaPrecioService {

    @Autowired
    private ListaPrecioJpaRepository listaPrecioRepository;

    @Override
    public List<ListaPrecioJpa> findAll() {
        return listaPrecioRepository.findAll();
    }

    @Override
    public Optional<ListaPrecioJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return listaPrecioRepository.findById(id);
    }

    @Override
    public ListaPrecioJpa save(ListaPrecioJpa listaPrecio) {
        if (listaPrecio == null) {
            throw new IllegalArgumentException("listaPrecio cannot be null");
        }
        return listaPrecioRepository.save(listaPrecio);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        listaPrecioRepository.deleteById(id);
    }
}
