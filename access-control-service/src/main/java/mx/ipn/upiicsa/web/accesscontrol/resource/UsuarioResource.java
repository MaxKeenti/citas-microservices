package mx.ipn.upiicsa.web.accesscontrol.resource;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import mx.ipn.upiicsa.web.accesscontrol.model.Usuario;
import mx.ipn.upiicsa.web.accesscontrol.model.Persona;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @GET
    public List<Usuario> list() {
        return Usuario.listAll();
    }

    @GET
    @Path("/{id}")
    public Usuario get(@PathParam("id") Integer id) {
        return Usuario.findById(id);
    }

    @GET
    @Path("/persona/{idPersona}")
    public Persona getByPersonaId(@PathParam("idPersona") Integer idPersona) {
        return Persona.findById(idPersona);
    }

    @POST
    @Transactional
    public Response create(mx.ipn.upiicsa.web.accesscontrol.dto.UserCreationDto form) {
        // Check if user exists
        if (Usuario.count("login", form.username) > 0) {
            return Response.status(Response.Status.CONFLICT).entity("El nombre de usuario ya existe").build();
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
        u.idRol = form.idRol != null ? form.idRol : 3; // Default to Client
        u.login = form.username;
        u.password = mx.ipn.upiicsa.web.accesscontrol.util.PasswordEncoder.encode(form.password);
        u.activo = true;
        u.persist();

        return Response.status(Response.Status.CREATED).entity(p).build(); // Return Persona with ID
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Usuario update(@PathParam("id") Integer id, Usuario usuario) {
        Usuario entity = Usuario.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.login = usuario.login;
        entity.password = usuario.password;
        entity.activo = usuario.activo;
        entity.idRol = usuario.idRol;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Usuario.deleteById(id);
    }
}
