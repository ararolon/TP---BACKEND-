package py.com.progweb.prueba.model;
import javax.persistence.*;

@Entity
@Table(name="asignacion_puntos")
public class AsignacionPuntos{
    @Id
    @Column(name = "id_asignacion")
    @Basic(optional = false)
    @GeneratedValue(generator = "asignacionSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="asignacionSec", sequenceName = "asignacion_puntos_id_asignacion_seq", allocationSize = 0)
    private Integer idAsignacion;

    @Column(name = "limite_inferior")
    @Basic(optional = false)
    private Integer limiteInferior;

    @Column(name = "limite_superior")
    @Basic(optional = false)
    private Integer limiteSuperior;
    
    @Column(name = "monto")
    @Basic(optional = false)
    private Integer monto;

    public AsignacionPuntos(){

    }
    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }
    public void setLimiteInferior(Integer limiteInferior) {
        this.limiteInferior = limiteInferior;
    }
    public void setLimiteSuperior(Integer limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }
    public void setMonto(Integer monto) {
        this.monto = monto;
    }
    public Integer getIdAsignacion() {
        return idAsignacion;
    }
    public Integer getLimiteInferior() {
        return limiteInferior;
    }
    public Integer getLimiteSuperior() {
        return limiteSuperior;
    }
    public Integer getMonto() {
        return monto;
    }

}