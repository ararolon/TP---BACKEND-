package py.com.progweb.prueba.model;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="uso_puntos_cabecera")
public class UsoPuntosCabecera {
    @Id
    @Column(name = "id_cabecera")
    @Basic(optional = false)
    @GeneratedValue(generator = "usoPuntosCabeceraSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="usoPuntosCabeceraSeq", sequenceName = "uso_puntos_cabecera_id_cabecera_seq", allocationSize = 0)
	private Integer idCabecera;

    @Column(name = "puntaje_utilizado")
    @Basic(optional = false)
    private Integer puntajeUtilizado;

    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fecha;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional=false)
    private Cliente cliente;

    @JoinColumn(name = "id_concepto", referencedColumnName = "id_concepto")
    @ManyToOne(optional=false)
    private ConceptoPuntos conceptoPuntos;

    public UsoPuntosCabecera(){

    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public void setConceptoPuntos(ConceptoPuntos conceptoPuntos) {
        this.conceptoPuntos = conceptoPuntos;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setIdCabecera(Integer idCabecera) {
        this.idCabecera = idCabecera;
    }
    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public ConceptoPuntos getConceptoPuntos() {
        return conceptoPuntos;
    }
    public Date getFecha() {
        return fecha;
    }
    public Integer getIdCabecera() {
        return idCabecera;
    }
    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }
}