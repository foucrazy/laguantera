package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Marcas;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetMarcas extends ActionBase{

    public List<Marcas> listaMarcas;

    public GetMarcas() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaMarcas = bbdd.loadMarcas();
            this.stopBD();

            debug("GetMarcas cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }

    public List<Marcas> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marcas> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }
}
