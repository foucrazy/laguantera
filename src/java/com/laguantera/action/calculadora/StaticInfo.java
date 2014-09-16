package com.laguantera.action.calculadora;

import com.laguantera.config.Configuracion;
import com.laguantera.util.Servicios;
import java.io.*;
import org.apache.commons.io.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Felix Gonzalez
 */
public final class StaticInfo {
    
    /**Ruta donde se encuentra el fichero de configuracion*/
    private String rutaJSON;
    
    /**Objeto para el tratamiento de la informacion del fichero de informacion por defecto.*/
    private JSONObject info=null;        
    
    /*Nombre de la clase declarado de forma estática para evitar la creación de objetos String innecesarios.*/
    private final String nombreClase="StaticInfo";
    
    private static StaticInfo INSTANCE = null;        
    
    private int contadorCambios=0;
    
    private StaticInfo()
    {    	
        this.reload();
        Servicios.logear(nombreClase,"Singleton StaticInfo creado.",Servicios.DEBUG);
    }

    public boolean reload()
    {
        try
        {
            FileReader ficheroConfiguracion;
            BufferedReader bufferLectura;
            String contenido="",linea;

            rutaJSON=Configuracion.getInstance().get("dirPermanentePrivado")+"StaticInfo.json";
            File archivo=new File(rutaJSON);            
            if (!archivo.exists()){
                String rutaBase= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                rutaBase=rutaBase.replace(".class", ".json");
                FileUtils.copyFile(new File(rutaBase), archivo, true);
                Servicios.logear(this.nombreClase,"Copiando StaticInfo.json por defecto a direcctorio de recursos.",Servicios.INFO);
            }
            ficheroConfiguracion = new FileReader(rutaJSON);
            
            bufferLectura=new BufferedReader(ficheroConfiguracion);
            while ((linea=bufferLectura.readLine())!=null)
                contenido+=linea;

            info=new JSONObject(contenido);
            bufferLectura.close();
            ficheroConfiguracion.close();
        }
        catch (JSONException ex)
        {
            Servicios.logear(nombreClase,"Ocurrio un problema con el JSON: "+ex.toString(),Servicios.FATAL);
            Servicios.logear(nombreClase,"Ruta que se intenta leer: "+rutaJSON,Servicios.INFO);
            return false;
        }
        catch (IOException ex)
        {
            Servicios.logear(nombreClase,"Ocurrio un problema de I/O: "+ex.toString(),Servicios.FATAL);
            Servicios.logear(nombreClase,"Ruta que se intenta leer: "+rutaJSON,Servicios.INFO);
            return false;
        }
        
        return true;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new StaticInfo();
        }
    }
 
    public static StaticInfo getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
    	throw new CloneNotSupportedException(); 
    }    

    /**
     * Actualiza la informacion con los parametros que se hayan establecido anteriormente con los metodos 'set'.
     */
    public boolean escribir()
    {	
        try
        {
            FileWriter ficheroSalida;
            BufferedWriter bufferSalida;

            ficheroSalida = new FileWriter(this.rutaJSON);
            bufferSalida = new BufferedWriter(ficheroSalida);

            bufferSalida.write(this.info.toString(), 0, this.info.toString().length());
            bufferSalida.close();
            ficheroSalida.close();
            return true;
        } catch (IOException ex) {
            Servicios.logear(nombreClase,"Ocurrion un problema al escribir fichero de informacion: "+ex.toString(),Servicios.FATAL);
            return false;
        }    	
    }
    
    /**
     * Obtiene el valor del parametro solicitado.
     * @param key Nombre del parametro que deseamos conocer.
     * @return Valor del parametro
     */
    public String get(String key)
    {
        try {
            return this.info.getString(key);
        } catch (Exception ex) {            
            Servicios.logear(nombreClase,"Imposible obtener el parametro: "+key+ "-->"+ex.toString(),Servicios.FATAL);
            return "";
        }    		    
    }    
    
    public double getDouble(String key)
    {
        try {
            return this.info.getDouble(key);
        } catch (Exception ex) {            
            Servicios.logear(nombreClase,"Imposible obtener el parametro: "+key+ "-->"+ex.toString(),Servicios.FATAL);
            return 0;
        }    		    
    }     
    
    /**
     * Establece un nuevo valor para un parametro de informacion.
     * @param key Nombre del parametro a modificar.
     * @param valor Valor que tomara el parametro.
     * @return true/false
     */
    public boolean set(String key, String valor)
    {
        try {
            this.info.put(key, valor);
            this.contadorCambios++;
            if (contadorCambios>=20){
                this.escribir();
            }
            return true;            
        } catch (Exception ex) {
            Servicios.logear(nombreClase,"Imposible establecer el parametro: "+key+ " con valor: "+ valor +"-->"+ex.toString(),Servicios.FATAL);
            return false;
        }
    }
}
