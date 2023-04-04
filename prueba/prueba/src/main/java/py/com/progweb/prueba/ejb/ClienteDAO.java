package py.com.progweb.prueba.ejb;

import org.omg.CORBA.PUBLIC_MEMBER;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.utils.CodigosDeEstado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


@Stateless
public class ClienteDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private  EntityManager em;

    // funcio para agregar un nuevo cliente
    public void add(Cliente cliente){
        cliente.setFechaNacimiento(Fecha.sumarRestarDiasFecha(cliente.getFechaNacimiento(),1));
        this.em.persist(cliente);
    }
    //funcion para listar todos los clientes
    public List<Cliente> getAll(){
        Query query = this.em.createQuery("select c from Cliente c");
        return  (List<Cliente>)  query.getResultList();
    }

    public Cliente get(Long id_cliente){
        Cliente cliente= this.em.find(Cliente.class,id_cliente);
        return cliente;
    }

    public List<Cliente> getClienteByName(String nombre) {
        nombre = nombre + "%";
        Query q= this.em.createQuery("select c from Cliente c where LOWER(c.nombre) like :nombre");
        System.out.println("Aca explota en el select");
        return (List<Cliente>) q.setParameter("nombre", nombre).getResultList();

    }

    public List<Cliente> getClienteByLastName(String apellido) {
        apellido = apellido + "%";
        Query q= this.em.createQuery("select c from Cliente c where LOWER(c.apellido) like :apellido");
        return (List<Cliente>)q.setParameter("apellido", apellido).getResultList();
    }

    public  List<Cliente> getClienteByBirth(String birthString){
        //conviero String a Date
        //LocalDate birth=LocalDate.parse(birthString);
        /*
        Date birth=null;
        try {
            birth=new SimpleDateFormat("dd/MM/yyyy").parse(birthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        java.util.Date birth= Date.valueOf(birthString);
        Query q= this.em.createQuery("select c from Cliente c where c.fechaNacimiento= :birth ");
        return  (List<Cliente>) q.setParameter("birth",birth).getResultList();
    }

    public String deleteCliente(Long id_cliente) {
        Cliente cliente=this.em.find(Cliente.class,id_cliente);
        String clienteNameUserName;
        if (cliente!=null){
            clienteNameUserName= cliente.getNombre()+" "+ cliente.getApellido();
            this.em.remove(cliente);
        }else{
            clienteNameUserName = null;
        }
        return clienteNameUserName;
    }

    /* Esta funcion solo actualiza el nombre y apellido*/
    public Integer updateCliente(Cliente newCliente) {
        Cliente cliente= this.em.find(Cliente.class,newCliente.getIdCliente());
        //si el cliente existe, recorremos todos los atributos para actualizar
        if (cliente!= null){
            if (newCliente.getNombre()!=null){
                cliente.setNombre(newCliente.getNombre());
            }
            if (newCliente.getApellido()!=null){
                cliente.setApellido(newCliente.getApellido());
            }
            if (newCliente.getNacionalidad()!=null){
                cliente.setNacionalidad((newCliente.getNacionalidad()));
            }
            if (newCliente.getEmail()!=null){
                cliente.setEmail((newCliente.getEmail()));
            }
            if (newCliente.getCi()!=null){
                cliente.setCi((newCliente.getCi()));
            }
            if (newCliente.getTelefono()!=null){
                cliente.setTelefono((newCliente.getTelefono()));
            }
            if (newCliente.getTipoDocumento()!=null){
                cliente.setTipoDocumento((newCliente.getTipoDocumento()));
            }
            if (newCliente.getFechaNacimiento()!=null){
                cliente.setFechaNacimiento((newCliente.getFechaNacimiento()));
            }
            return CodigosDeEstado.SUCCESS;
        }else{
            return CodigosDeEstado.NOT_FOUND;
        }
    }
}
