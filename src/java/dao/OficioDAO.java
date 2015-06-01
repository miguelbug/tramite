/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Dependencia;
import maping.DetallOficcirc;
import maping.DocusInternos;
import maping.OficCirc;
import maping.Oficina;
import maping.TiposDocumentos;

/**
 *
 * @author OGPL
 */
public interface OficioDAO {
    public List getOficiosCirculares();
    public List getDependencias();
    public Dependencia getDependencia(String nombre);
    public List getFirma();
    public String getResponsable(String usuario);
    public String getAreaResponsable(String usuario);
    public void guardarOficioCircular(OficCirc ofi);
    public Dependencia getDependencias2(String nombre);
    public Long getIndice(String correlativo, String anio);
    public void guardarDetalleOfCirc(DetallOficcirc deofi);
    public OficCirc getOficioCircular(String correla, String anio);
    public String getCorrelativo(String anio);
    public Long getCodigo(String nombre);
    public List getOficioUnicoExpediente();
    public List getOficioUnicoNoExp();
    public List getOficoCircDetal(String correla);
    public String getCorrela(String usu, String tipodocu);
    public List getTiposDocus(String flag);
    public TiposDocumentos getTipoDocu(String nombre);
    public String getOficioDocumento(String tramnum);
    public void GuardarDocumentoOfiInt(DocusInternos di);
    public List obtenerTiposDocusOfCirc(String f);
    public List gettipos(String g);
    public List getDependencias(String tipo);
    public String getNombreOfi(String usu);
    public List getOficOgplUser(String user);
    public void ActualizarOficio(String correla, String asunto, String destino, String asignado, String tramNum, String fecha);
    public void ActualizarOficio2(String correla, String asunto, String destino, String asignado);
    public void DeleteOficio(String correlativo);
    public List getAllDependencias();
    public void ActualizarOficioCircular(String correla, String asunto, String origen);
    public void ActualizarOficioCircularUser(String correla, String asunto);
    public void ActualizarDocusInternosOficinas(String id, String asunto);
    public void EliminarDocumentosInternosOficinas(String id);
    public List listarTramitesNumeros(String oficina);
    public String getTramNUm_TramFecha(String id);
    public String getTramNUm_TramFecha2(String id);
    public void ActualizarTramite(String tram_fecha);
    public void ELiminarTramite(String tram_fecha_movi);
}
