package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposElemento;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetElementos extends ActionBase{

    public List<TiposElemento> listaTiposElemento;

    public GetElementos() {
    }

    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaTiposElemento=bbdd.loadTiposElemento();
            this.stopBD();
            debug("GetElementos cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<TiposElemento> getListaTiposElemento() {
        return listaTiposElemento;
    }

    public void setListaTiposElemento(List<TiposElemento> listaTiposElemento) {
        this.listaTiposElemento = listaTiposElemento;
    }

}
