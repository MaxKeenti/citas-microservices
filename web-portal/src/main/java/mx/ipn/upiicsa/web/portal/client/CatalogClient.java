package mx.ipn.upiicsa.web.portal.client;

import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import mx.ipn.upiicsa.web.portal.model.Servicio;

@RegisterRestClient(configKey = "catalog-api")
@Path("/services")
public interface CatalogClient {

    @GET
    List<Servicio> getAll();
}
