package com.laguantera.action.operaciones;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Elimia un elemento de operacion.
 * @author Felix Gonzalez de Santos
 */

public class DelElemento extends ActionBase{

    private Integer idElemento;

    public DelElemento() {
    }

    @Override
    public String execute() {
        String resultado=Constantes.RES_ERROR;
        try{
            this.startBD();
            if (bbdd.delElemento(idElemento))
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

    @RequiredFieldValidator(message="${getCommonMessage('faltaElemento')}")
    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }
}