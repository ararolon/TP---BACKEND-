package py.com.progweb.prueba.ejb;


import py.com.progweb.prueba.model.BolsaPuntos;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Singleton
public class BolsaScheduler {
    @Inject
    BolsaPuntosDAO bolsaPuntosDAO;

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    //aca seteamos cada cuando tiempo queremos correr este proceso
    @Schedule(dayOfWeek="*" ,hour = "*/1",persistent = false)
    //@Schedule(hour = "*",minute = "*/1",persistent = false)
    public  void updateBolsaPuntos(){
        Date currentDate= new Date(); //aca obtenemos la fecha de hoy
        //aca actualizamos los saldos cuya fechaCaducida < fechaActual
        Query q= this.em.createQuery("update BolsaPuntos bp SET bp.saldoPuntos=0 where bp.fechaCaducidad < :currentDate");
        q.setParameter("currentDate",currentDate).executeUpdate();
    }
}