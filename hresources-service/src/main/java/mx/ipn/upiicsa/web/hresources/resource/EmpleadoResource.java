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
    @SuppressWarnings("null")
    public List<mx.ipn.upiicsa.web.hresources.dto.EmpleadoDto> list() {
        List<Empleado> employees = Empleado.listAll();
        return employees.stream().map(e -> {
            try {
                mx.ipn.upiicsa.web.hresources.dto.PersonaDto p = accessControlClient.getPersona(e.id);
                mx.ipn.upiicsa.web.hresources.dto.SucursalDto sDto = new mx.ipn.upiicsa.web.hresources.dto.SucursalDto(
                        e.sucursal.id, e.sucursal.nombre);
                return new mx.ipn.upiicsa.web.hresources.dto.EmpleadoDto(e.id, p.nombre, p.primerApellido,
                        p.segundoApellido, sDto);
            } catch (Exception ex) {
                // Fallback if user not found
                mx.ipn.upiicsa.web.hresources.dto.SucursalDto sDto = new mx.ipn.upiicsa.web.hresources.dto.SucursalDto(
                        e.sucursal.id, e.sucursal.nombre);
                return new mx.ipn.upiicsa.web.hresources.dto.EmpleadoDto(e.id, "Unknown", "Employee", "", sDto);
            }
        }).toList();
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

    @GET
    @Path("/{id}/horarios")
    @SuppressWarnings("null")
    public List<mx.ipn.upiicsa.web.hresources.dto.HorarioDto> getHorarios(@PathParam("id") Integer id) {
        List<mx.ipn.upiicsa.web.hresources.model.EmpleadoHorario> ehList = mx.ipn.upiicsa.web.hresources.model.EmpleadoHorario
                .list("idPersona", id);
        return ehList.stream().map(eh -> {
            mx.ipn.upiicsa.web.hresources.model.Horario h = eh.horario;
            return new mx.ipn.upiicsa.web.hresources.dto.HorarioDto(
                    h.id,
                    h.sucursal != null ? h.sucursal.id : null,
                    h.sucursal != null ? h.sucursal.nombre : null,
                    h.diaLaboral.id,
                    h.diaLaboral.nombre,
                    h.horaInicio,
                    h.horaFin);
        }).toList();
    }

    @POST
    @Path("/{id}/horarios")
    @Transactional
    public Response assignSchedule(@PathParam("id") Integer id,
            mx.ipn.upiicsa.web.hresources.dto.HorarioAssignmentDto dto) {
        // Check duplication
        long count = mx.ipn.upiicsa.web.hresources.model.EmpleadoHorario.count("idPersona = ?1 and idHorario = ?2", id,
                dto.idHorario);
        if (count > 0) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        mx.ipn.upiicsa.web.hresources.model.EmpleadoHorario eh = new mx.ipn.upiicsa.web.hresources.model.EmpleadoHorario();
        eh.idPersona = id;
        eh.idHorario = dto.idHorario;
        eh.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}/horarios/{idHorario}")
    @Transactional
    public void removeSchedule(@PathParam("id") Integer id, @PathParam("idHorario") Integer idHorario) {
        mx.ipn.upiicsa.web.hresources.model.EmpleadoHorario.delete("idPersona = ?1 and idHorario = ?2", id, idHorario);
    }
}
