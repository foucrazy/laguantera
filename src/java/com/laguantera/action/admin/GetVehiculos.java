package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.model.VehiculoResumen;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetVehiculos extends ActionBase{

    public List<VehiculoResumen> listaVehiculos;
    public int total;

    public GetVehiculos() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaVehiculos=bbdd.loadVehiculosResumen();
            this.total=this.listaVehiculos.size();
            this.stopBD();

            debug("GetVehiculos cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
            this.listaVehiculos=null;
            this.total=0;
        }
        return Constantes.RES_OK;
    }

    public List<VehiculoResumen> getListaVehiculos() {
        return listaVehiculos;
    }

    public void setListaVehiculos(List<VehiculoResumen> listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
