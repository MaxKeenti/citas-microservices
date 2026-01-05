package mx.ipn.upiicsa.web.accesscontrol.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LoginDto {
    public String username;
    public String password;

    public LoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
