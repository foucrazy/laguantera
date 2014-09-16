package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Neumaticos;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.laguantera.util.DownloadFile;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;



/**
 * Carga de los datos de neumaticos de la web de Oponeo
 * http://www.oponeo.es/neumaticos/(de-verano,de-invierno,todas-las-estaciones)/av,0,0,cantidadResultados,ordenacion,0,pagina
 * @author Felix Gonzalez de Santos
 */

public class CargarNeumaticosOponeo2 extends ActionBase{

    public CargarNeumaticosOponeo2() {

    }

    @Override
    public String execute() {
        try
        {
            this.startBD();
            bbdd.borrarNeumaticos();
            Configuracion config =  Configuracion.getInstance();
            String path = config.get(Constantes.RUTA_PERMANENTE_PUBLICA);            
            String proveedor="Oponeo";
            String url="";

            for(int i=1;i<=316;i++)
            {                
                url="http://www.oponeo.es/neumaticos/de-pasajeros/"+i;
                String ruta=path+proveedor+"-"+i+".html";
                DownloadFile.download(url,ruta);
                this.procesar(ruta,proveedor);
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return "error";
        }
        this.stopBD();
        return "ok";
    }

    public void procesar(String ruta,String proveedor)
    {
        debug("Procesando :"+ruta);        
        try {
            int contador=0;
            Parser parser = new Parser(ruta);
            parser.setEncoding("UTF-8");

            //Los detalles de cada neumaticos están en un div con la clase "LiWyDesc"            
            CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter("DIV.tl_WyDesc");
            NodeList neuDivs = parser.parse(cssFilter);            

            //Por cada neumatico tenemos que extraer la info almacenada en los divs de las clases tl_WyDescHdName y tl_WyDescPrice
            for(int posicion=0;posicion<neuDivs.size();posicion++)
            {
                Node neumatico=neuDivs.elementAt(posicion);

                //Extraemos los datos del neumatico                
                NodeList cabeceras = neumatico.getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter("DIV.tl_WyDescHd"));
                NodeList nombres = cabeceras.elementAt(0).getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter("DIV.tl_WyDescHdName"));
                String nombre=nombres.elementAt(0).getFirstChild().getFirstChild().getText();
                String enlTemp=nombres.elementAt(0).getFirstChild().getText();
                String enlace="www.oponeo.es"+enlTemp.substring(8, enlTemp.length()-1);

                //Extraemos el precio del neumatico
                cabeceras = neumatico.getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter("DIV.tl_WyDescCont"));
                nombres = cabeceras.elementAt(0).getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter("DIV.tl_WyDescPriceBox"));
                NodeList precios = nombres.elementAt(0).getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter("DIV.tl_WyDescPrice"));
                String precio=precios.elementAt(0).getFirstChild().getText();

                Neumaticos newNeuma;
                try{
                    newNeuma = new Neumaticos(nombre, precio,proveedor,"-",enlace);
                }catch(Exception ex){
                    continue;
                }
                     
                if (precio!=null && !precio.equals("")&& bbdd.addNeumatico(newNeuma))
                    contador++;
            }

            info("Añadidos "+contador+" de "+neuDivs.size()+" neumaticos desde "+proveedor);
        } catch (ParserException ex) {
            error(ex.toString());
        }        
    }

}