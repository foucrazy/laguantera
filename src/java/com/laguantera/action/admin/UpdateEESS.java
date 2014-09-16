package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.action.calculadora.Calcular;
import com.laguantera.action.calculadora.StaticInfo;
import com.laguantera.dao.Eess;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.laguantera.util.DownloadFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * Descarga desde el mityc la información de las Estaciones de Servicio, analiza su contenido y lo almacena en la base de datos.
 * @author Felix Glez
 */
public class UpdateEESS extends ActionBase{

    public UpdateEESS() {
    }

    @Override
    public String execute() throws Exception {
        return this.procesar();
    }

    public String procesar(){
        try{
            //Descargar información actuaizada
            debug("Descargando ficheros actualizados de las eess.");
            String urlBase="http://geoportal.mityc.es/hidrocarburos/eess/searchTotal.do?tipoCons=2&tipoBusqueda=0&";
            String url95=urlBase+"tipoCarburante=1&textoCarburante=Gasolina95";
            String url98=urlBase+"tipoCarburante=3&textoCarburante=Gasolina98";
            String urlA=urlBase+"tipoCarburante=4&textoCarburante=GasoseloA";
            String urlAExtra=urlBase+"tipoCarburante=5&textoCarburante=GasoleoAExtra";

            String pathSalida=Configuracion.getInstance().get(Constantes.RUTA_PERMANENTE_PRIVADA);
            DownloadFile.download(url95, pathSalida+"gas95.html");
            DownloadFile.download(url98, pathSalida+"gas98.html");
            DownloadFile.download(urlA, pathSalida+"gasA.html");
            DownloadFile.download(urlAExtra, pathSalida+"gasAExtra.html");

            //Guardar información en la BD
            ArrayList<Eess> eessList=new ArrayList<Eess>();
            analizarHTML(pathSalida+"gas95.html",eessList,1);
            analizarHTML(pathSalida+"gas98.html",eessList,2);
            analizarHTML(pathSalida+"gasA.html",eessList,3);
            analizarHTML(pathSalida+"gasAExtra.html",eessList,4);

            this.startBD();
            //TODO backup eess antes de borrar ante posibles fallos
            if (!bbdd.borrarEESS()){
                throw new Exception("No se ha podido vaciar previamente la tabla eess.");
            }

            for(int index=0;index<eessList.size();index++)
            {
                bbdd.addEstacionServicio(eessList.get(index));
                if (index%100==0){
                    debug("Añadidas 100 estaciones.");
                }
            }
            debug("Hay un total de "+eessList.size()+" estaciones de servicio.");
            
            //Actualizacion de la informacion estatica utilizada por la calculadora
            StaticInfo info=StaticInfo.getInstance();
            info.set("ultimaActualizacion", new Date().toString());
            info.set("precioMedio95",String.valueOf(bbdd.getPrecioCombustible("precio95")));
            info.set("precioMedio98",String.valueOf(bbdd.getPrecioCombustible("precio98")));
            info.set("precioMedioA",String.valueOf(bbdd.getPrecioCombustible("precioA")));
            info.set("precioMedioAExtra",String.valueOf(bbdd.getPrecioCombustible("precioAExtra")));
            info.set("consumoMedio95",bbdd.calcularConsumoMedio("95",Constantes.GASOLINA));
            info.set("consumoMedio98",bbdd.calcularConsumoMedio("98",Constantes.GASOLINA));
            info.set("consumoMedioA",bbdd.calcularConsumoMedio("A",Constantes.DIESEL));
            info.set("consumoMedioAExtra",bbdd.calcularConsumoMedio("AExtra",Constantes.DIESEL));
            info.escribir();

            this.stopBD();
            return Constantes.RES_OK;
        }catch(Exception ex)
        {
            this.bbdd.getTransaccion().rollback();
            this.stopBD();
            error(ex.toString());
            return Constantes.RES_ERROR;
        }
    }

