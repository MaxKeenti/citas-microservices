package mx.ipn.upiicsa.web.appointment.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

// Stub for interacting with HResources service
@RegisterRestClient(configKey = "hresources-api")
@Path("/")
public interface HResourcesClient {
    @GET
    @Path("/ping")
    String ping();

    @GET
    @Path("/employees/{id}/horarios")
    List<mx.ipn.upiicsa.web.appointment.dto.HorarioDto> getHorarios(@PathParam("id") Integer id);
}
