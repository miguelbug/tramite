/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Dependencia;
import maping.Jefatura;
import maping.Oficina;
import maping.Profesion;
import maping.TipoContrato;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface GestionUsuarioDAO {
    public Usuario ValidarClave(String clave, String usu);
    public void Cambiar(Usuario usu);
    public List getOficinas();
    public List getProfesion();
    public List getContrato();
    public Usuario getUsuario(String nombre);
    public Dependencia getDependencia(String nombre);
    public Oficina getOficina(String nombre);
    public Profesion getProfesion(String nombre);
    public TipoContrato getContrato(String nombre);
    public void GuardarJefatura(Jefatura jefatura, Usuario usu);
    public void GuardarUsuario(Usuario usu);
    public void GuardarJefe(Jefatura jefe);
    public List listarJefes();
}
