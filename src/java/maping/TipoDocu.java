package maping;
// Generated 02/09/2014 12:28:10 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * TipoDocu generated by hbm2java
 */
public class TipoDocu  implements java.io.Serializable {


     private BigDecimal idDocu;
     private TramiteDatos tramiteDatos;
     private String docuPric;
     private String docuNombre;
     private String docuNum;
     private String docuSiglas;
     private String docuAnio;

    public TipoDocu() {
    }

	
    public TipoDocu(BigDecimal idDocu, String docuPric, String docuNombre) {
        this.idDocu = idDocu;
        this.docuPric = docuPric;
        this.docuNombre = docuNombre;
    }
    public TipoDocu(BigDecimal idDocu, TramiteDatos tramiteDatos, String docuPric, String docuNombre, String docuNum, String docuSiglas, String docuAnio) {
       this.idDocu = idDocu;
       this.tramiteDatos = tramiteDatos;
       this.docuPric = docuPric;
       this.docuNombre = docuNombre;
       this.docuNum = docuNum;
       this.docuSiglas = docuSiglas;
       this.docuAnio = docuAnio;
    }
   
    public BigDecimal getIdDocu() {
        return this.idDocu;
    }
    
    public void setIdDocu(BigDecimal idDocu) {
        this.idDocu = idDocu;
    }
    public TramiteDatos getTramiteDatos() {
        return this.tramiteDatos;
    }
    
    public void setTramiteDatos(TramiteDatos tramiteDatos) {
        this.tramiteDatos = tramiteDatos;
    }
    public String getDocuPric() {
        return this.docuPric;
    }
    
    public void setDocuPric(String docuPric) {
        this.docuPric = docuPric;
    }
    public String getDocuNombre() {
        return this.docuNombre;
    }
    
    public void setDocuNombre(String docuNombre) {
        this.docuNombre = docuNombre;
    }
    public String getDocuNum() {
        return this.docuNum;
    }
    
    public void setDocuNum(String docuNum) {
        this.docuNum = docuNum;
    }
    public String getDocuSiglas() {
        return this.docuSiglas;
    }
    
    public void setDocuSiglas(String docuSiglas) {
        this.docuSiglas = docuSiglas;
    }
    public String getDocuAnio() {
        return this.docuAnio;
    }
    
    public void setDocuAnio(String docuAnio) {
        this.docuAnio = docuAnio;
    }




}

