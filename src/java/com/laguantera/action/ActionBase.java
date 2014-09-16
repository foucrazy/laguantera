package com.laguantera.action;

import com.laguantera.dao.PeticionesBD;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Funcionalidad básica de un Action
 *
 * @author Felix Glez
 */
public class ActionBase extends ActionSupport {

    protected PeticionesBD bbdd;

    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    public void startBD() {
        bbdd = new PeticionesBD();
        bbdd.abrirSesion();
    }

    public void stopBD() {
        bbdd.cerrarSesion();
    }

    public void debug(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.DEBUG);
    }
    
    public void debugCorrecto(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.DEBUG);
         addActionMessage(msg);
    }
    
    public void debugError(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.DEBUG);
        addActionError(msg);
    }

    public void info(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.INFO);
    }
    
    public void infoCorrecto(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.INFO);
        addActionMessage(msg);
    }
    
    public void infoError(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.INFO);
        addActionError(msg);
    }

    public void error(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.ERROR);
        addActionError(msg);
    }

    public void fatal(String msg) {
        Servicios.logear(this.getClass().getName(), msg, Servicios.FATAL);
        addActionError(msg);
    }

    public String getActionMessage(String key) {
        //nombre_aplicacion.jsp|action|commons.nombre|commons.elemento
        System.out.println(this.getClass().getName());
        return getText(this.getClass().getName() + Constantes.STR_DOT + key);
    }

    public String getActionMessage(String key, String args[]) {
        //nombre_aplicacion.jsp|action|commons.nombre|commons.elemento
        return getText(this.getClass().getName() + Constantes.STR_DOT + key, args);
    }

    public String getJspMessage(String key) {
        //nombre_aplicacion.jsp|action|commons.nombre|commons.elemento
        return getText(Constantes.NOMBRE_APP + Constantes.STR_DOT + Constantes.STR_JSP + Constantes.STR_DOT + this.getClass().getName() + Constantes.STR_DOT + key);
    }

    public String getJspMessage(String key, String args[]) {
        //nombre_aplicacion.jsp|action|commons.nombre|commons.elemento
        return getText(Constantes.NOMBRE_APP + Constantes.STR_DOT + Constantes.STR_JSP + Constantes.STR_DOT + this.getClass().getName() + Constantes.STR_DOT + key, args);
    }

    public String getCommonMessage(String key) {
        //nombre_aplicacion.jsp|action|commons.nombre|commons.elemento
        return getText(Constantes.NOMBRE_APP + Constantes.STR_DOT + Constantes.STR_COMMONS + Constantes.STR_DOT + key);
    }

    public String getCommonMessage(String key, String args[]) {
        //nombre_aplicacion.jsp|action|commons.nombre|commons.elemento
        return getText(Constantes.NOMBRE_APP + Constantes.STR_DOT + Constantes.STR_COMMONS + Constantes.STR_DOT + key, args);
    }
}
