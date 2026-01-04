package mx.ipn.upiicsa.web.hresources.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.hresources.application.port.in.DiaLaboralService;
import mx.ipn.upiicsa.web.hresources.application.port.out.DiaLaboralJpaRepository;
import mx.ipn.upiicsa.web.hresources.domain.DiaLaboralJpa;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaLaboralServiceImpl implements DiaLaboralService {
    private final DiaLaboralJpaRepository diaLaboralJpaRepository;

    @Override
    public List<DiaLaboralJpa> findAll() {
        return diaLaboralJpaRepository.findAll();
    }

    @Override
    public DiaLaboralJpa save(DiaLaboralJpa diaLaboral) {
        if (diaLaboral == null) {
            throw new IllegalArgumentException("diaLaboral cannot be null");
        }
        DiaLaboralJpa saved = diaLaboralJpaRepository.save(diaLaboral);
        return saved;
    }

    @Override
    public java.util.Optional<DiaLaboralJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return diaLaboralJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        diaLaboralJpaRepository.deleteById(id);
    }
}
