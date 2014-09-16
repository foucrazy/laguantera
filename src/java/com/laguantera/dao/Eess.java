package com.laguantera.dao;
// Generated 14-mar-2011 22:55:49 by Hibernate Tools 3.2.1.GA



/**
 * Eess generated by hbm2java
 */
public class Eess  implements java.io.Serializable {


     private Integer idEess;
     private String provincia;
     private String localidad;
     private String direccion;
     private String rotulo;
     private String horario;
     private Double latitud;
     private Double longitud;
     private Double precio95;
     private Double precio98;
     private Double precioA;
     private Double precioAextra;

    public Eess() {
    }

    public Eess(String provincia, String localidad, String direccion, String rotulo, String horario, Double latitud, Double longitud, Double precio95, Double precio98, Double precioA, Double precioAextra) {
       this.provincia = provincia;
       this.localidad = localidad;
       this.direccion = direccion;
       this.rotulo = rotulo;
       this.horario = horario;
       this.latitud = latitud;
       this.longitud = longitud;
       this.precio95 = precio95;
       this.precio98 = precio98;
       this.precioA = precioA;
       this.precioAextra = precioAextra;
    }

    @Override
    public boolean equals(Object obj) {        
        try{
            Eess toCompare = (Eess)obj;
            if (toCompare.getProvincia().equals(this.provincia)
                    && toCompare.getLocalidad().equals(this.localidad)
                    && toCompare.getDireccion().equals(this.direccion))
            return true;
            else
                return false;
        }catch(Exception ex)
        { return false; }
    }
   
    public Integer getIdEess() {
        return this.idEess;
    }
    
    public void setIdEess(Integer idEess) {
        this.idEess = idEess;
    }
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    public String getLocalidad() {
        return this.localidad;
    }
    
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getRotulo() {
        return this.rotulo;
    }
    
    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }
    public String getHorario() {
        return this.horario;
    }
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public Double getLatitud() {
        return this.latitud;
    }
    
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return this.longitud;
    }
    
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    public Double getPrecio95() {
        return this.precio95;
    }
    
    public void setPrecio95(Double precio95) {
        this.precio95 = precio95;
    }
    public Double getPrecio98() {
        return this.precio98;
    }
    
    public void setPrecio98(Double precio98) {
        this.precio98 = precio98;
    }
    public Double getPrecioA() {
        return this.precioA;
    }
    
    public void setPrecioA(Double precioA) {
        this.precioA = precioA;
    }
    public Double getPrecioAextra() {
        return this.precioAextra;
    }
    
    public void setPrecioAextra(Double precioAextra) {
        this.precioAextra = precioAextra;
    }




}

