package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.dao.Galerias;
import com.laguantera.dao.Vehiculos;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

/**
 * Crea una nueva galería asociada a un vehículo
 * @author FouCrazy
 */
public class AddGaleria extends ActionBase{

     private Integer idVehiculo;
     private String titulo;     

    public AddGaleria() {
    }
     
    @Override
    public String execute(){        

        try{           
            this.startBD();

            Vehiculos vehiculo = new Vehiculos();
            vehiculo.setIdVehiculo(idVehiculo);
            
            Galerias nueva = new Galerias();
            nueva.setTitulo(titulo);
            nueva.setVehiculos(vehiculo);

            bbdd.addGaleria(nueva);
            this.stopBD();

            info(getActionMessage(Constantes.RES_OK));
            return(Constantes.RES_OK);
        }catch(Exception ex)
        {            
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            return(Constantes.RES_ERROR);
        }        
    }  

    @RequiredFieldValidator(message="${getCommonMessage('faltaVehiculo')}")
    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="1",maxLength="30")
    @RequiredStringValidator(message="${getCommonMessage('faltaTitulo')}")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



}
