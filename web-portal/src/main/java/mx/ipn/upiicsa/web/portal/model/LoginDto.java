package mx.ipn.upiicsa.web.portal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LoginDto {
    public String username;
    public String password;
}
