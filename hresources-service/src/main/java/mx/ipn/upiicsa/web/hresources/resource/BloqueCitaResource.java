package mx.ipn.upiicsa.web.hresources.resource;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import mx.ipn.upiicsa.web.hresources.model.BloqueCita;

@Path("/appointment-blocks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BloqueCitaResource {

    @GET
    public List<BloqueCita> list() {
        return BloqueCita.listAll();
    }

    @POST
    @Transactional
    public Response create(BloqueCita obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }
}
