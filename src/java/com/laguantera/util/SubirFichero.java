package com.laguantera.util;

import com.laguantera.dao.AccesoBD;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SubirFichero 
{
    //Control para el fichero de log
    protected static final Log log = LogFactory.getLog(SubirFichero.class);    
    
    /**Atributo que almacena la carpeta destino donde se va almacenar el archivo*/
    private String carpetaDestino;
    
    /**
     * Constructor de la clase que inicializa la carpeta donde se almacenara la información
     * @param destino Atributo que almacena la carpeta destino donde se va almacenar el archivo
     */
    public SubirFichero(String destino)
    {
        this.carpetaDestino=destino;
    }
//
//    /**
//     *Metodo mediante el cual subimos un unico fichero al servidor y le asignamos un determinado nombre
//     * @param fichero Archivo que queremos subir en formato FormFile
//     * @param nombre Nombre con el que queremnos que se guarde el archivo a subir
//     * @return Devuelve true si se ejecuta correctamente y false en otro caso
//     */
//    public boolean copiarFicheroFTP(File fichero,String nombre)
//    {
//        Configuracion json= Configuracion.getInstance();
//        SimpleFTP ftp=new SimpleFTP();
//        boolean resultado=false;
//        try
//        {
//                ftp.connect(json.get("ipServidor"), 21 ,  json.get("usuarioFTP") , json.get("passUsuarioFTP"));
//                ftp.bin();
//                ftp.cwd(this.carpetaDestino);
//                resultado = ftp.stor(fichero.getInputStream(),fichero.getFileName());
//                if (resultado)
//                    this.log.info("Subido fichero por FTP : "+fichero.getFileName());
//                else
//                    this.log.error("NO se ha podido subir fichero por FTP : "+fichero.getFileName());
//                return resultado;
//        }
//        catch (IOException e)
//        {
//            this.log.fatal("Excepcion IOException - StackTrace : " + e.toString());
//        }
//
//        return false;
//    }
//
//    /**
//     *Metodo mediante el cual subimos un unico fichero al servidor y le asignamos un determinado nombre
//     * @param fichero Archivo que queremos subir en formato FormFile
//     * @param nombre Nombre con el que queremnos que se guarde el archivo a subir
//     * @return Devuelve true si se ejecuta correctamente y false en otro caso
//     */
//    public boolean copiarFichero(FormFile fichero,String nombre)
//    {
//        Configuracion json= new Configuracion();
//        FileOutputStream salida = null;
//
//        try
//        {
//            File ficheroNuevo = new File(json.get("hdFtp") + json.get("raizFtp")+ nombre);
//            this.log.debug("------->"  + ficheroNuevo);
//            salida = new FileOutputStream (ficheroNuevo);
//            salida.write(fichero.getFileData());
//
//        }
//        catch (IOException e)
//        {
//            this.log.fatal("Excepcion IOException - StackTrace : " + e.toString());
//        }
//
//        return false;
//    }
//
//    /**
//     * Método mediante el cual subimos tres imagen al servidor
//     * @param ImagenPequena Imgen pequeña en formato FormeFile
//     * @param nombrePequena Nombre con el que se va a guardar la imagen pequeña
//     * @param ImagenMedia Imgen media en formato FormeFile
//     * @param nombreMedia Nombre con el que se va a guardar la imagen media
//     * @param ImagenGrande Imgen grande en formato FormeFile
//     * @param nombreGrande Nombre con el que se va a guardar la imagen pequeña
//     * @return Devuelve true si se ejecuta correctamente y false en otro caso
//     */
//    public boolean subirImgenes(FormFile ImagenPequena,String nombrePequena,FormFile ImagenMedia,String nombreMedia,FormFile ImagenGrande,String nombreGrande)
//    {
//        boolean correcto=true;
//
//        if(ImagenPequena!=null)
//        {
//            correcto=this.copiarFichero(ImagenPequena, nombrePequena);
//        }
//
//        if(correcto&&(ImagenMedia!=null))
//        {
//            this.copiarFichero(ImagenMedia, nombreMedia);
//        }
//
//        if(correcto&&(ImagenGrande!=null))
//        {
//            this.copiarFichero(ImagenGrande, nombreGrande);
//        }
//
//        return true;
//    }
//
//    /**
//     * Metodo mediante el cual suibimos una aplicación al servidor
//     * @param formulario Clase Bean que almacena todos los campos necesario para poder subir una aplicación
//     * @param nombre Nombre de la aplicación con la que se va a registrar la aplicación en el sistema
//     * @param usuario Nombre del usuario que ha realizado la aplicación
//     * @return Devuelve true si se ejecuta correctamente y false en otro caso
//     */
//    public boolean subirAplicacion(AplicacionBean formulario,String nombre,String usuario)
//    {
//
//        Configuracion jsonConfig= new Configuracion();
//
//        boolean correcto=true;
//        correcto=this.subirImgenes(formulario.getImagenPequena(),"pequena"+nombre,formulario.getImagenMedia(),"media"+nombre,formulario.getImagenGrande(),"grande"+nombre);
//        //controlZip zip = new controlZip(jsonConfig.get("hdFtp") + this.carpetaDestino, jsonConfig.get("hdFtp") + this.carpetaDestino,formulario.getTitulo()+".zip");
//
//        //Escribimos la informacion en el fichero JSON
//        try
//        {
//            AccesoBD baseDatos = new AccesoBD();
//            Connection conexion = baseDatos.abrirConexion();
//
//            String categorias[][]=baseDatos.consulta(conexion,"SELECT descripcion_categoria FROM categorias WHERE id_categoria='"+formulario.getCategoria()+"';", 1, 1);
//            String tecnologia[][]=baseDatos.consulta(conexion,"SELECT descripcion_tecnologia FROM tecnologias WHERE id_tecnologia='"+formulario.getTecnologia()+"';", 1, 1);
//
//            //Escribir los datos en el fichero JSONOBJECT aun no esta en el fichero, sino en memoria virtual
//            JSONObject json = new JSONObject();
//            json.put("Descripcion",formulario.getDescripcion());
//            json.put("FechaAlta",formulario.getFechaAlta());
//            json.put("Tecnologia",tecnologia[0][0]);
//            json.put("Titulo",formulario.getTitulo());
//            json.put("Usuario",usuario);
//            json.put("Categoria",categorias[0][0]);
//
//            File detalles=null;
//            FileOutputStream ficheroSalida = null;
//            BufferedOutputStream bufferSalida = null;
//
//this.carpetaDestino = jsonConfig.get("raizFtp").toString();
//            detalles =  new File (jsonConfig.get("hdFtp") + this.carpetaDestino+formulario.getTitulo()+".json");
//            //Escribimos los datos que hemos introducido anteriormente en el fichero JSON en un fichero físico en el disco duro
//            ficheroSalida = new FileOutputStream(detalles);
//            //ficheroSalida = new FileWriter(jsonConfig.get("hdFtp") + this.carpetaDestino+formulario.getTitulo()+".json");
//            bufferSalida = new BufferedOutputStream(ficheroSalida);
//            bufferSalida.write(json.toString().getBytes(), 0, json.toString().length());
//            bufferSalida.close();
//            ficheroSalida.close();
//
//            //Generamos el PDF con los datos
////            GenerarPDF pdf = new GenerarPDF(this.carpetaDestino+formulario.getTitulo()+".pdf");
////
////            System.out.println(this.carpetaDestino+formulario.getTitulo()+".pdf");
////
////            pdf.abrirFichero(nombre);
////            pdf.insertarParrafo("Datos de la aplicación descargada desde dmovil.cc.uah.es");
////            pdf.insertarParrafo("---------------------------------------------------------");
////            pdf.insertarParrafo("Titulo: "+formulario.getTitulo());
////            pdf.insertarParrafo("Descripcion: "+formulario.getDescripcion());
////            pdf.insertarParrafo("Usuario: "+usuario);
////            pdf.insertarParrafo("FechaAlta: "+formulario.getFechaAlta());
////            pdf.insertarParrafo("Tecnologia: "+tecnologia[0][0]);
////            pdf.insertarParrafo("Categoria: "+categorias[0][0]);
////
////            pdf.cerrarFichero();
//
//        }
//        catch (ErrorAccesoBD ex)
//        {
//            this.log.fatal("Excepcion ErrorAccesoBD - StackTrace : " + ex.toString());
//        }
//        catch (FileNotFoundException ex)
//        {
//            this.log.fatal("Excepcion FileNotFoundException - StackTrace : " + ex.toString());
//        }
//        catch (JSONException ex)
//        {
//            this.log.fatal("Excepcion JSONException - StackTrace : " + ex.toString());
//            return false;
//        }
//        catch (IOException ex)
//        {
//            this.log.fatal("Excepcion IOException - StackTrace : " + ex.toString());
//            return false;
//        }
//
//        //añadir al zip
//        //zip.agregar(formulario.getTitulo()+".json");
//
//        if((formulario.getFichero1()!=null)&&(formulario.getFichero1().getFileSize()>0))
//        {
//            correcto=this.copiarFichero(formulario.getFichero1(),formulario.getFichero1().getFileName());
//            //añadirlo al Zip
//            //zip.agregar(formulario.getFichero1().getFileName());
//        }
//
//        if((formulario.getFichero2()!=null)&&(formulario.getFichero2().getFileSize()>0))
//        {
//            correcto=this.copiarFichero(formulario.getFichero2(),formulario.getFichero2().getFileName());
//            //añadirlo al zip
//            //zip.agregar(formulario.getFichero2().getFileName());
//        }
//
//        //zip.finalizar();
//        return correcto;
//    }

}
