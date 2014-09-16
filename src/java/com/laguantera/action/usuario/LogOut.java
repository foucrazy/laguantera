package com.laguantera.action.usuario;


import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

/**
 * Action encargado de cerrar la sesion de usuario
 * @author Felix Gonzalez de Santos
 */

public class LogOut extends ActionBase{

    public LogOut() {
    }

    @Override
    public String execute()
    {
        try
        {
            Map session = ActionContext.getContext().getSession();
            session.remove("usuario");
            session.remove("idUsuario");

            info(getActionMessage(Constantes.RES_OK));
            return Constantes.RES_OK;
        }catch(Exception ex)
        {
            error(getActionMessage(Constantes.RES_ERROR));
            return Constantes.RES_ERROR;
        }
    }

}