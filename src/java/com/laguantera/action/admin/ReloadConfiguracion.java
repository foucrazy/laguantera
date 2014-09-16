package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;

/**
 * Provoca la recarga de la configuración del portal.
 * @author Félix Glez
 */
public class ReloadConfiguracion extends ActionBase{


    public ReloadConfiguracion() {        
    }


    @Override
    public String execute() throws Exception {

        try{                    
            Configuracion config = Configuracion.getInstance();
            if (config.reload()){
                info("Configuracion recargada correctamente.");
                return Constantes.RES_OK;
            }else{
                info("No se ha podido recargar la configuración.");
                return Constantes.RES_ERROR;
            }
        }catch(Exception ex)
        {
            error(ex.toString());
            return Constantes.RES_ERROR;
        }        
    }
}
