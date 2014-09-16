package com.laguantera.action.galerias;

import com.laguantera.action.ActionBase;
import com.laguantera.config.Configuracion;
import com.laguantera.util.Constantes;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

/**
 * Edita un fichero multimedia, modificando el propio fichero.
 * @author Felix Gonzalez de Santos
 */

public class EditMultimedia extends ActionBase{

    private String coordenadas;
    private Integer idMultimedia;
    private String ruta;
    private String color;
    private String resultado;
    private String accion;
    private String titulo;

    private final String DEF_COLOR = "d7d7d7";

    public EditMultimedia() {
    }

    @Override
    public String execute() {
        try{

            Configuracion config = Configuracion.getInstance();            
            String rutaAbs = config.get(Constantes.RUTA_PERMANENTE_PUBLICA)+config.get("dirImgMultimedia")+ruta;
            debug("Procesando imagen:"+rutaAbs);
            ProcesadorImagenes proces = new ProcesadorImagenes();

            if (this.color==null)
                this.color=DEF_COLOR;

            int[] coordenadasX=null;
            int[] coordenadasY=null;
            String[] coordBruto = coordenadas.split("#");
            coordenadasX = new int[coordBruto.length];
            coordenadasY = new int[coordBruto.length];
            String[] coord=null;
            for (int cb=0;cb<coordBruto.length;cb++)
            {
                coord=coordBruto[cb].split(",");
                coordenadasX[cb]=Integer.parseInt(coord[0]);
                coordenadasY[cb]=Integer.parseInt(coord[1]);
            }
            //TODO
            proces.editarImagen("F:/FouCrazy/Programacion/LaGuantera/web/test/FougosoParqueOcio.jpg", "F:/FouCrazy/Programacion/LaGuantera/web/test/FougosoParqueOcioEdit.jpg",coordenadasX,coordenadasY,color, titulo, true);
            
            resultado=Constantes.RES_OK;
        }catch(Exception ex)
        {
            String args[]={ex.toString()};
            error(getActionMessage(Constantes.STR_EXCEPCION,args));
            resultado=Constantes.RES_ERROR;
        }
        return resultado;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaAccion')}")
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaCoordenadas')}")
    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaIdMultimedia')}")
    public Integer getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(Integer idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    @RequiredFieldValidator(message="${getCommonMessage('faltaRuta')}")
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}