package com.laguantera.util;

import com.laguantera.config.Configuracion;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SimpleFTP is a simple package that implements a Java FTP client. With
 * SimpleFTP, you can connect to an FTP server and upload multiple files.
 * Ejemplo de uso:
 * 		SimpleFTP ftp=new SimpleFTP();
		try 
		{
			ftp.connect("172.29.44.128", 21);
			ftp.bin();
			System.out.println("Directorio actual:"+ftp.pwd());
			
			ftp.cwd("/pub/DMovil/Aplicaciones/NET/Aplicacion/GlobosUAH");
			System.out.println("Directorio actual:"+ftp.pwd());
			boolean res=ftp.existeArchivo("GlobosUAH.CAB");
			
			System.out.println("Res: "+res);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
 *  
 */
public class SimpleFTP {

    
    protected static final Log log = LogFactory.getLog(SimpleFTP.class);    
  /**
   * Create an instance of SimpleFTP.
   */
  public SimpleFTP() {

  }

  /**
   * Connects to the default port of an FTP server and logs in as
   * anonymous/anonymous.
   */
  public synchronized void connect(String host) throws IOException {
    connect(host, 21);
  }

  /**
   * Connects to an FTP server and logs in as anonymous/anonymous.
   */
  public synchronized void connect(String host, int port) throws IOException {
    connect(host, port, "anonymous", "anonymous");
  }

  /**
   * Connects to an FTP server and logs in with the supplied username and
   * password.
   */
  public synchronized void connect(String host, int port, String user,
      String pass) throws IOException {
    if (socket != null) {
      throw new IOException("SimpleFTP is already connected. Disconnect first.");
    }
    socket = new Socket(host, port);
    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    writer = new BufferedWriter(
        new OutputStreamWriter(socket.getOutputStream()));

    String response = readLine();
    if (!response.startsWith("220 ")) {
      throw new IOException(
          "SimpleFTP received an unknown response when connecting to the FTP server: "
              + response);
    }

    sendLine("USER " + user);

    response = readLine();
    if (!response.startsWith("331 ")) {
      throw new IOException(
          "SimpleFTP received an unknown response after sending the user: "
              + response);
    }

    sendLine("PASS " + pass);

    response = readLine();
    if (!response.startsWith("230 ")) {
      throw new IOException(
          "SimpleFTP was unable to log in with the supplied password: "
              + response);
    }

    // Now logged in.
  }

  /**
   * Disconnects from the FTP server.
   */
  public synchronized void disconnect() throws IOException {
    try {
      sendLine("QUIT");
    } finally {
      socket = null;
    }
  }

  /**
   * Returns the working directory of the FTP server it is connected to.
   */
  public synchronized String pwd() throws IOException {
    sendLine("PWD");
    String dir = null;
    String response = readLine();
    if (response.startsWith("257 ")) {
      int firstQuote = response.indexOf('\"');
      int secondQuote = response.indexOf('\"', firstQuote + 1);
      if (secondQuote > 0) {
        dir = response.substring(firstQuote + 1, secondQuote);
      }
    }
    return dir;
  }

  /**
   * Changes the working directory (like cd). Returns true if successful.
   */
  public synchronized boolean cwd(String dir) throws IOException {
    sendLine("CWD " + dir);
    String response = readLine();
    boolean resultado = response.startsWith("250 ");
    if (!resultado)
            this.log.error("No se ha podido ejecutar CWD en el FTP");
    return resultado;
  }
  
  /**
   * Crea un directorio en el servidor remoto.
   * @param dir Ruta con el nombre del directorio que se crear�.
   * @return True si se realiz� correctamente, false en caso contrario.
   * @throws IOException Si hay error de entrada/salida.
   */
  public synchronized boolean mkd(String dir) throws IOException 
  {
	sendLine("MKD " + dir);
	String response = readLine();	  
        boolean resultado = response.startsWith("257 ");
        if (!resultado)
                this.log.error("No se ha podido ejecutar MKD en el FTP");          
        return resultado;
  }

    /**
   * Elimina un directorio en el servidor remoto.
   * @param dir Ruta con el nombre del directorio que se elimina�.
   * @return True si se realiz� correctamente, false en caso contrario.
   * @throws IOException Si hay error de entrada/salida.
   */
  public synchronized boolean rmd(String dir) throws IOException 
  {
	sendLine("RMD " + dir);
	String response = readLine();
        boolean resultado = response.startsWith("250 ");
        if (!resultado)
                this.log.error("No se ha podido ejecutar RMD en el FTP");
        return resultado;        
  }
  
    /**
   * Elimina un fichero en el servidor remoto.
   * @param dir Ruta con el nombre del fichero que se elimina�.
   * @return True si se realiz� correctamente, false en caso contrario.
   * @throws IOException Si hay error de entrada/salida.
   */
  public synchronized boolean del(String dir) throws IOException 
  {
	sendLine("DELE " + dir);
	String response = readLine();
        boolean resultado = response.startsWith("250 ");
        if (!resultado)
                this.log.error("No se ha podido ejecutar DELE en el FTP");
        return resultado;            
  }
    
  /**
   * Sends a file to be stored on the FTP server. Returns true if the file
   * transfer was successful. The file is sent in passive mode to avoid NAT or
   * firewall problems at the client end.
   */
  public synchronized boolean stor(File file) throws IOException {
    if (file.isDirectory()) {
      throw new IOException("SimpleFTP cannot upload a directory.");
    }
    String filename = file.getName();
    boolean resultado = stor(new FileInputStream(file), filename);
    if (!resultado)
        this.log.error("No se ha podido subir fichero en el FTP");
    return resultado;
  }

  /**
   * Sends a file to be stored on the FTP server. Returns true if the file
   * transfer was successful. The file is sent in passive mode to avoid NAT or
   * firewall problems at the client end.
   */
  public synchronized boolean stor(InputStream inputStream, String filename)
      throws IOException {

    BufferedInputStream input = new BufferedInputStream(inputStream);

    sendLine("PASV");
    String response = readLine();
    if (!response.startsWith("227 ")) {
      throw new IOException("SimpleFTP could not request passive mode: "
          + response);
    }

    String ip = null;
    int port = -1;
    int opening = response.indexOf('(');
    int closing = response.indexOf(')', opening + 1);
    if (closing > 0) {
      String dataLink = response.substring(opening + 1, closing);
      StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
      try {
        ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."
            + tokenizer.nextToken() + "." + tokenizer.nextToken();
        port = Integer.parseInt(tokenizer.nextToken()) * 256
            + Integer.parseInt(tokenizer.nextToken());
      } catch (Exception e) {
        throw new IOException("SimpleFTP received bad data link information: "
            + response);
      }
    }

    sendLine("STOR " + filename);

    Socket dataSocket = new Socket(ip, port);

    response = readLine();
    //if (!response.startsWith ("125 ")) {
    if (!response.startsWith("150 ")) {
      throw new IOException("SimpleFTP was not allowed to send the file: "
          + response);
    }

    BufferedOutputStream output = new BufferedOutputStream(dataSocket
        .getOutputStream());
    byte[] buffer = new byte[4096];
    int bytesRead = 0;
    while ((bytesRead = input.read(buffer)) != -1) {
      output.write(buffer, 0, bytesRead);
    }
    output.flush();
    output.close();
    input.close();

    response = readLine();
    return response.startsWith("226 ");
  }

  /**
   * Enter binary mode for sending binary files.
   */
  public synchronized boolean bin() throws IOException {
    sendLine("TYPE I");
    String response = readLine();
    return (response.startsWith("200 "));
  }

  /**
   * Enter ASCII mode for sending text files. This is usually the default mode.
   * Make sure you use binary mode if you are sending images or other binary
   * data, as ASCII mode is likely to corrupt them.
   */
  public synchronized boolean ascii() throws IOException {
    sendLine("TYPE A");
    String response = readLine();
    return (response.startsWith("200 "));
  }
  
  /**
   * Comprueba si existe un archivo en un ftp.
   * @param archivo Nombre del archivo completo (con extensi�n).
   * @return True si existe, false en caso contrario.
   * @throws IOException
   */
  public synchronized boolean existeArchivo(String archivo) throws IOException
  {
	  sendLine("SIZE "+archivo);
	  String response=readLine();
	  return (response.startsWith("213 "));
  }

  /**
   * Sends a raw command to the FTP server.
   */
  private void sendLine(String line) throws IOException {
    if (socket == null) {
      throw new IOException("SimpleFTP is not connected.");
    }
    try {
      writer.write(line + "\r\n");
      writer.flush();
      if (DEBUG) {          
        System.out.println("> " + line);
      }
    } catch (IOException e) {
      socket = null;
      throw e;
    }
  }

  private String readLine() throws IOException {
    String line = reader.readLine();
    if (DEBUG) {
      System.out.println("< " + line);
    }
    return line;
  }
  
  
  static public boolean crearDir (String ruta){
        Configuracion config= Configuracion.getInstance();
        SimpleFTP ftp=new SimpleFTP();
        boolean resultado=false;
        try 
        {
            ftp.connect(config.get("ipServidor"), 21 , config.get("usuarioFTP") , config.get("passUsuarioFTP"));
            ftp.bin();                        
            resultado = ftp.mkd(ruta);
            ftp.disconnect();
        }
        catch (IOException e)
        {
                e.printStackTrace();
        }        
      return resultado;
  }
  
  static public boolean eliminarDir (String ruta){     
        Configuracion json= Configuracion.getInstance();
        SimpleFTP ftp=new SimpleFTP();
        boolean resultado=false;
        try 
        {
            ftp.connect(json.get("ipServidor"), 21 , json.get("usuarioFTP") , json.get("passUsuarioFTP"));
            ftp.bin();                        
            resultado = ftp.rmd(ruta);
            ftp.disconnect();
        }
        catch (IOException e)
        {
                e.printStackTrace();
        }        
      return resultado;
  }  
  
  static public boolean eliminarFichero(String nombre){
        boolean resultado = false;
        Configuracion json= Configuracion.getInstance();
        SimpleFTP ftp=new SimpleFTP();        
        try 
        {
            ftp.connect(json.get("ipServidor"), 21 , json.get("usuarioFTP") , json.get("passUsuarioFTP"));
            ftp.bin();                        
            resultado = ftp.del(nombre);
            ftp.disconnect();
        }
        catch (IOException e)
        {
                e.printStackTrace();
        }              
      return resultado;
  }
  
  private Socket socket = null;

  private BufferedReader reader = null;

  private BufferedWriter writer = null;

  private static boolean DEBUG = false;

}
