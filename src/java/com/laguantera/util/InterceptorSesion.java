package com.laguantera.util;

import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;

/**
 * Interceptor de sesiones.
 *
 * @author Felix Gonzalez de Santos
 */
public class InterceptorSesion implements Interceptor {
    private final String nombreClase="InterceptorSesion";
    
    //Metodo abstracto que se llama cuando termina la ejecución del interceptor

    @Override
    public void destroy() {
    }

    //Metodo abstracto que se llama cuando comienza la ejecución del interceptor
    @Override
    public void init() {
    }

    //Metodo abstracto que se llama cuando termina la ejecución la acción de interceptar una petición al controlador antes de que se ejecute la acción
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Servicios.logear(nombreClase, "Interceptada sesion para validar seguridad.", Servicios.DEBUG);
        //Obtenemos la sessión
        Map session = ActionContext.getContext().getSession();
        if (session == null || session.isEmpty()) {
            Servicios.logear(nombreClase, "No tiene sesion, redirigiendo a login.", Servicios.DEBUG);
            return Constantes.RES_LOGIN;
        } else if (session.containsKey("idUsuario")&&session.containsKey("idTipoUsuario")) {            
            Servicios.logear(nombreClase, "Esta logeado, comprobando autorizacion", Servicios.DEBUG);
            Integer idUsuario=(Integer)session.get("idUsuario");            
            Integer idTipoUsuario=(Integer)session.get("idTipoUsuario");
            String nombreAction=actionInvocation.getInvocationContext().getName();
            
            PermissionChecker checker = PermissionChecker.getInstance();
            if (checker.isAuthorized(idTipoUsuario, nombreAction)) {
                Servicios.logear(nombreClase, "Autorizado, ejecutando solicitud.", Servicios.DEBUG);
                //Devolvemos el mismo contexto con el que se nos invoco
                return actionInvocation.invoke();
            } else {
                Servicios.logear(nombreClase, "El usuario ("+idUsuario+") no esta autorizado para ejecutar "+nombreAction, Servicios.INFO);
                return Constantes.RES_NO_AUTORIZADO;
            }
        } else {
            Servicios.logear(nombreClase, "No esta logeado, redirigiendo a login.", Servicios.DEBUG);
            return Constantes.RES_LOGIN;
        }
    }
}
