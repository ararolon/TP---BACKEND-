package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.VencimientoDAO;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.VencimientoPuntos;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("vencimiento")
@Consumes("application/json")
@Produces("application/json")
public class VencimientoRest {
    @Inject
    private VencimientoDAO vencimientoDAO;
    @POST
    @Path("/")
    public Response agregar(List<VencimientoPuntos> vencimientoPuntos){
        for (VencimientoPuntos vencimientoPunto:vencimientoPuntos){
            if (!this.vencimientoDAO.add(vencimientoPunto)){
                return Response.status(404).entity("Regla Vencimiento se solapan").build();
            }
        }
        return Response.ok().build();
    }

    @GET
    @Path("/")
    public Response getAll( ){
        return Response.ok(vencimientoDAO.getAll()).build();
    }

    @GET
    @Path("/duracion/{fecha}")
    public Response getDuracionByFecha(@PathParam("fecha") String fecha){
        String respuesta = "{\"duracion\":" + vencimientoDAO.getDuracion(fecha) + " }";
        return Response.ok(respuesta).build();
    }
    @DELETE
    @Path("eliminar/{id_vencimiento}")
    public  Response deleteVencimientoRest(@PathParam("id_vencimiento") Integer id_vencimiento){
        Integer status = vencimientoDAO.deleteVencimiento(id_vencimiento);
        Response respuesta = Response.status(400).build();
        try{
            if (status == CodigosDeEstado.SUCCESS) {
                respuesta = Response.ok("Vencimiento Eliminado Correctamente").build();
            } else if (status == CodigosDeEstado.NOT_FOUND) {
                respuesta = Response.status(404).entity("Vencimiento No encontrado").build();
            }
        }catch (Exception ex){
            respuesta = Response.serverError().build();
        }
        return respuesta;
    }
    @PUT
    @Path("acutalizar")
    public Response updateVencimientoRest(VencimientoPuntos vencimientoPuntos){
        Integer status = vencimientoDAO.updateVencimiento(vencimientoPuntos);
        Response respuesta = Response.status(400).build();
        try{
            if (status == CodigosDeEstado.SUCCESS) {
                respuesta = Response.ok("Vencimiento Actualizado Correctamente").build();
            } else if (status == CodigosDeEstado.NOT_FOUND) {
                respuesta = Response.status(404).entity("Vencimiento No encontrado").build();
            }
        }catch (Exception ex){
            respuesta = Response.serverError().build();
        }
        return respuesta;
    }
}
