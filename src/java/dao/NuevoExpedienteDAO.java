/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Dependencia;
import maping.Indicador;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;

/**
 *
 * @author OGPL
 */
public interface NuevoExpedienteDAO {
    public List getDependencias(String tipo);
    public String getSigla(String codigo);
    public List getTipoDocumento();
    public String getCodigoDepe(String nombre);
    public String getCodigoTipoDocu(String nombre);
    public Dependencia getDepe(String nombre);
    public List getDependenciasOficina();
    public Indicador getIndicador(String n);
    public Dependencia getDepePorNombre(String n);
    public Dependencia getDepePorCodigo(String n);
    public void guardarTramiteDatos(TramiteDatos td);
    public void guardarTipoDocu(TipoDocu tdc);
    public void guardarTramiteMovimiento(TramiteMovimiento tm);
}
