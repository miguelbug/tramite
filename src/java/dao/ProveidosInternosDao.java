/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface ProveidosInternosDao {
    public List getDocumentosInternos();
    public Usuario getUsuario(String nombre);
    public List getProveidosinternos();
    public List getDependencias();
    public void EditarProveidos(String documento, String asunto, String origen, String destino);
    public String getCodigoDepe(String nombre);
}
