package com.laguantera.dao;

import com.laguantera.action.calculadora.Calcular;
import com.laguantera.dao.model.GaleriaResumen;
import com.laguantera.dao.model.MultimediaResumen;
import com.laguantera.dao.model.VehiculoResumen;
import com.laguantera.util.Constantes;
import com.laguantera.util.Servicios;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Clase encargada de realizar todas las consultas de la base de datos
 * @author Felix Gonzalez de Santos
 */
public class PeticionesBD {

    private final String nombreClase="PeticionesBD";
    public Session session = null;
    Transaction transaccion = null;

     public PeticionesBD() {
    }

    public Transaction getTransaccion() {
        return transaccion;
    }

     public boolean abrirSesion()
    {
        try{
            if (this.session==null || !this.session.isOpen()||!this.session.isConnected())
            {
                session = HibernateUtil.getSessionFactory().openSession();
            }
            else
                this.session = HibernateUtil.getSessionFactory().getCurrentSession();
            this.transaccion=session.beginTransaction();
            
            //Servicios.logear(nombreClase, "abrirSesion:nueva sesion y transaccion", Servicios.DEBUG);
            return true;            
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "abrirSesion Exception:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Cierra la sesion y la transaccion actualmente abiertas.
     */
    public boolean cerrarSesion()
    {
        //Servicios.logear(nombreClase, "cerrarSesion: cerrando transaccion y sesion.", Servicios.DEBUG);
        try
        {
            if (transaccion.isActive()){
                transaccion.commit(); //Suele provocar el cierre de la session
                transaccion=null;
            }
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "cerrarSesion: Problema cerrando transaccion:"+ex.getMessage(), Servicios.ERROR);
            ex.printStackTrace();
            return false;
        }

        try{
            if (session.isOpen())
            {
                session.flush();
                session.disconnect();
                session.close();
                session=null;
                //sessionFactory.close();
            }
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "cerrarSesion: Problema cerrando session:"+ex.getMessage(), Servicios.ERROR);
            ex.printStackTrace();
            return false;
        }
        return true;
    }

//TODO corregir
    /**
     * Ejecuta una consulta SQL 
     * @param consulta
     * @return
     */
    /*public String[][] ejecutarSQL(String consulta,int numeroFilas, int numeroColumnas)
    {
        ResultSet resultado=null;
        String resu[][] = new String[numeroFilas][numeroColumnas];
        int contador = 0, i = 0;

        try{
            //Abrimos conexion JDBC
            Connection con = session.creconnection();
            Servicios.logear(nombreClase,"Se va a ejecutar la consulta SQL:"+consulta, Servicios.INFO);
            //Ejecutamos la consulta
            resultado = con.createStatement().executeQuery(consulta);

            while (resultado.next() && (i < numeroFilas)) {
                for (contador = 0; contador < numeroColumnas; contador++) 
                {
                    try{
                        resu[i][contador] = resultado.getString(contador + 1);
                    }catch(SQLException sqlEx){
                        Servicios.logear(nombreClase,"Columna solicitada no disponible:"+sqlEx.toString(),Servicios.DEBUG);
                    }
                }
                i++;
            }

            //Cerramos la conexion
            con.close();
        } catch (SQLException errorSql) {
            Servicios.logear(nombreClase, "ejecutarSQL,SQLExcepcion:"+errorSql.getMessage(), Servicios.ERROR);            
    	}catch (Exception ex) {
            Servicios.logear(nombreClase, "ejecutarSQL,Excepcion:"+ex.getMessage(), Servicios.ERROR);
            resultado=null;
        }
        return resu;
    }*/

//
// CONFIGURACION DEL PORTAL
//
    /**
     * Comprueba si existen configuraciones para el portal en la base de datos.
     * @return
     */
    public boolean estaConfigurado(){
    	try{
            Query q = session.createQuery("select count(*) from Config");
            Long cantidad = (Long)q.uniqueResult();
            if (cantidad!=null && cantidad>0){
            	return true;
            }else{
            	return false;
            }
    	}catch (Exception ex) {
            Servicios.logear(nombreClase, "estaConfigurado,Excepcion:"+ex.getMessage(), Servicios.ERROR);
            return false;
        }     
    }
    
    /**
     * Obtiene todas las configuraciones del portal. 
     * @return
     */
    public List<Config> loadConfigs(){
    	try{
            Query q = session.createQuery("from Config");
            List resultados=(List<Config>) q.list();
            return resultados;

    	}catch (Exception ex) {
            Servicios.logear(nombreClase, "loadConfigs,Excepcion:"+ex.getMessage(), Servicios.ERROR);
            return null;
        }         	
    }
    
    /**
     * Guarda un valor de configuración en la base de datos
     * @param configVal Valor de configuración que se desea guardar.
     * @return true/false
     */
    public boolean guardarConfig(Config configVal)
    {
        try{            
            session.saveOrUpdate(configVal);
            return true;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "No se ha podido guardar la config:"+ex.toString(), Servicios.FATAL);
            return false;
        }
    }

    /**
     * Eliminar un valor de configuración en la base de datos
     * @param configVal Valor de configuración que se desea eliminar.
     * @return true/false
     */
    public boolean eliminarConfig(Config configVal)
    {
        try{
            String hql = "delete from Config where nombre='"+configVal.getNombre()+"'";
            Query query = session.createQuery(hql);
            int row = query.executeUpdate();
            session.flush();
            if (row>0){
                Servicios.logear(nombreClase, "Configuración eliminada.", Servicios.DEBUG);
                return true;
            }else{
                Servicios.logear(nombreClase, "No se ha eliminado ninguna configuración.", Servicios.DEBUG);
                return false;
            }
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "No se ha podido eliminar la config:"+ex.toString(), Servicios.FATAL);
            return false;
        }
    }

