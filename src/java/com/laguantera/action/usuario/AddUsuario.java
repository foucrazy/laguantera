package com.laguantera.action.usuario;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import java.util.Map;


/**
 * Registro de un usuario.
 * @author Felix Gonzalez de Santos
 */

public class AddUsuario extends ActionBase{

    private String aliasRegistro;
    private String passwordRegistro;
    private String rePasswordRegistro;
    private Integer codigoPostalRegistro;
    private String emailRegistro;
    private String reEmailRegistro;
    private String answer;

    public AddUsuario() {
    }

    @Override
    public String execute() {
        String resultado=Constantes.RES_ERROR;
        
        Map session = ActionContext.getContext().getSession();//Obtenemos la sessión

        //CAPTCHA
        if (Servicios.validarCaptcha(session, answer))
        {   //REPETIR CONTRASEÑA
            if (this.passwordRegistro.equals(this.rePasswordRegistro))
            {
                //REPETIR EMAILS
                if (this.emailRegistro.equals(this.reEmailRegistro))
                {
                    this.startBD();
                    try
                    {
                        //NOMBRES RESERVADOS
                        if (!Servicios.esNombreIlegal(aliasRegistro))
                        {
                            //NOMBRES DUPLICADOS
                            if(!bbdd.existeUsuario(aliasRegistro,emailRegistro))
                            {
                                //ACCESO A LA BD
                                if (bbdd.addUsuario(aliasRegistro, Servicios.cifrar(passwordRegistro), codigoPostalRegistro, emailRegistro,Constantes.DEFAULT_TIPO_USUARIO))
                                {
                                    Servicios.mandarCorreo(emailRegistro, getJspMessage("asuntoBienvenido"), getJspMessage("mensajeBienvenida"));
                                    info(getActionMessage(Constantes.RES_OK));
                                    resultado=Constantes.RES_OK;
                                }
                                else
                                {
                                    error(getActionMessage(Constantes.RES_ERROR));
                                    resultado=Constantes.RES_ERROR;
                                }
                            }
                            else
                            {
                                error(getActionMessage("yaExiste"));
                                resultado=Constantes.RES_INPUT;
                            }
                        }
                        else
                        {
                            info(getActionMessage("aliasIlegal"));
                            resultado=Constantes.RES_INPUT;
                        }
                    }catch(Exception ex)
                    {
                        String args[]={ex.toString()};
                        error(getActionMessage(Constantes.STR_EXCEPCION,args));
                        return(Constantes.RES_ERROR);
                    }
                    this.stopBD();
                }
                else{
                    error(getActionMessage("errorEmailsDiferentes"));
                    resultado=Constantes.RES_INPUT;
                }
            }
            else
            {
                error(getActionMessage("errorPasswordwsDiferentes"));
                resultado=Constantes.RES_INPUT;
            }
        }
        else
        {
            error(getActionMessage("errorCaptcha"));
            resultado=Constantes.RES_INPUT;
        }
        return resultado;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="3",maxLength="20")
    @RequiredStringValidator(message="${getCommonMessage('faltaAlias')}")
    public String getAliasRegistro() {
        return aliasRegistro;
    }

    public void setAliasRegistro(String alias) {
        this.aliasRegistro = alias;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaCodigoPostal')}")
    @IntRangeFieldValidator(min = "0", max = "99999", message ="getCommonMessage('errorCodigoPostal')")
    public Integer getCodigoPostalRegistro() {
        return codigoPostalRegistro;
    }

    public void setCodigoPostalRegistro(Integer codigoPostal) {
        this.codigoPostalRegistro = codigoPostal;
    }


    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="5",maxLength="200")
    @RequiredStringValidator(message="${getCommonMessage('faltaEmail')}")
    @EmailValidator(message="${getCommonMessage('errorEmail')}")
    public String getEmailRegistro() {
        return emailRegistro;
    }

    public void setEmailRegistro(String email) {
        this.emailRegistro = email;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoFijo')} ${minLength} ${getCommonMessage('caracteres')}",minLength="8",maxLength="8")
    @RequiredStringValidator(message="${getCommonMessage('faltaPass')}")
    public String getPasswordRegistro() {
        return passwordRegistro;
    }

    public void setPasswordRegistro(String password) {
        this.passwordRegistro = password;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoFijo')} ${minLength} ${getCommonMessage('caracteres')}",minLength="8",maxLength="8")
    @RequiredStringValidator(message="${getCommonMessage('faltaPass')}")
    public String getRePassword() {
        return rePasswordRegistro;
    }

    public void setRePasswordRegistro(String rePassword) {
        this.rePasswordRegistro = rePassword;
    }


    @RequiredStringValidator(message="${getCommonMessage('faltaCaptcha')}")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="5",maxLength="200")
    @RequiredStringValidator(message="${getCommonMessage('faltaReEmail')}")
    @EmailValidator(message="${getCommonMessage('errorEmail')}")
    public String getReEmailRegistro() {
        return reEmailRegistro;
    }

    public void setReEmailRegistro(String reEmail) {
        this.reEmailRegistro = reEmail;
    }
}