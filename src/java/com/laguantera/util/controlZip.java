package Clases;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.fileupload.*;

/**
 * @author FouCrazy
 * http://www.manual-java.com/codigos-java/compresion-decompresion-archivos-zip-java.html
 */
public class controlZip {
    private String rutaOrigen=null;
    private String ficheroDestino=null;
    private String rutaDestino=null;
    private FileOutputStream ficheroSalida=null;
    private ZipOutputStream ficheroZip=null;
    
    public controlZip(String rutaOrigen, String rutaDestino , String ficheroDestino)
    {
        try {
            this.rutaDestino = rutaDestino;
            this.rutaOrigen =  rutaOrigen;
            this.ficheroDestino = ficheroDestino;
            this.ficheroSalida = new FileOutputStream(this.rutaDestino + this.ficheroDestino);
            this.ficheroZip = new ZipOutputStream(new BufferedOutputStream(ficheroSalida));
            
            System.out.println("------------------ He creado los ficheros para el zip");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(controlZip.class.getName()).log(Level.SEVERE, " No se ha podido crear el nuevo fichero comprimido.", ex);
        }
    }
    
    /**
     * Finaliza el fichero que se estaba creando, dando por finalizado el fichero zip.
     * @return Devuelve 0 si se ejecuta correctamente y -1 en caso de errores-problemas.
     */
    public int finalizar()
    {
        try {
            this.ficheroZip.close();
            this.ficheroSalida.close();
            return 0;
        } catch (IOException ex) {
            //Logger.getLogger(controlZip.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Se comprime un elemento dado por el parametro nombreFichero y lo empaqueta en el fichero que se esta creando establecido en el constructor. Una vez agregado lo elimina del directorio temporal (rutaOrigen)
     * @param nombreFichero nombre del fichero que se desea comprimir, solo nombre, se utiliza como base rutaOrigen.
     * @return Devuelve 0 si se ejecuta satisfactoriamente, -1 en caso de error.
     */
    public int agregar(String nombreFichero)
    {                        
        try {
            BufferedInputStream origin = null;
            byte[] data = new byte[1000];                        
                                    
           
            FileInputStream fichero = new FileInputStream(this.rutaOrigen+nombreFichero);
            origin = new BufferedInputStream(fichero, 1000);
            ZipEntry entry = new ZipEntry(nombreFichero);   
            
            //Crea una nueva entrada en el fichero que se esta generando.
            ficheroZip.putNextEntry(entry);

            int count;
            //Lee bloques de 1000 bytes del fichero origen.
            while ((count = origin.read(data, 0, 1000)) != -1) 
            {
                ficheroZip.write(data, 0, count);
            }
            
            //Cerramos el fichero creado y comprimido
            origin.close();                                                          
            fichero.close();                                    
            
            //Borrado del fichero origen, considerado como temporal.
            File ficheroTemporal=new File(this.rutaOrigen+nombreFichero);
            if (ficheroTemporal.delete()){
                System.out.println("El fichero "+nombreFichero+" ha sido borrado satisfactoriamente");
                return 0;
            }
            else{
                System.out.println("El fichero "+nombreFichero+" no puede ser borrado");                     
                return -1;
            }                        
            
        } catch (Exception e) {
            //e.printStackTrace();
            return -1;
        }          
    }
    
    /**
     * Devuelve un listado con los ficheros que contiene el parametro nombreFichero. La información contenida es el nombre del fichero , fecha de creacion, tamaño original y tamaño comprimido.
     * @param nombreFichero nombre del fichero que se quiere analizar, solo nombre, usando como ruta la establecida en rutaDestino.
     * @return array bidimensional con una fila por cada elemento comprimido y una columna para los datos nombre, fecha, tamaño y tamaño comprimido.
     */            
    public String[][] contenido (String nombreFichero) 
    {
        String listado[][] = new String [20][4];                
        int contador = 0;
        
         try
            {             
                FileInputStream ficheroOrigen = new FileInputStream(this.rutaDestino+nombreFichero);
                ZipInputStream ficheroZip = new ZipInputStream( new BufferedInputStream( ficheroOrigen ) );
                
                ZipEntry elementoZip;
                DateFormat controlFecha = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT );                
                
                while( ( elementoZip = ficheroZip.getNextEntry() ) != null )
                {
                    if( !elementoZip.isDirectory() )
                    {
                        contador++;
                        
                        listado[contador][0] = elementoZip.getName();
                        listado[contador][1] = controlFecha.format( new Date( elementoZip.getTime() ) );
                        listado[contador][2] = String.valueOf(elementoZip.getSize());
                        listado[contador][3] = String.valueOf(elementoZip.getCompressedSize());
                    }
                }
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }          
        
        return listado;
    }
    
//    public void comprimir(String jad,String jar,String nombre,String descripcion)
//    {        
//        Configuracion config=new Configuracion();
//        try {
//            BufferedInputStream origin = null;
//            FileOutputStream dest = new FileOutputStream(config.getDirectorioAplicaciones()+"comprimido.zip");
//            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
//            byte[] data = new byte[1000];                        
//                                    
//            //Compresion de los ficheros
//                //JAD
//                FileInputStream fichero = new FileInputStream(jad);
//                origin = new BufferedInputStream(fichero, 1000);
//                StringTokenizer tokens=new StringTokenizer(jad,"\\");
//                
//                while(tokens.hasMoreElements())
//                {
//                    jad=tokens.nextToken();
//                }
//                
//                ZipEntry entry = new ZipEntry(jad);
//                out.putNextEntry(entry);
//                int count;
//                while ((count = origin.read(data, 0, 1000)) != -1) 
//                {
//                    out.write(data, 0, count);
//                }
//                origin.close();
//                
//                //JAR
//                fichero = new FileInputStream(jar);
//                origin = new BufferedInputStream(fichero, 1000);
//                
//                tokens=new StringTokenizer(jar,"\\");
//                while(tokens.hasMoreElements())
//                {
//                    jar=tokens.nextToken();
//                }
//                
//                entry = new ZipEntry(jar);
//                out.putNextEntry(entry);                
//                while ((count = origin.read(data, 0, 1000)) != -1) 
//                {
//                    out.write(data, 0, count);
//                }
//                origin.close();
//
//                //JSON                                
//                JSONObject detallesJSON=new JSONObject();
//                detallesJSON.put("nombre", nombre);
//                detallesJSON.put("descripcion", descripcion);                                
//                System.out.println(detallesJSON.toString());
//                entry = new ZipEntry(nombre+".json");                                
//                out.putNextEntry(entry);
//                out.write(detallesJSON.toString().getBytes(), 0, detallesJSON.toString().length());                               
//            
//            fichero.close();            
//            
//            out.close();
//            dest.close();
//            
//            File ficheroTemporal=new File(config.getDirectorioTemporal()+jad);
//            if (ficheroTemporal.delete())
//                System.out.println("El fichero JAD ha sido borrado satisfactoriamente");
//            else
//                System.out.println("El fichero JAD no puede ser borrado");    
//            
//            ficheroTemporal=new File(config.getDirectorioTemporal()+jar);
//            if (ficheroTemporal.delete())
//                System.out.println("El fichero JAR ha sido borrado satisfactoriamente");
//            else
//                System.out.println("El fichero JAR no puede ser borrado");             
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }        
//    }        
//    
//    public void visualizar()
//    {
//        Configuracion config=new Configuracion();
//         try
//            {             
//                FileInputStream fis = new FileInputStream(config.getDirectorioAplicaciones()+"fichero.zip");
//                ZipInputStream zis = new ZipInputStream( new BufferedInputStream( fis ) );
//                
//                ZipEntry entry;
//                DateFormat df = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT );
//                while( ( entry = zis.getNextEntry() ) != null )
//                {
//                    if( !entry.isDirectory() )
//                    {
//                        System.out.println( entry.getName() + ", " +
//                        df.format( new Date( entry.getTime() ) ) + ", " +
//                        entry.getSize() + ", " + entry.getCompressedSize() );
//                    }
//                }
//            }
//            catch( Exception e )
//            {
//                e.printStackTrace();
//            }        
//    }

}
