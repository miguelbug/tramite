/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.Date;
import java.util.List;
import maping.Dependencia;
import maping.TramiteDatos;

/**
 *
 * @author OGPL
 */
public interface DerivarDAO {
    public String getIndice();
    public String getSiglas(String ofi);
    public int getMovimiento(String tramnum);
    public void InsertarMovimiento(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino);
    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas,String anio, String numtram);
    public TramiteDatos getTramite(String tramite);
    public Dependencia getDependencia(String nombre);
    public Dependencia getDependencia2(String nombre);
    public String getCodOficina(String nombreofi);
    public void ConfirmarTramites(String numtram, Date fecha);
}
