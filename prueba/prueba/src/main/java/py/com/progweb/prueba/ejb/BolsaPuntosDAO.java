package py.com.progweb.prueba.ejb;


import py.com.progweb.prueba.model.BolsaPuntos;
import py.com.progweb.prueba.model.Cliente;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Stateless
public class BolsaPuntosDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    @Inject
    private ClienteDAO clienteDAO;

    @Inject
    AsignacionDAO asignacionDAO;

    @Inject
    VencimientoDAO vencimientoDAO;

    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    public  String add(BolsaPuntos bolsa){

       Date  currentDate=new Date(); //obtengo la fecha actual
       Integer duracion=vencimientoDAO.getDuracion(formatearDateString(currentDate));
       System.out.println("-----------1----------");
       Integer cantPuntos = asignacionDAO.getReglaByMonto(bolsa.getMontoOperacion());
       if (cantPuntos==null){
           return "No existe regla para ese Monto";
        }
        System.out.println("-----------2----------");
       bolsa.setSaldoPuntos(cantPuntos); // mi saldo de puntos es igual a puntaAsignado al crear una nueva Bolsa
       bolsa.setPuntajesAsignado(cantPuntos); //le Cargo cantPuntos
       bolsa.setFechaAsignacion(currentDate);//La fecha es la fecha Actualidad
       bolsa.setFechaCaducidad(sumarRestarDiasFecha(currentDate,duracion.intValue())); // calculo la fecha de caducidad de acurdo a la duracion de mi puntaje
       bolsa.setPuntajeUtilizado(0); //No use ningun punto aun
       Cliente cliente= clienteDAO.get(bolsa.getCliente().getIdCliente());

       if (cliente==null){
           return "No existe Cliente con ese Id";
       }
        System.out.println("-----------3----------");
       bolsa.setCliente(cliente);
       this.em.persist(bolsa);
       return "";
    }

    public List<BolsaPuntos> getAll(){
        Query q= this.em.createQuery("select  b from BolsaPuntos b");
        return  (List<BolsaPuntos>) q.getResultList();
    }

    //Optenemos las bolsaPuntos por Cliente
    public List<BolsaPuntos>  getByClienteId(Long id_cliente){
        Cliente cliente = clienteDAO.get(id_cliente); //obtengo el cleinte de mi BD
        Query q= em.createQuery("select b from BolsaPuntos b where  b.cliente= :cliente order by fechaAsignacion asc");
        return (List<BolsaPuntos>) q.setParameter("cliente",cliente).getResultList();
    }
    public List<BolsaPuntos>  getByClienteIdSaldoNoCero(Long id_cliente){
        Cliente cliente = clienteDAO.get(id_cliente); //obtengo el cleinte de mi BD
        Query q= em.createQuery("select b from BolsaPuntos b where  b.cliente= :cliente and b.saldoPuntos>0 order by fechaAsignacion asc");
        return (List<BolsaPuntos>) q.setParameter("cliente",cliente).getResultList();
    }
    //obtenemos todas las Bolsa de puntos cuyo SaldoPuntos estre entre el rango de LimiteInferior y limiteSuperior
    public List<BolsaPuntos> getByRange(Integer limiteInferior, Integer limiteSuperior){
        List<BolsaPuntos> bolsaPuntos= getAll();
        List<BolsaPuntos> resultSet= new ArrayList<>();

        for( BolsaPuntos bolsa: bolsaPuntos){
            if (bolsa.getSaldoPuntos()>=limiteInferior && bolsa.getSaldoPuntos()<=limiteSuperior){
                resultSet.add(bolsa);
            }
        }
        return resultSet;
    }


    public List<Cliente> getByExpireDays(int dias) {
        Date currentDate= new  Date(); //obtengo el dia actual
        List<Cliente> cliente= new ArrayList<>();
        Date expireDate = sumarRestarDiasFecha(currentDate,dias);
        Query q = this.em.createQuery("select distinct b.cliente from BolsaPuntos b where b.saldoPuntos>0 and b.fechaCaducidad = :expireDate ");
        List<Cliente> clientes= (List<Cliente>) q.setParameter("expireDate",expireDate).getResultList(); // obtengo todas las bolsa de puntos
        return clientes;
    }
    public void usarPuntos(BolsaPuntos bolsaPuntos, Integer puntosAUsar) {
        BolsaPuntos bolsa = this.em.find(BolsaPuntos.class,bolsaPuntos.getId());
        Integer saldo = bolsa.getSaldoPuntos();
        Integer puntajeUtilizado = bolsa.getPuntajeUtilizado();
        bolsa.setPuntajeUtilizado( puntajeUtilizado + puntosAUsar );
        bolsa.setSaldoPuntos( saldo - puntosAUsar );
        
    }
    public Integer getTotalDePuntosByCliente(Long id_cliente){
        Cliente cliente = clienteDAO.get(id_cliente); //obtengo el cleinte de mi BD
        Query q= em.createQuery("select SUM(b.saldoPuntos) from BolsaPuntos b where  b.cliente= :cliente and b.saldoPuntos>0");
        Long result = (Long)q.setParameter("cliente",cliente).getSingleResult();
        return result.intValue();
    }
    public Date sumarRestarDiasFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        //System.out.println("Fechaaa");
       //System.out.println(java.sql.Date.valueOf(formatearDateString(calendar.getTime())));
        return java.sql.Date.valueOf(formatearDateString(calendar.getTime())); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public  String formatearDateString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public  List<BolsaPuntos> getBolsaPuntosVencidos(Date currentDate){
        Query q= this.em.createQuery("select b from BolsaPuntos b where b.fechaCaducidad< :currentDate");
        return (List<BolsaPuntos>) q.setParameter("currentDate",currentDate).getResultList();
    }
}
