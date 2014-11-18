/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.Date;
import java.util.List;
import maping.Dependencia;
import maping.DocusExt;
import maping.DocusExtint;
import maping.Indicador;
import maping.Oficina;
import maping.Proveido;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface DerivarDAO {
    public String getIndice(String tramnum, String tipodocu);
    public String getSiglas(String ofi, String usu);
    public String getSiglas2(String nombofi);
    public int getMovimiento(String tramnum);
    public void InsertarMovimiento(int movimiento, Date fechaenvio,String asunto, String estado, String numtram, String origen, String destino, Indicador i);
    public void InsertarMovimiento2(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino);
    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas, String anio, String numtram, Date fecharegistro, Usuario usu, String asunto);
    public TramiteDatos getTramite(String tramite);
    public Dependencia getDependencia(String nombre);
    public Dependencia getDependencia2(String nombre);
    public String getCodOficina(String nombreofi);
    public void ConfirmarTramites(String numtram, int movinum, Date fechaing);
    public List getConfirmados(String oficina);
    public void Confirmar(String numtram, int movimiento);
    public Indicador getIndic(String codigo);
    public List getConfDeriv(String oficina);
    public Date getFecha();
    public String getCorre(Usuario usu, String tipo);
    public DocusExt getDocuExt(String codigo);
    public void guardarDocusExt(DocusExtint de);
    public Dependencia getDep(String nombre);
    public String getAnio();
    public String getDocExt(String n);
    public String getCorreProv();
    public void GuardarProveido(Proveido p);
    public void ActualizarTramite(String tramaux, String movimiento);
    public String getCorrelativoOficio();
    public Usuario getUsuario(String oficina);
    public List getUsu(String nombre);
    public void ActualizarUsuario(String tramnum, String movi, Usuario usu);
    public String getCodigoDep(String nombdepe);
    public List listandoUsuario(String nombdepe);
    public Oficina getOficina(String nombre);
    public Usuario transformar(String nombre);
    public TramiteMovimiento getTramiteMovimiento(String numtram, String movi);
}
