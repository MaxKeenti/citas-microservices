package mx.ipn.upiicsa.web.appointment.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// Stub for interacting with HResources service
@RegisterRestClient(configKey = "hresources-api")
@Path("/")
public interface HResourcesClient {
    @GET
    @Path("/ping")
    String ping();
}
