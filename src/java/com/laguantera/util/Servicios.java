package com.laguantera.util;

import com.laguantera.config.Configuracion;
import com.laguantera.util.mail.Correo;
import java.io.*;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**
 *
 * @author Felix Gonzalez de Santos
 */
public class Servicios
{
    /*Determina si la aplicacion esta en desarrollo*/
    public static final boolean isInDevelopment=true;
    
    /*Para establecer el modo de log a salida a consola(false) o a fichero de log(true)*/
    private static final boolean modoLog=false;

    /**Log global del sistema*/
    protected static Logger log = null;
    protected static Logger logEmail = null;
    public static final int DEBUG=0;
    public static final int ERROR=1;
    public static final int INFO=2;
    public static final int FATAL=3;

    /*Nombre de la clase declarado de forma estatica para evitar la creacion de objetos String extras.*/
    private static final String nombreClase="Servicios";

    public Servicios() {
        if(modoLog)
            log = Logger.getLogger("laguantera");
            logEmail = Logger.getLogger("laguantera");

        BasicConfigurator.configure();
    }

    /**
    * Sustituye los caracteres de extension del latin-1 de un texto a su equivalente en html
    * Valores unicode en http://www.unicode.org/charts/PDF/U0080.pdf
    * Valores HTML en http://www.lookuptables.com/
    * @param txt Texto a convertir.
    * @autor Foucrazy
    * @return
    */
    private String convertToHTML(String txt){
    final String[][] caracteres={
        {"\u00e1", "&aacute;"},{"\u00e9", "&eacute;"},{"\u00ed", "&iacute;"},
        {"\u00f3", "&oacute;"},{"\u00fa", "&uacute;"},{"\u00c1", "&Aacute;"},
        {"\u00c9", "&Eacute;"},{"\u00cd", "&Iacute;"},{"\u00d3", "&Oacute;"},
        {"\u00da", "&Uacute;"},{"\u00f1", "&ntilde;"},{"\u00d1", "&Ntilde;"},
        {"\u20ac", "&euro;"},{"\u00a1", "&#161;"},{"\u00bf", "&#191;"},
        {"\u00e7", "&ccedil;"},{"\u00c7", "&Ccedil;"},{"\u00e4", "&auml;"},
        {"\u00eb", "&euml;"},{"\u00ef", "&iuml;"},{"\u00f6", "&ouml;"},
        {"\u00fc", "&uuml;"},{"\u00c4", "&Auml;"},{"\u00cb", "&Euml;"},
        {"\u00cf", "&Iuml;"},{"\u00d6", "&Ouml;"},{"\u00dc", "&Uuml;"}, 
        {"\u00e0", "&agrave;"},{"\u00e8", "&egrave;"},{"\u00ec", "&igrave;"},
        {"\u00f2", "&ograve;"},{"\u00f9", "&ugrave;"},{"\u00c0", "&Agrave;"},
        {"\u00c8", "&Egrave;"},{"\u00cc", "&Igrave;"},{"\u00d2", "&Ograve;"},
        {"\u00d9", "&Ugrave;"},{"\u00e2", "&acirc;"},{"\u00ea", "&ecirc;"},
        {"\u00ee", "&icirc;"},{"\u00f4", "&ocirc;"},{"\u00fb", "&ucirc;"},
        {"\u00c2", "&Acirc;"},{"\u00ca", "&Ecirc;"},{"\u00ce", "&Icirc;"},
        {"\u00d4", "&Ocirc;"},{"\u00db", "&Ucirc;"},{"\u00ba", "&#186;"},
        {"\u00aa", "&#170;"}};

        for (int i=0;i<caracteres.length;i++){
        txt=txt.replaceAll(caracteres[i][0],caracteres[i][1]);
        }
        return txt;
    }
    
    public static boolean validarCaptcha(Map sesion, String respuesta)
    {
        boolean resultado;
        try {
            Captcha captcha = (Captcha) sesion.get(Captcha.NAME);
            if(captcha!=null){
                if (captcha.isCorrect(respuesta)) {
                    logear(nombreClase, "Captcha correcto.", DEBUG);
                    resultado=true;
                }
                else
                {                
                    logear(nombreClase, "Captcha erroneo.", DEBUG);
                    resultado=false;
                }
            }else{
                AudioCaptcha audioCaptcha = (AudioCaptcha) sesion.get(AudioCaptcha.NAME);
                if (audioCaptcha.isCorrect(respuesta)) {
                    logear(nombreClase, "Captcha correcto.", DEBUG);
                    resultado=true;
                }
                else
                {                
                    logear(nombreClase, "Captcha erroneo.", DEBUG);
                    resultado=false;
                }
            }
        } catch (Exception ex) {
            logear(nombreClase, "Error validando captcha:"+ex.toString(), INFO);
            resultado=false;
        }

        sesion.remove(Captcha.NAME);
        return resultado;
    }


