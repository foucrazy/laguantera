package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposMultimedia;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 * Obtiene los tipos de multimedia existentes
 *
 * @author FouCrazy
 */
public class LoadTiposMultimedia extends ActionBase {

    private Integer idGaleria;
    private List<TiposMultimedia> listTiposMultimedia;

    public LoadTiposMultimedia() {
    }

    @Override
    public String execute() {
        String resultado = Constantes.RES_ERROR;

        try {
            startBD();
            this.listTiposMultimedia = bbdd.loadTiposMultimedia();
            if (this.listTiposMultimedia != null) {
                resultado = Constantes.RES_OK;
                debug(getActionMessage(Constantes.RES_OK));
            } else {
                resultado = Constantes.RES_ERROR;
                info(getActionMessage(Constantes.RES_ERROR));
            }
            stopBD();

        } catch (Exception ex) {
            String args[] = {ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION, args));
            resultado = Constantes.RES_ERROR;
        }
        return resultado;
    }
   
    public Integer getIdGaleria() {
        return idGaleria;
    }    

    public void setIdGaleria(Integer idGaleria) {
        this.idGaleria = idGaleria;
    }    
    
    public List<TiposMultimedia> getListTiposMultimedia() {
        return listTiposMultimedia;
    }

    public void setListTiposMultimedia(List<TiposMultimedia> listTiposMultimedia) {
        this.listTiposMultimedia = listTiposMultimedia;
    }
}
