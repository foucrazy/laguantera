package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;

/**
 * Provoca la carga de los parametros del JSON a la BD de la configuración del portal.
 * @author Félix Glez
 */
public class RestoreConfiguracion extends ActionBase{


    public RestoreConfiguracion() {        
    }


    @Override
    public String execute() throws Exception {

        try{                    
            Configuracion config = Configuracion.getInstance();
            if (config.restore()){
                info("Configuracion volcada a base de datos correctamente.");
                config.reload();
                return Constantes.RES_OK;
            }
            else{
                error("Error volcando configuracion a base de datos.");
                return Constantes.RES_ERROR;
            }
        }catch(Exception ex)
        {
            error(ex.toString());
            return Constantes.RES_ERROR;
        }        
    }
}
