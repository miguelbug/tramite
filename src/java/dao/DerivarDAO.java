/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import maping.Dependencia;
import maping.DocusExtint;
import maping.Indicador;
import maping.Oficina;
import maping.TiposDocumentos;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface DerivarDAO {

    public String getIndice(String tramnum, String tipodocu, String anio);

    public String getSiglas(String ofi, String usu);

    public String getSiglas2(String nombofi);

    public int getMovimiento(String tramnum);

    public void InsertarMovimiento(Usuario usu, int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String tramfecha, String origen, String destino, Indicador i);

    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas, String anio, String numtram, String tramfecha, Date fecharegistro, Usuario usu, String asunto, String movi, Dependencia d, Dependencia d1);

    public TramiteDatos getTramite(String tramite, String tramfecha);

    public Dependencia getDependencia(String nombre);

    public Dependencia getDependencia2(String nombre);

    public String getCodOficina(String nombreofi);

    public void ConfirmarTramites(String numtram, int movinum, Date fechaing);

    public List getConfirmados(String oficina);

    public void Confirmar(String numtram, int movimiento, Date fecha);

    public Indicador getIndic(String codigo);

    public List getConfDeriv(String oficina);

    public Date getFecha();

    public String getCorre(Usuario usu, String tipo);

    public void guardarDocusExt(DocusExtint de);

    public Dependencia getDep(String nombre);

    public String getAnio();

    public String getDocExt(String n);

    public String getCorreProv(String anio);

    public void ActualizarTramite(String tramaux, String movimiento, Date fecha, String tipodocu);

    public String getCorrelativoOficio(String anio);

    public Usuario getUsuario(String oficina);

    public List getUsu(String nombre);

    public void ActualizarUsuario(String tramnum, String movi, Usuario usu);

    public String getCodigoDep(String nombdepe);

    public List listandoUsuario(String nombdepe);

    public Oficina getOficina(String nombre);

    public Usuario transformar(String nombre);

    public TramiteMovimiento getTramiteMovimiento(String numtram, String movi);

    public TiposDocumentos getTipoDoc(String tipo);

    public TiposDocumentos getTipoDocIndix(String indice);

    public String getCorrelativoOficinaInterna(Usuario usu, String tipo, String anio);

    public String getCodigoUsuario(String usu);

    public List listaUsuarios(String oficina);

    public Usuario getUsuarioDI(String nombre);
    
    public void cambiarEstado(String numtram, String movimiento);
    
    public String getIndice2(String tipodocu, String anio);
    
    public boolean validar_TIpoDocu(String tramNum, String tramFecha);
    
    public void ActualizarTramMov(String numtram, String movi, Date fecha);
}
