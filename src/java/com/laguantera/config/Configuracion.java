package com.laguantera.config;

import com.laguantera.dao.Config;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.laguantera.dao.PeticionesBD;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;


/**
 * Clase encargada de administrar los parametros de configuración del portal. 
 * @version 2.0
 * @author Felix Gonzalez
 */
public final class Configuracion {
    
    /**Ruta donde se encuentra el fichero de configuracion*/
    private String rutaJSON;
    
    /**Objeto para el tratamiento de la informacion del fichero de configuracion por defecto.*/
    private JSONObject configuracionesJSON=null;
    
    private List<Config> configuraciones=null;
    
    /*Nombre de la clase declarado de forma estática para evitar la creación de objetos String innecesarios.*/
    private final String nombreClase="Configuracion";
    
    /**Determina si el portal ya ha sido configurado (en base de datos)*/
    private boolean estaConfigurado;    
    
    private static Configuracion INSTANCE = null;        
    
    private Configuracion()
    {    	
        this.reload();
        Servicios.logear(nombreClase,"Singleton de configuracion creado.",Servicios.DEBUG);
    }

    /**
     * Recargar los parámetros de configuración desde donde estén disponibles (JSON o BD).
     */
    public boolean reload()
    {
        //Cargamos siempre la información del JSON por si la necesitamos
        //Procesado del fichero de configuración por defecto Configuracion.json
        try
        {
            FileReader ficheroConfiguracion;
            BufferedReader bufferLectura;
            String contenido="",linea="";
            
            //Obtenemos la ruta de ejecucion de esta clase
            String rutaBase= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            rutaJSON = rutaBase.replaceAll("/config/Configuracion.class", "/resources/Configuracion.json");
            Servicios.logear(nombreClase, "Cargando config desde:"+rutaJSON, Servicios.INFO);
            ficheroConfiguracion = new FileReader(rutaJSON);
            
            bufferLectura=new BufferedReader(ficheroConfiguracion);
            while ((linea=bufferLectura.readLine())!=null)
                contenido+=linea;

            //Al crear un JSONObject con lo leido desde el fichero el automaticamente es capaz de reconocer los datos que contenia el fichero.
            configuracionesJSON=new JSONObject(contenido);
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

    	//Comprobando si el portal ya ha sido configurado, en caso contrario se utilizan los valores por defecto del fichero json.
    	PeticionesBD bbdd=new PeticionesBD();
    	bbdd.abrirSesion();
        
        //Control para desarrollo
        if (Servicios.isInDevelopment) this.estaConfigurado=false;
        else this.estaConfigurado=bbdd.estaConfigurado();        
        
    	if (!this.estaConfigurado)
            this.configuraciones=this.JSONtoListConf(configuracionesJSON);
    	else    	
            this.configuraciones=bbdd.loadConfigs();
        
        return bbdd.cerrarSesion();
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new Configuracion();
        }
    }
 
