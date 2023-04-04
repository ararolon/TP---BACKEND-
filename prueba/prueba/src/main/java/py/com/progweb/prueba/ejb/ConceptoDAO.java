package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.ConceptoPuntos;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ConceptoDAO {
    @PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
    public void add(ConceptoPuntos conceptoPuntos){
        em.persist(conceptoPuntos);
    }
    public List<ConceptoPuntos> getAll(){
        Query q = this.em.createQuery("select c from ConceptoPuntos c");
        return (List<ConceptoPuntos>) q.getResultList();
    }
    public ConceptoPuntos getById(Integer idConcepto){
        try{
            Query q = this.em.createQuery("select c from ConceptoPuntos c where c.idConcepto = :idConcepto");
            return (ConceptoPuntos) q.setParameter("idConcepto", idConcepto).getSingleResult();
        }catch (NoResultException e ){
            return null;
        }
    }
    public Integer updateConcepto(ConceptoPuntos newConcepto){
        ConceptoPuntos concepto = this.em.find(ConceptoPuntos.class, newConcepto.getIdConcepto());
        if(concepto != null){
            if(newConcepto.getDescripcion() != null){
                concepto.setDescripcion(newConcepto.getDescripcion());
            }
            if(newConcepto.getPuntosRequeridos() != null){
                concepto.setPuntosRequeridos(newConcepto.getPuntosRequeridos());
            }
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }
    public Integer deleteConcepto(Integer idConcepto){
        ConceptoPuntos concepto = this.em.find(ConceptoPuntos.class, idConcepto);
        if(concepto!= null){
            this.em.remove(concepto);
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }
}
