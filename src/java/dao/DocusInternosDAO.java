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
    public String getRespuesta(String tramnum);
    public List getDocInternos(String usu);
    public List getDocInternosXtipo(String usu, String tipo);
    public List getCircularesOficInterna(String usu);
}
