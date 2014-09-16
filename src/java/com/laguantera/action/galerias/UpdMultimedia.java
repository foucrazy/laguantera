package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Galerias;
import com.laguantera.dao.Multimedias;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


/**
 * Actualiza la información de un multimedia
 * @author Felix Gonzalez de Santos
 */
public class UpdMultimedia extends ActionBase{

    private String titulo;
    private String descripcion;
    private Integer idGaleria;
    private Integer idMultimedia;

    public UpdMultimedia() {
    }

    @Override
    public String execute() {

        String resultado=Constantes.RES_ERROR;
        try
        {
            Multimedias mulToUpd = new Multimedias();
            Galerias gal = new Galerias();
            gal.setIdGaleria(idGaleria);
            mulToUpd.setGalerias(gal);
            mulToUpd.setTitulo(titulo);
            mulToUpd.setDescripcion(descripcion);
            mulToUpd.setIdMultimedia(idMultimedia);

            this.startBD();
            if (bbdd.updMultimedia(mulToUpd))
            {                
                resultado=Constantes.RES_OK;
                info(getActionMessage(Constantes.RES_OK));
            }
            else
            {
                error(getActionMessage(Constantes.RES_ERROR));
                resultado=Constantes.RES_ERROR;
            }
            this.stopBD();
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }


    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="1",maxLength="200")
    @RequiredStringValidator(message="${getCommonMessage('faltaDescripcion')}")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaGaleria')}")
    public Integer getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="1",maxLength="30")
    @RequiredStringValidator(message="${getCommonMessage('faltaTitulo')}")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaMultimedia')}")
    public Integer getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(Integer idMultimedia) {
        this.idMultimedia = idMultimedia;
    }   
}
