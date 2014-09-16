package com.laguantera.util;

import com.laguantera.action.ActionBase;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * Action encargado de cambiar el idioma de la web en función del pararametro
 * request_locale recibido en la cabecera.
 * @author Felix Gonzalez
 */

public class CambiarIdioma extends ActionBase implements ServletRequestAware,ServletResponseAware
{
    private HttpServletRequest request;
    private HttpServletResponse response;
    public String url;

    public CambiarIdioma()
    {   }

    @Override
    public String execute() throws Exception
    {
        try
        {
            String idiomaDeseado=this.request.getParameter("request_locale");                        
            Locale nuevoLocale=new Locale(idiomaDeseado);
            Locale.setDefault(nuevoLocale);

            this.setUrl(request.getHeader("Referer"));
            
            String args[]={idiomaDeseado};
            info(getActionMessage(Constantes.RES_OK,args));
            return Constantes.RES_REDIRECT;
        }catch(Exception ex)
        {            
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            return Constantes.RES_ERROR;
        }        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //Metodos encargados de manejar el request y el response
    
    public void setServletRequest(HttpServletRequest request){
        this.request = request;
      }

      public HttpServletRequest getServletRequest(){
        return request;
      }

      public void setServletResponse(HttpServletResponse response){
        this.response = response;
      }

      public HttpServletResponse getServletResponse(){
        return response;
      }

}