package py.com.progweb.prueba.model;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="agenda")
public class Agenda {

    @Id
    @Column(name = "id_agenda")
    @Basic(optional = false)
    @GeneratedValue(generator = "agendaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="agendaSec", sequenceName = "agenda_sec", allocationSize = 0)
    private Integer idAgenda;
    @Column(name = "actividad", length = 200)
    @Basic(optional = false)
    private String actividad;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false)
    private Persona persona;
    
    public Agenda(){

    }
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
    public String getActividad() {
        return actividad;
    }
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    public Persona getPersona() {
        return persona;
    }
    public Integer getIdAgenda() {
        return idAgenda;
    }
    public void setIdAgenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getFecha() {
        return fecha;
    }
    
}
