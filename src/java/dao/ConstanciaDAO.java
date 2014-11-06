/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import maping.Constancias;

/**
 *
 * @author OGPL
 */
public interface ConstanciaDAO {
    public String getIndice();
    public List getJefatura();
    public String getContrato(String nombre);
    public void guardarConstancia(Constancias c);
    public List getConstancias();
}
