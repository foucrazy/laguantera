package com.laguantera.action.operaciones;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Elementos;
import com.laguantera.dao.TiposElemento;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


/**
 * Creación de un elemento.
 * @author Felix Gonzalez de Santos
 */

public class AddElemento extends ActionBase{

     private Integer idOperaciones;
     private Integer idTipoElemento;
     private String concepto;

    public AddElemento() {
    }

    @Override
    public String execute() {
        String resultado=Constantes.RES_ERROR;
        try{
            startBD();
            Elementos ele=new Elementos();
            ele.setConcepto(concepto);

            TiposElemento tipoElemento = new TiposElemento();
            tipoElemento.setIdTipoElemento(idTipoElemento);
            
            ele.setTiposElemento(tipoElemento);

            if (bbdd.addElemento(ele))
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

        stopBD();
        return resultado;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="3",maxLength="500")
    @RequiredStringValidator(message="${getCommonMessage('faltaConcepto')}")
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @RequiredFieldValidator(message="getCommonMessage('faltaOperacion')")
    public Integer getIdOperaciones() {
        return idOperaciones;
    }

    public void setIdOperaciones(Integer idOperaciones) {
        this.idOperaciones = idOperaciones;
    }

    @RequiredFieldValidator(message="getCommonMessage('faltaTipo')")
    public Integer getIdTipoElemento() {
        return idTipoElemento;
    }

    public void setIdTipoElemento(Integer idTipoElemento) {
        this.idTipoElemento = idTipoElemento;
    }
        
}