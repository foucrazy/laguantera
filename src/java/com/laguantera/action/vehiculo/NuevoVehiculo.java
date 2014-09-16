package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Marcas;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 * Carga los datos necesarios para añadir un nuevo vehiculo.
 * @author Félix Glez
 */
public class NuevoVehiculo extends ActionBase{
        
    public List<Marcas> listaMarcas;
    public List<TiposCombustible> listaCombustibles;

    public NuevoVehiculo() {        
    }


    @Override
    public String execute() throws Exception {
        String resultado=Constantes.RES_ERROR;
        try{        
            this.startBD();            
            this.listaMarcas=bbdd.loadMarcas();
            this.listaCombustibles=bbdd.loadTiposCombustible();
            this.stopBD();
            resultado=Constantes.RES_OK;
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }

    public List<TiposCombustible> getListaCombustibles() {
        return listaCombustibles;
    }

    public void setListaCombustibles(List<TiposCombustible> listaCombustibles) {
        this.listaCombustibles = listaCombustibles;
    }

    public List<Marcas> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marcas> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }
}
