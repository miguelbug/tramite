package maping;
// Generated 02/10/2014 12:28:57 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * Temporal generated by hbm2java
 */
public class Temporal  implements java.io.Serializable {


     private long id;
     private String tramNum;
     private Date fecha;
     private String origen;
     private String asunto;
     private BigDecimal impreso;
     private BigDecimal reimpreso;
     private String destino;
     private String siglas;

    public Temporal() {
    }

	
    public Temporal(long id) {
        this.id = id;
    }

    public Temporal(long id, String tramNum, Date fecha, String origen, String asunto, BigDecimal impreso, BigDecimal reimpreso, String destino, String siglas) {
        this.id = id;
        this.tramNum = tramNum;
        this.fecha = fecha;
        this.origen = origen;
        this.asunto = asunto;
        this.impreso = impreso;
        this.reimpreso = reimpreso;
        this.destino = destino;
        this.siglas=siglas;
    }
    
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getTramNum() {
        return this.tramNum;
    }
    
    public void setTramNum(String tramNum) {
        this.tramNum = tramNum;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getOrigen() {
        return this.origen;
    }
    
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public String getAsunto() {
        return this.asunto;
    }
    
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public BigDecimal getImpreso() {
        return this.impreso;
    }
    
    public void setImpreso(BigDecimal impreso) {
        this.impreso = impreso;
    }
    public BigDecimal getReimpreso() {
        return this.reimpreso;
    }
    
    public void setReimpreso(BigDecimal reimpreso) {
        this.reimpreso = reimpreso;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
}


