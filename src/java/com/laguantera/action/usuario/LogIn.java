package com.laguantera.action.usuario;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Usuarios;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import java.util.Map;

/**
 * Clase encargada de realizar el login para usuarios.
 * @author Felix Gonzalez de Santos
 */
@Validation
public class LogIn extends ActionBase {

    private String alias;
    private String password;
    private String resultado = Constantes.RES_ERROR;

    public LogIn() {
    }

    @Override
    public String execute() throws Exception {
        alias = alias.toLowerCase();
        info("Accediendo usuario:" + alias);

        if (!Servicios.esNombreIlegal(alias)) {
            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
            password = Servicios.cifrar(password);
            //Comprobamos si es un usuario registrado y se obtiene sus datos basicos
            startBD();
            Usuarios usuario = bbdd.loadUsuarioCompleto(alias, password);
            if (usuario != null) {
                info(getActionMessage(Constantes.RES_OK));
                session.put("idUsuario", usuario.getIdUsuario());
                session.put("usuario", usuario.getAlias());
                session.put("idTipoUsuario", usuario.getTiposUsuario().getIdTipoUsuario());
                resultado = Constantes.RES_OK;
            } else //No es un usuario registrado
            {
                error(getActionMessage(Constantes.RES_ERROR));
                resultado = Constantes.RES_ERROR;
            }
            stopBD();
        } else {
            error(getActionMessage(Constantes.RES_ERROR));
            resultado = Constantes.RES_ERROR;
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

    @StringLengthFieldValidator(message = "${getCommonMessage('errorTamanoFijo')} ${minLength} ${getCommonMessage('caracteres')}", minLength = "8", maxLength = "8")
    @RequiredStringValidator(message = "${getCommonMessage('faltaPass')}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
