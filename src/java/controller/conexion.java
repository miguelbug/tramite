/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author USUARIO
 */
public class conexion {
    
    Connection conn=null;
    
    public conexion() throws SQLException{
        if(conn!=null){
            conn.close();
        }else{
            obtenerConexion();
        }
        
        
    }

    /*public Connection obetenerConecion() throws SQLException {
        Connection conn = null;
        try {
            
            // Obtenemos un contexto inicial
            InitialContext ctx = new InitialContext();

// Obtenemos el contexto de nuestro entorno. La cadena
// "java:comp/env" es siempre la misma
            Context envCtx = (Context) ctx.lookup("java:comp/env");

// Obtenemos una fuente de datos identificada con la cadena que
// le hemos asignado en los ficheros de configuración
            DataSource ds = (DataSource) envCtx.lookup("jdbc/hr");

// Ya podemos obtener una conexión y operar con ella normalmente
            conn = ds.getConnection();
            
            //return conn;
        } catch (Exception e) {
            System.out.println("PROBLEMAS EN CONEXION: " + e.getMessage());
            e.printStackTrace();

        } finally {
            conn.close();
        }
        return conn;

    }*/

    public void obtenerConexion() {
        try{
            InitialContext ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/hr");
            conn = ds.getConnection();
        }
        catch(Exception e){
            System.out.println("PROBLEMAS EN CONEXION: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
