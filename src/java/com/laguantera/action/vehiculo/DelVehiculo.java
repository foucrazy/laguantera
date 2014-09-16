package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Elimia un vehiculo de la base de datos
 * @author Felix Gonzalez de Santos
 */

public class DelVehiculo extends ActionBase{

    private Integer idVehiculo;
    private boolean confirmacion;
    private String resultado=Constantes.RES_ERROR;

    public DelVehiculo() {
    }

    @Override
    public String execute() {
        if (this.confirmacion)
        {
            try{
                startBD();
                if (bbdd.delVehiculo(idVehiculo))
                {
                    info(getActionMessage(Constantes.RES_OK));
                    resultado=Constantes.RES_OK;
                }
                else
                {
                    error(getActionMessage(Constantes.RES_ERROR));
                    resultado=Constantes.RES_ERROR;
                }
                stopBD();
            }catch(Exception ex)
            {
                String arg[]={ex.toString()};
                error(getActionMessage(Constantes.STR_EXCEPCION,arg));
                resultado=Constantes.RES_ERROR;
            }
        }
        else{
            resultado=Constantes.RES_INPUT;
        }
        return resultado;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaVehiculo')}")
    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaConfirmacion')}")
    public boolean getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }
}