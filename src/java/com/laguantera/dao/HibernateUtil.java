package com.laguantera.dao;


import com.laguantera.util.Servicios;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Desarrollo
 */
public class HibernateUtil {
    private static  SessionFactory sessionFactory=null;


    public static SessionFactory getSessionFactory() {

        if (sessionFactory==null)
        {
            try {                                
                Configuration configHB = new Configuration().configure(); //Se carga la configuracion existente en hibernate.cfg.xml
                sessionFactory = configHB.buildSessionFactory();//Se crea el sessionFactory con la combinación de propiedades
                Servicios.logear("HibernateUtil", "Creado sessionFactory", Servicios.DEBUG);

                //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            } catch (Exception ex) {                
                Servicios.logear("HibernateUtil", "No se ha podido crear el sessionFactory:"+ex.toString(), Servicios.FATAL);                
            }
        }

        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        HibernateUtil.sessionFactory = sessionFactory;
    }
}
