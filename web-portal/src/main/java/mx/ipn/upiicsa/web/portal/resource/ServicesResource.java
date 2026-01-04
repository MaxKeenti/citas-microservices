package mx.ipn.upiicsa.web.portal.resource;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

import mx.ipn.upiicsa.web.portal.client.CatalogClient;
import mx.ipn.upiicsa.web.portal.model.Servicio;

@Path("/services")
public class ServicesResource {

    @Inject
    Template services; // maps to services.html

    @Inject
    @RestClient
    CatalogClient catalogClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        List<Servicio> list = catalogClient.getAll();
        return services.data("services", list);
    }
}
