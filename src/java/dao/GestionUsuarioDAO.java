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
public interface GestionUsuarioDAO {
    public Usuario ValidarClave(String clave, String usu);
    public void Cambiar(Usuario usu);
}
