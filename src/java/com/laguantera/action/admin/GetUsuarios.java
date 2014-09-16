package com.laguantera.action.admin;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.TiposUsuario;
import com.laguantera.dao.Usuarios;
import com.laguantera.util.Constantes;
import java.util.List;

/**
 *
 * @author Félix Glez
 */
public class GetUsuarios extends ActionBase{

    public List<Usuarios> listaUsuarios;
    public List<TiposUsuario> listaTiposUsuarios;

    public GetUsuarios() {
    }


    @Override
    public String execute() throws Exception {

        try{        
            this.startBD();
            this.listaUsuarios=bbdd.loadUsuarios();
            this.listaTiposUsuarios=bbdd.loadTiposUsuario();
            this.stopBD();
            debug("GetUsuarios cargado correctamente.");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            error(ex.toString());
        }
        return Constantes.RES_OK;
    }


    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<TiposUsuario> getListaTiposUsuarios() {
        return listaTiposUsuarios;
    }

    public void setListaTiposUsuarios(List<TiposUsuario> listaTiposUsuarios) {
        this.listaTiposUsuarios = listaTiposUsuarios;
    }
}
