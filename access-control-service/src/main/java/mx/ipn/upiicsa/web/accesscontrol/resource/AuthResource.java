package mx.ipn.upiicsa.web.accesscontrol.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.ipn.upiicsa.web.accesscontrol.dto.LoginDto;
import mx.ipn.upiicsa.web.accesscontrol.dto.RegistrationDto;
import mx.ipn.upiicsa.web.accesscontrol.model.Persona;
import mx.ipn.upiicsa.web.accesscontrol.model.Usuario;
import mx.ipn.upiicsa.web.accesscontrol.util.PasswordEncoder;

@Path("/auth")
public class AuthResource {

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDto loginDto) {
        // Find by login first
        Usuario usuario = Usuario.find("login", loginDto.username).firstResult();

        if (usuario != null) {
            // Check password hash
            // NOTE: Legacy data might be plain text if not migrated properly,
            // but we assume SHA-512 as per legacy RegistrationController.
            // If verification fails, we could try plain text as fallback (optional
            // migration strategy).
            boolean matches = PasswordEncoder.matches(loginDto.password, usuario.password);

            // Fallback for dev/initial seed (admin/password might be plain text in our
            // seed?)
            if (!matches && loginDto.password.equals(usuario.password)) {
                matches = true;
            }

            if (matches) {
                if (Boolean.TRUE.equals(usuario.activo)) {
                    return Response.ok(usuario.persona).build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("El usuario está inactivo").build();
                }
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Error, usuario y/o contraseña incorrectos")
                .build();
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(RegistrationDto form) {
        // Basic validations
        if (form.password == null || !form.password.equals(form.confirmPassword)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Las contraseñas no coinciden").build();
        }

        // Check if user exists
        if (Usuario.count("login", form.username) > 0) {
            return Response.status(Response.Status.CONFLICT).entity("El nombre de usuario ya existe").build();
        }

        // Validate Email
        if (!form.username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El nombre de usuario debe ser un correo electrónico válido").build();
        }

        // Validate Password Complexity
        if (form.password.length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST).entity("La contraseña debe tener al menos 8 caracteres")
                    .build();
        }
        if (!form.password.matches(".*[0-9].*")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("La contraseña debe tener al menos un número")
                    .build();
        }
        if (!form.password.matches(".*[^A-Za-z0-9].*")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La contraseña debe tener al menos un carácter especial").build();
        }

        // Create Persona
        Persona p = new Persona();
        p.idGenero = form.idGenero;
        p.nombre = form.nombre;
        p.primerApellido = form.primerApellido;
        p.segundoApellido = form.segundoApellido;
        p.fechaNacimiento = form.fechaNacimiento;
        p.persist(); // Id generated

        // Create Usuario
        Usuario u = new Usuario();
        u.id = p.id; // Shared PK
        u.idRol = 3; // CLIENT role
        u.login = form.username;
        u.password = PasswordEncoder.encode(form.password);
        u.activo = true;
        u.persist();

        return Response.ok(p).build();
    }

    @jakarta.ws.rs.PUT
    @Path("/profile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateProfile(mx.ipn.upiicsa.web.accesscontrol.dto.ProfileUpdateDto dto) {
        if (dto.id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID required").build();
        }

        Persona persona = Persona.findById(dto.id);
        if (persona == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        persona.nombre = dto.nombre;
        persona.primerApellido = dto.primerApellido;
        persona.segundoApellido = dto.segundoApellido;
        persona.fechaNacimiento = dto.fechaNacimiento;
        persona.idGenero = dto.idGenero;

        persona.persist(); // Not strictly needed inside transaction but good for clarity

        return Response.ok(persona).build();
    }
}
