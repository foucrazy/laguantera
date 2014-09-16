package com.laguantera.util;

import com.laguantera.config.Configuracion;
import com.laguantera.action.admin.Actualizador;
import com.laguantera.dao.PeticionesBD;
import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CleanupListener implements ServletContextListener {
    @Override
  public void contextInitialized(ServletContextEvent event)
  {
      System.out.println("<[Iniciando contexto de la aplicacion]>");      

      //Arrancamos la conexion con la base de datos
      new PeticionesBD().abrirSesion();

      //Iniciamos demonio de actualizador de eess
      Thread demonActualizador = new Thread(new Actualizador());
      demonActualizador.start();
      
      //Copia de recursos
      Configuracion  config = Configuracion.getInstance();
      String pathPrivate = config.get(Constantes.RUTA_PERMANENTE_PRIVADA);     
      String rutaBase= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
      rutaBase = rutaBase.replaceAll("/util/CleanupListener.class", "/resources/");
      
      Servicios.logear("CleanupListener", "Copiando recursos de :"+rutaBase+" a "+pathPrivate,Servicios.INFO);
      Servicios.copyDirectory(new File(rutaBase),new File(pathPrivate));
  } 
  public void contextDestroyed(ServletContextEvent event) {      
    try {
      System.out.println("<[Destruyendo contexto de la aplicacion]>");

      //TODO revisar, en glassfish con el plugin de hibernate al descargar los drivers ya no es posible volver a cargarlos, es necesario reiniciar el servidro
      //Descargamos los drivers de la base de datos
//      Enumeration e = DriverManager.getDrivers();
//      while( e.hasMoreElements())
//      {
//        Driver driver = (Driver) e.nextElement();
//
//        //if (driver.getClass().getClassLoader() == getClass().getClassLoader())
//        //{
//          DriverManager.deregisterDriver(driver);
//        //}
//      }
//      e=null;
//
//      //Descargamos los logs
//      LogFactory.release(Thread.currentThread().getContextClassLoader());
//      LogFactory.releaseAll();
//
//      Introspector.flushCaches();

      System.gc();
    } catch (Throwable e) {
      System.err.println("Failled to cleanup ClassLoader for webapp");
      e.printStackTrace();
    }
  }
}