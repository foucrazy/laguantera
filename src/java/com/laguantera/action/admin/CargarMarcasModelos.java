package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Info_Vehiculos;
import com.laguantera.dao.Marcas;
import com.laguantera.dao.Modelos;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;


/**
 * Carga de los datos de marcas y modelos de vehiculos a partir de XMLs
 * @author Felix Gonzalez de Santos
 */

public final class CargarMarcasModelos extends ActionBase{

    private List<String> nombreMarcas;
    private List<String> nombreModelos;
    
    
    private final int GASOLINA=1;
    private final int DIESEL=2;
    
    private TiposCombustible Tipo_Gasolina,Tipo_Diesel;

    public CargarMarcasModelos() {

    }

    @Override
    public String execute() {
        try
        {
            Configuracion config = Configuracion.getInstance();
            String rutaBase = config.get(Constantes.RUTA_PERMANENTE_PUBLICA)+"WEB-INF/classes/com/laguantera/resources/XML/";

            File dirFicherosMarcas=new File(rutaBase);
            String[] ficheros = dirFicherosMarcas.list();

            this.inicializarDatos();
            for(int i=0;i<ficheros.length;i++)
            {
                try {
                    File origenXML = new File(rutaBase+ficheros[i]);
                    if (!origenXML.isDirectory())
                    {
                        info("Procesando fichero:"+origenXML.getAbsolutePath());
                        SAXBuilder builder = new SAXBuilder(false);
                        Document documento = builder.build(origenXML);

                        Element raiz = documento.getRootElement();

                        //'marca' representa bloque de marca
                        ElementFilter filtroElementos = new ElementFilter("marca");
                        this.procesar(raiz.getDescendants(filtroElementos));
                    }

                } catch (IOException ex) {
                    error(ex.toString());

                } catch (JDOMException ex) {
                    error(ex.toString());
                }
            }
            this.stopBD();
        } catch (Exception e) {
            error(e.toString());
            e.printStackTrace();
            addActionError(e.getMessage());
            return Constantes.RES_ERROR;
        }
        
        return Constantes.RES_OK;
    }

    private void anadeMarca(Marcas nuevaMarca)
    {       
        if (!this.nombreMarcas.contains(nuevaMarca.getNombreMarca()))
        {
            this.bbdd.addMarca(nuevaMarca);            
            this.nombreMarcas.add(nuevaMarca.getNombreMarca());
        }        
    }

    /*private void anadeModelo(Modelos nuevoModelo)
    {
        if (!this.nombreModelos.contains(nuevoModelo.getNombreModelo()))
        {
            this.bbdd.addModelos(nuevoModelo);            
            this.nombreModelos.add(nuevoModelo.getNombreModelo());
        }       
    } */ 
    
    /*private void anadeCombustible(TiposCombustible nuevoCombustible){
        if (!this.nombreCombustibles.contains(nuevoCombustible.getNombre()))
        {
            this.bbdd.addTipoCombustible(nuevoCombustible);
            this.bbdd.session.flush();
            this.nombreCombustibles.add(nuevoCombustible.getNombre());
        }               
    }*/

    private void inicializarDatos()
    {
        this.startBD();
        //this.bbdd.session.setFlushMode(FlushMode.ALWAYS);
        
        this.nombreMarcas = new ArrayList<String>();
        this.nombreModelos = new ArrayList<String>();        
        
        bbdd.borrarInfoVehiculos();
        bbdd.borrarModelos();
        bbdd.borrarMarcas();                  
        
        this.Tipo_Gasolina=bbdd.loadTipoCombustible(GASOLINA);
        this.Tipo_Diesel=bbdd.loadTipoCombustible(DIESEL);
    }

