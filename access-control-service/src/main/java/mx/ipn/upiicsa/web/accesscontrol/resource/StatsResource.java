package mx.ipn.upiicsa.web.accesscontrol.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import mx.ipn.upiicsa.web.accesscontrol.model.Usuario;
import java.util.HashMap;
import java.util.Map;

@Path("/stats")
@Produces(MediaType.APPLICATION_JSON)
public class StatsResource {

    @GET
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();

        // Total Users
        stats.put("totalUsers", Usuario.count());

        // Active Users (assuming st_activo = true)
        stats.put("activeUsers", Usuario.count("activo = true"));

        return stats;
    }
}
