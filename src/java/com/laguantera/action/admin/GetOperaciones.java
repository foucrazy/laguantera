package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposOperacion;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetOperaciones extends ActionBase{

    public List<TiposOperacion> listaTiposOperacion;

    public GetOperaciones() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaTiposOperacion=bbdd.loadTiposOperacion();
            this.stopBD();

            debug("GetOperaciones cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<TiposOperacion> getListaTiposOperacion() {
        return listaTiposOperacion;
    }

    public void setListaTiposOperacion(List<TiposOperacion> listaTiposOperacion) {
        this.listaTiposOperacion = listaTiposOperacion;
    }

}
