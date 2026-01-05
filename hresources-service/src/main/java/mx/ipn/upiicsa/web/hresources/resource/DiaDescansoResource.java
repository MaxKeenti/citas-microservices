package mx.ipn.upiicsa.web.hresources.resource;

import java.util.List;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import mx.ipn.upiicsa.web.hresources.model.DiaDescanso;
import mx.ipn.upiicsa.web.hresources.model.Empleado;
import mx.ipn.upiicsa.web.hresources.dto.DiaDescansoDto;

@Path("/days-off")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiaDescansoResource {

    @GET
    @Path("/employee/{id}")
    public List<DiaDescansoDto> getByEmployee(@PathParam("id") Integer id) {
        List<DiaDescanso> list = DiaDescanso.list("empleado.id", id);
        return list.stream().map(d -> new DiaDescansoDto(d.id, d.empleado.id, d.fechaDescanso)).toList();
    }

    @POST
    @Transactional
    public Response create(DiaDescansoDto dto) {
        Empleado emp = Empleado.findById(dto.idEmpleado);
        if (emp == null)
            throw new NotFoundException("Employee not found");

        // Prevent duplicates
        long count = DiaDescanso.count("empleado.id = ?1 and fechaDescanso = ?2", dto.idEmpleado, dto.fecha);
        if (count > 0)
            return Response.status(Response.Status.CONFLICT).build();

        DiaDescanso entity = new DiaDescanso();
        entity.empleado = emp;
        entity.fechaDescanso = dto.fecha;
        entity.persist();

        return Response.status(Response.Status.CREATED)
                .entity(new DiaDescansoDto(entity.id, entity.empleado.id, entity.fechaDescanso)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        DiaDescanso.deleteById(id);
    }
}