    public void procesar(Iterator iter)
    {
        //Bucle marcas
        while (iter.hasNext())
        {
            Marcas nuevaMarca=new Marcas();
            Element marca = (Element)iter.next();            
            String nombreMarca = marca.getChild("AW").getValue();//AW es el nombre de la marca            
            nuevaMarca.setNombreMarca(nombreMarca);
            //this.anadeMarca(nuevaMarca);
                        
            //Bucle modelos
            //B representa bloque de modelo
            ElementFilter filtroB = new ElementFilter("modelo");
            Iterator modelos = marca.getDescendants(filtroB);
            while (modelos.hasNext())
            {
                Modelos nuevoModelo=new Modelos();
                nuevoModelo.setMarcas(nuevaMarca);
                Element modelo = (Element)modelos.next();               
                String nombreModelo = modelo.getChild("AX").getValue();//AX es el nombre del modelos                
                nuevoModelo.setNombreModelo(nombreModelo);    
                nuevaMarca.addModeloses(nuevoModelo);
                //this.anadeModelo(nuevoModelo);
                
                //Bucle de versiones de cada modelo
                ElementFilter filtroAZ = new ElementFilter("AZ");
                Iterator versiones = modelo.getDescendants(filtroAZ);
                while (versiones.hasNext())
                {
                    try{
                        Element version = (Element)versiones.next();
                        String motor = version.getChild("Motorizacion").getValue();
                        Integer tara = convertToInteger(version.getChild("pesoVacio").getValue());
                        Integer cilindrada = convertToInteger(version.getChild("cilindrada").getValue());
                        float cv = convertToFloat(version.getChild("cv").getValue());
                        float kw = convertToFloat(version.getChild("kw").getValue());
                        String combustible = version.getChild("combustible").getValue();
                        double emisiones = convertToDouble(version.getChild("EmisionesCo2").getValue());
                        double consCarretera = convertToDouble(version.getChild("Cons1").getValue());
                        double consMedio = convertToDouble(version.getChild("Cons2").getValue());
                        double consCiudad = convertToDouble(version.getChild("Cons3").getValue());

                        Info_Vehiculos nuevoInfoVehiculo = new Info_Vehiculos();
                        nuevoInfoVehiculo.setMotor(motor);
                        nuevoInfoVehiculo.setTara(tara);
                        nuevoInfoVehiculo.setCilindrada(cilindrada);
                        nuevoInfoVehiculo.setCv(cv);
                        nuevoInfoVehiculo.setKw(kw);        
                        
                        if (combustible.equalsIgnoreCase("G")) nuevoInfoVehiculo.setTiposCombustible(this.Tipo_Gasolina);
                        else if (combustible.equalsIgnoreCase("D")) nuevoInfoVehiculo.setTiposCombustible(this.Tipo_Diesel);
                        else info("Vehiculo con combustible desconocido!");
                        
                        nuevoInfoVehiculo.setEmisiones(emisiones);
                        nuevoInfoVehiculo.setConsCarretera(consCarretera);
                        nuevoInfoVehiculo.setConsCiudad(consCiudad);
                        nuevoInfoVehiculo.setConsMedio(consMedio);
                        nuevoInfoVehiculo.setModelos(nuevoModelo);
                        nuevoModelo.addInfoVehiculoses(nuevoInfoVehiculo);
                        nuevoInfoVehiculo.setModelos(nuevoModelo);
                        //this.bbdd.addInfoVehiculo(nuevoInfoVehiculo);      
                    }catch (Exception ex){
                        error(ex.toString());
                        continue;
                    }
                }                      
            }      
            this.anadeMarca(nuevaMarca);
            this.bbdd.session.getTransaction().commit();
            this.bbdd.session.getTransaction().begin();
        }
    }
    
    private Integer convertToInteger(String numero){
        if (numero!=null && !numero.equals("")){
            return Integer.valueOf(numero);
        }else{
            return 0;
        }    
    }
    
   private Double convertToDouble(String numero){
        if (numero!=null && !numero.equals("")){
            return Double.parseDouble(numero);
        }else{
            return 0.0;
        }    
    }    
   
   private Float convertToFloat(String numero){
        if (numero!=null && !numero.equals("")){
            return Float.parseFloat(numero);
        }else{
            return 0f;
        }    
    }   
}