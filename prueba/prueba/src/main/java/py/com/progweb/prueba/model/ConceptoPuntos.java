package py.com.progweb.prueba.model;
import javax.persistence.*;

@Entity
@Table(name="concepto_puntos")
public class ConceptoPuntos{
    @Id
    @Column(name = "id_concepto")
    @Basic(optional = false)
    @GeneratedValue(generator = "conceptoSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="conceptoSec", sequenceName = "concepto_puntos_id_concepto_seq", allocationSize = 0)
    private Integer idConcepto;
    
    @Column(name = "descripcion", length = 100)
    @Basic(optional = false)
    private String descripcion;

    @Column(name = "puntos_requeridos")
    @Basic(optional = false)
    private Integer puntosRequeridos;

    public ConceptoPuntos(){

    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }
    public void setPuntosRequeridos(Integer puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public Integer getIdConcepto() {
        return idConcepto;
    }
    public Integer getPuntosRequeridos() {
        return puntosRequeridos;
    }
}