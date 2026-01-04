package mx.ipn.upiicsa.web.catalog.resource;

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

import mx.ipn.upiicsa.web.catalog.model.Servicio;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicioResource {

    @GET
    public List<Servicio> list() {
        return Servicio.listAll();
    }

    @GET
    @Path("/{id}")
    public Servicio get(@PathParam("id") Integer id) {
        return Servicio.findById(id);
    }

    @POST
    @Transactional
    public Response create(Servicio service) {
        service.persist();
        return Response.status(Response.Status.CREATED).entity(service).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Servicio update(@PathParam("id") Integer id, Servicio service) {
        Servicio entity = Servicio.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.nombre = service.nombre;
        entity.descripcion = service.descripcion;
        entity.duracion = service.duracion;
        entity.activo = service.activo;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Servicio.deleteById(id);
    }
}
