package com.laguantera.action.usuario;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import java.util.Map;


/**
 * Cambio de la contraseña actual del usuario al que esta asociada una determinada
 * dirección de correo electrónico y es notificada vía e-mail.
 * @author Felix Gonzalez de Santos
 */

public class RecuperarPass extends ActionBase{

    private String email;
    private String answer;    
    private String resultado=Constantes.RES_ERROR;

    public RecuperarPass() {
    }

    @Override
    public String execute()
    {        
        try{
            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
            if (Servicios.validarCaptcha(session, answer))
            {
                startBD();

                String nueva = Servicios.generaPass(8);
                //TODO quitar mensaje con la pass nueva
                debug("Nueva pass:"+nueva);

                if (bbdd.cambiarPass(email, Servicios.cifrar(nueva)))
                {
                    String args[]={nueva};
                    Servicios.mandarCorreo(email, getActionMessage("asuntoEmail"),getActionMessage("mensajeEmail",args));
                    info(getActionMessage(Constantes.RES_OK));
                    resultado=Constantes.RES_OK;
                }
                else
                {                 
                    error(getActionMessage(Constantes.RES_ERROR));
                    resultado=Constantes.RES_ERROR;
                }
                stopBD();
            }
            else
            {
                info(getActionMessage("errorCaptcha"));
                resultado=Constantes.RES_INPUT;
            }
        }catch(Exception ex)
        {            
            error(getActionMessage(Constantes.RES_ERROR));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaEmail')}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaCaptcha')}")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}