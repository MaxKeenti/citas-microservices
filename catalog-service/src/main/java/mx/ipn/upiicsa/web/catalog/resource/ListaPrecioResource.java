package mx.ipn.upiicsa.web.catalog.resource;

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

import mx.ipn.upiicsa.web.catalog.model.ListaPrecio;

@Path("/price-lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListaPrecioResource {

    @GET
    public List<ListaPrecio> list() {
        return ListaPrecio.listAll();
    }

    @GET
    @Path("/{id}")
    public ListaPrecio get(@PathParam("id") Integer id) {
        return ListaPrecio.findById(id);
    }

    @POST
    @Transactional
    public Response create(ListaPrecio lista) {
        if (lista.estado == null) {
            lista.estado = mx.ipn.upiicsa.web.catalog.model.EstadoListaPrecio.findById(1);
        }
        lista.persist();
        return Response.status(Response.Status.CREATED).entity(lista).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public ListaPrecio update(@PathParam("id") Integer id, ListaPrecio lista) {
        ListaPrecio entity = ListaPrecio.findById(id);
        if (entity == null) {
            throw new jakarta.ws.rs.NotFoundException();
        }
        entity.nombre = lista.nombre;
        entity.estado = lista.estado;
        entity.inicio = lista.inicio;
        entity.fin = lista.fin;
        entity.activo = lista.activo;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Integer id) {
        ListaPrecio.deleteById(id);
    }

    @GET
    @Path("/{id}/items")
    public List<mx.ipn.upiicsa.web.catalog.dto.PriceListItemDto> getItems(@PathParam("id") Integer id) {
        List<mx.ipn.upiicsa.web.catalog.model.ServicioListaPrecio> items = mx.ipn.upiicsa.web.catalog.model.ServicioListaPrecio
                .list("listaPrecio.id", id);
        return items.stream().map(item -> new mx.ipn.upiicsa.web.catalog.dto.PriceListItemDto(
                item.id,
                item.servicio.id,
                item.servicio.nombre,
                item.precio)).toList();
    }

    @POST
    @Path("/{id}/items")
    @Transactional
    public Response addItem(@PathParam("id") Integer id, mx.ipn.upiicsa.web.catalog.dto.PriceListItemDto dto) {
        mx.ipn.upiicsa.web.catalog.model.ListaPrecio lista = ListaPrecio.findById(id);
        if (lista == null)
            throw new jakarta.ws.rs.NotFoundException();

        mx.ipn.upiicsa.web.catalog.model.Servicio servicio = mx.ipn.upiicsa.web.catalog.model.Servicio
                .findById(dto.idServicio);
        if (servicio == null)
            throw new jakarta.ws.rs.NotFoundException("Service not found");

        mx.ipn.upiicsa.web.catalog.model.ServicioListaPrecio item = mx.ipn.upiicsa.web.catalog.model.ServicioListaPrecio
                .find("listaPrecio.id = ?1 and servicio.id = ?2", id, dto.idServicio).firstResult();

        if (item == null) {
            item = new mx.ipn.upiicsa.web.catalog.model.ServicioListaPrecio();
            item.listaPrecio = lista;
            item.servicio = servicio;
        }
        item.precio = dto.precio;
        item.persist();

        return Response.ok(new mx.ipn.upiicsa.web.catalog.dto.PriceListItemDto(
                item.id, item.servicio.id, item.servicio.nombre, item.precio)).build();
    }

    @DELETE
    @Path("/{id}/items/{itemId}")
    @Transactional
    public void deleteItem(@PathParam("id") Integer id, @PathParam("itemId") Integer itemId) {
        mx.ipn.upiicsa.web.catalog.model.ServicioListaPrecio.deleteById(itemId);
    }
}
