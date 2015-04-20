package maping;
// Generated 17/04/2015 04:53:35 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * TramiteMovimiento generated by hbm2java
 */
public class TramiteMovimiento  implements java.io.Serializable {


     private BigDecimal idMovi;
     private TramiteDatos tramiteDatos;
     private Dependencia dependenciaByCodigo1;
     private Usuario usuario;
     private Dependencia dependenciaByCodigo;
     private Indicador indicador;
     private Short moviNum;
     private Date fechaEnvio;
     private Date fechaIngr;
     private String moviObs;
     private String estaNombre;
     private String estadConfrirm;
     private Date fechaDerivacion;
     private String estado;

    public TramiteMovimiento() {
    }

	
    public TramiteMovimiento(BigDecimal idMovi) {
        this.idMovi = idMovi;
    }
    public TramiteMovimiento(BigDecimal idMovi, TramiteDatos tramiteDatos, Dependencia dependenciaByCodigo1, Usuario usuario, Dependencia dependenciaByCodigo, Indicador indicador, Short moviNum, Date fechaEnvio, Date fechaIngr, String moviObs, String estaNombre, String estadConfrirm, Date fechaDerivacion, String estado) {
       this.idMovi = idMovi;
       this.tramiteDatos = tramiteDatos;
       this.dependenciaByCodigo1 = dependenciaByCodigo1;
       this.usuario = usuario;
       this.dependenciaByCodigo = dependenciaByCodigo;
       this.indicador = indicador;
       this.moviNum = moviNum;
       this.fechaEnvio = fechaEnvio;
       this.fechaIngr = fechaIngr;
       this.moviObs = moviObs;
       this.estaNombre = estaNombre;
       this.estadConfrirm = estadConfrirm;
       this.fechaDerivacion = fechaDerivacion;
       this.estado = estado;
    }
   
    public BigDecimal getIdMovi() {
        return this.idMovi;
    }
    
    public void setIdMovi(BigDecimal idMovi) {
        this.idMovi = idMovi;
    }
    public TramiteDatos getTramiteDatos() {
        return this.tramiteDatos;
    }
    
    public void setTramiteDatos(TramiteDatos tramiteDatos) {
        this.tramiteDatos = tramiteDatos;
    }
    public Dependencia getDependenciaByCodigo1() {
        return this.dependenciaByCodigo1;
    }
    
    public void setDependenciaByCodigo1(Dependencia dependenciaByCodigo1) {
        this.dependenciaByCodigo1 = dependenciaByCodigo1;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Dependencia getDependenciaByCodigo() {
        return this.dependenciaByCodigo;
    }
    
    public void setDependenciaByCodigo(Dependencia dependenciaByCodigo) {
        this.dependenciaByCodigo = dependenciaByCodigo;
    }
    public Indicador getIndicador() {
        return this.indicador;
    }
    
    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }
    public Short getMoviNum() {
        return this.moviNum;
    }
    
    public void setMoviNum(Short moviNum) {
        this.moviNum = moviNum;
    }
    public Date getFechaEnvio() {
        return this.fechaEnvio;
    }
    
    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    public Date getFechaIngr() {
        return this.fechaIngr;
    }
    
    public void setFechaIngr(Date fechaIngr) {
        this.fechaIngr = fechaIngr;
    }
    public String getMoviObs() {
        return this.moviObs;
    }
    
    public void setMoviObs(String moviObs) {
        this.moviObs = moviObs;
    }
    public String getEstaNombre() {
        return this.estaNombre;
    }
    
    public void setEstaNombre(String estaNombre) {
        this.estaNombre = estaNombre;
    }
    public String getEstadConfrirm() {
        return this.estadConfrirm;
    }
    
    public void setEstadConfrirm(String estadConfrirm) {
        this.estadConfrirm = estadConfrirm;
    }
    public Date getFechaDerivacion() {
        return this.fechaDerivacion;
    }
    
    public void setFechaDerivacion(Date fechaDerivacion) {
        this.fechaDerivacion = fechaDerivacion;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }




}


