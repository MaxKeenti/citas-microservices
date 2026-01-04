package mx.ipn.upiicsa.web.hresources.resource;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import mx.ipn.upiicsa.web.hresources.model.DiaDescanso;

@Path("/days-off")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiaDescansoResource {

    @GET
    public List<DiaDescanso> list() {
        return DiaDescanso.listAll();
    }

    @GET
    @Path("/{id}")
    public DiaDescanso get(@PathParam("id") Integer id) {
        return DiaDescanso.findById(id);
    }

    @POST
    @Transactional
    public Response create(DiaDescanso obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        DiaDescanso.deleteById(id);
    }
}
