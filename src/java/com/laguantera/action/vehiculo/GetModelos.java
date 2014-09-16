package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Modelos;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import java.util.List;

/**
 * Obtiene los modelos de una determinada marca.
 * @author FouCrazy
 */
public class GetModelos extends ActionBase{

    private List<Modelos> listaModelos;
    private Integer idMarca;

    public GetModelos() {
    }
     
    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;
        try{
            this.startBD();
            this.listaModelos=bbdd.loadModelos(this.idMarca);
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

    public List<Modelos> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<Modelos> listaModelos) {
        this.listaModelos = listaModelos;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaIdMarca')}")
    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

}
