package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bolsa_puntos")
public class BolsaPuntos {
    @Id
    @Column(name = "id_bolsa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Column(name = "fecha_asignacion")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaAsignacion;

    @Column(name = "fecha_caducidad")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaCaducidad;

    @Column(name = "puntajes_asignado")
    @Basic(optional = false)
    private  Integer puntajesAsignado;

    @Column(name = "puntaje_utilizado")
    @Basic(optional = false)
    private  Integer puntajeUtilizado;

    @Column(name = "saldo_puntos")
    @Basic(optional = false)
    private  Integer saldoPuntos;

    @Column(name = "monto")
    @Basic(optional = false)
    private Integer montoOperacion;

    @ManyToOne()
    @JoinColumn(name = "id_cliente",referencedColumnName = "id_cliente")
    @Basic(optional = false)
    private  Cliente cliente;


    public  BolsaPuntos(){

    }

    public BolsaPuntos(Long id, Date fechaAsignacion, Date fechaCaducidad, Integer puntajesAsignado, Integer puntajeUtilizado, Integer saldoPuntos, Integer montoOperacion, Cliente idCliente) {
        this.id = id;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaCaducidad = fechaCaducidad;
        this.puntajesAsignado = puntajesAsignado;
        this.puntajeUtilizado = puntajeUtilizado;
        this.saldoPuntos = saldoPuntos;
        this.montoOperacion = montoOperacion;
        this.cliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getPuntajesAsignado() {
        return puntajesAsignado;
    }

    public void setPuntajesAsignado(Integer puntajesAsignado) {
        this.puntajesAsignado = puntajesAsignado;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Integer getSaldoPuntos() {
        return saldoPuntos;
    }

    public void setSaldoPuntos(Integer saldoPuntos) {
        this.saldoPuntos = saldoPuntos;
    }

    public Integer getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(Integer montoOperacion) {
        this.montoOperacion = montoOperacion;
    }
}
