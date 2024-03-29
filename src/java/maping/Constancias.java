package maping;
// Generated 17/04/2015 04:53:35 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Constancias generated by hbm2java
 */
public class Constancias  implements java.io.Serializable {


     private long idConst;
     private TiposDocumentos tiposDocumentos;
     private Usuario usuario;
     private String correlativo;
     private Date fechaEmision;
     private String drigidoA;
     private String tipoContrato;
     private Date desde;
     private Date hasta;
     private Date fechaRegistro;

    public Constancias() {
    }

	
    public Constancias(long idConst) {
        this.idConst = idConst;
    }
    public Constancias(long idConst, TiposDocumentos tiposDocumentos, Usuario usuario, String correlativo, Date fechaEmision, String drigidoA, String tipoContrato, Date desde, Date hasta, Date fechaRegistro) {
       this.idConst = idConst;
       this.tiposDocumentos = tiposDocumentos;
       this.usuario = usuario;
       this.correlativo = correlativo;
       this.fechaEmision = fechaEmision;
       this.drigidoA = drigidoA;
       this.tipoContrato = tipoContrato;
       this.desde = desde;
       this.hasta = hasta;
       this.fechaRegistro = fechaRegistro;
    }
   
    public long getIdConst() {
        return this.idConst;
    }
    
    public void setIdConst(long idConst) {
        this.idConst = idConst;
    }
    public TiposDocumentos getTiposDocumentos() {
        return this.tiposDocumentos;
    }
    
    public void setTiposDocumentos(TiposDocumentos tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getCorrelativo() {
        return this.correlativo;
    }
    
    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }
    public Date getFechaEmision() {
        return this.fechaEmision;
    }
    
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    public String getDrigidoA() {
        return this.drigidoA;
    }
    
    public void setDrigidoA(String drigidoA) {
        this.drigidoA = drigidoA;
    }
    public String getTipoContrato() {
        return this.tipoContrato;
    }
    
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    public Date getDesde() {
        return this.desde;
    }
    
    public void setDesde(Date desde) {
        this.desde = desde;
    }
    public Date getHasta() {
        return this.hasta;
    }
    
    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }




}


