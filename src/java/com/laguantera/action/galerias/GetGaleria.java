package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Galerias;
import com.laguantera.dao.model.GaleriaResumen;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 *
 * @author Felix
 */
public class GetGaleria extends ActionBase {

    private Integer idGaleria;
    private GaleriaResumen galeria;

    public GetGaleria() {
    }

    @Override
    public String execute() {
        String resultado = Constantes.RES_ERROR;

        try {
            if (idGaleria != null) {
                startBD();
                this.galeria = bbdd.loadGaleriaCompleta(idGaleria);
                resultado = Constantes.RES_OK;
                stopBD();
            } else {
                resultado = Constantes.RES_INPUT;
                info(getActionMessage(Constantes.RES_INPUT));
            }

        } catch (Exception ex) {
            String args[] = {ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION, args));
            resultado = Constantes.RES_ERROR;
        }
        return resultado;
    }

    @RequiredFieldValidator(message = "${getCommonMessage('faltaIdGaleria')}")
    public Integer getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }

    public GaleriaResumen getGaleria() {
        return galeria;
    }

    public void setGaleria(GaleriaResumen galeria) {
        this.galeria = galeria;
    }
}
