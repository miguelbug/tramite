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
public interface OficioDAO {
    public List getOficiosCirculares();
    public List getDependencias();
    public String getFirma();
    public String getResponsable(String usuario);
    public String getAreaResponsable(String usuario);
}
