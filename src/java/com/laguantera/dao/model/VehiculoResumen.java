package com.laguantera.dao.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class VehiculoResumen  implements java.io.Serializable {


     private Integer idVehiculo;
     private String marca;
     private String modelo;
     private String matricula;
     private String imagen;


    public VehiculoResumen() {
    }

    public VehiculoResumen(Integer idVehiculo, String marca, String modelo, String matricula,String imagen) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.imagen=imagen;
    }

    static public List<VehiculoResumen> ToListVehiculoResumen(List lista){
         List<VehiculoResumen> lst=new ArrayList<VehiculoResumen>();
         Iterator it = lista.iterator();
         while (it.hasNext())
         {
             Object[] obj = (Object[])it.next();
             VehiculoResumen vehi = new VehiculoResumen((Integer)obj[0], (String)obj[1],
                     (String)obj[2], (String)obj[3], (String)obj[4]);
             lst.add(vehi);
         }
         return lst;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public Integer getIdVehiculo() {
        return this.idVehiculo;
    }
    
    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }
    
    public String getMarcaNormalizada() {
        return marca.replaceAll(" ", "").replaceAll("_", "").replaceAll("-", "").toLowerCase();
    }    

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}


