package py.com.progweb.prueba.ejb;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fecha {
    public static Date sumarRestarDiasFecha(java.util.Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        //System.out.println("Fechaaa");
        //System.out.println(java.sql.Date.valueOf(formatearDateString(calendar.getTime())));
        return java.sql.Date.valueOf(formatearDateString(calendar.getTime())); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static String formatearDateString(java.util.Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
