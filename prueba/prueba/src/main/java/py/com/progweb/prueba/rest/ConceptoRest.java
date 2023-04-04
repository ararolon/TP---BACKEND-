package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ConceptoDAO;
import py.com.progweb.prueba.model.ConceptoPuntos;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("conceptoPuntos")
@Consumes("application/json")
@Produces("application/json")
public class ConceptoRest {
    @Inject
    private ConceptoDAO conceptoDAO;
    @POST
    @Path("/")
    public Response agregar(List<ConceptoPuntos> conceptoPuntos){
        for (ConceptoPuntos concepto: conceptoPuntos){
            this.conceptoDAO.add(concepto);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/")
    public Response getAll(){
        return Response.ok(conceptoDAO.getAll()).build();
    }

    @GET
    @Path("/{idConcepto}")
    public Response getConceptoById(@PathParam("idConcepto") Integer idConcepto){
        Response response;
        try {
            ConceptoPuntos concepto = conceptoDAO.getById(idConcepto);
            if(concepto == null){
                response = Response.status(404).entity("Concepto Uso No encontrado").build();
            }else {
                response = Response.ok().build();
            }
        }catch (Exception ex){
            response = Response.serverError().build();
        }
        return response;
    }
    @DELETE
    @Path("/{idConcepto}")
    public  Response deleteConceptoPuntos(@PathParam("idConcepto") Integer idConcepto){
        Integer status = conceptoDAO.deleteConcepto(idConcepto);
        Response respuesta = Response.status(400).build();
        try {
            if (status == CodigosDeEstado.SUCCESS) {
                respuesta = Response.ok("Concepto " + idConcepto + " Eliminado Correctamente").build();
            } else if (status == CodigosDeEstado.NOT_FOUND) {
                respuesta = Response.status(404).entity("Concepto Uso No encontrado").build();
            }
        }catch (Exception ex){
            respuesta = Response.serverError().build();
        }
        return respuesta;
    }
    @PUT
    @Path("/")
    public Response updateConceptoPuntos(ConceptoPuntos conceptoPuntos){
        Response respuesta = Response.status(400).build();;
        Integer status = conceptoDAO.updateConcepto(conceptoPuntos);
        try {
            if (status == CodigosDeEstado.SUCCESS) {
                respuesta = Response.ok("Concepto Actualizado Correctamente").build();
            } else if (status == CodigosDeEstado.NOT_FOUND) {
                respuesta = Response.status(404).entity("Concepto Uso No encontrado").build();
            }
        }catch (Exception ex){
            respuesta = Response.serverError().build();
        }
        return respuesta;
    }
}
