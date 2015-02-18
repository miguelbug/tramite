/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.jdbc.Work;

/**
 *
 * @author USUARIO
 */
public class categoriaServicio {

    Session session;
    conexion nuevacon;

    public Connection getConexion() throws SQLException {
        nuevacon= new conexion();
        return nuevacon.getConn();
    }

    /*
     public void doWorkOnConnection(Session session) {
     session.doWork(new Work() {
     @Override
     public void execute(Connection connection) throws SQLException {
        
     }
     });
     }
     */
}
