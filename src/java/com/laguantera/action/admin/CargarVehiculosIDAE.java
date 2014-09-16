package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;

import com.laguantera.util.DownloadFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.htmlparser.Node;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;


/**
 * Carga de los datos de vehiculos desde la base de datos del IDAE
 * http://www.idae.es/Coches/portal/BaseDatos/Avanzada.aspx
 * @author Felix Gonzalez de Santos
 */

public class CargarVehiculosIDAE extends ActionBase{

    private List<String> nombreMarcas;
    private List<String> nombreModelos;
    private List<String> nombreCombustibles;
    
    private final int GASOLINA=1;
    private final int DIESEL=2;
    
    private TiposCombustible Tipo_Gasolina,Tipo_Diesel;

    
    private String viewState;
    private String eventValidation;
    
    public CargarVehiculosIDAE() {

    }

    @Override
    public String execute() {
        try
        {
            Configuracion config =  Configuracion.getInstance();
            String path = config.get(Constantes.RUTA_PERMANENTE_PRIVADA);
            this.inicializarDatos();
            
            DownloadFile.download("http://www.idae.es/Coches/portal/BaseDatos/Avanzada.aspx", path+"idae.html");
            this.leerMarcas(path+"idae.html");
            this.obtenerTokens(path+"idae.html");
            
            List<String[]> parametros= new ArrayList<String[]>();
            String[] viewstate=new String[2];
            viewstate[0]="__VIEWSTATE";
            viewstate[1]=this.viewState;
            
            String[] eventvalidation=new String[2];
            eventvalidation[0]="__EVENTVALIDATION";
            eventvalidation[1]=this.eventValidation;

            /*String[] marca=new String[2];
            marca[0]="ctl00_ContenidoPagina_ddlMarca";
            marca[1]="12";

            String[] modelo=new String[2];
            modelo[0]="ctl00_ContenidoPagina_ddlModelo";
            modelo[1]="-1";

            String[] combustible=new String[2];
            combustible[0]="ctl00_ContenidoPagina_ddlMotorizacion";
            combustible[1]="-1";*/

            parametros.add(viewstate);
            parametros.add(eventvalidation);
            /*parametros.add(marca);
            parametros.add(modelo);
            parametros.add(combustible);*/
            DownloadFile.download("http://www.idae.es/Coches/portal/BaseDatos/Avanzada.aspx",parametros , path+"idae_tokens.html");            
            
            /*File dirFicherosMarcas=new File(path);
            String[] ficheros = dirFicherosMarcas.list();

            for(int i=0;i<ficheros.length;i++)
            {
                File origenHTML = new File(path+"/IDAE/"+ficheros[i]);
                if (!origenHTML.isDirectory())
                {
                    System.out.println("Procesando fichero:"+origenHTML.getAbsolutePath());
                    this.procesar(origenHTML.getAbsolutePath());
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return "error";
        }
        
        return "ok";
    }
    
    private void inicializarDatos()
    {
        this.startBD();
        
        this.nombreMarcas = new ArrayList<String>();
        this.nombreModelos = new ArrayList<String>();
        this.nombreCombustibles = new ArrayList<String>();                             
        
        this.Tipo_Gasolina=bbdd.loadTipoCombustible(GASOLINA);
        this.Tipo_Diesel=bbdd.loadTipoCombustible(DIESEL);
    }

    public void obtenerTokens(String ruta){
        try{
            Parser parser = new Parser(ruta);
            parser.setEncoding("ISO-8859-1");            
            NodeList inputsViewState = parser.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("input"),new HasAttributeFilter("id", "__VIEWSTATE")));
            if( inputsViewState.size() > 0 ) {
                Tag input = (Tag) inputsViewState.elementAt(0);
                this.viewState=input.getAttribute("value");
                debug("ViewState:"+this.viewState);
            }
            
            NodeList inputsEventValidation = parser.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("input"),new HasAttributeFilter("id", "__EVENTVALIDATION")));
            if( inputsEventValidation.size() > 0 ) {
                Tag input = (Tag) inputsEventValidation.elementAt(0);
                this.eventValidation=input.getAttribute("value");
                debug("EventValidation:"+this.eventValidation);
            }            
            
        }catch(Exception ex){
            error("Error leyendo marcas:"+ex.toString());
        }        
    }
    
    public void leerMarcas(String ruta){
        try{
            Parser parser = new Parser(ruta);
            parser.setEncoding("ISO-8859-1");
            NodeList selects = parser.extractAllNodesThatMatch(new TagNameFilter("select"));            
            Node selectMarca=selects.elementAt(0);
            NodeList options=selectMarca.getChildren();
            SimpleNodeIterator iterOptions = options.elements();
            while (iterOptions.hasMoreNodes()){
                Node option=iterOptions.nextNode();
                if (option.getClass()==OptionTag.class){
                    OptionTag op=(OptionTag)option;                    
                    debug(op.getOptionText() +" - "+ op.getValue());                    
                }
            }
        }catch(Exception ex){
            error("Error leyendo marcas:"+ex.toString());
        }
    }
    
    public void procesar(String ruta)
    {
//<tr class="color1">
//   <td class="principal" align="left">
//        <a href="http://www.idae.es/Coches/portal/BaseDatos/FichaVehiculo.aspx?vehiculo=7942" target="_blank">Alfa Romeo 147 1.6 TS 16V 105 CV  3/5</a>
//   </td>
//   <td>8,1</td>
//   <td>192</td>
//   <td><input name="ctl00$ContenidoPagina$gvCoches$ctl02$ImagenD7942" id="ctl00_ContenidoPagina_gvCoches_ctl02_ImagenD7942" src="AlfaRomeo_files/coches_D.gif" alt="Icono de clasificación D" style="height: 16px; width: 130px; border-width: 0px; cursor: pointer;" type="image"></td>
//   <td><input id="ctl00_ContenidoPagina_gvCoches_ctl02_7942" name="ctl00$ContenidoPagina$gvCoches$ctl02$7942" onclick="javascript:setTimeout('__doPostBack(\'ctl00$ContenidoPagina$gvCoches$ctl02$7942\',\'\')', 0)" type="checkbox"><label for="ctl00_ContenidoPagina_gvCoches_ctl02_7942" id="ctl00_ContenidoPagina_gvCoches_ctl02_Etiqueta7942"></label>        </td>
//</tr>
        try {
            int contador=0;
            Parser parser = new Parser(ruta);
            parser.setEncoding("UTF-8");
            NodeList trs = parser.extractAllNodesThatMatch(new TagNameFilter("tr"));
            SimpleNodeIterator iterTRS = trs.elements();
            while(iterTRS.hasMoreNodes())
            {                
                Node tr=iterTRS.nextNode();
                NodeList tds = tr.getChildren().extractAllNodesThatMatch(new TagNameFilter("td"));

                if (tds.size()>=3)
                {
                    Node modeloNode= tds.elementAt(0);
                    Node consumoNode= tds.elementAt(1);
                    Node emisionesNode= tds.elementAt(2);
                    
                    String modeloSTR=modeloNode.toHtml();

                    if(modeloSTR.length()>100) //La última fila es de relleno y no tiene datos
                    {
                        String identificador=modeloSTR.substring(modeloSTR.indexOf("?vehiculo=")+10,modeloSTR.indexOf("target=")-2);
                        DownloadFile.download("http://www.idae.es/Coches/portal/BaseDatos/FichaVehiculo.aspx?vehiculo="+identificador, "C:/Users/Fou7/Documents/LaGuantera/IDAE/Fichas/"+identificador+".html");

                        Parser parserVehiculo = new Parser("C:/Users/Fou7/Documents/LaGuantera/IDAE/Fichas/"+identificador+".html");
                        //parserVehiculo.setEncoding("UTF-8");
                        NodeList trsVehiculo = parserVehiculo.extractAllNodesThatMatch(new TagNameFilter("tr"));

                        Node combustibleNode=trsVehiculo.elementAt(2);
                        Node cilindradaNode=trsVehiculo.elementAt(3);
                        Node taraNode=trsVehiculo.elementAt(5);
                        Node potenciaNode=trsVehiculo.elementAt(6);

                        String nombreMarca=ruta.substring(ruta.lastIndexOf("/")+1, ruta.length()-4);
                        String nombreModelo=modeloNode.getFirstChild().getFirstChild().getText().replace("'", "-");
                        String consMedio=consumoNode.getFirstChild().getText().replace(',', Constantes.STR_DOT);
                        Double consMedioDouble=Double.parseDouble(consMedio);
                        String emisiones=emisionesNode.getFirstChild().getText().replace(',', Constantes.STR_DOT);
                        Double emisionesDouble=Double.parseDouble(emisiones);
                        String combustible=combustibleNode.getChildren().elementAt(2).getFirstChild().getFirstChild().getText();
                        if(combustible.equals("Gasolina")||combustible.equals("gasolina"))
                            combustible="G";
                        else
                            combustible="D";
                        String cilindrada=cilindradaNode.getChildren().elementAt(2).getFirstChild().getFirstChild().getText();
                        String tara=taraNode.getChildren().elementAt(2).getFirstChild().getFirstChild().getText();
                        String cv=potenciaNode.getChildren().elementAt(2).getFirstChild().getFirstChild().getText().replace(',', Constantes.STR_DOT);
                        String kw=cv.substring(cv.indexOf("(")+1, cv.indexOf(")"));
                        cv=cv.substring(0, cv.indexOf("(")-1);
                        Float cvFloat=Float.parseFloat(cv);                        
                        Float kwFloat=Float.parseFloat(kw);

                        String motor=nombreModelo;

                        /*if (bbdd.mantenimiento(conexion, "INSERT INTO info_vehiculos(marca,modelo,motor,tara,cilindrada,cv,kw,combustible,emisiones,consMedio)"
                        + "VALUES ('"+nombreMarca+"','"+nombreModelo+"','"+motor+"','"+tara+"','"+cilindrada+"','"+cvFloat+"','"+kwFloat+"','"+combustible+"','"+emisionesDouble+"','"+consMedioDouble+"');"))
                            contador++;*/
                    }
                }                
            }
            info("Añadidos "+contador+" vehiculos desde IDAE.");
        } catch (ParserException ex) {
            Logger.getLogger(CargarVehiculosIDAE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}