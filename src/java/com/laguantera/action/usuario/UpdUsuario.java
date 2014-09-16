package com.laguantera.action.usuario;

import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import java.util.Map;

/**
 * Actualización de un usuario.
 *
 * @author Felix Gonzalez de Santos
 */
public class UpdUsuario extends ActionBase {

    private String alias;
    private String oldPassword;
    private String password;
    private String rePassword;
    private Integer codigoPostal;
    private String email;

    public UpdUsuario() {
    }

    @Override
    public String execute() {
        String resultado = Constantes.RES_ERROR;
        startBD();
        Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
        Integer idUsuario = bbdd.loadUsuario((String) session.get("usuario"), Servicios.cifrar(this.oldPassword));
        //Comprobación de la contraseña antigua
        if (idUsuario != null) {
            //REPETIR CONTRASEÑA
            if (comprobarPassword()) {
                try {
                    //NOMBRES RESERVADOS
                    if (!Servicios.esNombreIlegal(alias)) {
                        //NOMBRES DUPLICADOS
                        if (!bbdd.existeUsuario(alias, email, idUsuario)) {
                            //ACCESO A LA BD
                            this.password=(this.password!=null&&!this.password.isEmpty())?(this.password):(this.oldPassword);
                            if (bbdd.updUsuario(idUsuario, alias, Servicios.cifrar(password), codigoPostal, email)) {
                                debugCorrecto(getActionMessage(Constantes.RES_OK));
                                //Por si se actualiza el alias de usuario
                                session.remove("usuario");
                                session.put("usuario", alias);
                                resultado = Constantes.RES_OK;
                            } else {
                                error(getActionMessage(Constantes.RES_ERROR));
                                resultado = Constantes.RES_ERROR;
                            }
                        } else {
                            debugError(getActionMessage("aliasYaUtilizado"));
                            resultado = Constantes.RES_INPUT;
                        }
                        stopBD();
                    } else {
                        debugError(getActionMessage("aliasIlegal"));
                        resultado = Constantes.RES_INPUT;
                    }
                } catch (Exception ex) {
                    String arg[] = {ex.toString()};
                    error(getCommonMessage("excepcion", arg));
                    resultado = Constantes.RES_ERROR;
                }
            } else {
                debugError(getActionMessage("passwordwsDiferentes"));
                resultado = Constantes.RES_INPUT;
            }
        } else{
            debugError(getActionMessage("passwordIncorrecta"));
            resultado = Constantes.RES_INPUT;
        }

        return resultado;
    }

    @StringLengthFieldValidator(message = "${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}", minLength = "3", maxLength = "20")
    @RequiredStringValidator(message = "${getCommonMessage('faltaAlias')}")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @StringLengthFieldValidator(message = "${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}", minLength = "5", maxLength = "200")
    @RequiredStringValidator(message = "${getCommonMessage('faltaEmail')}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    private boolean comprobarPassword() {
        return (((this.password==null || this.password.isEmpty()) && (this.rePassword==null || this.rePassword.isEmpty())) 
                || (this.password.equals(this.rePassword)));
    }
}
