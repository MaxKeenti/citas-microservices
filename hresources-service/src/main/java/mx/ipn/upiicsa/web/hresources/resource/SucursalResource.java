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

import mx.ipn.upiicsa.web.hresources.model.Sucursal;

@Path("/branches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SucursalResource {

    @GET
    public List<Sucursal> list() {
        return Sucursal.listAll();
    }

    @GET
    @Path("/{id}")
    public Sucursal get(@PathParam("id") Integer id) {
        return Sucursal.findById(id);
    }

    @POST
    @Transactional
    public Response create(Sucursal obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Sucursal update(@PathParam("id") Integer id, Sucursal obj) {
        Sucursal entity = Sucursal.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.nombre = obj.nombre;
        entity.ubicacion = obj.ubicacion;
        entity.establecimiento = obj.establecimiento;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Sucursal.deleteById(id);
    }
}
