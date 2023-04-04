package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.ejb.PersonaDataAccessObject;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.Persona;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.inject.Inject;
import javax.management.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Path("cliente")
@Consumes("application/json")
@Produces("application/json")

public class ClienteRest {
    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response getAllRest(){
     return  Response.ok(this.clienteDAO.getAll()).build();
    }


    @GET
    @Path("/id/{id_cliente}")
    public Response getClienteRest(@PathParam("id_cliente") Long id_cliente){
        Cliente cliente = clienteDAO.get(id_cliente);
        try {
            if (cliente != null) {
                return Response.ok(cliente).build();
            } else {
                return Response.status(404).entity("Cliente no encontrado").build();
            }
        }catch (Exception ex){
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/name/{nombre_cliente}")
    public Response getClienteByNameRest(@PathParam("nombre_cliente") String nombre ){
        List<Cliente> clientes= this.clienteDAO.getClienteByName(nombre.toLowerCase());
        return  Response.ok(clientes).build();
    }

    @GET
    @Path("/lastName/{apellido_cliente}")
    public Response getClienteByLastNameRest(@PathParam("apellido_cliente") String apellido ){
        List<Cliente> clientes= this.clienteDAO.getClienteByLastName(apellido.toLowerCase());
        return  Response.ok(clientes).build();
    }

    @GET
    @Path("/birth/{birth}")
    public Response getClienteByBirthRest(@PathParam("birth") String birth){
        List<Cliente> clientes= this.clienteDAO.getClienteByBirth(birth);
        return  Response.ok(clientes).build();
    }
    @POST
    @Path("/")
    public  Response agregar(List<Cliente> clientes){
        for (Cliente cliente : clientes){
            this.clienteDAO.add(cliente);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("eliminar/{id_cliente}")
    public  Response deleteClienteRest(@PathParam("id_cliente") Long id_cliente){
        String clienteEliminado=clienteDAO.deleteCliente(id_cliente);
        try {
            if (clienteEliminado != null) {
                return Response.ok("Usuario: " + clienteEliminado + " Eliminado Correctamente").build();
            } else {
                return Response.status(404).entity("Cliente no encontrado").build();
            }
        }catch (Exception ex){
            return Response.serverError().build();
        }

    }

    @PUT
    @Path("actualizar")
    public Response updateClienteRest(Cliente cliente){
        Integer status = clienteDAO.updateCliente(cliente);
        Response respuesta = Response.status(400).build();
        try{
            if (status == CodigosDeEstado.SUCCESS) {
                respuesta = Response.ok("Cliente Actualizado Correctamente").build();
            } else if (status == CodigosDeEstado.NOT_FOUND) {
                respuesta = Response.status(404).entity("Cliente no encontrado").build();
            }
        }catch (Exception ex){
            respuesta = Response.serverError().build();
        }
        return respuesta;
    }

}

