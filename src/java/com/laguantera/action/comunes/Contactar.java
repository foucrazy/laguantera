package com.laguantera.action.comunes;

import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import java.util.Map;

/**
 *
 * @author Fou7
 */
public class Contactar extends ActionBase{
    
    private String contactarNombre;
    private String contactarEmail;
    private String contactarAsunto;
    private String contactarDuda;
    private String answer;
    
    @Override
    public String execute() throws Exception {
        
        if (!this.isEmpty()){
            //CAPTCHA
            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
            if (Servicios.validarCaptcha(session, answer)){
                Configuracion config=Configuracion.getInstance();
                String destino=config.get("remiteCorreo");
                if (Servicios.mandarCorreo(destino, this.contactarAsunto, this.contactarDuda))
                    return Constantes.RES_OK;
                else
                    return Constantes.RES_ERROR;
            }else{
                error(getActionMessage("errorCaptcha"));
                return Constantes.RES_INPUT;
            }            
        }else{
            debug("Faltan datos obligatorios");
            return Constantes.RES_INPUT;
        }
        
    }
    
    private boolean isEmpty(){
        if (contactarNombre==null || contactarNombre.equals(Constantes.STR_EMPTY) 
                || contactarEmail==null || contactarEmail.equals(Constantes.STR_EMPTY)
                || contactarAsunto==null || contactarAsunto.equals(Constantes.STR_EMPTY)
                || contactarDuda==null || contactarDuda.equals(Constantes.STR_EMPTY)
                || answer==null || answer.equals(Constantes.STR_EMPTY)){
            return true;
        }else{
            return false;
        }
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaAsunto')}")
    public String getContactarAsunto() {
        return contactarAsunto;
    }

    public void setContactarAsunto(String contactarAsunto) {
        this.contactarAsunto = contactarAsunto;
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaMensaje')}")
    public String getContactarDuda() {
        return contactarDuda;
    }

    public void setContactarDuda(String contactarDuda) {
        this.contactarDuda = contactarDuda;
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaEmail')}")
    @EmailValidator(message="${getCommonMessage('errorEmail')}")
    public String getContactarEmail() {
        return contactarEmail;
    }

    public void setContactarEmail(String contactarEmail) {
        this.contactarEmail = contactarEmail;
    }

    @RequiredStringValidator(message="${getCommonMessage('faltaNombre')}")
    public String getContactarNombre() {
        return contactarNombre;
    }

    public void setContactarNombre(String contactarNombre) {
        this.contactarNombre = contactarNombre;
    }
    
    @RequiredStringValidator(message="${getCommonMessage('faltaCaptcha')}")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }    
}
