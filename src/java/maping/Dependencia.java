package maping;
// Generated 06/10/2014 08:55:07 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Dependencia generated by hbm2java
 */
public class Dependencia  implements java.io.Serializable {


     private long codigo;
     private String nombre;
     private Set<TramiteMovimiento> tramiteMovimientosForCodigo1 = new HashSet<TramiteMovimiento>(0);
     private Set<DocusExtint> docusExtintsForCodigo1 = new HashSet<DocusExtint>(0);
     private Set<TramiteMovimiento> tramiteMovimientosForCodigo = new HashSet<TramiteMovimiento>(0);
     private Set<DocusExtint> docusExtintsForCodigo = new HashSet<DocusExtint>(0);
     private Set<TramiteDatos> tramiteDatoses = new HashSet<TramiteDatos>(0);

    public Dependencia() {
    }

	
    public Dependencia(long codigo) {
        this.codigo = codigo;
    }
    public Dependencia(long codigo, String nombre, Set<TramiteMovimiento> tramiteMovimientosForCodigo1, Set<DocusExtint> docusExtintsForCodigo1, Set<TramiteMovimiento> tramiteMovimientosForCodigo, Set<DocusExtint> docusExtintsForCodigo, Set<TramiteDatos> tramiteDatoses) {
       this.codigo = codigo;
       this.nombre = nombre;
       this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
       this.docusExtintsForCodigo1 = docusExtintsForCodigo1;
       this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
       this.docusExtintsForCodigo = docusExtintsForCodigo;
       this.tramiteDatoses = tramiteDatoses;
    }
   
    public long getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo1() {
        return this.tramiteMovimientosForCodigo1;
    }
    
    public void setTramiteMovimientosForCodigo1(Set<TramiteMovimiento> tramiteMovimientosForCodigo1) {
        this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
    }
    public Set<DocusExtint> getDocusExtintsForCodigo1() {
        return this.docusExtintsForCodigo1;
    }
    
    public void setDocusExtintsForCodigo1(Set<DocusExtint> docusExtintsForCodigo1) {
        this.docusExtintsForCodigo1 = docusExtintsForCodigo1;
    }
    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo() {
        return this.tramiteMovimientosForCodigo;
    }
    
    public void setTramiteMovimientosForCodigo(Set<TramiteMovimiento> tramiteMovimientosForCodigo) {
        this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
    }
    public Set<DocusExtint> getDocusExtintsForCodigo() {
        return this.docusExtintsForCodigo;
    }
    
    public void setDocusExtintsForCodigo(Set<DocusExtint> docusExtintsForCodigo) {
        this.docusExtintsForCodigo = docusExtintsForCodigo;
    }
    public Set<TramiteDatos> getTramiteDatoses() {
        return this.tramiteDatoses;
    }
    
    public void setTramiteDatoses(Set<TramiteDatos> tramiteDatoses) {
        this.tramiteDatoses = tramiteDatoses;
    }




}


