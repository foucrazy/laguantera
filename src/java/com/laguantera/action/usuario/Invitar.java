package com.laguantera.action.usuario;


import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import java.util.Map;

/**
 * Envio de una invitación por e-mail para que se una a la web.
 * @author Felix Gonzalez de Santos
 */

public class Invitar extends ActionBase{

    private String email;

    public Invitar() {
    }

    @Override
    public String execute() {
        try{
            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión

            if(Servicios.mandarCorreo(email, getActionMessage("asuntoInvitacion"),getActionMessage("mensajeInvitacion")))
            {
                infoCorrecto(getActionMessage(Constantes.RES_OK));
                return Constantes.RES_OK;
            }
            else
            {
                error(getActionMessage(Constantes.RES_ERROR));
                return Constantes.RES_ERROR;
            }
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            return(Constantes.RES_ERROR);
        }
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaEmail')}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}