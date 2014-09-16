package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;

/**
 * Elimina una configuración del portal.
 * @author Félix Glez
 */
public class RemoveConfiguracion extends ActionBase{

    public String nombre;
    public String valor;

    public RemoveConfiguracion() {
    }


    @Override
    public String execute() throws Exception {

        try{                    
            Configuracion config = Configuracion.getInstance();
            if (config.delete(nombre,valor)){
                info("Configuracion eliminada correctamente.");
                return Constantes.RES_OK;       
            }
            else{
                error("No se ha podido eliminar la configuración.");
                return Constantes.RES_ERROR;
            }
        }catch(Exception ex)
        {
            error(ex.toString());
            return Constantes.RES_ERROR;
        }        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }    
}
