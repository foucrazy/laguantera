package com.laguantera.action.usuario;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Usuarios;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

/**
 * Obtiene un usuario a partir de su identificador.
 * @author Félix Glez.
 */
public class LoadUsuario extends ActionBase{

    public Integer idUsuario;
    public Usuarios usuario;

    public LoadUsuario() {
    }

    @Override
    public String execute() throws Exception {
        String resultado=Constantes.RES_ERROR;
        try{
            if (this.idUsuario==null){
                Map session = ActionContext.getContext().getSession();
                this.idUsuario=(Integer)session.get("idUsuario");
            }

            startBD();
            usuario=bbdd.loadUsuario(idUsuario);
            resultado=Constantes.RES_OK;
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
}
