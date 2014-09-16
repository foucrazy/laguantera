package com.laguantera.action.operaciones;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Elimia un tipo de elemento.
 * @author Felix Gonzalez de Santos
 */

public class DelTipoElemento extends ActionBase{

    private Integer idTipoElemento;

    public DelTipoElemento() {
    }

    @Override
    public String execute() {
        String resultado=Constantes.RES_ERROR;
        try{
            this.startBD();
            if (bbdd.delTipoElemento(idTipoElemento))
            {
                info(getActionMessage(Constantes.RES_OK));
                resultado=Constantes.RES_OK;
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

    @RequiredFieldValidator(message="${getCommonMessage('faltaTipoElemento')}")
    public Integer getIdTipoElemento() {
        return idTipoElemento;
    }

    public void setIdTipoElemento(Integer idTipoElemento) {
        this.idTipoElemento = idTipoElemento;
    }
}