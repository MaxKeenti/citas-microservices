package mx.ipn.upiicsa.web.appointment.resource;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import mx.ipn.upiicsa.web.appointment.model.Cita;
import mx.ipn.upiicsa.web.appointment.client.HResourcesClient;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @Inject
    @RestClient
    HResourcesClient hResourcesClient;

    @GET
    public List<Cita> list() {
        return Cita.listAll();
    }

    @GET
    @Path("/{id}")
    public Cita get(@PathParam("id") Integer id) {
        return Cita.findById(id);
    }

    @POST
    @Transactional
    public Response create(Cita obj) {
        // TODO: Validate availability via hResourcesClient
        obj.persist();
        // TODO: Notify hResources to blocking the slot?
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Cita update(@PathParam("id") Integer id, Cita obj) {
        Cita entity = Cita.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.fechaHora = obj.fechaHora;
        entity.duracion = obj.duracion;
        entity.idServicio = obj.idServicio;
        entity.idSucursal = obj.idSucursal;
        entity.idEmpleado = obj.idEmpleado;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Cita.deleteById(id);
    }
}
