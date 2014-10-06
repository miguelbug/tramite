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
import maping.TramiteDatos;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public interface DerivarDAO {
    public String getIndice(String tramnum);
    public String getSiglas(String ofi);
    public String getSiglas2(String nombofi);
    public int getMovimiento(String tramnum);
    public void InsertarMovimiento(int movimiento, Date fechaenvio,String asunto, String estado, String numtram, String origen, String destino, Indicador i);
    public void InsertarMovimiento2(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino);
    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas,String anio, String numtram, Date fecharegistro, Usuario usu);
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
}
