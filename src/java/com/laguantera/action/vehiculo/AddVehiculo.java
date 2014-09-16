package com.laguantera.action.vehiculo;

import com.laguantera.action.ActionBase;
import com.laguantera.action.galerias.CodigosFormatosImagenes;
import com.laguantera.action.galerias.ProcesadorImagenes;
import com.laguantera.dao.Modelos;
import com.laguantera.dao.TiposCombustible;
import com.laguantera.dao.Usuarios;
import com.laguantera.dao.Vehiculos;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Añade un vehiculo a la cuenta de un usuario
 * @author FouCrazy
 */
public class AddVehiculo extends ActionBase{

     private Integer idMarca;
     private Integer idModelo;
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
     private String answer;
     private File imagen;
     private String contentType;
     private String filename;

    public AddVehiculo() {
    }

     
    @Override
    public String execute(){
        String resultado=Constantes.RES_ERROR;

        try{
            Map session = ActionContext.getContext().getSession();//Obtenemos la sessión
            //CAPTCHA
            if (Servicios.validarCaptcha(session, answer))
            {
                Integer idUsuario=(Integer)session.get("idUsuario");

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
                

                Usuarios usuario = new Usuarios();
                usuario.setIdUsuario(idUsuario);

                Modelos modelo=new Modelos();
                modelo.setIdModelo(idModelo);

                startBD();
                Vehiculos nuevo = new Vehiculos();
                nuevo.setUsuarios(usuario);
                nuevo.setModelos(modelo);
                nuevo.setAsientos(asientos);
                nuevo.setCilindrada(cilindrada);
                nuevo.setCilindros(cilindros);
                TiposCombustible tipoCombustible=new TiposCombustible();
                tipoCombustible.setIdTipoCombustible(idTipoCombustible);
                nuevo.setTiposCombustible(tipoCombustible);
                nuevo.setCv(cv);
                nuevo.setFechaFabricacion(fechaFabricacion);
                nuevo.setFechaMatriculacion(fechaMatriculacion);
                nuevo.setKw(kw);
                nuevo.setMatricula(matricula);
                nuevo.setMotor(motor);
                nuevo.setNeumaticos(neumaticos);
                nuevo.setTara(tara);
                nuevo.setImagen(filename);
                bbdd.addVehiculo(nuevo);
                stopBD();

                resultado=Constantes.RES_OK;
                info(getActionMessage(Constantes.RES_OK));
            }
            else{
                error(getActionMessage("errorCaptcha"));
                resultado=Constantes.RES_INPUT;
            }

        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
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

    @RequiredFieldValidator(message="${getCommonMessage('faltaModelo')}")
    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaMarca')}")
    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
    
    public Float getKw() {
        return kw;
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
    public Integer getIdTipoCombustible() {
        return idTipoCombustible;
    }

    public void setIdTipoCombustible(Integer idTipoCombustible) {
        this.idTipoCombustible = idTipoCombustible;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaCaptcha')}")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
