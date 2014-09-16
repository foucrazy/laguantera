package com.laguantera.action.calculadora;

import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Data {
	private Date fecha;
	private float distancia;
        private Integer duracionIda;
        private Integer duracionVuelta;
	private String origen;
	private String destino;
	private int pasajeros;
	private int bultos;
	private int tipoConduccion;
	private boolean idayvuelta;
	private double consumo;
	private int idCombustible;
	private String nombreVehiculo;
        private int cilindrada;
	private int peso;
	private int potencia;
	private float litrosConsumidos;
	private double precioCombustible;
	private double costeTotal;
	
	private String ultimaActualizacion;
	private int rutasCalculadas;	
        
        private String encodeStringIda;
        private String encodeStringVuelta;
        
        private String tipoConduccionName;
        private String combustibleName;
        
	public void cargar (HttpServletRequest request){		
		if (request.getParameter("distancia")==null || request.getParameter("distancia").equals("")){
			this.setDistancia(0);
		}else{
			this.setDistancia(Float.valueOf(request.getParameter("distancia")));
		}
		
		if (request.getParameter("origen")==null || request.getParameter("origen").equals("")){
			this.setOrigen("");
		}else{
			this.setOrigen(request.getParameter("origen"));
		}
		
		if(request.getParameter("destino")==null || request.getParameter("destino").equals("")){
			this.setDestino("");
		}else{
			this.setDestino(request.getParameter("destino"));
		}
		
		if (request.getParameter("pasajeros")==null || request.getParameter("pasajeros").equals("")){
			this.setPasajeros(0);
		}else{
			this.setPasajeros(Integer.valueOf(request.getParameter("pasajeros")));
		}
		
		if (request.getParameter("bultos")==null || request.getParameter("bultos").equals("")){
			this.setBultos(0);
		}else{
			this.setBultos(Integer.valueOf(request.getParameter("bultos")));
		}
		
		if (request.getParameter("tipoConduccion")==null || request.getParameter("tipoConduccion").equals("")){
			this.setTipoConduccion(0);
		}else{
			this.setTipoConduccion(Integer.valueOf(request.getParameter("tipoConduccion")));
		}
		String dobleRecorrido=request.getParameter("idayvuelta");
		if (dobleRecorrido==null || dobleRecorrido.equals("")){
			this.setIdayvuelta(false);
		}else{
			this.setIdayvuelta(true);
		}
		
		if (request.getParameter("consumo")==null || request.getParameter("consumo").equals("")){
			this.setConsumo(0);
		}else{
			this.setConsumo(Float.valueOf(request.getParameter("consumo")));
		}
		
		if (request.getParameter("idCombustible")==null || request.getParameter("idCombustible").equals("")){
			this.setIdCombustible(0);
		}else{
			this.setIdCombustible(Integer.valueOf(request.getParameter("idCombustible")));
		}

		if (request.getParameter("modeloVehiculo")==null || request.getParameter("modeloVehiculo").equals("")){
			this.setNombreVehiculo("Desconocido");
		}else{
			this.setNombreVehiculo(request.getParameter("modeloVehiculo"));
		}                
                
		if (request.getParameter("cilindrada")==null || request.getParameter("cilindrada").equals("")){
			this.setCilindrada(0);
		}else{
			this.setCilindrada(Integer.valueOf(request.getParameter("cilindrada")));
		}
		
		if (request.getParameter("peso")==null || request.getParameter("peso").equals("")){
			this.setPeso(0);
		}else{
			this.setPeso(Integer.valueOf(request.getParameter("peso")));
		}
		
		if (request.getParameter("potencia")==null || request.getParameter("potencia").equals("")){
			this.setPotencia(0);
		}else{
			this.setPotencia(Integer.valueOf(request.getParameter("potencia")));
		}
                
		if (request.getParameter("duracionIda")==null || request.getParameter("duracionIda").equals("")){
			this.setDuracionIda(0);
		}else{
			this.setDuracionIda(Integer.valueOf(request.getParameter("duracionIda")));
		}
                
		if (request.getParameter("duracionVuelta")==null || request.getParameter("duracionVuelta").equals("")){
			this.setDuracionVuelta(0);
		}else{
			this.setDuracionVuelta(Integer.valueOf(request.getParameter("duracionVuelta")));
		}                
                
		if (request.getParameter("encodeStringIda")==null || request.getParameter("encodeStringIda").equals("")){
			this.setEncodeStringIda("");
		}else{
			this.setEncodeStringIda(request.getParameter("encodeStringIda"));
		}      
                
		if (request.getParameter("encodeStringVuelta")==null || request.getParameter("encodeStringVuelta").equals("")){
			this.setEncodeStringVuelta("");
		}else{
			this.setEncodeStringVuelta(request.getParameter("encodeStringVuelta"));
		}                  
	}
	
        public void cargarResultado(HttpServletRequest request){		
		if (request.getParameter("r_distancia")==null || request.getParameter("r_distancia").equals("")){
			this.setDistancia(0);
		}else{
			this.setDistancia(Float.valueOf(request.getParameter("r_distancia")));
		}
		
		if (request.getParameter("r_origen")==null || request.getParameter("r_origen").equals("")){
			this.setOrigen("");
		}else{
			this.setOrigen(request.getParameter("r_origen"));
		}
		
		if(request.getParameter("r_destino")==null || request.getParameter("r_destino").equals("")){
			this.setDestino("");
		}else{
			this.setDestino(request.getParameter("r_destino"));
		}
		
		if (request.getParameter("r_pasajeros")==null || request.getParameter("r_pasajeros").equals("")){
			this.setPasajeros(0);
		}else{
			this.setPasajeros(Integer.valueOf(request.getParameter("r_pasajeros")));
		}
		
		if (request.getParameter("r_bultos")==null || request.getParameter("r_bultos").equals("")){
			this.setBultos(0);
		}else{
			this.setBultos(Integer.valueOf(request.getParameter("r_bultos")));
		}
		
		if (request.getParameter("r_tipoConduccion")==null || request.getParameter("r_tipoConduccion").equals("")){
			this.setTipoConduccion(0);
		}else{
			this.setTipoConduccion(Integer.valueOf(request.getParameter("r_tipoConduccion")));
		}
		String dobleRecorrido=request.getParameter("r_idayvuelta");
		if (dobleRecorrido==null || dobleRecorrido.equals("")){
			this.setIdayvuelta(false);
		}else{
			this.setIdayvuelta(true);
		}
		
		if (request.getParameter("r_consumo")==null || request.getParameter("r_consumo").equals("")){
			this.setConsumo(0);
		}else{
			this.setConsumo(Float.valueOf(request.getParameter("r_consumo")));
		}
		
		if (request.getParameter("r_idCombustible")==null || request.getParameter("r_idCombustible").equals("")){
			this.setIdCombustible(0);
		}else{
			this.setIdCombustible(Integer.valueOf(request.getParameter("r_idCombustible")));
		}

		if (request.getParameter("r_nombreVehiculo")==null || request.getParameter("r_nombreVehiculo").equals("")){
			this.setNombreVehiculo("Desconocido");
		}else{
			this.setNombreVehiculo(request.getParameter("r_nombreVehiculo"));
		}                
                
		if (request.getParameter("r_cilindrada")==null || request.getParameter("r_cilindrada").equals("")){
			this.setCilindrada(0);
		}else{
			this.setCilindrada(Integer.valueOf(request.getParameter("r_cilindrada")));
		}
		
		if (request.getParameter("r_peso")==null || request.getParameter("r_peso").equals("")){
			this.setPeso(0);
		}else{
			this.setPeso(Integer.valueOf(request.getParameter("r_peso")));
		}
		
		if (request.getParameter("r_potencia")==null || request.getParameter("r_potencia").equals("")){
			this.setPotencia(0);
		}else{
			this.setPotencia(Integer.valueOf(request.getParameter("r_potencia")));
		}
                
		if (request.getParameter("r_precioCombustible")==null || request.getParameter("r_precioCombustible").equals("")){
			this.setPrecioCombustible(0);
		}else{
			this.setPrecioCombustible(Double.valueOf(request.getParameter("r_precioCombustible")));
		}                
                
		if (request.getParameter("r_costeTotal")==null || request.getParameter("r_costeTotal").equals("")){
			this.setCosteTotal(0);
		}else{
			this.setCosteTotal(Double.valueOf(request.getParameter("r_costeTotal")));
		}                                
                
		if (request.getParameter("r_duracionIda")==null || request.getParameter("r_duracionIda").equals("")){
			this.setDuracionIda(0);
		}else{
			this.setDuracionIda(Integer.valueOf(request.getParameter("r_duracionIda")));
		}
                
		if (request.getParameter("r_duracionVuelta")==null || request.getParameter("r_duracionVuelta").equals("")){
			this.setDuracionVuelta(0);
		}else{
			this.setDuracionVuelta(Integer.valueOf(request.getParameter("r_duracionVuelta")));
		}                
                
		if (request.getParameter("encodeStringIda")==null || request.getParameter("encodeStringIda").equals("")){
			this.setEncodeStringIda("");
		}else{
			this.setEncodeStringIda(request.getParameter("encodeStringIda"));
		}      
                
		if (request.getParameter("encodeStringVuelta")==null || request.getParameter("encodeStringVuelta").equals("")){
			this.setEncodeStringVuelta("");
		}else{
			this.setEncodeStringVuelta(request.getParameter("encodeStringVuelta"));
		}                  
	}        
        
	double roundDoubleTwoDecimals(double d) {
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            String tmp = twoDForm.format(d).replace(',', '.');
            return Double.valueOf(tmp);
	}	

	float roundFloatTwoDecimals(float d) {
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            String tmp = twoDForm.format(d).replace(',', '.');
            return Float.valueOf(tmp);
	}		
	
	public String toJSONString() throws JSONException{
		JSONObject resultado=new JSONObject();
		
		resultado.put("fecha",this.getFecha().toLocaleString());
		resultado.put("distancia",this.getDistancia());
		resultado.put("origen",this.getOrigen());
		resultado.put("destino",this.getDestino());
		resultado.put("pasajeros",this.getPasajeros());
		resultado.put("bultos",this.getBultos());
		resultado.put("tipoConduccion",this.getTipoConduccion());
		resultado.put("idayvuelta",this.isIdayvuelta());
		resultado.put("consumo",roundDoubleTwoDecimals(this.getConsumo()));
		resultado.put("idCombustible",this.getIdCombustible());
                resultado.put("nombreVehiculo",this.getNombreVehiculo());
		resultado.put("cilindrada",this.getCilindrada());
		resultado.put("peso",this.getPeso());
		resultado.put("potencia",this.getPotencia());
		resultado.put("litrosConsumidos",roundFloatTwoDecimals(this.getLitrosConsumidos()));
		resultado.put("precioCombustible",roundDoubleTwoDecimals(this.getPrecioCombustible()));
		resultado.put("costeTotal",roundDoubleTwoDecimals(this.getCosteTotal()));
		resultado.put("rutasCalculadas",this.getRutasCalculadas());
		resultado.put("ultimaActualizacion",this.getUltimaActualizacion());
                resultado.put("duracionIda",this.getDuracionIda());
                resultado.put("duracionVuelta",this.getDuracionVuelta());                
                resultado.put("encodeStringIda",this.getEncodeStringIda());
                resultado.put("encodeStringVuelta",this.getEncodeStringVuelta());
		return resultado.toString();		
	}
	
	public Date getFecha() {
		return fecha;
	}
	public float getDistancia() {            
		return distancia;
	}
	public String getOrigen() {
		return origen;
	}
	public String getDestino() {
		return destino;
	}
	public int getPasajeros() {
		return pasajeros;
	}
	public int getBultos() {
		return bultos;
	}
	public int getTipoConduccion() {
		return tipoConduccion;
	}
	public boolean isIdayvuelta() {
		return idayvuelta;
	}
	public double getConsumo() {
		return this.roundDoubleTwoDecimals(consumo);
	}
	public int getIdCombustible() {
		return idCombustible;
	}
	public int getCilindrada() {
		return cilindrada;
	}
	public int getPeso() {
		return peso;
	}
	public int getPotencia() {
		return potencia;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public void setPasajeros(int pasajeros) {
		this.pasajeros = pasajeros;
	}
	public void setBultos(int bultos) {
		this.bultos = bultos;
	}
	public void setTipoConduccion(int tipoConduccion) {
		this.tipoConduccion = tipoConduccion;
	}
	public void setIdayvuelta(boolean idayvuelta) {
		this.idayvuelta = idayvuelta;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	public void setIdCombustible(int idCombustible) {
		this.idCombustible = idCombustible;
	}
	public void setCilindrada(int cilindrada) {
		this.cilindrada = cilindrada;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public float getLitrosConsumidos() {
		return litrosConsumidos;
	}

	public double getPrecioCombustible() {
		return this.roundDoubleTwoDecimals(precioCombustible);
	}

	public double getCosteTotal() {
		return this.roundDoubleTwoDecimals(costeTotal);
	}

	public void setLitrosConsumidos(float litrosConsumidos) {
		this.litrosConsumidos = litrosConsumidos;
	}

	public void setPrecioCombustible(double precioCombustible) {
		this.precioCombustible = precioCombustible;
	}

	public void setCosteTotal(double costeTotal) {
		this.costeTotal = costeTotal;
	}

	public String getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public int getRutasCalculadas() {
		return rutasCalculadas;
	}

	public void setUltimaActualizacion(String ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public void setRutasCalculadas(int rutasCalculadas) {
		this.rutasCalculadas = rutasCalculadas;
	}

    public String getNombreVehiculo() {
        return nombreVehiculo;
    }

    public void setNombreVehiculo(String nombreVehiculo) {
        this.nombreVehiculo = nombreVehiculo;
    }

    public Integer getDuracionIda() {
        return duracionIda;
    }

    public void setDuracionIda(Integer duracionIda) {
        this.duracionIda = duracionIda;
    }

    public Integer getDuracionVuelta() {
        return duracionVuelta;
    }

    public void setDuracionVuelta(Integer duracionVuelta) {
        this.duracionVuelta = duracionVuelta;
    }
    
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));         
    }    
    
    float roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Float.valueOf(twoDForm.format(d));         
    }

    public String getEncodeStringIda() {
        return encodeStringIda;
    }

    public void setEncodeStringIda(String encodeStringIda) {
        this.encodeStringIda = encodeStringIda;
    }

    public String getEncodeStringVuelta() {
        return encodeStringVuelta;
    }

    public void setEncodeStringVuelta(String encodeStringVuelta) {
        this.encodeStringVuelta = encodeStringVuelta;
    }

    public String getCombustibleName() {
        return combustibleName;
    }

    public void setCombustibleName(String combustibleName) {
        this.combustibleName = combustibleName;
    }

    public String getTipoConduccionName() {
        return tipoConduccionName;
    }

    public void setTipoConduccionName(String tipoConduccionName) {
        this.tipoConduccionName = tipoConduccionName;
    }
    
    
}
