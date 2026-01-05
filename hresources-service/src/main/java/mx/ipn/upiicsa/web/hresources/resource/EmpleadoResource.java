package mx.ipn.upiicsa.web.hresources.resource;

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

import mx.ipn.upiicsa.web.hresources.model.Empleado;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.ipn.upiicsa.web.hresources.client.AccessControlClient;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpleadoResource {

    @Inject
    @RestClient
    AccessControlClient accessControlClient;

    @GET
    public List<mx.ipn.upiicsa.web.hresources.dto.EmpleadoDto> list() {
        List<Empleado> employees = Empleado.listAll();
        return employees.stream().map(e -> {
            try {
                mx.ipn.upiicsa.web.hresources.dto.PersonaDto p = accessControlClient.getPersona(e.id);
                return new mx.ipn.upiicsa.web.hresources.dto.EmpleadoDto(e.id, p.nombre, p.primerApellido,
                        p.segundoApellido, e.sucursal);
            } catch (Exception ex) {
                // Fallback if user not found
                return new mx.ipn.upiicsa.web.hresources.dto.EmpleadoDto(e.id, "Unknown", "Employee", "", e.sucursal);
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Empleado get(@PathParam("id") Integer id) {
        return Empleado.findById(id);
    }

    @POST
    @Transactional
    public Response create(Empleado obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Empleado update(@PathParam("id") Integer id, Empleado obj) {
        Empleado entity = Empleado.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.sucursal = obj.sucursal;
        // id is PK and foreign key to Persona, likely immutable effectively or managed
        // by creation
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Empleado.deleteById(id);
    }
}
