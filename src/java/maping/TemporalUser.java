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
public class TemporalUser {

    private long idTempu;
    private String documento;
    private Date fecha;
    private String asunto;
    private String origen;
    private String destino;
    private String responsable;
    private String impreso;
    private String reimpreso;

    public TemporalUser() {
    }

    public TemporalUser(long idTempu) {
        this.idTempu = idTempu;
    }

    public TemporalUser(long idTempu, String documento, Date fecha, String asunto, String origen, String destino, String responsable, String impreso, String reimpreso) {
        this.idTempu = idTempu;
        this.documento = documento;
        this.fecha = fecha;
        this.asunto = asunto;
        this.origen = origen;
        this.destino = destino;
        this.responsable = responsable;
        this.impreso = impreso;
        this.reimpreso = reimpreso;
    }

    public long getIdTempu() {
        return idTempu;
    }

    public void setIdTempu(long idTempu) {
        this.idTempu = idTempu;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
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