    public static Configuracion getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
    	throw new CloneNotSupportedException(); 
    }    


    /**
     * Extrae todos los parametros contenidos en un JSON.
     * @param json Objeto JSON con toda la información a extraer.
     * @return Listado de configuraciones.
     */
    private List<Config> JSONtoListConf(JSONObject json)
    {
        List<Config> lista= new ArrayList<Config>();
        try{
            Iterator it= json.keys();
            String key;
            Config conf;
            while (it.hasNext())
            {
                key= (String)it.next();
                conf = new Config(key, json.getString(key));
                lista.add(conf);
            }
        }
        catch(JSONException ex)
        {            
            Servicios.logear(nombreClase,"Ocurrio un problema con el parseo del JSON: "+ex.toString(),Servicios.FATAL);
        }
        return lista;
    }
    /**
     * Actualiza la configuracion con los parametros que se hayan establecido anteriormente con los metodos 'set'.
     */
    public boolean escribir()
    {
    	if (this.estaConfigurado)
    	{
            PeticionesBD bbdd=new PeticionesBD();
            bbdd.abrirSesion();
            ListIterator<Config> lstCnf = this.configuraciones.listIterator();
            boolean resTemp=false;
            while (lstCnf.hasNext())
            {
                resTemp=bbdd.guardarConfig(lstCnf.next());
                if (!resTemp)
                    Servicios.logear(nombreClase, "No se ha podido guardar todos los parametros para la configuración en la BD.", Servicios.FATAL);
            }            
            return bbdd.cerrarSesion();
    	}
    	else
    	{    	
            try
            {
                FileWriter ficheroSalida = null;
                BufferedWriter bufferSalida = null;

                ficheroSalida = new FileWriter(this.rutaJSON + "json");
                bufferSalida = new BufferedWriter(ficheroSalida);

                bufferSalida.write(this.configuracionesJSON.toString(), 0, this.configuracionesJSON.toString().length());
                bufferSalida.close();
                ficheroSalida.close();
                return true;
            } catch (IOException ex) {
                Servicios.logear(nombreClase,"Ocurrion un problema al escribir fichero de configuracion: "+ex.toString(),Servicios.FATAL);
                return false;
            }
    	}
    }
    
    /**
     * Obtiene el valor del parametro solicitado.
     * @param key Nombre del parametro que deseamos conocer.
     * @return Valor del parametro
     */
    public String get(String key)
    {
    	if (this.estaConfigurado)
    	{
            Iterator it = this.configuraciones.iterator();
            Config cnf=null;
            int index=0;
            while (it.hasNext())
            {
                cnf = (Config)it.next();
                if (cnf.getNombre().equals(key))
                    break;
                index++;
            }
            return this.configuraciones.get(index).getValor();
    	}
    	else
    	{
            try {
                return this.configuracionesJSON.getString(key);
            } catch (Exception ex) {            
                Servicios.logear(nombreClase,"Imposible obtener el parametro: "+key+ "-->"+ex.toString(),Servicios.FATAL);
                return "";
            }    		
    	}
    }    
    
    /**
     * Establece un nuevo valor para un parametro de configuracion.
     * @param key Nombre del parametro a modificar.
     * @param valor Valor que tomara el parametro.
     * @return true/false
     */
    public boolean set(String key, String valor)
    {
        try {
            if (this.estaConfigurado)
            {
                Config newConfig = new Config();
                newConfig.setNombre(key);
                newConfig.setValor(valor);
                configuraciones.add(newConfig);
                return true;
            }
            else
            {
                this.configuracionesJSON.put(key, valor);
                return true;
            }
        } catch (Exception ex) {
            Servicios.logear(nombreClase,"Imposible establecer el parametro: "+key+ " con valor: "+ valor +"-->"+ex.toString(),Servicios.FATAL);
            return false;
        }
    }

    /**
     * Actualiza un parametro de configuracion.
     * @param key Nombre del parametro a modificar.
     * @param valor Valor que tomara el parametro.
     * @return true/false
     */
    public boolean update(String key, String valor)
    {
        try {
            if (this.estaConfigurado)
            {                                                
                Iterator it = this.configuraciones.iterator();
                Config cnf=null;
                int index=0;
                while (it.hasNext())
                {
                    cnf = (Config)it.next();
                    if (cnf.getNombre().equals(key))
                        break;
                    index++;
                }
                this.configuraciones.set(index,new Config(key,valor));
                return true;
            }
            else
            {
                this.configuracionesJSON.put(key, valor);
                return true;
            }
        } catch (Exception ex) {
            Servicios.logear(nombreClase,"Imposible establecer el parametro: "+key+ "con valor: "+ valor +"-->"+ex.toString(),Servicios.FATAL);
            return false;
        }
    }

    /**
     * Vuelca la configuración del JSON a la base de datos
     * @return
     */
    public boolean restore()
    {
        try{
            this.configuraciones=this.JSONtoListConf(configuracionesJSON);
            this.estaConfigurado=true;//Para fozar el guardado en BD                        
            return (this.escribir() && this.reload());
        }catch(Exception ex){
            Servicios.logear(nombreClase, "No se ha podido restaurar la config:"+ex.toString(), Servicios.FATAL);
            return false;
        }
    }

    /**
     * Eliminar un valor de configuración
     * @param key Nombre del parametro a eliminar.
     * @return
     */
    public boolean delete(String key, String value)
    {
        try{
            if (estaConfigurado)
            {
                Config cnfgToRemove=new Config(key, value);
                this.configuraciones.remove(cnfgToRemove);
                PeticionesBD bbdd=new PeticionesBD();
                bbdd.abrirSesion();
                boolean res;
                if (res=bbdd.eliminarConfig(cnfgToRemove))
                    Servicios.logear(nombreClase, "Parametro de configuración eliminado.", Servicios.INFO);
                else
                    Servicios.logear(nombreClase, "No se ha eliminado el parametro de config de la BD.", Servicios.ERROR);

                res=res && bbdd.cerrarSesion();
                //La session se cierra con el reload
                return (res && this.reload());
            }
            else
            {
                Servicios.logear(nombreClase, "Se está trabajando con el fichero JSON, no se permite la eliminación de parámetros.", Servicios.ERROR);
                return false;
            }
        }catch(Exception ex){
            Servicios.logear(nombreClase, "No se ha podido eliminar el parametro de config:"+ex.toString(), Servicios.FATAL);
            return false;
        }
    }

    public List<Config> getConfiguraciones() {
        return configuraciones;
    }

    public boolean isEstaConfigurado() {
        return estaConfigurado;
    }
}
