package com.laguantera.util.mail;

import com.laguantera.config.Configuracion;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


public class Correo
{
    /**Almacena la dirección de los usuario a los que se le va enviar el correo*/
    private Vector direcciones = new Vector();
    /**Almacena el asunto del mensaje*/
    private String asunto;
    /**Almacena el mensaje propiamente dicho*/
    private String mensaje;
    /**Almacen el host desde el cual se va enviar*/
    private String hostSmtp;
    /**Almacena el puerto SMTP para el envio*/
    private String puertoSMTP;
    /**Dirección desde la que se envia*/
    private String origen;
    /**Ruta en disco del ficho que se desea adjuntar*/
    private String rutaAdjunto=null;
    /**Nombre que se mostrara para el fichero adjunto*/
    private String nombreAdjunto=null;

    /**
     * Constructor por defecto
     */
    public Correo()
    {
        Configuracion config = Configuracion.getInstance();
        this.hostSmtp=config.get("servidorCorreo");
        this.puertoSMTP=config.get("puertoCorreo");        
    }

    public String getAsunto()
    {
        return asunto;
    }

    public void setAsunto(String asunto)
    {
        this.asunto = asunto;
    }

    public String[] getDireccion()
    {
        return (String[]) this.direcciones.toArray();
    }

    public void setDireccion(String direccion)
    {
        this.direcciones.addElement(direccion);
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public String getHostSmtp()
    {
        return hostSmtp;
    }

    public void setHostSmtp(String hostSmtp)
    {
        this.hostSmtp = hostSmtp;
    }

    public String getOrigen()
    {
        return origen;
    }

    public void setOrigen(String origen)
    {
        this.origen = origen;
    }

    public String getPuertoSMTP()
    {
        return puertoSMTP;
    }

    public void setPuertoSMTP(String puertoSMTP)
    {
        this.puertoSMTP = puertoSMTP;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public String getRutaAdjunto() {
        return rutaAdjunto;
    }

    public void setRutaAdjunto(String rutaAdjunto) {
        this.rutaAdjunto = rutaAdjunto;
    }
    
    
    public boolean send()
    {
        try
        {            
            //creamos las propiedades del mail
            Properties propiedades = System.getProperties();
            propiedades.put("mail.smtp.host",hostSmtp);
            propiedades.put("mail.smtp.auth","true");
            propiedades.put("mail.smtp.port",puertoSMTP);
            
            //creamos la sesión para enviar el mail            
            SMTPAuthentication auth = new SMTPAuthentication();
            Session mailSesion = Session.getInstance(propiedades, auth);

            //creamos el mensaje
            MimeMessage mens = new MimeMessage(mailSesion);

            //Definimos la dirección del remitente
            mens.setFrom(new InternetAddress(this.origen));

            //creamos un array de las direcciones de los destinatarios
            InternetAddress[] addressTo = new InternetAddress[this.direcciones.size()];
            for (int i=0; i < this.direcciones.size(); i++)
            {
                addressTo[i] = new InternetAddress((String) this.direcciones.get(i));
            }

           //definimos los destinatarios
            mens.addRecipients(Message.RecipientType.TO, addressTo);

            //definiemos la fecha de envio
            mens.setSentDate(new Date());

            //Definimos el asunto
            mens.setSubject(asunto);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart texto = new MimeBodyPart();
            texto.setContent(this.mensaje,"text/html");
            multipart.addBodyPart(texto);
            
            if (this.rutaAdjunto!=null){
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(this.rutaAdjunto)));
                adjunto.setFileName(this.nombreAdjunto);   
                multipart.addBodyPart(adjunto);
            }

            //Definimos el cuerpo del mensaje
            mens.setContent(multipart);
            
            //Creamos el objeto transport con el método
            Transport transporte = mailSesion.getTransport("smtp");

            //enviamos el correo
            transporte.send(mens);

        }
        catch (AddressException ex)
        {
            ex.printStackTrace();
            return false;
        }
        catch (SendFailedException ex)
        {
            ex.printStackTrace();
            return false;
        }
        catch (MessagingException ex)
        {
            ex.printStackTrace();
            return false;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
class SMTPAuthentication extends javax.mail.Authenticator
{
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        Configuracion config = Configuracion.getInstance();
        String username = config.get("usuarioCorreo");
        String password = config.get("passwordCorreo");
        return new PasswordAuthentication(username, password);
    }
}
