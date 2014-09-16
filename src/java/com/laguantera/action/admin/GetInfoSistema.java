package com.laguantera.action.admin;
import com.laguantera.action.ActionBase;
import com.laguantera.util.Constantes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

        
/**
 *
 * @author Felix Gonzalez de Santos
 */
        
public class GetInfoSistema extends ActionBase{
    List<String> listaPropiedades;

    public GetInfoSistema() {
    }

    @Override
    public String execute() {
        try {
            listaPropiedades=new ArrayList<String>();
            Properties props = System.getProperties();
            Enumeration element = props.elements();
            Enumeration keys = props.keys();
            while (element.hasMoreElements() && keys.hasMoreElements())
            {
                listaPropiedades.add(keys.nextElement().toString()+":"+element.nextElement().toString());
            }
        } catch (Exception ex) {
            error("Excepcion:"+ex.toString());
            return Constantes.RES_ERROR;
        }
        return Constantes.RES_OK;
    }

    public List<String> getListaPropiedades() {
        return listaPropiedades;
    }

    public void setListaPropiedades(List<String> listaPropiedades) {
        this.listaPropiedades = listaPropiedades;
    }

}
