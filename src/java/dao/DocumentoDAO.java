/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;

/**
 *
 * @author OGPL
 */
public interface DocumentoDAO {
    public List getDocumentos();
    public List getCodigos();
    public List getBusqueda(String n1, String n2, String n3, String n4, String n5);
    public String CrearAnd(String objeto, int posi);
    public String CrearVariable(int i);
    public String getSQL(String[] a);
    public List getDetalle(String tramnum);
}
