package mx.ipn.upiicsa.web.accesscontrol.application.port.in;

import io.vavr.control.Either;
import mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.in.web.dto.LoginDto;
import mx.ipn.upiicsa.web.accesscontrol.domain.Persona;

public interface LoginService {
    Either<Integer, Persona> login(LoginDto login);
}
