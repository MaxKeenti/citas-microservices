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

import mx.ipn.upiicsa.web.hresources.model.Horario;

@Path("/schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HorarioResource {

    @GET
    @SuppressWarnings("null")
    public List<mx.ipn.upiicsa.web.hresources.dto.HorarioDto> list() {
        List<Horario> horarios = Horario.listAll();
        return horarios.stream().map(h -> new mx.ipn.upiicsa.web.hresources.dto.HorarioDto(
                h.id,
                h.sucursal != null ? h.sucursal.id : null,
                h.sucursal != null ? h.sucursal.nombre : null, // Assuming HorarioDto has simple fields, checking
                                                               // constructor
                h.diaLaboral.id,
                h.diaLaboral.nombre,
                h.horaInicio,
                h.horaFin)).toList();
    }

    @GET
    @Path("/{id}")
    @SuppressWarnings("null")
    public mx.ipn.upiicsa.web.hresources.dto.HorarioDto get(@PathParam("id") Integer id) {
        Horario h = Horario.findById(id);
        if (h == null)
            return null;
        return new mx.ipn.upiicsa.web.hresources.dto.HorarioDto(
                h.id,
                h.sucursal != null ? h.sucursal.id : null,
                h.sucursal != null ? h.sucursal.nombre : null,
                h.diaLaboral.id,
                h.diaLaboral.nombre,
                h.horaInicio,
                h.horaFin);
    }

    @POST
    @Transactional
    public Response create(Horario obj) {
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Horario update(@PathParam("id") Integer id, Horario obj) {
        Horario entity = Horario.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.horaInicio = obj.horaInicio;
        entity.horaFin = obj.horaFin;
        entity.diaLaboral = obj.diaLaboral;
        entity.sucursal = obj.sucursal;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Horario.deleteById(id);
    }
}
