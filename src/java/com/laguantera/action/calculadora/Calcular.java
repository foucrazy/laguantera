package com.laguantera.action.calculadora;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class Calcular extends ActionBase implements ServletRequestAware,ServletResponseAware{   
    
    private HttpServletRequest request;
    private HttpServletResponse response;        
    
    private float factorVelocidadMedia=1f;
    private float factorTipoConduccion = 1.0f;
    private int pesoExtra = 0;
    private float litrosConsumidos = 0;
    private double precioCarburante;
    private double costeTotal;
    private StaticInfo info;        
    private Data data;

    public Calcular() {
        super();
    }

    @Override
    public String execute() throws Exception {
        try {
            this.startBD();
            info = StaticInfo.getInstance();
            
            data = new Data();
            data.cargar(request);
            data.setFecha(new Date());
            Integer rutasCalculadas=Integer.valueOf(info.get("rutasCalculadas")+1);
            info.set("rutasCalculadas", String.valueOf(rutasCalculadas));
            data.setRutasCalculadas(rutasCalculadas);            
            data.setUltimaActualizacion(info.get("ultimaActualizacion"));
            
            //Calculo del peso total del vehiculo: Ocupantes + bultos
            pesoExtra = (data.getPasajeros() * Constantes.PESO_PASAJERO) + (data.getBultos() * Constantes.PESO_BULTO);

            //Influencia del tipo de conducción
            switch (data.getTipoConduccion()) {
                case 1: {
                    factorTipoConduccion = 0.90f;
                    break;
                }
                case 2: {
                    factorTipoConduccion = 1.0f;
                    break;
                }
                case 3: {
                    factorTipoConduccion = 1.20f;
                    break;
                }
            }

            String carburante = "";
            double consumoMedio = 0;
            switch (data.getIdCombustible()) {
                case Constantes.COMBUSTIBLE_95: {
                    carburante = "precio95";
                    consumoMedio = info.getDouble("consumoMedio95");
                    break;
                }
                case Constantes.COMBUSTIBLE_98: {
                    carburante = "precio98";
                    consumoMedio = info.getDouble("consumoMedio98");
                    break;
                }
                case Constantes.COMBUSTIBLE_A: {
                    carburante = "precioA";
                    consumoMedio = info.getDouble("consumoMedioA");
                    break;
                }
                case Constantes.COMBUSTIBLE_A_EXTRA: {
                    carburante = "precioAExtra";
                    consumoMedio = info.getDouble("consumoMedioAExtra");
                    break;
                }
            }
            precioCarburante = this.getPrecioCombustible(carburante);
            data.setPrecioCombustible(precioCarburante);

            if (data.getConsumo() == 0) {
                if (data.getPeso() != 0 && data.getPotencia() != 0 && data.getCilindrada() != 0 && data.getIdCombustible() != 0) {
                    //Se hace un analisis estadistico para averiguar un consumo aproximado
                    double cons = bbdd.calcularConsumoMedio(data.getPeso(), data.getPotencia(), data.getCilindrada(), data.getIdCombustible());
                    if (cons != -1) {
                        data.setConsumo(cons);
                    } else {
                        data.setConsumo(consumoMedio);
                    }
                } else {
                    data.setConsumo(consumoMedio);
                }
            }

            //Modificacion del consumo en base a la velocidad media
            this.factorVelocidadMedia=this.calcularFactorVelocidadMedia(data);
            //Litros consumidos durante todo el viaje
            litrosConsumidos = (((float) (((data.getDistancia()/1000) / 100) * (data.getConsumo()*factorVelocidadMedia))) * factorTipoConduccion);
            //teniendo en cuanta el factor peso
            litrosConsumidos += litrosConsumidos * ((pesoExtra * Constantes.FACTOR_PESO) / 100);

            //Obtención del precio del combustible            
            costeTotal = (litrosConsumidos * precioCarburante);

            data.setLitrosConsumidos(litrosConsumidos);            
            data.setCosteTotal(costeTotal);
            
            debug(data.toJSONString());
            this.stopBD();
            return Constantes.RES_OK;
        } catch (Exception e) { 
            error(e.toString());
            e.printStackTrace();
            return Constantes.RES_ERROR;
        }
    }

    /*
     * http://motorgiga.com/c/consumo-combinado-definicion/gmx-niv143-con88320.htm
     * http://pacoros.wordpress.com/2009/08/28/%C2%BFcomo-se-calculan-los-consumos-oficiales/
     * http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CELEX:31980L1268:ES:HTML
     * http://autoconsultorio.com/coches-ecologicos/consumo-homologado-y-real-por-que-gasta-mas-mi-coche-de-lo-que-dice-el-fabricante/
     */
    private float calcularFactorVelocidadMedia(Data data){
        float factor=1f;
        Integer duracionTotal = (data.getDuracionIda()+data.getDuracionVuelta());
        float vmed=data.getDistancia()/duracionTotal;
        vmed=vmed/(3600000); //Metros a Km
        //El consumo medio se considera que en ciudad circula un 37%(50km/h) y 
        //en carretera un 63%(120km/h), lo que supone una vmed de 94,1km/h
        if(vmed>110){
            //mucho mas tiempo en carretera                          
            factor=0.75f;           
        }else if(vmed>100){
            //poco tiempo en ciudad            
            factor=0.9f;
        }else if (vmed>90 && vmed<100){
            //Consumo medio
            factor=1f;
        }else if(vmed>50){
            //Consumo urbano con momentos de circulacion rapida
            factor=1.2f;
        }else{
            //Consumo urbano
            factor=1.35f;
        }
        
        debug("Velocidad media:"+vmed+" aplicando factor:"+factor);
        return factor;
    }
    
    /**
     * Devuelve el precio medio en españa del precio del carburante indicado.
     * @param carburante toma los valores: precio95,precio98,precioA,precioAExtra
     * @return
     */
    public double getPrecioCombustible(String carburante) {
        double precio=0;
        try {
            if (carburante.equals("precio95")) {
                precio = info.getDouble("precioMedio95");
            } else if (carburante.equals("precio98")) {
                precio = info.getDouble("precioMedio98");
            } else if (carburante.equals("precioA")) {
                precio = info.getDouble("precioMedioA");
            } else if (carburante.equals("precioAExtra")) {
                precio = info.getDouble("precioMedioAExtra");
            } 
            
            if(Double.isNaN(precio)){
                throw new Exception("No existe informacion pre-calculada para el carburante:"+carburante);
            }
        } catch (Exception ex) {            
            error(ex.toString());
            precio=bbdd.getPrecioCombustible(carburante);
        }
        return precio;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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
}
