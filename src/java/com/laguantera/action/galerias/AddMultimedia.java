package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Galerias;
import com.laguantera.dao.Multimedias;
import com.laguantera.dao.TiposMultimedia;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Añade un nuevo multimedia (imagen o video) a una galería.
 *
 * @author FouCrazy
 */
public class AddMultimedia extends ActionBase {

    private String titulo;
    private Integer idTipoMultimedia;
    private String ruta;
    private String descripcion;
    private Integer idGaleria;
    private File imagen;
    private String contentType;
    private String filename;

    public AddMultimedia() {
    }

    @Override
    public String execute() {

        String resultado = Constantes.RES_ERROR;
        try {
            //Sube una imagen
            if (idTipoMultimedia == Constantes.DEFAULT_ID_TIPO_IMAGEN) {
                if (imagen != null) {                    
                    String nombreImagen=idGaleria+"_"+(Math.random()*10000);
                    this.ruta = nombreImagen +"."+CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC;
                    
                    Configuracion config = Configuracion.getInstance();
                    ProcesadorImagenes imgPro = new ProcesadorImagenes();

                    String extension = this.getImagenFileName().substring(this.getImagenFileName().indexOf(".") + 1);
                    String path = config.get(Constantes.RUTA_PERMANENTE_PUBLICA) + config.get("dirImgMultimedia");
                    final String rutaOriginal = path + nombreImagen + "_original."+CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC;
                    final String rutaProcesada = path + this.ruta;
                    final String rutaMiniatura = path + "tb_" + this.ruta;

                    //Escalamos la imagen original 
                    BufferedImage bufImg = imgPro.escalarATamanyo(imagen, Integer.valueOf(config.get("alturaImagen")),
                            Integer.valueOf(config.get("anchuraImagen")), extension);
                    imgPro.salvarImagen(bufImg, rutaOriginal, CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);
                    
                    //Procesamos la imagen original anadiendo marcas de agua
                    imgPro.anadirMarcaAgua(rutaOriginal, rutaProcesada);
                    
                    //Creamos miniaturas de la imagen original                   
                    bufImg = imgPro.escalarATamanyo(imagen, Integer.valueOf(config.get("alturaImagenTb")), 
                            Integer.valueOf(config.get("anchuraImagenTb")), extension);
                    imgPro.salvarImagen(bufImg, rutaMiniatura, CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);

                    resultado = Constantes.RES_OK;
                    debug("Imagen subida y redimensionada:" + this.ruta);
                } else {
                    info(getActionMessage("faltaImagen"));
                    resultado = Constantes.RES_INPUT;
                }

            } //Añade un video
            else if (idTipoMultimedia == Constantes.DEFAULT_ID_TIPO_VIDEO) {
                if (ruta == null || ruta.equals("")) {
                    info(getActionMessage("faltaVideo"));
                    resultado = Constantes.RES_INPUT;
                } else {
                    resultado = Constantes.RES_OK;
                }
            }

            if (resultado.equals(Constantes.RES_OK)) {
                Multimedias nuevoMulti = new Multimedias();
                nuevoMulti.setDescripcion(descripcion);
                Galerias gal = new Galerias();
                gal.setIdGaleria(idGaleria);
                nuevoMulti.setGalerias(gal);
                nuevoMulti.setRuta(ruta);
                TiposMultimedia tipoMulti = new TiposMultimedia();
                tipoMulti.setIdTipoMultimedia(idTipoMultimedia);
                nuevoMulti.setTiposMultimedia(tipoMulti);
                nuevoMulti.setTitulo(titulo);

                this.startBD();
                bbdd.addMultimedia(nuevoMulti);
                this.stopBD();
                info(getActionMessage(Constantes.RES_OK));
            }

        } catch (Exception ex) {
            String args[] = {ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION, args));
            resultado = Constantes.RES_ERROR;
        }
        return resultado;
    }

    public String getImagenContentType() {
        return contentType;
    }

    public void setImagenContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getImagenFileName() {
        return filename;
    }

    public void setImagenFileName(String filename) {
        this.filename = filename;
    }

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    @StringLengthFieldValidator(message = "${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}", minLength = "1", maxLength = "200")
    @RequiredStringValidator(message = "${getCommonMessage('faltaDescripcion')}")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @RequiredFieldValidator(message = "${getCommonMessage('faltaGaleria')}")
    public Integer getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @StringLengthFieldValidator(message = "${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}", minLength = "1", maxLength = "30")
    @RequiredStringValidator(message = "${getCommonMessage('faltaTitulo')}")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @RequiredFieldValidator(message = "${getCommonMessage('faltaTipo')}")
    public Integer getIdTipoMultimedia() {
        return idTipoMultimedia;
    }

    public void setIdTipoMultimedia(Integer idTipoMultimedia) {
        this.idTipoMultimedia = idTipoMultimedia;
    }
}
