package mx.ipn.upiicsa.web.hresources.resource;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import mx.ipn.upiicsa.web.hresources.model.DiaLaboral;

@Path("/work-days")
@Produces(MediaType.APPLICATION_JSON)
public class DiaLaboralResource {

    @GET
    public List<DiaLaboral> list() {
        return DiaLaboral.listAll();
    }
}
