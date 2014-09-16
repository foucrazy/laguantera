package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Elimia una galeria de la base de datos
 * @author Felix Gonzalez de Santos
 */
public class DelGaleria extends ActionBase {

    private Integer idGaleria;
    private String resultado = Constantes.RES_ERROR;

    public DelGaleria() {
    }

    @Override
    public String execute() {
        try {
            this.startBD();
            if (bbdd.delGaleria(idGaleria)) {
                info(getActionMessage(Constantes.RES_OK));
                resultado = Constantes.RES_OK;
            } else {
                error(getActionMessage(Constantes.RES_ERROR));
                resultado = Constantes.RES_ERROR;
            }
            this.stopBD();
        } catch (Exception ex) {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));            
            resultado = Constantes.RES_ERROR;
        }
        return resultado;
    }

    @RequiredFieldValidator(message = "${getCommonMessage('faltaGaleria')}")
    public Integer getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }
}
