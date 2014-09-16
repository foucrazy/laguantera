package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Modelos;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetModelos extends ActionBase{

    public List<Modelos> listaModelos;

    public GetModelos() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaModelos=bbdd.loadModelos();
            this.stopBD();            
            debug("GetModelos cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<Modelos> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<Modelos> listaModelos) {
        this.listaModelos = listaModelos;
    }
}