    /**
     * Comprueba si el alias introducido es ilegal
     * @param alias
     * @return true si el nombre es ilegal, false en otro caso.
     */
    public static boolean esNombreIlegal(String alias)
    {
        List lst = new ArrayList();
        lst.add("root");
        lst.add("admin");
        lst.add("webmaster");
        lst.add("laguantera");
        lst.add("laguanteraweb");
        lst.add("control");
        lst.add("administrador");
        lst.add("responsable");
        lst.add("soporte");
        lst.add("support");
        lst.add("contacto");
        lst.add("moderador");
        lst.add("moderator");

        if (alias.contains("root") ||alias.contains("admin") ||alias.contains("webmaster") ||alias.contains("laguantera") ||alias.contains("laguanteraweb") ||
                alias.contains("control") ||alias.contains("administrador") ||alias.contains("responsable") ||alias.contains("soporte") ||
                alias.contains("support") ||alias.contains("contacto") ||alias.contains("moderador") ||alias.contains("moderator") )
        {
            Servicios.logear("LogIn", "Se estan intentando registrar con un nombre ilegal.", Servicios.INFO);
            return true;
        }

        else return false;
    }

    /**
     * Método encargado de escribir en el log
     * @param clase Nombre de la clase que pide escribir
     * @param texto Mensaje que se va a escribir en el log
     */
    public static void logear(String clase, String texto, int tipo)
    {
        try{
        if (!modoLog)
        {
            System.out.println("["+clase+"]("+tipo+")"+texto);
        }
        else
        {            
            log = Logger.getLogger("laguantera."+clase);
            logEmail = Logger.getLogger("laguanteraEmail."+clase); //Log utilizado para mandar por email errores

            switch(tipo)
            {
                case 0: log.debug(texto); break;
                case 1: 
                {
                    log.error(texto);
                    //logEmail.error(texto);
                     break;
                }
                case 2: log.info(texto); break;
                case 3: 
                {
                    log.fatal(texto);
                    //logEmail.fatal(texto);
                    break;
                }
                default: log.warn(texto);
            }
        }
        }catch(Exception ex)
        {
            logEmail.fatal("--- PROBLEMAS CON EL LOG:"+ex.toString());
            System.out.println("--- PROBLEMAS CON EL LOG:"+ex.toString());
        }
    }

    public static String cifrar(String texto)
    {
        if (texto!=null)
        {
            try {
                MessageDigest messagedigest = MessageDigest.getInstance("MD5");
                messagedigest.update(texto.getBytes());
                String cifrado= new String(messagedigest.digest());
                //logear(nombreClase, "cifrar: Texto cifrado correctamente:"+cifrado, DEBUG);
                return cifrado;
            } catch (NoSuchAlgorithmException ex) {
                logear(nombreClase, "cifrar: No se ha podido realizar el cifrado:"+ex.getMessage(), ERROR);
            }
        }
        return null;
    }

