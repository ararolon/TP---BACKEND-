package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.AgendaDAO;
import py.com.progweb.prueba.ejb.PersonaDAO;
import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("agenda")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AgendaRest {

    @Inject
    PersonaDAO personaDAO;


    @Inject
    AgendaDAO agendaDAO;



    @POST
    @Path("/")
    public Response agregar(Agenda entidad) {
        agendaDAO.agregar(entidad);
        return Response.ok(entidad).build();
    }

    @GET
    @Path("/")
    public Response lista() {
        return Response.ok(agendaDAO.todos()).build();
    }

    @GET
    @Path("/persona/{idPersona}")
    public Response listaFiltradaPorPersona(@PathParam("idPersona") Integer idPersona) {
        return Response.ok(agendaDAO.filtrado(idPersona)).build();
    }


    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(agendaDAO.getById(id)).build();
    }
}
