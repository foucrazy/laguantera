package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Config;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetConfig extends ActionBase{

    public List<Config> listaConfiguraciones;
    public boolean estaConfigurado;

    public GetConfig() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            Configuracion config = Configuracion.getInstance();
            this.listaConfiguraciones=config.getConfiguraciones();
            this.estaConfigurado=config.isEstaConfigurado();

            this.stopBD();

            debug("GetConfig cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<Config> getListaConfiguraciones() {
        return listaConfiguraciones;
    }

    public void setListaConfiguraciones(List<Config> listaConfiguraciones) {
        this.listaConfiguraciones = listaConfiguraciones;
    }

    public boolean estaConfigurado() {
        return estaConfigurado;
    }
}