//
// USUARIOS
//

    /**
     * Comprueba si existe el usuario dado por el alias y la password.
     * @param alias
     * @param pass
     * @return null si no existe o el id del Usuario
     */
    public Integer loadUsuario(String alias,String pass)
    {
        Integer idUsuario=null;
        try {            
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("select idUsuario from Usuarios where alias=:alias and password=:pass");
            q.setParameter("alias",alias);
            q.setParameter("pass",pass);
            idUsuario = (Integer)q.uniqueResult();

            if (idUsuario!=null)
                Servicios.logear(nombreClase, "loadUsuario(alias),el usuario existe.", Servicios.DEBUG);            
            else
                Servicios.logear(nombreClase, "loadUsuario(alias),intentando cargar usuario inexsistente:"+alias+"-"+pass, Servicios.INFO);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadUsuario(alias),Excepcion:"+ex.getMessage(), Servicios.ERROR);
        }        
        return idUsuario;
    }

   /**
     * Comprueba si existe el usuario dado por el alias y la password.
     * @param alias
     * @param pass
     * @return null si no existe o el objeto con la informacion del Usuario
     */
    public Usuarios loadUsuarioCompleto(String alias,String pass)
    {
        Usuarios usuario=null;
        Integer idUsuario=null;
        try {            
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("select idUsuario from Usuarios where alias=:alias and password=:pass");
            q.setParameter("alias",alias);
            q.setParameter("pass",pass);
            idUsuario = (Integer)q.uniqueResult();

            if (idUsuario!=null){
                usuario=loadUsuario(idUsuario);
                usuario.getTiposUsuario();
            }
            else
                Servicios.logear(nombreClase, "loadUsuarioCompleto,intentando cargar usuario inexsistente:"+alias+"-"+pass, Servicios.INFO);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadUsuarioCompleto,Excepcion:"+ex.getMessage(), Servicios.ERROR);
        }        
        return usuario;
    }    
    
    /**
     * Carga un usuario.
     * @param idUsuario
     * @return
     */
    public Usuarios loadUsuario(Integer idUsuario)
    {
        Usuarios usuario=null;
        try {
            usuario=(Usuarios)session.load(Usuarios.class,new Integer(idUsuario));
            Servicios.logear(nombreClase, "loadUsuario, usuario cargado:"+usuario.getAlias(), Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadUsuario:"+ex.getMessage(), Servicios.ERROR);
        }

        return usuario;
    }

    /**
     * Carga una lista de todos los usuarios que existen.
     * @return Lista de usuarios o null en caso de fallo.
     */
    public List<Usuarios> loadUsuarios()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Usuarios");
            lista = (List<Usuarios>) q.list();
            Servicios.logear(nombreClase, "loadUsuarios,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadUsuarios, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de todos los tipos de usuarios que existen.
     * @return Lista de tipos de usuarios o null en caso de fallo.
     */
    public List<TiposUsuario> loadTiposUsuario()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from TiposUsuario");
            lista = (List<TiposUsuario>) q.list();
            Servicios.logear(nombreClase, "loadTiposUsuario,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTiposUsuario, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Da de alta un nuevo usuario.
     * @param alias
     * @param password
     * @param ciudad
     * @param codigoPostal
     * @param email
     * @param tipo
     * @return true si se completa con exito o false en caso de problemas.
     */
    public boolean addUsuario(String alias,String password,Integer codigoPostal, String email,Integer tipo)
    {        
        try{            
            //Añadimos el usuario
            alias=alias.toLowerCase();
            Usuarios nuevoUsuario=new Usuarios();
            nuevoUsuario.setAlias(alias);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setCodigoPostal(codigoPostal);
            nuevoUsuario.setEmail(email);
            TiposUsuario tipoUsuario=new TiposUsuario();
            tipoUsuario.setIdTipoUsuario(tipo);
            nuevoUsuario.setTiposUsuario(tipoUsuario);
            session.save(nuevoUsuario);            
            Servicios.logear(nombreClase, "Usuario añadido correctamente:"+alias, Servicios.INFO);

        }catch(Exception ex){
            transaccion.rollback();
            Servicios.logear(nombreClase, "addUsuario,Excepcion::"+ex.toString(), Servicios.ERROR);            
            return false;
        }
        
        return true;
    }

    /**
     * Comprueba si existe algun usuario con un determinado alias o email.
     * @param alias
     * @param email
     * @return true si existe algun usuario con ese alias o email, false en otro caso.
     */
    public boolean existeUsuario(String alias,String email)
    {
        boolean resultado=false;;
        try {            
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("from Usuarios where alias=:alias or email=:email");
            q.setParameter("alias", alias);
            q.setParameter("email", email);
            if (q.list().size()>0)
            {
                resultado=true;
                Servicios.logear(nombreClase, "existeUsuario,alias o email ya utilizado.", Servicios.DEBUG);
            }
            else
                Servicios.logear(nombreClase, "existeUsuario,no existe.", Servicios.DEBUG);
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "existeUsuario,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
       return resultado;
    }

    /**
     * Comprueba si existe algun usuario con un determinado alias o email que no sea el
     * especificado por parametro.
     * @param alias
     * @param email
     * @param idUsuario id del usuario a excluir para la comprobacion
     * @return true si existe algun usuario con ese alias o email, false en otro caso.
     */
    public boolean existeUsuario(String alias,String email,Integer idUsuario)
    {
        boolean resultado=false;;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("from Usuarios where (alias=:alias or email=:email) and idUsuario!=:idUsuario");
            q.setParameter("alias", alias);
            q.setParameter("email", email);
            q.setParameter("idUsuario", idUsuario);
            if (q.list().size()>0)
            {
                resultado=true;
                Servicios.logear(nombreClase, "existeUsuario,alias o email ya utilizado.", Servicios.DEBUG);
            }
            else
                Servicios.logear(nombreClase, "existeUsuario,no existe.", Servicios.DEBUG);
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "existeUsuario,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
       return resultado;
    }

    /**
     * Cambia la pass de un usuario a partir de su email.
     * @param email
     * @return null si no se ha podido cambiar.
     */
    public boolean cambiarPass(String email, String nuevaPass)
    {
        boolean resultado=false;

        try {            
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("update Usuarios set password=:nueva where email=:email");
            q.setString("nueva", nuevaPass);
            q.setString("email", email);
            int actualizadas = q.executeUpdate();

            if (actualizadas>0)
            {
                Servicios.logear(nombreClase, "cambiarPass,Contraseña modificada.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "cambiarPass,no existe usuario con ese e-mail.", Servicios.DEBUG);
            }

        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "cambiarPass,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        
        return resultado;
    }

    /**
     * Eliminación de un usuario de la base de datos ( y con ello todos sus datos asociados )
     * @param idUsuario Identificador del usuario a eliminar
     */
    public boolean delUsuario(Integer idUsuario)
    {
        boolean resultado=false;
        try {            
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Usuarios where idUsuario=:id");
            q.setInteger("id", idUsuario);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delUsuario,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delUsuario,no existe usuario con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delUsuario,Excepcion:"+ex.toString(), Servicios.ERROR);
        }       
        return resultado;
    }

    /**
     * Actualización de los datos de un usuario a partir de su id
     * @param alias
     * @param codigoPostal
     * @param email
     * @param idUsuario
     * @param password Contraseña ya cifrada
     * @return true o false
     */
    public boolean updUsuario(Integer idUsuario,String alias, String password, Integer codigoPostal, String email)
    {
        boolean resultado=false;
        try {            
            //Parametrizacion para evitar SQL Injection
            Query q=null;
            if (password!=null&&!password.isEmpty()){
                q = session.createQuery("update Usuarios set alias=:ali , password=:pass , codigoPostal=:cp , email=:correo where idUsuario=:id");
                q.setString("pass", password);
            }else{
                q = session.createQuery("update Usuarios set alias=:ali , codigoPostal=:cp , email=:correo where idUsuario=:id");
            }
            
            q.setString("ali", alias);
            q.setInteger("cp", codigoPostal);
            q.setString("correo", email);
            q.setInteger("id", idUsuario);
            
            int actualizadas = q.executeUpdate();

            if (actualizadas>0)
            {
                Servicios.logear(nombreClase, "updUsuario,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "updUsuario,no existe usuario con ese id.", Servicios.DEBUG);
            }

        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "updUsuario,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        
        return resultado;
    }


//
// VEHICULOS
//
    /**
     * Añade un nueva ficha tecnica de un vehiculo.
     * @param nuevo
     * @return
     */
    public boolean addInfoVehiculo(Info_Vehiculos nuevo)
    {
        try{
            session.save(nuevo);
            Servicios.logear(nombreClase, "addInfoVehiculo,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addInfoVehiculo,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addInfoVehiculo,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }
    
    /**
     * Borra todas las fichas de vehiculos
     * @return
     */
    public boolean borrarInfoVehiculos(){
        boolean resultado=false;
        try {
            Query q = session.createQuery("Delete from Info_Vehiculos");
            q.executeUpdate();
            Servicios.logear(nombreClase, "borrarInfoVehiculos,OK.", Servicios.DEBUG);
            resultado=true;
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "borrarInfoVehiculos,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }       
    
    /**
     * Añade un nuevo vehiculo a la cuenta de un usuario.
     * @param nuevo Objeto tipo Vehiculo con todos los datos cargados.
     * @return
     */
    public boolean addVehiculo(Vehiculos nuevo)
    {
        try{
            session.save(nuevo);
            Servicios.logear(nombreClase, "addVehiculo,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addVehiculo,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addVehiculo,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }


    /**
     * Actualización de la información de un vehiculo
     * @param vToUpd Objeto de tipo Vehiculos con los nuevos datos.
     * @return true - false
     */
    public boolean updVehiculo(Vehiculos vToUpd)
    {
        boolean resultado=false;

        try{
            Query q;
            String imagen= vToUpd.getImagen();
            if(imagen!=null && !imagen.equals(""))
            {
                //Parametrizacion para evitar SQL Injection
                q = session.createQuery("update Vehiculos set tiposCombustible=:com," +
                        " cv=:cv, tara=:tara,motor=:motor,asientos=:asientos,cilindrada=:cilindrada,cilindros=:cilindros, " +
                        "fechaFabricacion=:fechaFabricacion, fechaMatriculacion=:fechaMatriculacion, kw=:kw, matricula=:matricula, " +
                        "neumaticos=:neumaticos, imagen=:imagen where idVehiculo=:id");
                q.setString("imagen",imagen);
            }
            else
            {
                q = session.createQuery("update Vehiculos set tiposCombustible=:com," +
                        " cv=:cv, tara=:tara,motor=:motor,asientos=:asientos,cilindrada=:cilindrada,cilindros=:cilindros, " +
                        "fechaFabricacion=:fechaFabricacion, fechaMatriculacion=:fechaMatriculacion, kw=:kw, matricula=:matricula, " +
                        "neumaticos=:neumaticos where idVehiculo=:id");
            }

            q.setInteger("com", vToUpd.getTiposCombustible().getIdTipoCombustible());
            q.setFloat("cv", vToUpd.getCv());
            q.setInteger("tara", vToUpd.getTara());
            q.setString("motor", vToUpd.getMotor());
            q.setInteger("asientos", vToUpd.getAsientos());
            q.setInteger("cilindrada", vToUpd.getCilindrada());
            q.setInteger("cilindros", vToUpd.getCilindros());
            q.setDate("fechaFabricacion", vToUpd.getFechaFabricacion());
            q.setDate("fechaMatriculacion", vToUpd.getFechaMatriculacion());
            q.setFloat("kw", vToUpd.getKw());
            q.setString("matricula", vToUpd.getMatricula());
            q.setString("neumaticos", vToUpd.getNeumaticos());
            q.setInteger("id", vToUpd.getIdVehiculo());                                        

            int actualizadas = q.executeUpdate();

            if (actualizadas>0)
            {
                Servicios.logear(nombreClase, "updVehiculo,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "updVehiculo,no existe vehiculo con ese id.", Servicios.DEBUG);
            }
                        
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "updVehiculo,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            resultado=false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "updVehiculo,Excep:"+ex.toString(), Servicios.ERROR);
            resultado=false;
        }
        return resultado;
    }

    /**
     * Eliminación de un vehiculo de la base de datos ( y con ello todos sus datos asociados )
     * @param idVehiculo Identificador del vehiculo a eliminar
     */
    public boolean delVehiculo(Integer idVehiculo)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Vehiculos where idVehiculo=:id");
            q.setInteger("id", idVehiculo);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delVehiculo,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delVehiculo,no existe vehiculo con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delVehiculo,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }


    /**
     * Carga un vehiculo.
     * @param idVehiculo
     * @return
     */
    public Vehiculos loadVehiculo(Integer idVehiculo)
    {
        Vehiculos vehiculo=null;
        try {
            vehiculo=(Vehiculos)session.load(Vehiculos.class,new Integer(idVehiculo));
            Servicios.logear(nombreClase, "loadVehiculo, vehiculo cargado:"+vehiculo.getModelos().getNombreModelo(), Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadVehiculo:"+ex.getMessage(), Servicios.ERROR);
        }

        return vehiculo;
    }

    /**
     * Carga una lista de todos los vehiculos que existen.
     * @return Lista de vehiculos o null en caso de fallo.
     */
    public List<Vehiculos> loadVehiculos()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Vehiculos");
            lista = (List<Vehiculos>) q.list();
            Servicios.logear(nombreClase, "loadVehiculos,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadVehiculos, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de todos los vehiculos que existen en formato resumen.
     * @return Lista de vehiculos o null en caso de fallo.
     */
    public List<VehiculoResumen> loadVehiculosResumen()
    {
        List lista=null;

        try {
            Query q = session.createQuery("Select idVehiculo,modelos.marcas.nombreMarca,modelos.nombreModelo,matricula,imagen" +
                    " from Vehiculos");
            lista = VehiculoResumen.ToListVehiculoResumen(q.list());
            Servicios.logear(nombreClase, "loadVehiculosResumen,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadVehiculosResumen, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de todos los vehiculos de un determinado usuario.
     * @return Lista de vehiculos del usuario o null en caso de fallo.
     */
    public List<VehiculoResumen> loadVehiculos(Integer idUsuario)
    {
         List<VehiculoResumen> lista=null;

        try {
            Query q = session.createQuery("Select idVehiculo,modelos.marcas.nombreMarca,modelos.nombreModelo,matricula,imagen" +
                    " from Vehiculos where usuarios.idUsuario=:id");
            q.setInteger("id", idUsuario);
            lista = VehiculoResumen.ToListVehiculoResumen(q.list());
            Servicios.logear(nombreClase, "loadVehiculos,OK:"+lista.size()+" vehiculos cargados.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadVehiculos, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Añade un nuevo modelos de coche.
     * @param nueva
     * @return
     */
    public boolean addModelos(Modelos modelo)
    {
        try{
            session.save(modelo);
            Servicios.logear(nombreClase, "addmodelos,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e){
            Servicios.logear(nombreClase, "addmodelos,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex){
            Servicios.logear(nombreClase, "addmodelos,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }    

    /**
     * Carga una lista de todos los modelos que existen.
     * @return Lista de modelos o null en caso de fallo.
     */
    public List<Modelos> loadModelos()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Modelos");
            lista = (List<Modelos>) q.list();
            Servicios.logear(nombreClase, "loadModelos,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadModelos, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de todos los modelos que existen para una determinada marca.
     * @return Lista de modelos o null en caso de fallo.
     */
    public List<Modelos> loadModelos(Integer idMarca)
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Modelos where idMarca=:id");
            q.setInteger("id", idMarca);
            lista = (List<Modelos>) q.list();
            Servicios.logear(nombreClase, "loadModelos(idMarca),OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadModelos(idMarca), Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }
    
    /**
     * Borra todos los modelos de la base de datos.
     * @return
     */
    public boolean borrarModelos(){
        boolean resultado=false;
        try {
            Query q = session.createQuery("Delete from Modelos");
            q.executeUpdate();
            Servicios.logear(nombreClase, "borrarModelos,OK.", Servicios.DEBUG);
            resultado=true;
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "borrarModelos,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }        

    
    /**
     * Añade una nueva marca de coches.
     * @param nueva
     * @return
     */
    public boolean addMarca(Marcas nueva)
    {
        try{
            session.save(nueva);
            Servicios.logear(nombreClase, "addMarca,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e){
            Servicios.logear(nombreClase, "addMarca,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex){
            Servicios.logear(nombreClase, "addMarca,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }
    
    /**
     * Carga una lista de todas las marcas que existen.
     * @return Lista de marcas o null en caso de fallo.
     */
    public List<Marcas> loadMarcas()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Marcas");
            lista = (List<Marcas>) q.list();
            Servicios.logear(nombreClase, "loadMarcas,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadMarcas, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de marcas que se ajusten a la consulta
     * @param query
     * @return 
     */
    public List getMarcas(String query){
        List resultados=new ArrayList();
        try{
            String consulta;            
            query=Servicios.filtroSQLInjection(query);
            if (query!=null && !query.equals("")){            
                consulta="SELECT idMarca, nombreMarca FROM marcas where nombreMarca like '%"+query+"%' limit 10;";
                Query q = session.createSQLQuery(consulta);
                resultados=q.list();                
            }
        }catch(Exception ex){
            Servicios.logear(nombreClase,"getMarcas,Exception:"+ex.toString(),Servicios.ERROR);
        }
        return resultados;
    }    
    
    /**
     * Borra todas las marcas de la base de datos.
     * @return
     */
    public boolean borrarMarcas(){
        boolean resultado=false;
        try {
            Query q = session.createQuery("Delete from Marcas");
            q.executeUpdate();
            Servicios.logear(nombreClase, "borrarMarcas,OK.", Servicios.DEBUG);
            resultado=true;
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "borrarMarcas,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }    
    
    /**
     * Añade un nuevo combustible.
     * @param nuevo
     * @return
     */
    public boolean addTipoCombustible(TiposCombustible nuevo)
    {
        try{
            session.save(nuevo);
            Servicios.logear(nombreClase, "addTipoCombustible,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e){
            Servicios.logear(nombreClase, "addTipoCombustible,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex){
            Servicios.logear(nombreClase, "addTipoCombustible,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }    

    /**
     * Carga un solo tipo de combustible
     * @return Objeto tipo de combustible o null en caso de fallo
     */
    public TiposCombustible loadTipoCombustible(int idTipoCombustible)
    {
        TiposCombustible tipo;

        try {
            Query q = session.createQuery("from TiposCombustible where idTipoCombustible=:id");
            q.setInteger("id", idTipoCombustible);
            tipo = (TiposCombustible) q.uniqueResult();
            Servicios.logear(nombreClase, "loadTipoCombustible,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTipoCombustible, Excepcion:"+ex.toString(), Servicios.ERROR);
            tipo=null;
        }
        return tipo;
    }
    
    
    /**
     * Carga una lista de todos los tipos de combustible que existen.
     * @return Lista de tipos de combustible o null en caso de fallo.
     */
    public List<TiposCombustible> loadTiposCombustible()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from TiposCombustible");
            lista = (List<TiposCombustible>) q.list();
            Servicios.logear(nombreClase, "loadTiposCombustible,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTiposCombustible, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

//
// GALERIAS
//

    /**
     * Crea una nueva galería asociada a un vehículo.
     * @return
     */
    public boolean addGaleria(Galerias galeria)
    {
        try{
            session.save(galeria);
            Servicios.logear(nombreClase, "addGaleria,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addGaleria,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addGaleria,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Eliminación de una galeria de la base de datos ( y con ello todos sus datos asociados )
     * @param idGaleria Identificador de la galeria a eliminar
     */
    public boolean delGaleria(Integer idGaleria)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Galerias where idGaleria=:id");
            q.setInteger("id", idGaleria);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delGaleria,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delGaleria,no existe galeria con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delGaleria,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

    /**
     * Actualización de la información de una galeria
     * @param gToUpd Objeto de tipo Galerias con los nuevos datos.
     * @return true - false
     */
    public boolean updGaleria(Galerias gToUpd)
    {
        boolean resultado=false;

        try{
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("update Galerias set titulo=:tit where idGaleria=:id");
            q.setString("tit", gToUpd.getTitulo());
            q.setInteger("id", gToUpd.getIdGaleria());

            int actualizadas = q.executeUpdate();

            if (actualizadas>0)
            {
                Servicios.logear(nombreClase, "updGaleria,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "updGaleria,no existe galeria con ese id.", Servicios.DEBUG);
            }

        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "updGaleria,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            resultado=false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "updGaleria,Excep:"+ex.toString(), Servicios.ERROR);
            resultado=false;
        }
        return resultado;
    }

    /**
     * Carga una galeria.
     * @param idGaleria
     * @return
     */
    public Galerias loadGaleria(Integer idGaleria)
    {
        Galerias galeria=null;
        try {
            galeria=(Galerias)session.load(Galerias.class,new Integer(idGaleria));
            Set<Multimedias> multi = galeria.getMultimediases();              
            String matricula = galeria.getVehiculos().getMatricula();
            Servicios.logear(nombreClase, "loadGaleria, galeria cargada:"+galeria.getTitulo(), Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadGaleria:"+ex.getMessage(), Servicios.ERROR);
        }

        return galeria;
    }


    /**
     * Carga toda la informacion y asociaciones necesarias de una galera
     * @return Datos y multimedias de una galeria o null en caso de fallo.
     */
    public GaleriaResumen loadGaleriaCompleta(Integer idGaleria)
    {
         GaleriaResumen galeria=null;

        try {
            Query q = session.createQuery("Select g.idGaleria,g.titulo,v.idVehiculo,v.matricula "
                    + "from Galerias g, Vehiculos v where g.vehiculos.idVehiculo=v.idVehiculo and g.idGaleria=:id");
            q.setInteger("id", idGaleria);
            galeria = GaleriaResumen.ToGaleriaResumen((Object [])q.uniqueResult());
            
            q = session.createQuery("Select idMultimedia,titulo,tiposMultimedia.idTipoMultimedia,ruta, descripcion,galerias.idGaleria"
                    + " from Multimedias where idGaleria=:id");
            q.setInteger("id", idGaleria);
            List<MultimediaResumen> multimedias = GaleriaResumen.ToListMultimediaResumen(q.list());
            galeria.setMultimedias(multimedias);
            
            Servicios.logear(nombreClase, "loadGaleriaCompleta,OK", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadGaleriaCompleta, Excepcion:"+ex.toString(), Servicios.ERROR);
            galeria=null;
        }
        return galeria;
    }    
    
    /**
     * Carga una lista de todos las galerias que existen.
     * @return Lista de galerias o null en caso de fallo.
     */
    public List<Galerias> loadGalerias()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Galerias");
            lista = (List<Galerias>) q.list();
            Servicios.logear(nombreClase, "loadGalerias,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadGalerias, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de todos las galerias que tiene un usuario.
     * @return Lista de galerias o null en caso de fallo.
     */
    public List<Galerias> loadGaleriasUsuario(Integer idUsuario)
    {
        List<Galerias> lista=new ArrayList<Galerias>();

        try {
            List<VehiculoResumen> vehiculos = loadVehiculos(idUsuario);
            for(VehiculoResumen vehiculo: vehiculos){                            
                Query q = session.createQuery("from Galerias where idVehiculo=:id");
                q.setInteger("id", vehiculo.getIdVehiculo());
                List<Galerias> temp = (List<Galerias>) q.list();
                lista.addAll(temp);
            }
            Servicios.logear(nombreClase, "loadGalerias,OK:"+lista.size()+" galerias encontradas", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadGalerias, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }    
    
    /**
     * Carga una lista de todos las galerias que existen.
     * @return Lista de galerias o null en caso de fallo.
     */
    public List<Galerias> loadGaleriasVehiculo(Integer idVehiculo)
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Galerias where idVehiculo=:id");
            q.setInteger("id", idVehiculo);
            lista = (List<Galerias>) q.list();
            Servicios.logear(nombreClase, "loadGalerias,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadGalerias, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }


//
// MULTIMEDIAS
//


    /**
     * Añade un nuevo multimedia a una galeria
     * @return true - false
     */
    public boolean addMultimedia(Multimedias multi)
    {
        try{
            session.save(multi);
            Servicios.logear(nombreClase, "addMultimedia,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addMultimedia,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addMultimedia,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Elimina un fichero multimedia de la base de datos
     * @param idGaleria Identificador de la galeria a eliminar
     */
    public boolean delMultimedia(Integer idMultimedia)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Multimedias where idMultimedia=:id");
            q.setInteger("id", idMultimedia);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delMultimedia,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delMultimedia,no existe multimedia con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delMultimedia,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

    /**
     * Actualización de la información de un multimedia
     * @param mToUpd Objeto de tipo Multimedias con los nuevos datos.
     * @return true - false
     */
    public boolean updMultimedia(Multimedias mToUpd)
    {
        boolean resultado=false;

        try{
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("update Multimedias set titulo=:tit,descripcion=:desc,idGaleria=:idGal where idMultimedia=:id");
            q.setString("tit", mToUpd.getTitulo());
            q.setString("desc", mToUpd.getDescripcion());
            q.setInteger("idGal", mToUpd.getGalerias().getIdGaleria());
            q.setInteger("id", mToUpd.getIdMultimedia());

            int actualizadas = q.executeUpdate();

            if (actualizadas>0)
            {
                Servicios.logear(nombreClase, "updMultimedia,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "updMultimedia,no existe multimedia con ese id.", Servicios.DEBUG);
            }

        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "updMultimedia,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            resultado=false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "updMultimedia,Excep:"+ex.toString(), Servicios.ERROR);
            resultado=false;
        }
        return resultado;
    }

     /**
     * Carga un multimedia.
     * @param idGaleria
     * @return
     */
    public Multimedias loadMultimedia(Integer idMultimedia)
    {
        Multimedias multimedia=null;
        try {
            multimedia=(Multimedias)session.load(Multimedias.class,new Integer(idMultimedia));
            Servicios.logear(nombreClase, "loadMultimedia, galeria cargada:"+multimedia.getTitulo(), Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadMultimedia:"+ex.getMessage(), Servicios.ERROR);
        }

        return multimedia;
    }

    /**
     * Carga una lista de todos los multimedias que existen.
     * @return Lista de multimedias o null en caso de fallo.
     */
    public List<Multimedias> loadMultimedias()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Multimedias");
            lista = (List<Multimedias>) q.list();
            Servicios.logear(nombreClase, "loadMultimedias,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadMultimedias, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

     public List<TiposMultimedia> loadTiposMultimedia(){
        List lista=null;

        try {
            Query q = session.createQuery("from TiposMultimedia");
            lista = (List<TiposMultimedia>) q.list();
            Servicios.logear(nombreClase, "loadTiposMultimedia,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTiposMultimedia, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }
    

//
// ELEMENTOS
//

    /**
     * Crea una nuevo elemento
     * @return boolean
     */
    public boolean addElemento(Elementos elemento)
    {
        try{
            session.save(elemento);
            Servicios.logear(nombreClase, "addElemento,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addElemento,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addElemento,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Eliminación de un elemento.
     * @param idElemento Identificador del elemento a eliminar.
     */
    public boolean delElemento(Integer idElemento)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Elementos where idElemento=:id");
            q.setInteger("id", idElemento);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delElemento,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delElemento,no existe elemento con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delElemento,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

    /**
     * Carga una lista de todos los elementos que existen.
     * @return Lista de elementos o null en caso de fallo.
     */
    public List<Elementos> loadElementos()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Elementos");
            lista = (List<Elementos>) q.list();
            Servicios.logear(nombreClase, "loadElementos,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadElementos, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Carga una lista de todos los elementos que existen de un determinado tipo.
     * @return Lista de elementos o null en caso de fallo.
     */
    public List<Elementos> loadElementosPorTipo(Integer idTipoElemento)
    {
        List lista=null;

        try {
            Query q = session.createQuery("from Elementos where idTipoElemento=:id");
            q.setInteger("id", idTipoElemento);
            lista = (List<Elementos>) q.list();
            Servicios.logear(nombreClase, "loadElementosPorTipo,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadElloadElementosPorTipoementos, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }


//
// TIPOS DE ELEMENTO
//


    /**
     * Crea un nuevo tipo de elemento.
     * @return boolean
     */
    public boolean addTipoElemento(TiposElemento tipoElemento)
    {
        try{
            session.save(tipoElemento);
            Servicios.logear(nombreClase, "addTipoElemento,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addTipoElemento,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addTipoElemento,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Eliminación de un tipo de elemento.
     * @param idTipoElemento Identificador del elemento a eliminar.
     */
    public boolean delTipoElemento(Integer idTipoElemento)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from TiposElemento where idTipoElemento=:id");
            q.setInteger("id", idTipoElemento);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delTipoElemento,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delTipoElemento,no existe tipo de elemento con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delTipoElemento,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

    /**
     * Carga una lista de todos los tipos de elemento que existen.
     * @return Lista de tipos de elemento o null en caso de fallo.
     */
    public List<TiposElemento> loadTiposElemento()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from TiposElemento");
            lista = (List<TiposElemento>) q.list();
            Servicios.logear(nombreClase, "loadTiposElemento,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTiposElemento, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }


//
// COSTES DERIVADOS
//

    /**
     * Crea un nuevo coste derivado
     * @return boolean
     */
    public boolean addCosteDerivado(CostesDerivados coste)
    {
        try{
            session.save(coste);
            Servicios.logear(nombreClase, "addCosteDerivado,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addCosteDerivado,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addCosteDerivado,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Eliminación de un coste derivado
     * @param idCosteDerivado Identificador del coste derivado
     */
    public boolean delCosteDerivado(Integer idTipoCoste, Integer idVehiculo)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from CostesDerivados where idTipoCoste=:idTipo and idVehiculo=:idVehiculo");
            q.setInteger("idTipo", idTipoCoste);
            q.setInteger("idVehiculo", idVehiculo);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delCosteDerivado,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delCosteDerivado,no existe coste derivado con esos ids.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delCosteDerivado,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }


//
// TIPOS DE COSTE
//

   /**
     * Crea un nuevo tipo de coste.
     * @return boolean
     */
    public boolean addTipoCoste(TiposCoste tipoCoste)
    {
        try{
            session.save(tipoCoste);
            Servicios.logear(nombreClase, "addTipoCoste,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addTipoCoste,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addTipoCoste,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Eliminación de un tipo de coste.
     * @param idTipoCoste Identificador del tipo a eliminar.
     */
    public boolean delTipoCoste(Integer idTipoCoste)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from TiposCoste where idTipoCoste=:id");
            q.setInteger("id", idTipoCoste);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delTipoCoste,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delTipoCoste,no existe tipo de coste con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delTipoCoste,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

    /**
     * Carga una lista de todos los tipos de costes derivados existentes
     * @return Lista de tipos de costes o null en caso de fallo.
     */
    public List<TiposCoste> loadTiposCostes()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from TiposCoste");
            lista = (List<TiposCoste>) q.list();
            Servicios.logear(nombreClase, "loadTiposCostes,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTiposCostes, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }


//
// TIPOS DE OPERACION
//

   /**
     * Crea un nuevo tipo de operacion.
     * @return boolean
     */
    public boolean addTipoOperacion(TiposOperacion tipoOperacion)
    {
        try{
            session.save(tipoOperacion);
            Servicios.logear(nombreClase, "addTipoOperacion,OK.", Servicios.DEBUG);
            return true;
        }catch(org.hibernate.exception.ConstraintViolationException e)
        {
            Servicios.logear(nombreClase, "addTipoOperacion,Problema con las restricciones (relaciones, valores null o unique)", Servicios.ERROR);
            return false;
        }catch(Exception ex)
        {
            Servicios.logear(nombreClase, "addTipoOperacion,Excep:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Eliminación de un tipo de operacion.
     * @param idTipoOperacion Identificador del tipo a eliminar.
     */
    public boolean delTipoOperacion(Integer idTipoOperacion)
    {
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from TiposOperacion where idTipoOperacion=:id");
            q.setInteger("id", idTipoOperacion);
            int eliminadas = q.executeUpdate();

            if (eliminadas>0)
            {
                Servicios.logear(nombreClase, "delTipoOperacion,OK.", Servicios.DEBUG);
                resultado=true;
            }
            else
            {
                resultado=false;
                Servicios.logear(nombreClase, "delTipoOperacion,no existe tipo de operacion con ese id.", Servicios.DEBUG);
            }
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "delTipoOperacion,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

    /**
     * Carga una lista de todos los tipos de operacion que existen.
     * @return Lista de tipos de operacion o null en caso de fallo.
     */
    public List<TiposOperacion> loadTiposOperacion()
    {
        List lista=null;

        try {
            Query q = session.createQuery("from TiposOperacion");
            lista = (List<TiposOperacion>) q.list();
            Servicios.logear(nombreClase, "loadTiposOperacion,OK.", Servicios.DEBUG);
        } catch (Exception ex) {
            Servicios.logear(nombreClase, "loadTiposOperacion, Excepcion:"+ex.toString(), Servicios.ERROR);
            lista=null;
        }
        return lista;
    }

    /**
     * Añade una nueva estación de servicio en la base de datos.
     * @param newES
     * @return
     */
    public boolean addEstacionServicio(Eess newES)
    {
        try{
            session.save(newES);
            //Servicios.logear(nombreClase, "addEstacionServicio,OK", Servicios.DEBUG);
            return true;
        }catch(Exception ex){
            Servicios.logear(nombreClase, "addEstacionServicio, Excepcion:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Borra todas las estaciones de servicio de la base de datos.
     * @return
     */
    public boolean borrarEESS(){
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Eess");
            q.executeUpdate();
            Servicios.logear(nombreClase, "borrarEESS,OK.", Servicios.DEBUG);
            resultado=true;
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "borrarEESS,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }


//
//   NEUMATICOS
//

    /**
     * Añade un nuevo neumatico en la base de datos.
     * @param neumatico
     * @return
     */
    public boolean addNeumatico(Neumaticos neumatico)
    {
        try{
            session.save(neumatico);
            Servicios.logear(nombreClase, "addNeumatico,OK", Servicios.DEBUG);
            return true;
        }catch(Exception ex){
            Servicios.logear(nombreClase, "addNeumatico, Excepcion:"+ex.toString(), Servicios.ERROR);
            return false;
        }
    }

    /**
     * Borra todos los neumaticos de la base de datos.
     * @return
     */
    public boolean borrarNeumaticos(){
        boolean resultado=false;
        try {
            //Parametrizacion para evitar SQL Injection
            Query q = session.createQuery("Delete from Neumaticos");
            q.executeUpdate();
            Servicios.logear(nombreClase, "borrarNeumaticos,OK.", Servicios.DEBUG);
            resultado=true;
        } catch (Exception ex) {
            resultado=false;
            Servicios.logear(nombreClase, "borrarNeumaticos,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return resultado;
    }

//
// CALCULADORA
//
    /**
     * Obtiene un valor estimado para el consumo medio de un vehículo basandose en sus características
     * @param peso en kg
     * @param potencia en cv
     * @param cilindrada en cc
     * @param idTipoCombustible valores de 1 a 4
     * @return
     */
    public double calcularConsumoMedio(int peso,float potencia,int cilindrada, int idTipoCombustible)
    {
        double consumo=0;
        char combustible='G';
        final int VAR_CILINDRADA=50;
        final int VAR_POTENCIA=5;
        final int VAR_PESO=50;

        switch(idTipoCombustible)
        {
            case Constantes.COMBUSTIBLE_95:
            case Constantes.COMBUSTIBLE_98:{combustible='G'; break;}
            case Constantes.COMBUSTIBLE_A:
            case Constantes.COMBUSTIBLE_A_EXTRA:{combustible='D'; break;}
        }

    	try{
            String consulta="select avg(consMedio) from info_vehiculos where "
                    + "cilindrada>"+(cilindrada-VAR_CILINDRADA)+" and cilindrada<"+(cilindrada+VAR_CILINDRADA)
                    + " and cv>"+(potencia-VAR_POTENCIA)+" and cv<"+(potencia+VAR_POTENCIA)
                    + " and tara>"+(peso-VAR_PESO)+" and tara<"+(peso+VAR_PESO)+" and combustible='"+combustible+"';";
            Query q = session.createSQLQuery(consulta);
            consumo = (Double)q.uniqueResult();
            Servicios.logear(nombreClase, "calcularConsumoMedio,consumo obtenido:"+consumo, Servicios.DEBUG);
        }
        catch(Exception ex){
            consumo=-1;
            Servicios.logear(nombreClase, "calcularConsumoMedio,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return consumo;
    }
    
    /*
     * Calcula el consumo medio en funcion del carburante, agrupando por cv
     * @param carburante toma los valores: precio95,precio98,precioA,precioAExtra
     */
    public String calcularConsumoMedio(String carburante,int tipo){
        try{
            double consumo=0;
            String consulta;
            if (carburante.equals("95")||carburante.equals("A")){
                consulta="select avg(consMedio) from info_vehiculos where cv<160 and idTipoCombustible='"+tipo+"';";
            }else{
                consulta="select avg(consMedio) from info_vehiculos where cv>160 and idTipoCombustible='"+tipo+"';";
            }
            Query q = session.createSQLQuery(consulta);
            consumo = (Double)q.uniqueResult();
            Servicios.logear(nombreClase, "calcularConsumoMedio,consumo obtenido:"+consumo, Servicios.DEBUG);            
            return String.valueOf(consumo);
        }catch(Exception ex){
            Servicios.logear(nombreClase,ex.toString(),Servicios.ERROR);
            return "0";
        }
    }    

    /**
     * Devuelve el precio medio en españa del precio del carburante indicado.
     * @param carburante toma los valores: precio95,precio98,precioA,precioAExtra
     * @return
     */
    public double getPrecioCombustible(String carburante)
    {
        double precio=0;        
    	try{
            String consulta="SELECT avg("+carburante+") FROM eess where "+carburante+">0;";
            Query q = session.createSQLQuery(consulta);
            precio = (Double)q.uniqueResult();
            Servicios.logear(nombreClase, "getPrecioCombustible,precio obtenido:"+precio, Servicios.DEBUG);
        }
        catch(Exception ex){
            precio=-1;
            Servicios.logear(nombreClase, "getPrecioCombustible,Excepcion:"+ex.toString(), Servicios.ERROR);
        }
        return precio;
    }

    public List getInfoVehiculo(String marca,String combustible, String query){
        List resultados=new ArrayList();
        try{String consulta;
            combustible=Servicios.filtroSQLInjection(combustible);
            query=Servicios.filtroSQLInjection(query);
            marca=Servicios.filtroSQLInjection(marca);
            
            if (combustible!=null && !combustible.equals("")){
                consulta="SELECT m.nombreModelo,cv,cilindrada,consMedio FROM info_vehiculos i,modelos m where m.nombreModelo like '%"+query+"%' and i.idTipoCombustible="+combustible+" and m.idMarca="+marca+" and m.idModelo=i.idModelo group by cv limit 20;";
            }else{
                consulta="SELECT m.nombreModelo,cv,cilindrada,consMedio FROM info_vehiculos i,modelos m where m.nombreModelo like '%"+query+"% and m.idMarca="+marca+" and m.idModelo=i.idModelo group by cv limit 20;";
            }
                        
            Query q = session.createSQLQuery(consulta);
            resultados=q.list();
        }catch(Exception ex){
            Servicios.logear(nombreClase,"getInfoVehiculo,Exception:"+ex.toString(),Servicios.ERROR);
        }
        return resultados;
    }
}
