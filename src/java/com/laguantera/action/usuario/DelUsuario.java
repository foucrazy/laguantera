package com.laguantera.action.usuario;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import java.util.Map;

/**
 * Elimia a un usuario de la base de datos
 * @author Felix Gonzalez de Santos
 */

public class DelUsuario extends ActionBase{

    private Integer idUsuario;
    private boolean confirmacion;
    private String answer;
    private String resultado=Constantes.RES_ERROR;

    public DelUsuario() {
    }

    @Override
    public String execute() {
        Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
        
        //CAPTCHA
        if (Servicios.validarCaptcha(session, answer))
        {
            if (this.confirmacion)
            {
                try{
                    startBD();
                    if (bbdd.delUsuario(idUsuario))
                    {
                        //Eliminamos la sesión de usuario
                        session.remove("usuario");
                        session.remove("idUsuario");
                        info(getActionMessage(Constantes.RES_OK));
                        resultado=Constantes.RES_OK;
                    }
                    else
                    {
                        error(getActionMessage(Constantes.RES_ERROR));
                        resultado=Constantes.RES_ERROR;
                    }
                    stopBD();
                }catch(Exception ex)
                {
                    String args[]={ex.toString()};
                    error(getActionMessage(Constantes.STR_EXCEPCION,args));
                    resultado=Constantes.RES_ERROR;
                }
            }
            else{
                resultado=Constantes.RES_INPUT;
            }
        }else{
            error(getActionMessage("errorCaptcha"));
            return Constantes.RES_INPUT;
        }
        
        return resultado;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaUsuario')}")
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaConfirmacion')}")
    public boolean getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }
    
    @RequiredStringValidator(message="${getCommonMessage('faltaCaptcha')}")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}