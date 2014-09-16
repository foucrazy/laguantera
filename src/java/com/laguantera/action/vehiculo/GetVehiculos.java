package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.model.VehiculoResumen;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;
import java.util.Map;

/**
 * Obtiene los vehiculos de un determinado usuario
 * @author FouCrazy
 */
public class GetVehiculos extends ActionBase{

    private List<VehiculoResumen> listVehiculos;

    public GetVehiculos() {
    }

     
    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;

        try{
            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
            Integer idUsuario=(Integer)session.get("idUsuario");

            if (idUsuario!=null)
            {
                startBD();
                this.listVehiculos = bbdd.loadVehiculos(idUsuario);
                if (this.listVehiculos!=null)
                {
                    resultado=Constantes.RES_OK;
                    debug(getActionMessage(Constantes.RES_OK));
                }
                else{
                    resultado=Constantes.RES_ERROR;
                    info(getActionMessage(Constantes.RES_ERROR));
                }
                stopBD();
            }else{
                resultado=Constantes.RES_INPUT;
                info(getActionMessage(Constantes.RES_INPUT));
            }

        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }

    public List<VehiculoResumen> getListVehiculos() {
        return listVehiculos;
    }

    public void setListVehiculos(List<VehiculoResumen> listVehiculos) {
        this.listVehiculos = listVehiculos;
    }
}
