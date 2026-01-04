package mx.ipn.upiicsa.web.accesscontrol.resource;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import mx.ipn.upiicsa.web.accesscontrol.model.Rol;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
public class RolResource {

    @GET
    public List<Rol> list() {
        return Rol.listAll();
    }
}
