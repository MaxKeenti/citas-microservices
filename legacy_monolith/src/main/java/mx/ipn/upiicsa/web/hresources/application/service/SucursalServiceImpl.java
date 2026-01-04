package mx.ipn.upiicsa.web.hresources.application.service;

import mx.ipn.upiicsa.web.hresources.application.port.in.SucursalService;
import mx.ipn.upiicsa.web.hresources.application.port.out.SucursalJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.SucursalJpa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@lombok.RequiredArgsConstructor
public class SucursalServiceImpl implements SucursalService {

    private final SucursalJpaRepository sucursalJpaRepository;

    @Override
    public List<SucursalJpa> findAll() {
        return sucursalJpaRepository.findAll();
    }

    @Override
    public SucursalJpa save(SucursalJpa sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("sucursal cannot be null");
        }
        SucursalJpa saved = sucursalJpaRepository.save(sucursal);
        return saved;
    }

    @Override
    public java.util.Optional<SucursalJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return sucursalJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        sucursalJpaRepository.deleteById(id);
    }
}
