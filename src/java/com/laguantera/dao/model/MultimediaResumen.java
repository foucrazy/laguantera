package com.laguantera.dao.model;

import com.laguantera.dao.Galerias;
import com.laguantera.dao.TiposMultimedia;

/**
 *
 * @author Felix
 */
public class MultimediaResumen {
     private Integer idMultimedia;
     private Integer idTipoMultimedia;
     private Integer idGaleria;
     private String titulo;
     private String ruta;
     private String descripcion;

    public MultimediaResumen(Integer idMultimedia, String titulo, Integer idTipoMultimedia,String ruta,String descripcion, Integer idGaleria) {
        this.idMultimedia = idMultimedia;
        this.idTipoMultimedia = idTipoMultimedia;
        this.idGaleria = idGaleria;
        this.titulo = titulo;
        this.ruta = ruta;
        this.descripcion = descripcion;
    }     
     
    public Integer getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(Integer idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    public Integer getIdTipoMultimedia() {
        return idTipoMultimedia;
    }

    public void setIdTipoMultimedia(Integer idTipoMultimedia) {
        this.idTipoMultimedia = idTipoMultimedia;
    }

    public Integer getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }          
}
