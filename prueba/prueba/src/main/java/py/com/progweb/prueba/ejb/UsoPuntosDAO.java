package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.*;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class UsoPuntosDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    @Inject
    BolsaPuntosDAO bolsaPuntosDAO;

    @Inject
    private Event<MailEvent> eventProducer;

    public void addCabecera(UsoPuntosCabecera usoPuntosCabecera){
        em.persist(usoPuntosCabecera);
    }
    public void addDetalle(UsoPuntosDetalle usoPuntosDetalle){
        em.persist(usoPuntosDetalle);
    }
    public List<UsoPuntosCabecera> getAll(){
        Query q = em.createQuery("select u from UsoPuntosCabecera u");
        return (List<UsoPuntosCabecera>)q.getResultList();
    }
    public List<UsoPuntosCabecera> getByConcepto(Integer idConcepto){
        ConceptoPuntos concepto = this.em.find(ConceptoPuntos.class, idConcepto);
        if(concepto == null){
            return null;
        }
        Query q = em.createQuery("select u from UsoPuntosCabecera u where u.conceptoPuntos = :concepto");
        return (List<UsoPuntosCabecera>)q.setParameter("concepto", concepto).getResultList();
    }
    public List<UsoPuntosCabecera> getByFecha(String fechaStr){
        java.util.Date fecha= java.sql.Date.valueOf(fechaStr);
        Query q= em.createQuery("select u from UsoPuntosCabecera u where u.fecha= :fecha");
        return (List<UsoPuntosCabecera>) q.setParameter("fecha",fecha).getResultList();
    }
    public List<UsoPuntosCabecera> getByCliente(Long idCLiente){
        Cliente cliente = this.em.find(Cliente.class, idCLiente);
        if(cliente == null){
            return null;
        }
        Query q = em.createQuery("select u from UsoPuntosCabecera u where u.cliente = :cliente");
        return (List<UsoPuntosCabecera>)q.setParameter("cliente", cliente).getResultList();
    }
    public String utilizarPuntos(UsoPuntosCabecera usoPuntosCabecera){
        Long idCliente = usoPuntosCabecera.getCliente().getIdCliente();
        Integer idConceptoUso = usoPuntosCabecera.getConceptoPuntos().getIdConcepto();
        Cliente cliente= this.em.find(Cliente.class, idCliente);
        if(cliente == null){
            return "Cliente  no Existe";
        }
        ConceptoPuntos conceptoPuntos = this.em.find(ConceptoPuntos.class, idConceptoUso);
        if(conceptoPuntos == null){
            return "Concepto Puntos No existe";
        }
        Integer puntosRequeridos = conceptoPuntos.getPuntosRequeridos();
        UsoPuntosCabecera nuevaCabecera = new UsoPuntosCabecera();//preparo la cabecera con la informacion necesaria
        nuevaCabecera.setCliente(cliente);
        nuevaCabecera.setPuntajeUtilizado(puntosRequeridos);
        nuevaCabecera.setConceptoPuntos(conceptoPuntos);
        nuevaCabecera.setFecha(new Date());


        if( bolsaPuntosDAO.getTotalDePuntosByCliente(idCliente) >= puntosRequeridos ){
            this.addCabecera(nuevaCabecera); //guardo la cabecera
            //Si la cantidad de puntos es la necesaria continuo
            List<BolsaPuntos> bolsaPuntosList = bolsaPuntosDAO.getByClienteIdSaldoNoCero(idCliente);
            //consigo todas las listas de puntos del cliente
            for (BolsaPuntos bolsa: bolsaPuntosList) {
                UsoPuntosDetalle nuevoDetalle = new UsoPuntosDetalle();
                if( puntosRequeridos > bolsa.getSaldoPuntos() ){//si los puntos son mas que la bolsa, vacio la bolsa
                    nuevoDetalle.setPuntajeUtilizado(bolsa.getSaldoPuntos());//cargo el detalle
                    puntosRequeridos -= bolsa.getSaldoPuntos();
                    bolsaPuntosDAO.usarPuntos( bolsa, bolsa.getSaldoPuntos() );
                }else{//si los puntos son menores que la bolsa actual
                    nuevoDetalle.setPuntajeUtilizado( puntosRequeridos );
                    bolsaPuntosDAO.usarPuntos( bolsa, puntosRequeridos );
                    puntosRequeridos = 0;
                }
                nuevoDetalle.setIdBolsa(bolsa);
                nuevoDetalle.setCabecera(nuevaCabecera);
                this.addDetalle(nuevoDetalle);// guardo el detalle
                if (puntosRequeridos == 0){
                    break;
                }
            }
        }else{
            return "Cliente no Tiene los puntos requeridos para usar ese vale";
        }
        if( cliente.getEmail() != null ){
            sendEmail( cliente.getEmail(), "Has utilizado puntos en " + conceptoPuntos.getDescripcion() );
        }
        return "";
    }
    private void sendEmail(String to, String message) {
        MailEvent event = new MailEvent();
        event.setTo(to);
        event.setSubject("Uso de puntos");
        event.setMessage(message);

        eventProducer.fire(event); //firing event!
    }

}
