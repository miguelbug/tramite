/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Dependencia;
import maping.DetallOficcirc;
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
    public Long getIndice(String correlativo);
    public void guardarDetalleOfCirc(DetallOficcirc deofi);
    public OficCirc getOficioCircular(String correla);
    public String getCorrelativo();
    public Long getCodigo(String nombre);
    public List getOficioUnicoExpediente();
    public List getOficioUnicoNoExp();
    public List getOficoCircDetal(String correla);
    public String getCorrela(String usu, String tipodocu);
    public List getTiposDocus(String flag);
    public TiposDocumentos getTipoDocu(String nombre);
    public String getOficioDocumento(String tramnum);
}
