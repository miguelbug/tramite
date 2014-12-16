/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package maping;

import java.util.Date;

/**
 *
 * @author OGPL
 */
public class TemporalDi {
     private long id_temp;
     private String nombDocu;
     private Date fechaReg;
     private String tramNum;
     private String usuario;
     private String asunto;
     private String impreso;
     private String reimpreso;

    public TemporalDi() {
    }

	
    public TemporalDi(long id_temp) {
        this.id_temp = id_temp;
    }

    public TemporalDi(long id_temp, String nombDocu, Date fechaReg, String tramNum, String usuario, String asunto, String impreso, String reimpreso) {
        this.id_temp = id_temp;
        this.nombDocu = nombDocu;
        this.fechaReg = fechaReg;
        this.tramNum = tramNum;
        this.usuario = usuario;
        this.asunto = asunto;
        this.impreso = impreso;
        this.reimpreso = reimpreso;
    }

    

    public long getId_temp() {
        return id_temp;
    }

    public void setId_temp(long id_temp) {
        this.id_temp = id_temp;
    }

    public String getNombDocu() {
        return nombDocu;
    }

    public void setNombDocu(String nombDocu) {
        this.nombDocu = nombDocu;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public String getTramNum() {
        return tramNum;
    }

    public void setTramNum(String tramNum) {
        this.tramNum = tramNum;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getImpreso() {
        return impreso;
    }

    public void setImpreso(String impreso) {
        this.impreso = impreso;
    }

    public String getReimpreso() {
        return reimpreso;
    }

    public void setReimpreso(String reimpreso) {
        this.reimpreso = reimpreso;
    }

    

}
