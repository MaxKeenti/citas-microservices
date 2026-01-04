package mx.ipn.upiicsa.web.hresources.resource;

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

import mx.ipn.upiicsa.web.hresources.model.Establecimiento;

@Path("/establishments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstablecimientoResource {

    @GET
    public List<Establecimiento> list() {
        return Establecimiento.listAll();
    }

    @GET
    @Path("/{id}")
    public Establecimiento get(@PathParam("id") Integer id) {
        return Establecimiento.findById(id);
    }

    @POST
    @Transactional
    public Response create(Establecimiento obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Establecimiento update(@PathParam("id") Integer id, Establecimiento obj) {
        Establecimiento entity = Establecimiento.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.nombre = obj.nombre;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Establecimiento.deleteById(id);
    }
}
