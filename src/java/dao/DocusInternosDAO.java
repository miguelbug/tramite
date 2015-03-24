/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.Date;
import java.util.List;

/**
 *
 * @author OGPL
 */
public interface DocusInternosDAO {
    public List getDocusInternos(String usu);
    public List getDocusInternos2(String usu);
    public String getRespuesta(String tramnum, String movi);
    public List getDocInternos(String usu);
    public List getDocInternos1(String usu);
    public List getDocInternos2(String usu);
    public List getDocInternosXtipo(String usu, String tipo);
    public List getDocInternosXtipo_1(String usu, String tipo);
    public List getDocInternosXtipo_2(String usu, String tipo);
    public List getCircularesOficInterna(String usu);
    public List getCircularesOficInternaXtipo(String usu, String tipo);
    public String getDestinoOfi(String tram, String tramfecha);
}
