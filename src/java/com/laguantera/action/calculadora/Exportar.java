package com.laguantera.action.calculadora;

import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.laguantera.util.mail.Correo;
import java.io.*;
import java.text.Normalizer;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
/**
 *
 * @author Fou7
 */
public class Exportar extends ActionBase implements ServletRequestAware,ServletResponseAware{
    
    private static final String IMPRIMIR = "imprimir";
    private static final String DESCARGAR = "descargar";
    private static final String EMAIL = "email";
    private static final String ERROR = "error";

    private InputStream inputStream;
    private HttpServletRequest request;
    private HttpServletResponse response;        

    private String url;
    
    @Override
    public String execute(){
        String result = null;
        try{
            String toPrinter = request.getParameter("toPrinter");
            String toMail = request.getParameter("toMail");
            Configuracion config = Configuracion.getInstance();
            Data resultados=this.formatearDatos(request);
            String name="F_"+System.currentTimeMillis()+".pdf";
            String outPDF =config.get(Constantes.RUTA_TEMPORAL_PUBLICA) + name;
            debug("Ruta pdf:"+outPDF);
            this.generate(config,resultados, outPDF,toPrinter);
            
            if (toMail==null || toMail.equals("false")){                                        
                if (toPrinter==null || toPrinter.equals("false")){
                    File file = new File(outPDF);
                    this.response.setContentLength((int)file.length());
                    this.inputStream= new FileInputStream(file);
                    debug("Factura generada para descarga.");
                    result = Exportar.DESCARGAR;
                }else{
                    debug("Factura generada para imprimir.");
                    this.url="../"+Constantes.NOMBRE_APP_RECURSOS+"/temp/"+name;
                    debug("Redirigiendo a:"+this.url);
                    result = Exportar.IMPRIMIR;
                }
            }else{
                debug("Enviando resultados por e-mail");
                String destinatario=request.getParameter("email");
                if (destinatario!=null && !destinatario.equals(""))
                {
                    Correo correo=new Correo();
                    correo.setAsunto("Ruta calculada con Laguantera.com");
                    correo.setDireccion(destinatario);
                    correo.setOrigen(config.get("remiteCalculadoraCorreo"));

                    FileReader template;
                    BufferedReader bufferLectura;
                    String contenido="",linea="";                                        
                    template = new FileReader(config.get(Constantes.RUTA_PERMANENTE_PRIVADA)+"mailTemplate.html");

                    bufferLectura=new BufferedReader(template);
                    while ((linea=bufferLectura.readLine())!=null)
                        contenido+=linea;
                                
                    contenido=contenido.replaceAll("@@vehiculo@@",resultados.getNombreVehiculo() );
                    contenido=contenido.replaceAll("@@origen@@", resultados.getOrigen());
                    contenido=contenido.replaceAll("@@destino@@",resultados.getDestino() );
                    contenido=contenido.replaceAll("@@ocupantes@@", String.valueOf(resultados.getPasajeros()));
                    contenido=contenido.replaceAll("@@bultos@@", String.valueOf(resultados.getBultos()));
                    contenido=contenido.replaceAll("@@conduccion@@",resultados.getTipoConduccionName() );
                    contenido=contenido.replaceAll("@@combustible@@",resultados.getCombustibleName() );
                    contenido=contenido.replaceAll("@@consumo@@",resultados.getConsumo()+"l/100Km" );
                    contenido=contenido.replaceAll("@@precio@@", resultados.getPrecioCombustible()+" &euro;/l");
                    contenido=contenido.replaceAll("@@distancia@@", resultados.getDistancia()/1000+" Km");
                    contenido=contenido.replaceAll("@@costetotal@@",resultados.getCosteTotal() +" &euro;");
                    
                    correo.setMensaje(contenido);
                    correo.setRutaAdjunto(outPDF);
                    correo.setNombreAdjunto("Factura.pdf");
                    correo.send();
                    
                    result = Exportar.EMAIL;
                    
                    inputStream = new ByteArrayInputStream(result.getBytes());
                    
                }else result = Exportar.ERROR;
            }
        }catch(JRException jre){
            jre.printStackTrace();
            error("Jasper Excepcion:"+jre.toString());
            result = Exportar.ERROR;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error("Excepcion:"+ex.toString());
            result = Exportar.ERROR;
        }
        
        return result;
    }

    public Data formatearDatos(HttpServletRequest request){
        Data data=new Data();
        data.cargarResultado(request);
        
        data.setOrigen(Normalizer.normalize(data.getOrigen(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
        data.setDestino(Normalizer.normalize(data.getDestino(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
                
        Integer tipo=Integer.valueOf(data.getTipoConduccion());
        switch(tipo){
            case 1:{
                data.setTipoConduccionName("Relajada");
                break;
            }
            case 2:{
                data.setTipoConduccionName("Normal");
                break;
            }
            case 3:{
                data.setTipoConduccionName("Agresiva");
                break;
            }
        }
        
        Integer combustible=Integer.valueOf(data.getIdCombustible());
        switch(combustible){
            case 1:{
                data.setCombustibleName("Gasolina 95");
                break;
            }
            case 2:{
                data.setCombustibleName("Gasolina 98");
                break;
            }
            case 3:{
                data.setCombustibleName("Diesel A");
                break;
            }
            case 4:{
                data.setCombustibleName("Diesel A Extra");
                break;
            }                    
        }          
        
        
        return data;
    }
    
    private void generate(Configuracion config,Data data,String outPDF,String toPrinter) throws JRException{
            //Obtenemos la ruta de ejecucion de esta clase
            //String rutaBase= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            //Reemplazamos el nombre            
            //String fileName = rutaBase.replaceAll("Exportar.class", "recursos/factura.jasper");                       
            String fileName=config.get(Constantes.RUTA_PERMANENTE_PRIVADA)+"factura.jasper";
            debug("Ruta del jasper utilizado:"+fileName);
            
            HashMap parametros = new HashMap();
            parametros.put("nombreVehiculo", data.getNombreVehiculo());
            parametros.put("origen", data.getOrigen());
            parametros.put("destino", data.getDestino());
            parametros.put("ocupantes", data.getPasajeros());
            parametros.put("bultos", data.getBultos());
            parametros.put("conduccion", data.getTipoConduccionName());
            parametros.put("combustible", data.getCombustibleName());
            parametros.put("consumo", data.getConsumo()+" l");
            parametros.put("precio", data.getPrecioCombustible()+" €");
            parametros.put("distancia", (data.getDistancia()/1000)+" Km");
            parametros.put("costeTotal", data.getCosteTotal()+ " €");
            parametros.put("encodeStringIda", data.getEncodeStringIda());
            parametros.put("encodeStringVuelta", data.getEncodeStringVuelta());

            JasperPrint print = JasperFillManager.fillReport(fileName, parametros);

            JRExporter exporterPDF = new JRPdfExporter();    
                        
            if (toPrinter!=null && toPrinter.equals("true")){
                exporterPDF.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");//Imprimir      
            }
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outPDF);
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporterPDF.setParameter(JRExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
            exporterPDF.exportReport();           
            debug("PDF generado");
    }
    
    //Metodos encargados de manejar el request y el response
    @Override
    public void setServletRequest(HttpServletRequest request){
        this.request = request;
      }

      public HttpServletRequest getServletRequest(){
        return request;
      }

    @Override
      public void setServletResponse(HttpServletResponse response){
        this.response = response;
      }

      public HttpServletResponse getServletResponse(){
        return response;
      }

    public InputStream getInputStream() {
            return inputStream;
    }
    
    public String getUrl()
    {
        return url;
    }    
}