    /**
     *
     * @param fichero
     * @param eessList
     * @param tipo 1=Gas95,2=Gas28,3=GasA,4=GasAExtra
     */
    private void analizarHTML(String fichero,ArrayList<Eess> eessList,int tipo)
    {
         try{
            info("Analizando :"+fichero);            
            FileReader ficheroDatos;
            BufferedReader bufferLectura;
            String contenido="",linea="";
            int lineasLeidas=0;
            ficheroDatos = new FileReader(fichero);
            bufferLectura=new BufferedReader(ficheroDatos);

            while ((linea=bufferLectura.readLine())!=null){
                contenido+=linea;
                lineasLeidas++;
                        
                while ((linea=bufferLectura.readLine())!=null && (lineasLeidas<15000 || !linea.contains("</tr>")) ){
                    contenido+=linea;
                    lineasLeidas++;
                }           
                
                debug("Parseando "+lineasLeidas+" lineas del fichero.");
                this.extract(contenido, eessList, tipo);
                lineasLeidas=0;
                contenido="";
                linea="";
            }
            
            bufferLectura.close();
            ficheroDatos.close();            
            debug("Cantidad de eess:"+eessList.size());   
        }catch(Exception ex){
            error(ex.toString());     
            ex.printStackTrace();
        }              
           
    }
    
    private void extract(String contenido,ArrayList<Eess> eessList,int tipo) throws Exception
    {
            Parser parser = new Parser();            
            parser.setInputHTML(contenido);
            NodeList trs = parser.extractAllNodesThatMatch (new TagNameFilter("tr"));
            SimpleNodeIterator iterTRS = trs.elements();
            //Cabeceras
            if (contenido.contains("<html>")){
                debug("Saltando cabeceras.");
                iterTRS.nextNode();
                iterTRS.nextNode();
                iterTRS.nextNode();
                iterTRS.nextNode();
            }

            //Recorremos las filas de resultados
            while (iterTRS.hasMoreNodes())
            {
                try{
                    Node tr=iterTRS.nextNode();

                    //Recorremos las celdas
                    NodeList tds = tr.getChildren().extractAllNodesThatMatch(new TagNameFilter("td"));
                    Eess newES=new Eess();
                    Double precio=0.0;
                    try{
                        newES.setProvincia(tds.elementAt(0).getFirstChild().getText());
                        newES.setLocalidad(tds.elementAt(1).getFirstChild().getText());
                        newES.setDireccion(tds.elementAt(2).getFirstChild().getText());
                        newES.setRotulo(tds.elementAt(6).getFirstChild().getText());
                        Node horario = tds.elementAt(9).getFirstChild();
                        if(horario!=null)
                            newES.setHorario(horario.getText());
                        String precioStr=tds.elementAt(5).getFirstChild().getText().replace(',', Constantes.STR_DOT);
                        precio=Double.parseDouble(precioStr);
                    }catch(NullPointerException nex){
                        error("Nodo con información insuficiente: " + tds.toHtml());
                        continue;
                    }catch(NumberFormatException nfe){
                        error("Formato de campo erroneo: " + tds.toHtml());
                        continue;
                    }

                    if (!eessList.contains(newES))
                    {
                        //debug("Añado nueva estacion de servicio");
                        switch(tipo){
                            case 1: newES.setPrecio95(precio); break;
                            case 2: newES.setPrecio98(precio); break;
                            case 3: newES.setPrecioA(precio); break;
                            case 4: newES.setPrecioAextra(precio); break;
                        }
                        eessList.add(newES);
                    }
                    else
                    {
                        //debug("La estacion de servicio ya estaba añadida, se añade precio.");
                        Eess ESToUpd=(Eess)eessList.get(eessList.indexOf(newES));
                        switch(tipo){
                            case 1: ESToUpd.setPrecio95(precio); break;
                            case 2: ESToUpd.setPrecio98(precio); break;
                            case 3: ESToUpd.setPrecioA(precio); break;
                            case 4: ESToUpd.setPrecioAextra(precio); break;
                        }
                    }
                }catch(NullPointerException npe){
                    error(npe.toString());
                    continue;                    
                }
            }                           
    }
}
