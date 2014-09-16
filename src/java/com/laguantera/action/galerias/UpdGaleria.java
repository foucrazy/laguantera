package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Galerias;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

/**
 * Actualiza una galería
 * @author FouCrazy
 */
public class UpdGaleria extends ActionBase{

     private Integer idGaleria;
     private String titulo;     

    public UpdGaleria() {
    }


    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;

        try{
            this.startBD();

            Galerias gToUpd = new Galerias();
            gToUpd.setTitulo(titulo);
            gToUpd.setIdGaleria(idGaleria);

            if (bbdd.updGaleria(gToUpd))
            {
                resultado=Constantes.RES_OK;
                info(getActionMessage(Constantes.RES_OK));
            }
            else
            {
                resultado=Constantes.RES_ERROR;
                error(getActionMessage(Constantes.RES_ERROR));
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


    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="1",maxLength="30")
    @RequiredStringValidator(message="${getCommonMessage('faltaTitulo')}")
    public String getTitulo() {
        return titulo;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaGaleria')}")
    public Integer getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



}
