/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author USUARIO
 */
public class conexion {

    public Connection obetenerConecion() {
        Connection conn = null;
        try {
<<<<<<< HEAD
            // Obtenemos un contexto inicial
=======
	// Obtenemos un contexto inicial
>>>>>>> origin/master
            InitialContext ctx = new InitialContext();

// Obtenemos el contexto de nuestro entorno. La cadena
// "java:comp/env" es siempre la misma
            Context envCtx = (Context) ctx.lookup("java:comp/env");

// Obtenemos una fuente de datos identificada con la cadena que
// le hemos asignado en los ficheros de configuración
<<<<<<< HEAD
            DataSource ds = (DataSource) envCtx.lookup("jdbc");

// Ya podemos obtener una conexión y operar con ella normalmente
            conn = ds.getConnection();
            //return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
=======
            DataSource ds = (DataSource) envCtx.lookup("jdbc/hr");

// Ya podemos obtener una conexión y operar con ella normalmente
            conn = ds.getConnection();
 //return conn;
        } catch (Exception e) {
>>>>>>> origin/master
        }
        return conn;

    }

}
