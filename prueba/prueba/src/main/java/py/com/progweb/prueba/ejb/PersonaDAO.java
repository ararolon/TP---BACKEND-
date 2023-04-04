package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PersonaDAO {
    @PersistenceContext(unitName="pruebaPU")
    private EntityManager em;

    @Inject
    AgendaDAO agendaDAO;

    //eesta anotacion es cuando requerimos que sea atomico el metodo
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregar(Persona entidad) {
        this.em.persist(entidad);
        //para agregar una persona y todas sus agendas de una vez, es decir
        // se manda con el atributo listaAgendasJson cargado
        /*for (Agenda a:entidad.getListaAgendasJson()) {
            Agenda aPersistir=new Agenda();
            aPersistir.setFecha(a.getFecha());
            aPersistir.setActividad(a.getActividad());
            aPersistir.setIdPersona(new Persona());
            aPersistir.getIdPersona().setIdPersona(entidad.getIdPersona());
            agendaDAO.agregar(aPersistir);
        }*/
    }

    public List<Persona> todos(){
        return this.em.createQuery("select p from Persona p").getResultList();
    }

    public List<Persona> lista(String nombreLike){
        return this.em.createQuery("select p from Persona p where nombre like :param")
                .setParameter("param","%"+nombreLike+"%").getResultList();
    }
    public Persona getById(Integer id){
        Persona p=this.em.find(Persona.class,id);
        List<Agenda> agendas=p.getListaAgenda();
        p.setListaAgenda(new ArrayList<Agenda>());
        for (Agenda a: agendas) {
            Agenda a2=new Agenda();
            a2.setActividad(a.getActividad());
            a2.setFecha(a.getFecha());
            p.getListaAgenda().add(a2);
        }
        return p;
    }
}
