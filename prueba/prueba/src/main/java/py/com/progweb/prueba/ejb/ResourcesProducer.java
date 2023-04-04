package py.com.progweb.prueba.ejb;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class ResourcesProducer {
    @Produces
    @PersistenceContext( unitName = "pruebaPU")
    private EntityManager entityManager;


}
