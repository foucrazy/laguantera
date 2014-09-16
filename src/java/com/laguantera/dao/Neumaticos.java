package com.laguantera.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * 
 */
public class Neumaticos  implements java.io.Serializable {


     private Integer idNeumatico;
     private String marca;
     private String modelo;
     private Integer ancho;
     private Integer alto;
     private Integer diametro;
     private Integer carga;
     private String velocidad;
     private String tipo;
     private String extra;
     private String proveedor;
     private Double precio;
     private String enlace;

    public Neumaticos() {
    }

    public Neumaticos(Integer idNeumatico, String marca, String modelo, Integer ancho, Integer alto, Integer diametros, Integer carga, String velocidad, String tipo, String extra, String proveedor, Double precio, String enlace) {
        this.idNeumatico = idNeumatico;
        this.marca = marca;
        this.modelo = modelo;
        this.ancho = ancho;
        this.alto = alto;
        this.diametro = diametros;
        this.carga = carga;
        this.velocidad = velocidad;
        this.tipo = tipo;
        this.extra = extra;
        this.proveedor = proveedor;
        this.precio = precio;
        this.enlace = enlace;
    }

    /**
     * Carga el objeto con los datos provenientes de la extracción del html
     * @param datos Texto como: Pirelli SottoZero 2 275/35 R20 102 W RUN ON FLAT XL
     * @param precio Precio del modelo
     */
    public Neumaticos(String datos, String precio, String proveedor,String tipo,String enlace)
    {
        //Las cadenas de datos tienen el formato:
        //marca [marca2] modelo [modelo2] ancho/alto Rdiametro carga velocidad extra
        String [] detalles = datos.split(" ");
        int posicion=0;
        for(posicion=0;posicion<detalles.length;posicion++)
        {
            Pattern p = Pattern.compile("[0-9][0-9][0-9]/[0-9][0-9]");
            Matcher m = p.matcher(detalles[posicion]);
            if (m.find()){
                break;
            }
        }

        switch(posicion){
            case 3://marca modelo modelo2 ancho/alto Rdiametro carga velocidad extra
            {
                marca=detalles[0];
                modelo=detalles[1]+ " "+detalles[2];
                ancho=Integer.parseInt(detalles[3].substring(0, detalles[3].indexOf('/')));
                alto=Integer.parseInt(detalles[3].substring(detalles[3].indexOf('/')+1));
                diametro=Integer.parseInt(detalles[4].substring(1));
                carga=Integer.parseInt(detalles[5]);
                velocidad=detalles[6];
                if (detalles.length>7)
                    extra=detalles[7];
                break;
            }
            case 4://marca marca2 modelo modelo2 ancho/alto Rdiametro carga velocidad extra
            {
                marca=detalles[0]+" "+detalles[1];
                modelo=detalles[2]+" "+detalles[3];
                ancho=Integer.parseInt(detalles[4].substring(0, detalles[4].indexOf('/')));
                alto=Integer.parseInt(detalles[4].substring(detalles[4].indexOf('/')+1));
                diametro=Integer.parseInt(detalles[5].substring(1));
                carga=Integer.parseInt(detalles[6]);
                velocidad=detalles[7];
                if (detalles.length>8)
                    extra=detalles[8];
                break;
            }
            default://marca modelo ancho/alto Rdiametro carga velocidad extra
            {
                marca=detalles[0];
                modelo=detalles[1];
                ancho=Integer.parseInt(detalles[2].substring(0, detalles[2].indexOf('/')));
                alto=Integer.parseInt(detalles[2].substring(detalles[2].indexOf('/')+1));
                diametro=Integer.parseInt(detalles[3].substring(1));
                carga=Integer.parseInt(detalles[4]);
                velocidad=detalles[5];
                if (detalles.length>6)
                    extra=detalles[6];
            }
        }

        this.precio=Double.parseDouble(precio.substring(0, precio.indexOf(" ")));
        this.tipo=tipo;
        this.proveedor=proveedor;
        this.enlace=enlace;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Integer getDiametro() {
        return diametro;
    }

    public void setDiametro(Integer diametros) {
        this.diametro = diametros;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getIdNeumatico() {
        return idNeumatico;
    }

    public void setIdNeumatico(Integer idNeumatico) {
        this.idNeumatico = idNeumatico;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public String toString() {
        String neumatico=this.marca + " "+ this.modelo + " " + this.ancho+"/"+this.alto+"/"+this.diametro+" " + this.velocidad + " "
                + this.carga + " " + this.extra+ " " + this.precio;
        return neumatico;
    }

    
}


