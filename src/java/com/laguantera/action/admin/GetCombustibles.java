package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetCombustibles extends ActionBase{

    public List<TiposCombustible> listaTiposCombustible;


    public GetCombustibles() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaTiposCombustible=bbdd.loadTiposCombustible();
            this.stopBD();

            debug("GetCombustibles cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<TiposCombustible> getListaTiposCombustible() {
        return listaTiposCombustible;
    }

    public void setListaTiposCombustible(List<TiposCombustible> listaTiposCombustible) {
        this.listaTiposCombustible = listaTiposCombustible;
    }
}
