

package py.com.progweb.prueba.rest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import py.com.progweb.prueba.ejb.PersonaDataAccessObject;
import py.com.progweb.prueba.model.Persona;

@Path("persona")
@Consumes("application/json")
@Produces("application/json")
public class PersonaRest{
    @Inject
    private PersonaDataAccessObject personaDao;
    @GET
    @Path("/")
    public Response listar(){
        return Response.ok(personaDao.lista()).build();
    }
    @POST
    @Path("/")
    public Response crear(Persona p){
        this.personaDao.agregar(p);
        return Response.ok().build();
    }
}