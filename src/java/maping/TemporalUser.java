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
    private String origenPrinc;
    private String documentoPrinc;
    private String impreso;
    private String reimpreso;
    private String tramNum;
    private String codigo;

    public TemporalUser() {
    }

    public TemporalUser(long idTempu) {
        this.idTempu = idTempu;
    }

    public TemporalUser(long idTempu, String documento, Date fecha, String origenPrinc, String documentoPrinc, String impreso, String reimpreso, String tramNum, String codigo) {
        this.idTempu = idTempu;
        this.documento = documento;
        this.fecha = fecha;
        this.origenPrinc = origenPrinc;
        this.documentoPrinc = documentoPrinc;
        this.impreso = impreso;
        this.reimpreso = reimpreso;
        this.tramNum = tramNum;
        this.codigo = codigo;
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

    public String getOrigenPrinc() {
        return origenPrinc;
    }

    public void setOrigenPrinc(String origenPrinc) {
        this.origenPrinc = origenPrinc;
    }

    public String getDocumentoPrinc() {
        return documentoPrinc;
    }

    public void setDocumentoPrinc(String documentoPrinc) {
        this.documentoPrinc = documentoPrinc;
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

    public String getTramNum() {
        return tramNum;
    }

    public void setTramNum(String tramNum) {
        this.tramNum = tramNum;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    
}
