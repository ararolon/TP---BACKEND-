package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.BolsaPuntosDAO;
import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bolsaPuntos")
@Consumes("application/json")
@Produces("application/json")
public class BolsaPuntosRest {

    @Inject
    BolsaPuntosDAO bolsaPuntosDAO;
    @GET
    @Path("/")
    public Response getAll(){
        return  Response.ok(bolsaPuntosDAO.getAll()).build();
    }
    /*
    @POST
    @Path("/")
    public  Response add(List<Integer> lista_clientes_id , List<Integer> lista_monto){
        for ( int i=0; i<lista_clientes_id.size();i++){
            bolsaPuntosDAO.add(lista_clientes_id[i],lista_monto[i]);
        }
        return Response.ok().build();
    }
    */
    @POST
    @Path("/")
    public  Response add(List<BolsaPuntos> bolsasPuntos){
        for ( BolsaPuntos bolsaPuntos: bolsasPuntos){
            String respuesta=bolsaPuntosDAO.add(bolsaPuntos);
            if (!respuesta.isEmpty()){
                return Response.status(404).entity(respuesta).build();
            }
        }
        return Response.ok().build();
    }


    @GET
    @Path("/cliente/{id_cliente}")
    public Response getByClienteId(@PathParam("id_cliente") Long id_cliente){
        List<BolsaPuntos> listBolsa=bolsaPuntosDAO.getByClienteId(id_cliente);
        if( !listBolsa.isEmpty()) return Response.ok(bolsaPuntosDAO.getByClienteId(id_cliente)).build();
        return  Response.status(404).entity("No hay Bolsas para ese cliente").build();
    }

    @GET
    @Path("/rango/limiteI/{limiteI}/limiteS/{limiteS}")
    public Response getByRange(@PathParam("limiteI") Integer limiteI, @PathParam("limiteS") Integer limteS){
        List<BolsaPuntos> listBolsa=bolsaPuntosDAO.getByRange(limiteI,limteS);
        if (!listBolsa.isEmpty()) Response.ok(listBolsa).build();
        return  Response.status(404).entity("No se encontro BolsasPuntos dentro de ese rango").build();
    }

    @GET
    @Path("/clientes/vencen/{dias}")
    public Response getByExpireDaysRest(@PathParam("dias") int dias){
        List<Cliente> clientes=bolsaPuntosDAO.getByExpireDays(dias);
        if (!clientes.isEmpty()) Response.ok(clientes).build();
        return  Response.status(404).entity("No se encontro Clientes que vencen en : "+ String.valueOf(dias)+" dias").build();
    }

}