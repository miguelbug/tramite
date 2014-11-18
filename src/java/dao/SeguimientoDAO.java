/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Temporal;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;

/**
 *
 * @author OGPL
 */
public interface SeguimientoDAO {
    public List getSeguimiento(String tramnum);
    public List seguimientoUser(String oficina);
    public List getSeguimientoGrande(String tramnum);
    public List getSeguimientoGrande1(String tramnum);
    public List getSeguimientoGrande2(String tramnum);
    public void GuadarTramiteDatos(TramiteDatos td,TipoDocu tdocu);
    public List tramiteDatos(String tramnum);
    public List TiposDocus(String tramnum);
    public void GuardarTD(TramiteDatos td);
    public void GuardarTDoc(TipoDocu tdocu);
    public void GuardarTramiteMovimiento(TramiteMovimiento tm);
    public void temporal(Temporal t);
    public List getDesignados(String oficina);
    
}
