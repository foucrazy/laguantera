package com.laguantera.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener encargado de controlar la creación y destrucción de sesiones.
 * @author Felix Gonzalez de Santos
 */
public class SessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Servicios.logear("SessionListener", "sessionCreated: se crea una sesion nueva de invitado.", Servicios.DEBUG);
        HttpSession session = se.getSession();
        session.setAttribute("idTipoUsuario", Constantes.TIPO_USUARIO_INVITADO);
        session.setMaxInactiveInterval(60*15);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Servicios.logear("SessionListener", "sessionDestroyed: se invalida la session abierta.", Servicios.DEBUG);
        HttpSession session = se.getSession();
        session.invalidate();
    }

}
