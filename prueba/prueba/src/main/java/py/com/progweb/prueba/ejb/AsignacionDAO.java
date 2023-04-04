package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.AsignacionPuntos;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.VencimientoPuntos;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class AsignacionDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private  EntityManager em;
    // funcion para agregar una nueva regla de asignacion
    public Boolean add(AsignacionPuntos asignacionPuntos){

        Query q = this.em.createQuery("select ap from AsignacionPuntos  ap where :LI between ap.limiteInferior and ap.limiteSuperior");
        Query q2= this.em.createQuery("select ap from AsignacionPuntos  ap where :LS between ap.limiteInferior and ap.limiteSuperior");
        if (q.setParameter("LI",asignacionPuntos.getLimiteInferior()).getResultList().isEmpty() && q2.setParameter("LS",asignacionPuntos.getLimiteSuperior()).getResultList().isEmpty() ){
            this.em.persist(asignacionPuntos);
            return true;
        }
        return false;
    }
    //funcion para listar todas las reglas
    public List<AsignacionPuntos> getAll(){
        Query query = this.em.createQuery("select c from AsignacionPuntos c");
        return  (List<AsignacionPuntos>)  query.getResultList();
    }


    public Integer getReglaByMonto( Integer monto ){
        Query q= this.em.createQuery("select c from AsignacionPuntos c where :monto between c.limiteInferior and c.limiteSuperior");
        AsignacionPuntos ap;
        try {
             ap = (AsignacionPuntos) q.setParameter("monto", monto).getSingleResult();
        }catch (Exception e){
            return null;
        }
        return  (Integer) monto/ap.getMonto();
    }

    public Integer deleteAsignacion(Integer id_asignacion) {
        AsignacionPuntos asignacionPuntos= this.em.find(AsignacionPuntos.class,id_asignacion);
        if (asignacionPuntos!=null) {
            this.em.remove(asignacionPuntos);
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }

    public Integer updateVencimiento(AsignacionPuntos asignacionPuntos) {
        AsignacionPuntos asignacionPuntosOriginal= this.em.find(AsignacionPuntos.class,asignacionPuntos.getIdAsignacion());
        if (asignacionPuntosOriginal!=null){
            if(asignacionPuntos.getLimiteInferior()!=null){
                asignacionPuntosOriginal.setLimiteInferior(asignacionPuntos.getLimiteInferior());
            }
            if(asignacionPuntos.getLimiteSuperior()!=null){
                asignacionPuntosOriginal.setLimiteSuperior(asignacionPuntos.getLimiteSuperior());
            }
            if(asignacionPuntos.getMonto()!=null){
                asignacionPuntosOriginal.setMonto(asignacionPuntos.getMonto());
            }
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }
}