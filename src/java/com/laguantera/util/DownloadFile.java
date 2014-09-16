package com.laguantera.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class DownloadFile
{

  public static boolean download(String origen,String destino)
  {
      try
      {
          long limit = System.currentTimeMillis()-86400000;
          File testFile=new File(destino);
          if (!testFile.exists() || testFile.lastModified()<limit)
          {                                   
            URL url  = new URL(origen);
            System.out.println("Opening connection to " + origen + "...");
            //URLConnection urlC = url.openConnection();
            // Copy resource to local file, use remote file
            // if no local file name specified
            InputStream is = url.openStream();
            // Print info about resource
            //System.out.print("Copying resource (type: " + urlC.getContentType());
            //Date date=new Date(urlC.getLastModified());
            //System.out.println(", modified on: " + date.toLocaleString() + ")...");
            //System.out.flush();
            FileOutputStream fos=null;
            fos = new FileOutputStream(destino);
            int oneChar, count=0;
            while ((oneChar=is.read()) != -1)
            {
                fos.write(oneChar);
                count++;
            }
            is.close();
            fos.close();
            System.out.println(count + " byte(s) copied");            
          }
          return true;
      }
      catch (MalformedURLException e)
      {
          Servicios.logear("DownloadFile", e.toString(), Servicios.ERROR);
          return false;
      }
      catch (IOException e)
      {
          Servicios.logear("DownloadFile", e.toString(), Servicios.ERROR);
          return false;
      }
  }

  public static boolean download(String origen,List<String[]> parametros,String destino)
  {
      try
      {
            String content="";
            Iterator iter = parametros.iterator();
            while (iter.hasNext())
            {
                String[] parametro=(String [])iter.next();
                content+="&"+URLEncoder.encode(parametro[0],"UTF-8")+"="+URLEncoder.encode(parametro[1],"UTF-8");
            }
	    System.out.println("Parametros: " + content.substring(1));

          URL url  = new URL(origen);
          System.out.println("Conectando con: " + origen );
          URLConnection urlC = url.openConnection();

          urlC.setDoOutput(true);
          urlC.setDoInput(true);
          urlC.setUseCaches(false);

         OutputStreamWriter wr = new OutputStreamWriter(urlC.getOutputStream());
         wr.write(content.substring(1));
         wr.flush();
          
          InputStream is = url.openStream();
          FileOutputStream fos=null;
          fos = new FileOutputStream(destino);
          int oneChar, count=0;
          while ((oneChar=is.read()) != -1)
          {
             fos.write(oneChar);
             count++;
          }
          is.close();
          fos.close();
          System.out.println(count + " byte(s) copied");
          return true;
      }
      catch (MalformedURLException e)
      {
          Servicios.logear("DownloadFile", e.toString(), Servicios.ERROR);
          return false;
      }
      catch (IOException e)
      {
          Servicios.logear("DownloadFile", e.toString(), Servicios.ERROR);
          return false;
      }
  }

}
