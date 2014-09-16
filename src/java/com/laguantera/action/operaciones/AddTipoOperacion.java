package com.laguantera.action.operaciones;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposElemento;
import com.laguantera.dao.TiposOperacion;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


/**
 * Creación de un tipo de operacion.
 * @author Felix Gonzalez de Santos
 */

public class AddTipoOperacion extends ActionBase{

     private String nombre;
     private String descripcion;

    public AddTipoOperacion() {
    }

    @Override
    public String execute() {
        String resultado=Constantes.RES_ERROR;
        
        try{

            this.startBD();
            TiposOperacion nuevoTipo=new TiposOperacion();
            nuevoTipo.setNombre(nombre);
            nuevoTipo.setDescripcion(descripcion);

            if (bbdd.addTipoOperacion(nuevoTipo))
            {
                info(getActionMessage(Constantes.RES_OK));
                resultado=Constantes.RES_OK;
            }
            else
            {
                error(getActionMessage(Constantes.RES_ERROR));
                resultado=Constantes.RES_ERROR;
            }
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }

        this.stopBD();
        return resultado;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="3",maxLength="100")
    @RequiredStringValidator(message="${getCommonMessage('faltaDescripcion')}")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="3",maxLength="40")
    @RequiredStringValidator(message="${getCommonMessage('faltaNombre')}")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
        
}