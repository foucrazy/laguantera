package com.laguantera.action.calculadora;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.dao.model.VehiculoResumen;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;
import java.util.Map;

/**
 * Carga los datos necesarios para la calculadora
 * @author Felix Glez
 */
public class LoadCalculadora extends ActionBase {

    private List<VehiculoResumen> listaVehiculos;
    //public List<TiposCombustible> listaTiposCombustible;
    
    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;

        try{
            startBD();
            //this.listaTiposCombustible=bbdd.loadTiposCombustible();

            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
            Integer idUsuario=(Integer)session.get("idUsuario");

            if (idUsuario!=null)
            {                
                this.listaVehiculos = bbdd.loadVehiculos(idUsuario);
                if (this.listaVehiculos!=null)
                {
                    resultado=Constantes.RES_OK;
                    debug(getActionMessage(Constantes.RES_OK));
                }
                else{
                    resultado=Constantes.RES_OK;
                    info(getActionMessage("sinVehiculos"));
                }
            }else{
                resultado=Constantes.RES_OK;
                debug(getActionMessage("anonimo"));
            }
            stopBD();
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }

//    public List<TiposCombustible> getListaTiposCombustible() {
//        return listaTiposCombustible;
//    }
//
//    public void setListaTiposCombustible(List<TiposCombustible> listaTiposCombustible) {
//        this.listaTiposCombustible = listaTiposCombustible;
//    }

    public List<VehiculoResumen> getListaVehiculos() {
        return listaVehiculos;
    }

    public void setListaVehiculos(List<VehiculoResumen> listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }
}
