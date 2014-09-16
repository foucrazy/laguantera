package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Elimia un multimedia de la base de datos
 * @author Felix Gonzalez de Santos
 */

public class DelMultimedia extends ActionBase{

    private Integer idMultimedia;
    private String resultado=Constantes.RES_ERROR;

    public DelMultimedia() {
    }

    @Override
    public String execute() {
        try{
            this.startBD();
            if (bbdd.delMultimedia(idMultimedia))
            {
                info(getActionMessage(Constantes.RES_OK));
                resultado=Constantes.RES_OK;
                //TODO elimiar fichero del disco duro
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

    @RequiredFieldValidator(message="${getCommonMessage('faltaMultimedia')}")
    public Integer getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(Integer idMultimedia) {
        this.idMultimedia = idMultimedia;
    }



}