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
}
