package com.laguantera.action.galerias;

import java.awt.*;
import java.awt.image.*;
import javax.swing.ImageIcon;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.*;

public class ProcesadorImagenes
{
    /* Constantes */
    /** Umbral a partir del cual aplicaremos filtros de convolucion . */
    protected static final double UMBRAL_APLICACION_FILTRO_CONVOLUCION = 0.5;

    /** Factor de convolucion que aplicamos para los algoritmos de
    * suavizado. */
    private static final Double FACTOR_CONVOLUCION_SUAVIZADO = 1.2;

    /* Atributos */
    /** Opciones de renderizado para las imagenes. */
    protected RenderingHints opcionesRenderizadoImagenes;

    /** Listado de formatos a los que debe aplicarse convolucion por
    * sus perdidas en caso de reducciones muy pronunciadas. */
    protected static List<String> listadoFormatosFiltroReduccionRuido;

    /* Metodos */
    /** Constructor de la clase. Carga opciones de renderizado
    * por defecto, tendiendo a la calidad. */
    public ProcesadorImagenes() {
        cargarOpcionesRenderizadoDefecto();
        cargarListadoFormatosReduccionRuido();
    }

    /** Metodo que carga el listado de formatos que requieren de reducccion de
    * ruido en caso de reducciones sensibles.*/
    private void cargarListadoFormatosReduccionRuido()
    {
        // Lo primero es crear el objeto
        listadoFormatosFiltroReduccionRuido = new ArrayList<String>();
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPEG_MAYUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPEG_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MAYUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_BMP_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_BMP_MAYUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_WBMP_MINUSC);
        listadoFormatosFiltroReduccionRuido.add(CodigosFormatosImagenes.CODIGO_FORMATO_WBMP_MAYUSC);
    }

    /** Metodo que analiza si un formato dado como parametro requiere la aplicacion
    * de algoritmos de reduccion de ruido si es una reduccion sensible.
    * @param formato Formato sobre el que analizamos
    * @return Codigo booleano indicando si se da esta condicion
    */
    protected Boolean esFormatoRequiereReduccionRuido(final String formato)
    {
        // Busco en mi listado
        Iterator<String> it = listadoFormatosFiltroReduccionRuido.iterator();
        while (it.hasNext())
        {
            // Recupero el formato del interior del iterador
            String formatoIt = it.next();
            // Comparo ambos
            if (formatoIt.equals(formato))
            {
                // Formato esta en mi lista “negra” :)
                return true;
            }
        }
        // Si llego hasta aqui es porque el elemento no estaba
        return false;
    }

    /** Metodo que genera una serie de valores por defecto para las opciones
    * de renderizado de las imagenes. */
    private void cargarOpcionesRenderizadoDefecto()
    {
        /* Cargo las opciones de renderizado por defecto para la clase: en general,
        en este caso se tiende a la calidad de las imagenes generadas */
        opcionesRenderizadoImagenes = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_ENABLE);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        opcionesRenderizadoImagenes.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    /** Devuelve la lista de formatos disponibles a leer por ImageIO
    * @return un array de strings con los mismos.
    */
    public String[] dameListadoFormatosUsables(){
        return ImageIO.getReaderFormatNames();
    }

    /** Calcula el factor de escala minimo y en base a eso
    * escala la imagen segun dicho factor.
    * @param maximoAncho maximo tamaño para el ancho de la nueva imagen
    * @param maximoAlto maximo tamaño para el alto de la nueva imagen
    * @param formato Formato de la imagen. Determina los filtros a aplicar
    * @param imagen Imagen original que vamos a escalar
    * @return Devuelve la imagen escalada
    * @throws IOException Excepciones de entrada / salida
    */
    public BufferedImage escalarATamanyo(final File ficheroImagen,
    final Integer maximoAncho, final Integer maximoAlto,
    final String formato) throws IOException
    {
        // Lo primero es obtener un BufferedImage
        BufferedImage imagenFichero = ImageIO.read(ficheroImagen);

        // Aplico otro metodo de la clase
        return escalarATamanyo(imagenFichero, maximoAncho, maximoAlto, formato);
    }

    /** Calcula el factor de escala minimo y en base a eso
    * escala la imagen segun dicho factor.
    * @param maximoAncho maximo tamaño para el ancho de la nueva imagen
    * @param maximoAlto maximo tamaño para el alto de la nueva imagen
    * @param formato Formato de la imagen. Determina los filtros a aplicar
    * @param imagen Imagen original que vamos a escalar
    * @return Devuelve la imagen escalada
    */
    public BufferedImage escalarATamanyo(final BufferedImage imagen,
    final Integer maximoAncho, final Integer maximoAlto,
    final String formato)
    {
        // Comprobacion de parametros
        if (imagen == null || maximoAlto <= 0 || maximoAncho <= 0)
            return imagen;


        // Capturo ancho y alto de la imagen
        int anchoImagen = imagen.getHeight();
        int altoImagen = imagen.getWidth();

        // Segunda comprobacion de parametros
        if (anchoImagen == 0 || altoImagen == 0)
            return imagen;


        // Calculo la relacion entre anchos y altos de la imagen
        double escalaX = (double)maximoAncho / (double)anchoImagen;
        double escalaY = (double)maximoAlto / (double)altoImagen;

        // Tomo como referencia el minimo de las escalas
        double fEscala = Math.min(escalaX, escalaY);

        // Devuelvo el resultado de aplicar esa escala a la imagen
        return escalar(fEscala, imagen, formato);
    }

    /** Escala una imagen en porcentaje.
    * @param factorEscala ejemplo: factorEscala=0.6 (escala la imagen al 60%)
    * @param srcImg una imagen en formato BufferedImage
    * @param formatoOrigen Formato de la imagen. Determina los filtros a aplicar
    * @return un BufferedImage escalado
    */
    public BufferedImage escalar(final Double factorEscala,
    final BufferedImage srcImg, final String formatoOrigen)
    {
        // Comprobacion de parametros
        if (srcImg == null || factorEscala == 0)
            return null;

        // Preparo el tipo de los nuevos BufferedImage
        int tipoFormatoBufferedReader;
        if (formatoOrigen.equals(CodigosFormatosImagenes.CODIGO_FORMATO_GIF))
            tipoFormatoBufferedReader = srcImg.getType();

        else
            tipoFormatoBufferedReader = BufferedImage.TYPE_INT_RGB;


        // Caso de que realmente tengamos que escalar …
        BufferedImage filtroInicial = null;

        // Compruebo escala nula
        if (factorEscala == 1)
        {
            // En ese caso, devuelvo una copia de la imagen original
            BufferedImage copia = new BufferedImage (srcImg.getWidth(),srcImg.getHeight(), tipoFormatoBufferedReader);
            copia.setData(srcImg.getData());
            return copia;
        }
        else
        {
            // Se trata de una reduccion muy acusada ?
            if (factorEscala < UMBRAL_APLICACION_FILTRO_CONVOLUCION && esFormatoRequiereReduccionRuido(formatoOrigen))
            {
                /* Para las imagenes cuyo factor de escala sea menor que el 0.5, preparo un objeto de tipo Kernel */
                Kernel kernel = crearKernelEscala(factorEscala);

                // Lanzo una transformacion afin previa de suavizado
                ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

                // Almaceno en filtroInicial la imagen suavizada
                BufferedImage copia = new BufferedImage (srcImg.getWidth(),srcImg.getHeight(), tipoFormatoBufferedReader);
                copia.setData(srcImg.getData());

                filtroInicial = op.filter(copia, filtroInicial);
            }
            else
            {
                // Factores de escala sin suavizado
                filtroInicial = srcImg;
            }
        }

        // De aqui en adelante, debemos trabajar en base a filtroInicial

        // La creo con las opciones de renderizado que tuviesemos
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(factorEscala, factorEscala), opcionesRenderizadoImagenes);
        BufferedImage resultadoFiltro = op.filter(filtroInicial, null);

        /* Balanceo entre elementos BufferedImage para eliminar canales
        de transparencia extras, si hay */
        BufferedImage biConversion = new BufferedImage (resultadoFiltro.getWidth(),resultadoFiltro.getHeight(), tipoFormatoBufferedReader);
        Graphics2D g = biConversion.createGraphics();

        g.setRenderingHints(opcionesRenderizadoImagenes);
        g.drawImage(resultadoFiltro, 0, 0, Color.WHITE , null);

        // Devuelvo el resultado de aplicar el filtro sobre la imagen
        return biConversion;
    }

    /** Metodo que guarda una imagen en disco
    * @param imagen Imagen a almacenar en disco
    * @param rutaFichero Ruta de la imagen donde vamos a salvar la imagen
    * @param formato Formato de la imagen al almacenarla en disco
    * @return Booleano indicando si se consiguio salvar con exito la imagen
    * @throws IOException Excepciones de entrada / salida generales
    */
    public Boolean salvarImagen(final BufferedImage imagen,
    final String rutaFichero, final String formato)
    throws IOException
    {
        // Comprobacion de parametros
        if (imagen != null && rutaFichero != null && formato != null)
        {
            ImageIO.write( imagen, formato, new File( rutaFichero ));
            return true;
        } else {
            // Fallo en tema de parametros
            return false;
        }
    }

    /** Metodo que crea un objeto de tipo Kernel para aplicar en
    * posteriores transformaciones.
    * @param factorEscala Factor de escala que tiene la imagen
    * @return Objeto Kernel construido
    */
    private Kernel crearKernelEscala(final Double factorEscala)
    {
        // Calculos matematicos de proporciones de suavizado
        int tamanyo = 1 + (int) (FACTOR_CONVOLUCION_SUAVIZADO / factorEscala);
        float[] datos = new float[tamanyo * tamanyo];
        float factor = 1 / (float) datos.length;
        for (int i = 0; i < datos.length; i++)
        {
            datos[i] = factor;
        }

        // Devuelvo un objeto Kernel entendible por el API
        return new Kernel(tamanyo, tamanyo, datos);
    }

    /**
     * Editar una imagen.
     * @param rutaEntradaImagen
     * @param rutaSalidaImagen
     * @param coordenadasX
     * @param coordenadasY
     * @param colorArea en formato hexadecimal (Ej :d7d7d7)
     * @param titulo Titulo para la imagen.
     * @param anadirMarcaAgua Marca de la web.
     * @return
     */
    public boolean editarImagen(String rutaEntradaImagen, String rutaSalidaImagen, int[] coordenadasX,int[] coordenadasY,String colorArea, String titulo,boolean anadirMarcaAgua)
    {
        try
        {
            //Comprobamos si existe el fichero
            File file = new File(rutaEntradaImagen);
            if (!file.exists())
                return false;

            //Cargamos la imagen
            ImageIcon photo = new ImageIcon(rutaEntradaImagen);
            BufferedImage bufferedImage = new BufferedImage(photo.getIconWidth(),photo.getIconHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            g2d.drawImage(photo.getImage(), 0, 0, null);

            //Pintamos un area a tapar(matricula)
            if ((coordenadasX!=null && coordenadasX.length>2) && (coordenadasY!=null && coordenadasY.length>2) && (coordenadasX.length==coordenadasY.length))
            {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.getColor(colorArea));
                g2d.fillPolygon(coordenadasX,coordenadasY, coordenadasX.length);
            }

            //Pintamos el titulo de la imagen
            if (titulo!=null && !titulo.equals(""))
            {
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setFont(new Font("Verdana", Font.BOLD, 15));
                //Recuadro de fondo
                g2d.setColor(Color.black);
                g2d.fillRect(0, 0, photo.getIconWidth(), 20);

                //Texto
                g2d.setColor(Color.white);
                //TODO problema con caracteres no estandar como la e de citroen
                g2d.drawString(titulo,10,16);
            }

            //Pintamos la marca de agua de la web
            if (anadirMarcaAgua)
            {
                ImageIcon marcaAgua = new ImageIcon("F:/FouCrazy/Programacion/LaGuantera/web/test/MarcaAgua.png");
                g2d.drawImage(marcaAgua.getImage(),photo.getIconWidth()-marcaAgua.getIconWidth(), 0, null);
            }

            //Volcamos los cambios
            g2d.dispose();

            //Guardamos la imagen procesada
            this.salvarImagen(bufferedImage, rutaSalidaImagen, CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Método encargado de añadir una marca de agua a una imagen.
     * @param rutaOrigen Ruta hasta la imagen a procesar. 
     * @param rutaDestino Ruta donde se guardará la imagen procesada.
     * @return true si se ha realizado correctamente false en otro caso.
     */
    public boolean anadirMarcaAgua(String rutaOrigen, String rutaDestino)
    {
        try
        {
            File file = new File(rutaOrigen);
            if (!file.exists()){                           
                return false;   
            }

            ImageIcon photo = new ImageIcon(rutaOrigen);           
            BufferedImage bufferedImage = new BufferedImage(photo.getIconWidth(),photo.getIconHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            g2d.drawImage(photo.getImage(), 0, 0, null);

            //Create an alpha composite of 75%
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.75f);
            g2d.setComposite(alpha);            
            g2d.setBackground(Color.BLACK);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            
            String watermark = "LaGuantera.com";

            //FontMetrics fontMetrics = g2d.getFontMetrics();
            //Rectangle2D rect = fontMetrics.getStringBounds(watermark, g2d);

            //Posicion central de la imagen
            //int posicionX=(photo.getIconWidth() - (int) rect.getWidth()) / 2;
            //int posicionY=(photo.getIconHeight() - (int) rect.getHeight()) / 2;

            g2d.setColor(Color.white);
            g2d.fillRect(0, photo.getIconHeight() - 50, photo.getIconWidth()/2, 50);
            int posicionX=(30);
            int posicionY=(photo.getIconHeight() - 15);
            g2d.setColor(Color.black);
            g2d.drawString(watermark,posicionX,posicionY);

            /*g2d.setColor(Color.red);
            int [] xpoints = {400,454,458,403};
            int [] ypoints = {304,301,318,320};
            g2d.fillPolygon(xpoints,ypoints, xpoints.length);*/
            
            //Free graphic resources
            g2d.dispose();

            //Guardamos la imagen procesada
            this.salvarImagen(bufferedImage, rutaDestino, CodigosFormatosImagenes.CODIGO_FORMATO_JPG_MINUSC);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }    	
    }

    public void infoPixelImagen()
    {
        Image image = Toolkit.getDefaultToolkit().getImage("F:/FouCrazy/Programacion/LaGuantera/web/test/colores.jpg");

        try {

            PixelGrabber grabber =
                    new PixelGrabber(image, 0, 0, -1, -1, false);

            if (grabber.grabPixels()) {
                int width = grabber.getWidth();
                int height = grabber.getHeight();

                if (isGreyscaleImage(grabber)) {
                    byte[] data = (byte[]) grabber.getPixels();

                    // Process greyscale image ...

                }
                else {
                    int[] data = (int[]) grabber.getPixels();
                    int j=0;
                    for (int i=0;i< data.length ; i++)
                    {
                        int alpha = (data[i] >> 24) & 0xff;
                        int red   = (data[i] >> 16) & 0xff;
                        int green = (data[i] >>  8) & 0xff;
                        int blue  = (data[i] ) & 0xff;

                        System.out.println(i+"# "+alpha+" # "+red+" # "+green+" # "+blue+" #");
                        j++;

                        if (j==16){
                            System.out.println("--------------------------------------");
                            j=0;
                        }
                    }
                }
            }
        }
        catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static final boolean isGreyscaleImage(PixelGrabber pg) {
        return pg.getPixels() instanceof byte[];
    }
}