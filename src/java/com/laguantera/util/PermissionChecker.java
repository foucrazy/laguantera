package com.laguantera.util;

import com.laguantera.dao.PeticionesBD;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import com.laguantera.dao.TiposUsuario;

/**
 *
 * @author Fou7
 */
public class PermissionChecker {
    /*Nombre de la clase declarado de forma estática para evitar la creación de objetos String innecesarios.*/
    private final String nombreClase="PermissionChecker";    
    private static PermissionChecker INSTANCE = null;
    private HashMap<Integer,String> permissions=null;

    private PermissionChecker() {
        permissions=new HashMap<Integer, String>();
        //Obtener desde BD los tipos de usuario 
        PeticionesBD bbdd=new PeticionesBD();
        bbdd.abrirSesion();
        List<TiposUsuario> tipos = bbdd.loadTiposUsuario();
        Iterator<TiposUsuario> it = tipos.iterator();
        while(it.hasNext()){
            TiposUsuario tu =(TiposUsuario) it.next();
            permissions.put(tu.getIdTipoUsuario(), tu.getPermisos());
        }
        
        Servicios.logear(nombreClase, "Cargados permisos para todos los tipos de usuarios.", Servicios.INFO);
        bbdd.cerrarSesion();
    }        
    
    public boolean isAuthorized(Integer idTipoUsuario, String actionName){        
        /*boolean authorized=false;
        
        String actionsPermited=permissions.get(idTipoUsuario);
        if (actionsPermited.contains(Constantes.STR_PAD+actionName+Constantes.STR_PAD)
                || idTipoUsuario==Constantes.TIPO_USUARIO_ADMINISTRADOR){
            authorized=true;
        }
            
        return authorized;*/
        return true;
    }
    
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new PermissionChecker();
        }
    }
 
    public static PermissionChecker getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
}
