/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class categoriaServicio {

    conexion nuevacon;

    public Connection getConexion() throws SQLException {
        nuevacon= new conexion();
        return nuevacon.getConn();
    }
    public void CerrandoConexion(){
        nuevacon.cerrarConexion();
    }

}
