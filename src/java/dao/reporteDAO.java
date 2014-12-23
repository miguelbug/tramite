/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

/**
 *
 * @author OGPL
 */
public interface reporteDAO {
    public void ActualizarTemporal();
    public String getfechaderivado(String tramnum, String movimiento);
}
