package com.laguantera.action.admin;
import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;

        
/**
 *
 * @author Felix Gonzalez de Santos
 */
        
public class GetLog extends ActionBase{
    private String contenido="";
    private String nombreFichero="";

    public GetLog() {
    }

    @Override
    public String execute() {
        try {
            Calendar calendar= Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            String month = ""+(calendar.get(Calendar.MONTH)+1);
            String day = ""+calendar.get(Calendar.DAY_OF_MONTH);

            if (month.length()<2)
                month="0"+month;

            if (day.length()<2)
                day="0"+day;
            
            String fecha=day+"-"+month+"-"+year;
            nombreFichero = Configuracion.getInstance().get("catalina.home") + "logs\\laguantera.txt."+fecha;

            FileReader reader = new FileReader(nombreFichero);
            BufferedReader bf = new BufferedReader(reader);

            String tmp="";
            tmp=bf.readLine();               
            while (tmp!=null)
            {                
                this.contenido+=tmp+"<br>";
                tmp=bf.readLine();
            }

            debug("Cargando log:"+nombreFichero);
        } catch (Exception ex) {
            error("Excepcion:"+ex.toString());
            return Constantes.RES_ERROR;
        }
        return Constantes.RES_OK;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

}