    public static String cifrarEmail(String email)
    {
        String cifrado="";
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec kspec = new DESKeySpec("passpass".getBytes());
            SecretKey ks = skf.generateSecret(kspec);

            Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cifrador.init(Cipher.ENCRYPT_MODE, ks);

            byte[] emailCifrado=cifrador.doFinal(email.getBytes());
            cifrado = new String(emailCifrado);            

            /*cifrador.init(Cipher.DECRYPT_MODE,ks);
            byte[] emailDescifrado=cifrador.doFinal(emailCifrado);
            String d = new String(emailDescifrado);
            System.out.println("Email des-cifrado:"+d);*/

        } catch (Exception ex) {
            logear(nombreClase, "cifrarEmail, Excepcion:"+ex.toString(), ERROR);
        }
        logear(nombreClase, "cifrarEmail,OK", DEBUG);
        return cifrado;
    }

    public static String desCifrarEmail(String email)
    {
        String descifrado="";
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec kspec = new DESKeySpec("passpass".getBytes());
            SecretKey ks = skf.generateSecret(kspec);

            Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cifrador.init(Cipher.DECRYPT_MODE, ks);

            byte[] emailDesCifrado=cifrador.doFinal(email.getBytes());
            descifrado = new String(emailDesCifrado);

        } catch (Exception ex) {
            logear(nombreClase, "desCifrarEmail, Excepcion:"+ex.toString(), ERROR);
        }
        logear(nombreClase, "desCifrarEmail,OK", DEBUG);
        return descifrado;
    }

    public static boolean mandarCorreo(String destino,String asunto,String mensaje)
    {
        boolean correcto = false;
        try
        {
            Configuracion config=Configuracion.getInstance();
            Correo envio = new Correo();
            envio.setHostSmtp(config.get("servidorCorreo"));
            envio.setPuertoSMTP(config.get("puertoCorreo"));
            envio.setAsunto(asunto);
            envio.setDireccion(destino);
            envio.setMensaje(mensaje);
            envio.setOrigen(config.get("remiteCorreo"));

            if (!envio.send())
               correcto = false;

            correcto=true;
            logear(nombreClase, "mandarCorreo: Correo enviado a :"+destino, DEBUG);
        }catch(Exception ex)
        {
            correcto=false;
            logear(nombreClase, "mandarCorreo: Imposible enviar correo a:"+destino+" -"+ex.getMessage(), DEBUG);
        }

        return correcto;
    }

    public static String generaPass(int longitud)
    {
        String pass="";
        String key = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < longitud; i++) {
			pass+=(key.charAt((int)(Math.random() * key.length())));
		}
        return pass;
    }

    /**
     * Elimina los caracteres sensibles de ser ataques SQLInjection
     * @param cadena String a filtrar
     * @return
     */
    public static String filtroSQLInjection(String cadena)
    {
         cadena = cadena.replaceAll("--", "");
         cadena = cadena.replaceAll(";", "");
         cadena = cadena.replaceAll("'", "");
         cadena = cadena.replaceAll("\"", "");
         cadena = cadena.replaceAll("<", "");
         cadena = cadena.replaceAll(">", "");
         return cadena;
    }
    
/**
     * Copia todo el contenido de un directorio a otro directorio
     * @param srcDir
     * @param dstDir
     * @throws IOException
     */
    public static void copyDirectory(File srcDir, File dstDir)
    {
        try{
            if (srcDir.isDirectory()) {
                if (!dstDir.exists()) {
                    dstDir.mkdir();
                }

                String[] children = srcDir.list();
                for (int i=0; i<=children.length; i++){
                    copyDirectory(new File(srcDir, children[i]),
                        new File(dstDir, children[i]));
                }
            } else {
                copyFile(srcDir, dstDir);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Copia un solo archivo
     * @param s
     * @param t
     * @throws IOException
     */
    public static void copyFile(File s, File t)
    {
        try{
              FileChannel in = (new FileInputStream(s)).getChannel();
              FileChannel out = (new FileOutputStream(t)).getChannel();
              in.transferTo(0, s.length(), out);
              in.close();
              out.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Copia un archivo y busca y sustituye un String dado
     * @param source_file
     * @param destination_file
     * @param toFind
     * @param toReplace
     * @throws IOException
     */
    public static void copyFindAndReplace(String source_file, String destination_file, String toFind, String toReplace)
    {
        String str;
        try{
            FileInputStream fis2 = new FileInputStream(source_file);
            DataInputStream input = new DataInputStream (fis2);
            FileOutputStream fos2 = new FileOutputStream(destination_file);
            DataOutputStream output = new DataOutputStream (fos2);

            while (null != ((str = input.readLine())))
            {
                String s2=toFind;
                String s3=toReplace;

                int x=0;
                int y=0;
                String result="";
                while ((x=str.indexOf(s2, y))>-1) {
                    result+=str.substring(y,x);
                    result+=s3;
                    y=x+s2.length();
                }
                result+=str.substring(y);
                str=result;

                if(str.indexOf("'',") != -1){
                    continue;
                }
                else{
                    str=str+"\n";
                    output.writeBytes(str);
                }
            }
        }
        catch (IOException ioe)
        {
            System.err.println ("I/O Error - " + ioe);
        }
    }   
}

