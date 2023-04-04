package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.VencimientoPuntos;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class VencimientoDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public boolean add(VencimientoPuntos vencimientoPuntos){
        if (existeRegla(Fecha.sumarRestarDiasFecha(vencimientoPuntos.getFechaInicio(),1 ) )&& existeRegla(Fecha.sumarRestarDiasFecha(vencimientoPuntos.getFechaFin(), 1))){
            vencimientoPuntos.setFechaInicio(Fecha.sumarRestarDiasFecha(vencimientoPuntos.getFechaInicio(),1));
            vencimientoPuntos.setFechaFin(Fecha.sumarRestarDiasFecha(vencimientoPuntos.getFechaFin(),1));
            em.persist(vencimientoPuntos);
            return true;
        }
        return false;
    }
    public List<VencimientoPuntos> getAll(){
        Query q = this.em.createQuery("select v from VencimientoPuntos v");
        return  (List<VencimientoPuntos>) q.getResultList();
    }
    public Integer getDuracion(String fecha){
        java.util.Date fechaDate= java.sql.Date.valueOf(fecha);
        Query q = this.em.createQuery("select v.duracion from VencimientoPuntos v where :fechaDate between v.fechaInicio and v.fechaFin");
        return  (Integer) q.setParameter("fechaDate", fechaDate).getSingleResult();
    }
    public  Boolean existeRegla(Date fecha){
        Query q = this.em.createQuery("select v.duracion from VencimientoPuntos v where :fechaDate between v.fechaInicio and v.fechaFin");
        return q.setParameter("fechaDate",fecha).getResultList().isEmpty();
    }

    public Integer deleteVencimiento(Integer id_vencimiento) {
        VencimientoPuntos vencimientoPuntos= this.em.find(VencimientoPuntos.class,id_vencimiento);
        if (vencimientoPuntos!=null){
            this.em.remove(vencimientoPuntos);
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }

    public Integer updateVencimiento(VencimientoPuntos vencimientoPuntos) {
        VencimientoPuntos vencimientoPuntosOriginal= this.em.find(VencimientoPuntos.class,vencimientoPuntos.getIdVencimiento());
        if (vencimientoPuntosOriginal!=null){
            if(vencimientoPuntos.getFechaInicio()!=null){
                vencimientoPuntosOriginal.setFechaInicio(vencimientoPuntos.getFechaInicio());
            }
            if(vencimientoPuntos.getFechaFin()!=null){
                vencimientoPuntosOriginal.setFechaFin(vencimientoPuntos.getFechaFin());
            }
            if(vencimientoPuntos.getDuracion()!=null){
                vencimientoPuntosOriginal.setDuracion(vencimientoPuntos.getDuracion());
            }
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }
}
