package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.dao;

import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;
import mx.ipn.upiicsa.web.accesscontrol.application.port.out.LoginRepository;
import mx.ipn.upiicsa.web.accesscontrol.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j

@Repository
public class LoginDao implements LoginRepository {
    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    public Optional<Persona> findByLoginAndPassword(String login, String password) {
        var resultado = usuarioJpaRepository.findByLoginAndPassword(login, password);
        if (!resultado.isEmpty()) {
            if (resultado.size() > 1) {
                log.warn("Found {} users with login '{}'. Using the first one.", resultado.size(), login);
            }
            var usuarioJpa = resultado.get(0);
            var persona = usuarioJpa.getPersona().toEntity();
            persona.setUsuario(usuarioJpa.toEntity());
            return Optional.of(persona);
        } else {
            return Optional.empty();
        }
    }
}
