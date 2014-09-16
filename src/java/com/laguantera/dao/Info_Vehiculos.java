package com.laguantera.dao;

public class Info_Vehiculos  implements java.io.Serializable {

     private Integer idInfoVehiculo;
     private Modelos modelos;          
     private String motor;
     private Integer tara;
     private Integer cilindrada;
     private float cv;
     private Float kw;
     private TiposCombustible tiposCombustible;
     private double emisiones;
     private double consCiudad;
     private double consMedio;
     private double consCarretera;

    public Info_Vehiculos() {
    }

    public Info_Vehiculos(Modelos modelos, String motor, Integer tara, Integer cilindrada, float cv, Float kw, TiposCombustible tiposCombustible, double emisiones, double consCiudad, double consMedio, double consCarretera) {
        this.modelos = modelos;
        this.motor = motor;
        this.tara = tara;
        this.cilindrada = cilindrada;
        this.cv = cv;
        this.kw = kw;
        this.tiposCombustible = tiposCombustible;
        this.emisiones = emisiones;
        this.consCiudad = consCiudad;
        this.consMedio = consMedio;
        this.consCarretera = consCarretera;
    }    
   
    public Integer getIdVehiculo() {
        return this.idInfoVehiculo;
    }
    
    public void setIdVehiculo(Integer idVehiculo) {
        this.idInfoVehiculo = idVehiculo;
    }
    public TiposCombustible getTiposCombustible() {
        return this.tiposCombustible;
    }
    
    public void setTiposCombustible(TiposCombustible tiposCombustible) {
        this.tiposCombustible = tiposCombustible;
    }
    public Modelos getModelos() {
        return this.modelos;
    }
    
    public void setModelos(Modelos modelos) {
        this.modelos = modelos;
    }

    public String getMotor() {
        return this.motor;
    }
    
    public void setMotor(String motor) {
        this.motor = motor;
    }
    public Integer getTara() {
        return this.tara;
    }
    
    public void setTara(Integer tara) {
        this.tara = tara;
    }

    public Integer getCilindrada() {
        return this.cilindrada;
    }
    
    public void setCilindrada(Integer cilindrada) {
        this.cilindrada = cilindrada;
    }
    public float getCv() {
        return this.cv;
    }
    
    public void setCv(float cv) {
        this.cv = cv;
    }
    public Float getKw() {
        return this.kw;
    }
    
    public void setKw(Float kw) {
        this.kw = kw;
    }

    public double getConsCarretera() {
        return consCarretera;
    }

    public void setConsCarretera(double consCarretera) {
        this.consCarretera = consCarretera;
    }

    public double getConsCiudad() {
        return consCiudad;
    }

    public void setConsCiudad(double consCiudad) {
        this.consCiudad = consCiudad;
    }

    public double getConsMedio() {
        return consMedio;
    }

    public void setConsMedio(double consMedio) {
        this.consMedio = consMedio;
    }

    public double getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(double emisiones) {
        this.emisiones = emisiones;
    }

    public Integer getIdInfoVehiculo() {
        return idInfoVehiculo;
    }

    public void setIdInfoVehiculo(Integer idInfoVehiculo) {
        this.idInfoVehiculo = idInfoVehiculo;
    }
 
}


