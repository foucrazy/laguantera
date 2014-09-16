package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Galerias;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.dao.Vehiculos;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import java.util.List;

/**
 * Obtiene los datos de un vehiculo
 * @author FouCrazy
 */
public class GetVehiculo extends ActionBase{

    private Vehiculos vehiculo;
    private Integer idVehiculo;
    private List<Galerias> listGalerias;
    public List<TiposCombustible> listaCombustibles;

    public GetVehiculo() {
    }

     
    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;

        try{
            if (idVehiculo!=null)
            {
                startBD();

                //TODO: realizar la carga de todos los datos en una única SQL
                this.vehiculo = bbdd.loadVehiculo(idVehiculo);               
                this.vehiculo.getModelos().getNombreModelo();
                this.vehiculo.getModelos().getMarcas().getNombreMarca();
                this.vehiculo.getTiposCombustible().getNombre();
                
                this.listGalerias=bbdd.loadGaleriasVehiculo(idVehiculo);
                if (this.vehiculo!=null)
                {
                    resultado=Constantes.RES_OK;
                    debug(getActionMessage(Constantes.RES_OK));
                }
                else{
                    resultado=Constantes.RES_ERROR;
                    info(getActionMessage(Constantes.RES_ERROR));
                }

                this.listaCombustibles=bbdd.loadTiposCombustible();
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

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaIdVehiculo')}")
    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public List<Galerias> getListGalerias() {
        return listGalerias;
    }

    public void setListGalerias(List<Galerias> listGalerias) {
        this.listGalerias = listGalerias;
    }

    public List<TiposCombustible> getListaCombustibles() {
        return listaCombustibles;
    }

    public void setListaCombustibles(List<TiposCombustible> listaCombustibles) {
        this.listaCombustibles = listaCombustibles;
    }

    
}
