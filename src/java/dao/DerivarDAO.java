/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

/**
 *
 * @author OGPL
 */
public interface DerivarDAO {
    public String getIndice();
    public String getSiglas(String ofi);
    public int getMovimiento(String tramnum);
}
