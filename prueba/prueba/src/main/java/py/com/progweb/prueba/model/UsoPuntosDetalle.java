package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

@Entity
@Table(name = "uso_puntos_detalle")
public class UsoPuntosDetalle {

    @Id
    @Column(name="id_detalle")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Column(name="puntaje_utilizado")
    @Basic(optional = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private  Integer puntajeUtilizado;

    @ManyToOne()
    @JoinColumn(name = "id_cabecera", referencedColumnName = "id_cabecera")
    @Basic(optional = false)
    private UsoPuntosCabecera cabecera;

    @ManyToOne()
    @JoinColumn(name = "id_bolsa", referencedColumnName = "id_bolsa")
    @Basic(optional = false)
    private BolsaPuntos idBolsa;

    public UsoPuntosDetalle(){

    }
    public UsoPuntosDetalle(Long id, Integer puntajeUtilizado, UsoPuntosCabecera cabecera, BolsaPuntos idBolsa) {
        this.id = id;
        this.puntajeUtilizado = puntajeUtilizado;
        this.cabecera = cabecera;
        this.idBolsa = idBolsa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public UsoPuntosCabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(UsoPuntosCabecera cabecera) {
        this.cabecera = cabecera;
    }

    public BolsaPuntos getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(BolsaPuntos idBolsa) {
        this.idBolsa = idBolsa;
    }


}
