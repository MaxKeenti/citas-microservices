package mx.ipn.upiicsa.web.accesscontrol.application.service;

import lombok.RequiredArgsConstructor;
import mx.ipn.upiicsa.web.accesscontrol.application.port.in.UsuarioService;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.UsuarioJpa;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    @Override
    public List<UsuarioJpa> findAll() {
        return usuarioJpaRepository.findAll();
    }

    @Override
    public UsuarioJpa save(UsuarioJpa usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("usuario cannot be null");
        }
        return usuarioJpaRepository.save(usuario);
    }

    @Override
    public Optional<UsuarioJpa> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return usuarioJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        usuarioJpaRepository.deleteById(id);
    }
}
