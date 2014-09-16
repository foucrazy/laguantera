package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposCoste;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetCostes extends ActionBase{

    public List<TiposCoste> listaTiposCoste;

    public GetCostes() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaTiposCoste=bbdd.loadTiposCostes();
            this.stopBD();
            debug("GetCostes cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<TiposCoste> getListaTiposCoste() {
        return listaTiposCoste;
    }

    public void setListaTiposCoste(List<TiposCoste> listaTiposCoste) {
        this.listaTiposCoste = listaTiposCoste;
    }
}
