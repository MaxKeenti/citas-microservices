package mx.ipn.upiicsa.web.hresources.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import mx.ipn.upiicsa.web.hresources.dto.PersonaDto;

@Path("/users")
@RegisterRestClient(configKey = "access-control-api")
public interface AccessControlClient {

    @GET
    @Path("/persona/{id}")
    PersonaDto getPersona(@PathParam("id") Integer id);
}
