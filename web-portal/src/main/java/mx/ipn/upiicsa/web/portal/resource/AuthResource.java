package mx.ipn.upiicsa.web.portal.resource;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import mx.ipn.upiicsa.web.portal.client.AccessControlClient;
import mx.ipn.upiicsa.web.portal.model.LoginDto;
import mx.ipn.upiicsa.web.portal.model.Persona;

@Path("/auth")
public class AuthResource {

    @Inject
    Template login; // maps to login.html

    @Inject
    Template welcome; // reusing welcome.html or index? Let's use welcome

    @Inject
    @RestClient
    AccessControlClient accessControlClient;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginPage() {
        return login.data("error", null);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance doLogin(@FormParam("username") String username, @FormParam("password") String password) {
        LoginDto dto = new LoginDto();
        dto.username = username;
        dto.password = password;

        try {
            Persona persona = accessControlClient.login(dto);
            // In a real app, we would set a session cookie here.
            // For now, just render a welcome page with the user data.
            return welcome.data("user", persona);
        } catch (Exception e) {
            return login.data("error", "Invalid credentials");
        }
    }
}
