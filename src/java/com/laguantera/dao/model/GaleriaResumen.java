package com.laguantera.dao.model;

import java.util.List;
import com.laguantera.dao.Vehiculos;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Felix
 */
public class GaleriaResumen implements java.io.Serializable {

    private Integer idGaleria;
    private String titulo;
    private Integer idVehiculo;
    private String matricula;
    private List<MultimediaResumen> multimedias;

    public GaleriaResumen(Integer idGaleria, String titulo, Integer idVehiculo, String matricula) {
        this.idGaleria = idGaleria;
        this.titulo = titulo;
        this.idVehiculo = idVehiculo;
        this.matricula = matricula;
    }

    static public GaleriaResumen ToGaleriaResumen(Object[] obj) {
        GaleriaResumen galeria = new GaleriaResumen((Integer) obj[0], (String) obj[1], (Integer) obj[2], (String) obj[3]);
        return galeria;
    }

    static public List<MultimediaResumen> ToListMultimediaResumen(List lista) {
        List<MultimediaResumen> lst = new ArrayList<MultimediaResumen>();
        Iterator it = lista.iterator();
        while (it.hasNext()) {
            Object[] obj = (Object[]) it.next();
            MultimediaResumen multi = new MultimediaResumen((Integer) obj[0], (String) obj[1], (Integer) obj[2], (String) obj[3], (String) obj[4], (Integer) obj[5]);
            lst.add(multi);
        }
        return lst;
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

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<MultimediaResumen> getMultimedias() {
        return multimedias;
    }

    public void setMultimedias(List<MultimediaResumen> multimedias) {
        this.multimedias = multimedias;
    }
}
