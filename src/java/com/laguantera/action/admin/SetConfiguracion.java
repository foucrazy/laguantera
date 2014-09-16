package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.HibernateUtil;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;


/**
 * Establece nuevos valores de configuración o bien actualiza alguno ya existente.
 * @author Felix Gonzalez de Santos
 */

public class SetConfiguracion extends ActionBase{

    public String nombre;
    public String valor;
    public boolean nueva;

    public SetConfiguracion() {
    }

    @Override
    public String execute()
    {
        try{
            Configuracion config = Configuracion.getInstance();
            boolean resultado;
            if (nueva)
                resultado=config.set(nombre, valor);
            else
                resultado=config.update(nombre,valor);
            
            resultado=resultado && config.escribir() && config.reload();
            //HibernateUtil.setSessionFactory(null);//Para que si se han cambiado datos de la BD tengan efecto inmediato.

            if (resultado){
                info("Configuracion actualizada.");
                return Constantes.RES_OK;
            }else{
                error("No se ha podido guardar la configuración.");
                return Constantes.RES_ERROR;
            }
        }catch(Exception ex){
            error("No se ha podido guardar la configuracion:"+ex.getMessage());
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

    public boolean isNueva() {
        return nueva;
    }

    public void setNueva(boolean nueva) {
        this.nueva = nueva;
    }
}
