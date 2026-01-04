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
    public Response create(Usuario usuario) {
        // Logic to link with Persona?
        // Assuming payload has valid referencing IDs or nested object?
        // For simplicity now, expect ID to be set if linking to existing Persona, OR
        // new Persona.
        // But Usuario ID IS ForeignKey to Persona ID.
        // So we probably need to create Persona FIRST, then Usuario.
        // Or receive a composite DTO.
        // Legacy: UsuarioController created UsuarioJpa with manual ID setting from
        // Form.

        // This is a rough migration.
        if (usuario.persona != null && usuario.persona.id == null) {
            usuario.persona.persist();
            usuario.id = usuario.persona.id;
        } else if (usuario.id != null) {
            // Link to existing
        }

        usuario.persist();
        return Response.status(Response.Status.CREATED).entity(usuario).build();
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
