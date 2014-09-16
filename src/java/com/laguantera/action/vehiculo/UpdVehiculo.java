package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.action.galerias.CodigosFormatosImagenes;
import com.laguantera.action.galerias.ProcesadorImagenes;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.dao.Vehiculos;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

/**
 * Actualiza la información de un vehículo
 * @author FouCrazy
 */
public class UpdVehiculo extends ActionBase{

     private Integer idVehiculo;
     private String motor;
     private Integer tara;
     private String neumaticos;
     private Integer asientos;
     private Integer cilindros;
     private Integer cilindrada;
     private Float cv;
     private Float kw;
     private Integer idTipoCombustible;
     private String matricula;
     private Date fechaMatriculacion;
     private Date fechaFabricacion;
     private File imagen;
     private String contentType;
     private String filename;

    public UpdVehiculo() {
    }

     
    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;

        try{

            if (imagen!=null)
            {
                Configuracion config = Configuracion.getInstance();
                String extension = this.getImagenFileName().substring(this.getImagenFileName().indexOf(".")+1);

                String rutaAbs = config.get(Constantes.RUTA_PERMANENTE_PUBLICA)+config.get("dirImgMultimedia")+filename;
                ProcesadorImagenes imgPro = new ProcesadorImagenes();
                //Escalamos la imagen
                BufferedImage bufImg = imgPro.escalarATamanyo(imagen, Integer.valueOf(config.get("alturaImagen")), Integer.valueOf(config.get("anchuraImagen")), extension);
                //Guardamos en formato jpg
                imgPro.salvarImagen(bufImg, rutaAbs, CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);
                //Creamos miniaturas
                rutaAbs = config.get(Constantes.RUTA_PERMANENTE_PUBLICA)+config.get("dirImgMultimedia")+"tb_"+filename;
                bufImg = imgPro.escalarATamanyo(imagen, Integer.valueOf(config.get("alturaImagenTb")), Integer.valueOf(config.get("anchuraImagenTb")), extension);
                imgPro.salvarImagen(bufImg, rutaAbs, CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);

                debug("Imagen subida y redimensionada:"+filename);
            }
            else
                filename="";

            startBD();
            
            Vehiculos vToUpd = new Vehiculos();
            vToUpd.setIdVehiculo(idVehiculo);
            TiposCombustible tipoCombustible=new TiposCombustible();
            tipoCombustible.setIdTipoCombustible(idTipoCombustible);
            vToUpd.setTiposCombustible(tipoCombustible);
            vToUpd.setCv(cv);
            vToUpd.setMotor(motor);
            vToUpd.setAsientos(asientos);
            vToUpd.setCilindrada(cilindrada);
            vToUpd.setCilindros(cilindros);            
            vToUpd.setFechaFabricacion(fechaFabricacion);
            vToUpd.setFechaMatriculacion(fechaMatriculacion);
            vToUpd.setKw(kw);
            vToUpd.setMatricula(matricula);            
            vToUpd.setNeumaticos(neumaticos);
            vToUpd.setTara(tara);
            
            if(filename!=null && !filename.equals(""))
                vToUpd.setImagen(filename);

            if (bbdd.updVehiculo(vToUpd))
            {                
                resultado=Constantes.RES_OK;
                info(getActionMessage(Constantes.RES_OK));
            }
            else
            {
                resultado=Constantes.RES_ERROR;
                error(getActionMessage(Constantes.RES_ERROR));
            }
            stopBD();
        }catch(Exception ex)
        {
            String arg[]={ex.toString()};
            error(getCommonMessage(Constantes.STR_EXCEPCION, arg));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }
    
    public Integer getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(Integer cilindrada) {
        this.cilindrada = cilindrada;
    }
  
    public Integer getCilindros() {
        return cilindros;
    }

    public void setCilindros(Integer cilindros) {
        this.cilindros = cilindros;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaCv')}")
    public Float getCv() {
        return cv;
    }

    public void setCv(Float cv) {
        this.cv = cv;
    }

    @DateRangeFieldValidator(min="01/01/1950" , max="01/01/2020" , message="getCommonMessage('errorFecha')")
    public Date getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(Date fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    @DateRangeFieldValidator(min="01/01/1950" , max="01/01/2020" , message="getCommonMessage('errorFecha')")
    public Date getFechaMatriculacion() {
        return fechaMatriculacion;
    }

    public void setFechaMatriculacion(Date fechaMatriculacion) {
        this.fechaMatriculacion = fechaMatriculacion;
    }

    public Float getKw() {
        return kw;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaVehiculo')}")
    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setKw(Float kw) {
        this.kw = kw;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoFijo')} ${minLength} ${getCommonMessage('caracteres')}",minLength="7",maxLength="7")
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="5",maxLength="100")
    @RequiredStringValidator(message="${getCommonMessage('faltaMotor')}")
    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    @StringLengthFieldValidator(message="${getCommonMessage('errorTamanoRango')} ${minLength} y ${maxLength} ${getCommonMessage('caracteres')}",minLength="5",maxLength="20")
    public String getNeumaticos() {
        return neumaticos;
    }

    public void setNeumaticos(String neumaticos) {
        this.neumaticos = neumaticos;
    }
    
    public Integer getTara() {
        return tara;
    }

    public void setTara(Integer tara) {
        this.tara = tara;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaCombustible')}")
    @IntRangeFieldValidator(min = "0", max = "99999", message ="getCommonMessage('faltaCombustible')")
    public Integer getIdTipoCombustible() {
        return idTipoCombustible;
    }

    public void setIdTipoCombustible(Integer idTipoCombustible) {
        this.idTipoCombustible = idTipoCombustible;
    }

    public String getImagenContentType() {
        return contentType;
    }

    public void setImagenContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getImagenFileName() {
        return filename;
    }

    public void setImagenFileName(String filename) {
        this.filename = filename;
    }

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }
}
