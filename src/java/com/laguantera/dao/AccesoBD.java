package com.laguantera.dao;

import java.sql.*;

public class AccesoBD {

    private String url;
    private String usuario;
    private String contrasenya;    

    public AccesoBD() {        

        try {
            //Cargamos el driver en memoria
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException claseNoEncontrada) {
            claseNoEncontrada.printStackTrace();
        } catch (Exception general) {
            general.printStackTrace();
        }
    }

    //Metodos GETS
    public String getURL() {
        return this.url;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getContrasenya() {
        return this.contrasenya;
    }

    //Metodos SETS
    public void setURL(String direccion) {
        this.url = direccion;
    }

    public void setUsuario(String nombre) {
        this.usuario = nombre;
    }

    public void setContrasenya(String pass) {
        this.contrasenya = pass;
    }

    //Metodos de conexion y acceso a la base de datos
    public Connection abrirConexion() {
        Connection conexion = null;
        //Se intenta realizar la conexi�n a la base de datos ejemplo
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://" + this.getURL(), this.getUsuario(), this.getContrasenya());
        } catch (SQLException errorSql) {
            conexion = null;
            errorSql.printStackTrace();
        } catch (Exception general) {
            conexion = null;
            general.printStackTrace();
        }

        return conexion;
    }

    public void cerrarConexion(Connection conexion) {
        try {
            conexion.close();
        } catch (SQLException errorSql) {
            errorSql.printStackTrace();
            
        } catch (Exception general) {
            general.printStackTrace();
        }
    }

    /** Consulta del numero de filas.
     *  ejemplo: SELECT count(campo) AS filas FROM tabla
     * 
     * @param conexion conexion con la base de datos
     * @param cons string de la consulta sql
     * @return int - devuelve el numero de filas de la consulta
     */
    public int consulta(Connection conexion, String cons) {
        int numeroFilas = 0;

        try {
            Statement stm = conexion.createStatement();
            ResultSet resultado = stm.executeQuery(cons);
            resultado.next();
            numeroFilas = resultado.getInt("filas");
            resultado.close();
            stm.close();
        } catch (SQLException errorSql) {
            numeroFilas = 0;
            
        } catch (Exception general) {
            numeroFilas = 0;
            
        }

        return numeroFilas;
    }

    public String[][] consulta(Connection conexion, String cons, int numeroColumnas, int numeroFilas) {
        ResultSet resultado = null;
        String resu[][] = new String[numeroFilas][numeroColumnas];
        int contador = 0, i = 0;

        try {
            Statement stm = conexion.createStatement();
            resultado = stm.executeQuery(cons);

            while (resultado.next() && (i < numeroFilas)) {
                for (contador = 0; contador < numeroColumnas; contador++) {
                    resu[i][contador] = resultado.getString(contador + 1);
                }
                i++;
            }

            resultado.close();
            stm.close();
        } catch (SQLException errorSql) {
            errorSql.printStackTrace();
        } catch (Exception general) {
            general.printStackTrace();
        }

        return resu;
    }   

    public boolean mantenimiento(Connection conexion, String lineasql)  {
        boolean resultado = false;

        try {
            Statement stm = conexion.createStatement();
            stm.execute(lineasql);
            resultado = true;
            stm.close();
        } catch (SQLException errorSql) {
            resultado = false;
            errorSql.printStackTrace();

        } catch (Exception general) {
            resultado = false;
            general.printStackTrace();
        }

        return resultado;
    }
}
