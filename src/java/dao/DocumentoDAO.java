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
public interface DocumentoDAO {
    public List getDocumentos();
    public List getBusqueda(String n1, String n2, String n3, String n4, String n5);
    public String CrearAnd(String objeto, int posi);
    public String CrearVariable(int i);
    public String getSQL(String[] a);
    public List getDetalle(String tramnum);
    public List getDeatalle2(String tramnum);
    public String getMotivo(String tramnum);
    public String getOficina(Usuario usu);
    public List getDocusInternos();
    public List getDependencias();
    public List getIndicadores();
}
