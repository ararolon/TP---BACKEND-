package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ConceptoDAO;
import py.com.progweb.prueba.ejb.UsoPuntosDAO;
import py.com.progweb.prueba.model.ConceptoPuntos;
import py.com.progweb.prueba.model.UsoPuntosCabecera;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("usoPuntos")
@Consumes("application/json")
@Produces("application/json")
public class UsoPuntosRest {
    @Inject
    private UsoPuntosDAO usoPuntosDAO;
    @POST
    @Path("/")
    public Response utilizarPuntos( List<UsoPuntosCabecera> listUsoPuntos){
        try{
            for (UsoPuntosCabecera usoPuntosCabecera : listUsoPuntos) {
                String respuesta= this.usoPuntosDAO.utilizarPuntos(usoPuntosCabecera);
                if(!respuesta.isEmpty()){
                    return Response.status(404).entity(respuesta).build();
                }
            }
            return Response.ok().build();
        }catch (Exception ex){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("/")
    public Response getAll( ){
        return Response.ok(usoPuntosDAO.getAll()).build();
    }
    @GET
    @Path("/concepto/{idConcepto}")
    public Response getUsoByConcepto(@PathParam("idConcepto") Integer idConcepto){
        List<UsoPuntosCabecera> listaUsoPuntos = usoPuntosDAO.getByConcepto(idConcepto);
        if(listaUsoPuntos == null){
            return Response.status(404).entity("No se encontro UsoPuntos para ese Concepto").build();
        }
        return Response.ok(listaUsoPuntos).build();
    }
    @GET
    @Path("/fecha/{fecha}")
    public Response getUsoByFecha(@PathParam("fecha") String fecha){
        return Response.ok(usoPuntosDAO.getByFecha(fecha)).build();
    }
    @GET
    @Path("/cliente/{idCliente}")
    public Response getUsoByCliente(@PathParam("idCliente") Long idCliente){
        List<UsoPuntosCabecera> listaUsoPuntos = usoPuntosDAO.getByCliente(idCliente);
        if(listaUsoPuntos == null){
            return Response.status(404).entity("No se encontro UsoPuntos para ese cliente").build();
        }
        return Response.ok(listaUsoPuntos).build();
    }
}