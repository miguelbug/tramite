/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.Date;
import java.util.List;
import maping.Oficios;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface DocumentoDAO {
    public List getDocumentos();
    public List getDocumentos_Confirm();
    public List getBusqueda(String n1, String n2, String n3, String n4, String n5);
    public String CrearAnd(String objeto, int posi);
    public String CrearVariable(int i);
    public String getSQL(String[] a);
    public List getDetalle(String tramnum);
    public List getDetalleOGPL(String tramnum);
    public List getDetalleNoOGPL(String tramnum,String movi);
    public List getDeatalle2(String tramnum, String movi);
    public String getMotivo(String tramnum, String fecha);
    public String getOficina(Usuario usu);
    public List getDocusInternos();
    public List getDependencias(String tipos);
    public List getIndicadores();
    public List getProveidos(String tramnum);
    public void guardarOficio(Oficios ofi, String tramnum, String movimiento);
    public void guardarOficio2(Oficios ofi);
    public void ActualizarMov(String tramnum, String mov);
    
    public void EliminarTramite(String tramnum, String fecha);
    public void EliminarTD(String tramnum, String fecha);
    public void EliminarTipDocu(String tramnum, String fecha);
    public void EliminarTramMov(String tramnum, String fecha);
    public void EliminarTemporal(String tramnum, String fecha);
    
    public List documentosCorregir();
    
    public void guardarNuevoAnio(String anio);
    
    public String getFlag(String dependencia);
    public String getTram_Fecha(String tramnum, String movi);
    public List mostrar_DocumentosOfInt();
    public List obtener_oficios();
    public List busquedaAvanzada(String oficina);
    public List busquedaAvanzada2();
    public List docusInternosOGPL();
    public void eliminarDocuInternoOGPL(String id);
}
