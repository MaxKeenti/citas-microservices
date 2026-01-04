package mx.ipn.upiicsa.web.portal.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import mx.ipn.upiicsa.web.portal.model.LoginDto;
import mx.ipn.upiicsa.web.portal.model.Persona;

@RegisterRestClient(configKey = "access-control-api")
@Path("/auth")
public interface AccessControlClient {

    @POST
    @Path("/login")
    Persona login(LoginDto loginDto);
}
