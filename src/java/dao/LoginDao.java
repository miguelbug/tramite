/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface LoginDao {
    public Usuario getUsuario(String usu, String pass);
    public Usuario getUniqeUsuario(String usu);
}
