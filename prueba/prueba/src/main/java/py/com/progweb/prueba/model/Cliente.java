package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "id_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private  Long idCliente;

    @Column(name = "nombre",length =100)
    @Basic(optional = false)
    private String nombre;

    @Column(name = "apellido",length =100)
    @Basic(optional = false)
    private String apellido;

    @Column(name = "numero_documento")
    @Basic(optional = false)
    private Integer ci;

    @Column(name = "tipo_documento",length =100)
    @Basic(optional = false)
    private String tipoDocumento;

    @Column(name = "nacionalidad",length =100)
    @Basic(optional = false)
    private String nacionalidad;

    @Column(name = "email",length =100)
    @Basic(optional = false)
    private  String email;

    @Column(name = "telefono")
    @Basic(optional = true)
    private  Integer telefono;

    @Column(name = "fecha_nacimiento")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")

    private Date fechaNacimiento;

    public  Cliente(){

    }
    public Cliente(Long idCliente, String nombre, String apellido, Integer ci, String tipoDocumento, String nacionalidad, String email, Integer telefono, Date fechaNacimiento) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.tipoDocumento = tipoDocumento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.telefono = telefono;
        //String dateString= new SimpleDateFormat("yyyy-MM-dd").format(this.fechaNacimiento);
        //this.fechaNacimiento = java.sql.Date.valueOf(dateString);
        this.fechaNacimiento=fechaNacimiento;
    }



    //Setters y Getters
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getCi() {
        return ci;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Date getFechaNacimiento() {
        //String dateString= new SimpleDateFormat("yyyy-MM-dd").format(this.fechaNacimiento);
        //return java.sql.Date.valueOf(dateString);
        return this.fechaNacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


}
