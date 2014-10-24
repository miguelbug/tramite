/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class ConstanciaBean {

    private String correlativo;
    private String escogido;
    private List empleados;
    private String tipocontrato;
    private Date desde;
    private Date hasta;
    private Date fechaemision;
    
    public ConstanciaBean() {
    }
    public void guardarconstancia(){
        
    }
    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getEscogido() {
        return escogido;
    }

    public void setEscogido(String escogido) {
        this.escogido = escogido;
    }

    public List getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List empleados) {
        this.empleados = empleados;
    }

    public String getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(String tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }
    
}
