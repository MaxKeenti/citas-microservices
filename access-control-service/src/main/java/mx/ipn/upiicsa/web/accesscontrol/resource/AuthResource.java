package mx.ipn.upiicsa.web.accesscontrol.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.ipn.upiicsa.web.accesscontrol.dto.LoginDto;
import mx.ipn.upiicsa.web.accesscontrol.model.Usuario;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @POST
    @Path("/login")
    public Response login(LoginDto loginDto) {
        // Simple plain text password check as per legacy (assuming legacy wasn't
        // hashing yet or we migrate logic as is)
        // Ideally we should use BCrypt.
        Usuario usuario = Usuario.find("login = ?1 and password = ?2", loginDto.username, loginDto.password)
                .firstResult();

        if (usuario != null) {
            if (Boolean.TRUE.equals(usuario.activo)) {
                // Fetch full graph? Panache/Hibernate might be lazy.
                // We return the USER, but maybe the UI expects Persona?
                // Legacy LoginController returned Persona.
                // Let's return the related Persona with the User info.
                return Response.ok(usuario.persona).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("El usuario está inactivo").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Error, usuario y/o contraseña incorrectos")
                .build();
    }
}
