/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import maping.Temporalcargos;
import maping.TemporalDi;
import maping.TemporalUser;

/**
 *
 * @author OGPL
 */
public interface TemporaldiDao {
    public void guardarTemporalDi(TemporalDi tdi);
    public void actualizarTemporalDi();
    public void guardarTemporalUser(TemporalUser tu);
    public void actualizarTemporalUser();
    public void guardarCargos(Temporalcargos tc);
    public void actualizarTemporalCargo();
}
