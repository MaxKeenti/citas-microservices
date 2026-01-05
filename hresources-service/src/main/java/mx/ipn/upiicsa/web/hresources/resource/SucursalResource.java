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

import mx.ipn.upiicsa.web.hresources.model.Sucursal;

@Path("/branches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SucursalResource {

    // Geometry factory
    private final org.locationtech.jts.geom.GeometryFactory geometryFactory = new org.locationtech.jts.geom.GeometryFactory();

    @GET
    public List<mx.ipn.upiicsa.web.hresources.dto.SucursalDto> list() {
        return Sucursal.listAll().stream().map(s -> (Sucursal) s).map(s -> toDto(s)).toList();
    }

    @GET
    @Path("/{id}")
    public mx.ipn.upiicsa.web.hresources.dto.SucursalDto get(@PathParam("id") Integer id) {
        Sucursal s = Sucursal.findById(id);
        if (s == null)
            return null;
        return toDto(s);
    }

    @POST
    @Transactional
    public Response create(mx.ipn.upiicsa.web.hresources.dto.SucursalDto dto) {
        Sucursal obj = new Sucursal();
        obj.nombre = dto.nombre;
        if (dto.lat != null && dto.lng != null) {
            obj.ubicacion = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(dto.lng, dto.lat));
            obj.ubicacion.setSRID(4326);
        }
        if (dto.idEstablecimiento != null) {
            // Assuming establishment exists or is optional, mostly ID 1 by default
            mx.ipn.upiicsa.web.hresources.model.Establecimiento e = mx.ipn.upiicsa.web.hresources.model.Establecimiento
                    .findById(dto.idEstablecimiento);
            if (e != null)
                obj.establecimiento = e;
        }

        // Default to ID 1 if not set
        if (obj.establecimiento == null) {
            obj.establecimiento = mx.ipn.upiicsa.web.hresources.model.Establecimiento.findById(1);
        }
        obj.persist();
        return Response.status(Response.Status.CREATED).entity(toDto(obj)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public mx.ipn.upiicsa.web.hresources.dto.SucursalDto update(@PathParam("id") Integer id,
            mx.ipn.upiicsa.web.hresources.dto.SucursalDto dto) {
        Sucursal entity = Sucursal.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.nombre = dto.nombre;
        if (dto.lat != null && dto.lng != null) {
            entity.ubicacion = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(dto.lng, dto.lat));
            entity.ubicacion.setSRID(4326);
        }
        // Update establishment if needed
        return toDto(entity);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        Sucursal.deleteById(id);
    }

    private mx.ipn.upiicsa.web.hresources.dto.SucursalDto toDto(Sucursal s) {
        Double lat = s.ubicacion != null ? s.ubicacion.getY() : null;
        Double lng = s.ubicacion != null ? s.ubicacion.getX() : null;
        Integer estId = s.establecimiento != null ? s.establecimiento.id : null;
        return new mx.ipn.upiicsa.web.hresources.dto.SucursalDto(s.id, s.nombre, lat, lng, estId);
    }
}
