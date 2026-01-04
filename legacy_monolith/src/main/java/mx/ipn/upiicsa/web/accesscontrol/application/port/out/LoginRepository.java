package mx.ipn.upiicsa.web.accesscontrol.application.port.out;

import mx.ipn.upiicsa.web.accesscontrol.domain.Persona;

import java.util.Optional;

public interface LoginRepository {
    Optional<Persona> findByLoginAndPassword(String login, String password);
}
