package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AgendaDAO {
    @PersistenceContext(unitName="pruebaPU")
    private EntityManager em;

    public void agregar(Agenda entidad) {
        this.em.persist(entidad);
    }

    public List<Persona> todos(){
        return this.em.createQuery("select p from Agenda p").getResultList();
    }


    public List<Agenda> filtrado(Integer idPersona){
        return this.em.createQuery("select p from Agenda p where p.idPersona.idPersona = :param")
                .setParameter("param",idPersona).getResultList();
    }

    /*public List<Persona> lista(String nombreLike){
        return this.em.createQuery("select p from Persona p where nombre like :param")
                .setParameter("param","%"+nombreLike+"%").getResultList();
    }*/
    public Agenda getById(Integer id){
        return this.em.find(Agenda.class,id);
    }
}
