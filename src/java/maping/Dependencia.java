package maping;
// Generated 17/04/2015 04:53:35 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Dependencia generated by hbm2java
 */
public class Dependencia  implements java.io.Serializable {


     private long codigo;
     private String nombre;
     private String tipodepe;
     private String flac;
     private String flac2;
     private String flac3;
     private Set<OficCirc> oficCircs = new HashSet<OficCirc>(0);
     private Set<DetallOficcirc> detallOficcircs = new HashSet<DetallOficcirc>(0);
     private Set<DocusExtint> docusExtintsForCodigo = new HashSet<DocusExtint>(0);
     private Set<TramiteMovimiento> tramiteMovimientosForCodigo1 = new HashSet<TramiteMovimiento>(0);
     private Set<DocusExtint> docusExtintsForCodigo1 = new HashSet<DocusExtint>(0);
     private Set<Jefatura> jefaturas = new HashSet<Jefatura>(0);
     private Set<Oficios> oficiosesForCodigo = new HashSet<Oficios>(0);
     private Set<DocusInternos> docusInternosesForCodigo = new HashSet<DocusInternos>(0);
     private Set<Oficios> oficiosesForCodigo1 = new HashSet<Oficios>(0);
     private Set<DocusInternos> docusInternosesForCodigo1 = new HashSet<DocusInternos>(0);
     private Set<TramiteMovimiento> tramiteMovimientosForCodigo = new HashSet<TramiteMovimiento>(0);
     private Set<TramiteDatos> tramiteDatoses = new HashSet<TramiteDatos>(0);

    public Dependencia() {
    }

	
    public Dependencia(long codigo) {
        this.codigo = codigo;
    }
    public Dependencia(long codigo, String nombre, String tipodepe, String flac, String flac2, String flac3, Set<OficCirc> oficCircs, Set<DetallOficcirc> detallOficcircs, Set<DocusExtint> docusExtintsForCodigo, Set<TramiteMovimiento> tramiteMovimientosForCodigo1, Set<DocusExtint> docusExtintsForCodigo1, Set<Jefatura> jefaturas, Set<Oficios> oficiosesForCodigo, Set<DocusInternos> docusInternosesForCodigo, Set<Oficios> oficiosesForCodigo1, Set<DocusInternos> docusInternosesForCodigo1, Set<TramiteMovimiento> tramiteMovimientosForCodigo, Set<TramiteDatos> tramiteDatoses) {
       this.codigo = codigo;
       this.nombre = nombre;
       this.tipodepe = tipodepe;
       this.flac = flac;
       this.flac2 = flac2;
       this.flac3 = flac3;
       this.oficCircs = oficCircs;
       this.detallOficcircs = detallOficcircs;
       this.docusExtintsForCodigo = docusExtintsForCodigo;
       this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
       this.docusExtintsForCodigo1 = docusExtintsForCodigo1;
       this.jefaturas = jefaturas;
       this.oficiosesForCodigo = oficiosesForCodigo;
       this.docusInternosesForCodigo = docusInternosesForCodigo;
       this.oficiosesForCodigo1 = oficiosesForCodigo1;
       this.docusInternosesForCodigo1 = docusInternosesForCodigo1;
       this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
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
    public String getTipodepe() {
        return this.tipodepe;
    }
    
    public void setTipodepe(String tipodepe) {
        this.tipodepe = tipodepe;
    }
    public String getFlac() {
        return this.flac;
    }
    
    public void setFlac(String flac) {
        this.flac = flac;
    }
    public String getFlac2() {
        return this.flac2;
    }
    
    public void setFlac2(String flac2) {
        this.flac2 = flac2;
    }
    public String getFlac3() {
        return this.flac3;
    }
    
    public void setFlac3(String flac3) {
        this.flac3 = flac3;
    }
    public Set<OficCirc> getOficCircs() {
        return this.oficCircs;
    }
    
    public void setOficCircs(Set<OficCirc> oficCircs) {
        this.oficCircs = oficCircs;
    }
    public Set<DetallOficcirc> getDetallOficcircs() {
        return this.detallOficcircs;
    }
    
    public void setDetallOficcircs(Set<DetallOficcirc> detallOficcircs) {
        this.detallOficcircs = detallOficcircs;
    }
    public Set<DocusExtint> getDocusExtintsForCodigo() {
        return this.docusExtintsForCodigo;
    }
    
    public void setDocusExtintsForCodigo(Set<DocusExtint> docusExtintsForCodigo) {
        this.docusExtintsForCodigo = docusExtintsForCodigo;
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
    public Set<Jefatura> getJefaturas() {
        return this.jefaturas;
    }
    
    public void setJefaturas(Set<Jefatura> jefaturas) {
        this.jefaturas = jefaturas;
    }
    public Set<Oficios> getOficiosesForCodigo() {
        return this.oficiosesForCodigo;
    }
    
    public void setOficiosesForCodigo(Set<Oficios> oficiosesForCodigo) {
        this.oficiosesForCodigo = oficiosesForCodigo;
    }
    public Set<DocusInternos> getDocusInternosesForCodigo() {
        return this.docusInternosesForCodigo;
    }
    
    public void setDocusInternosesForCodigo(Set<DocusInternos> docusInternosesForCodigo) {
        this.docusInternosesForCodigo = docusInternosesForCodigo;
    }
    public Set<Oficios> getOficiosesForCodigo1() {
        return this.oficiosesForCodigo1;
    }
    
    public void setOficiosesForCodigo1(Set<Oficios> oficiosesForCodigo1) {
        this.oficiosesForCodigo1 = oficiosesForCodigo1;
    }
    public Set<DocusInternos> getDocusInternosesForCodigo1() {
        return this.docusInternosesForCodigo1;
    }
    
    public void setDocusInternosesForCodigo1(Set<DocusInternos> docusInternosesForCodigo1) {
        this.docusInternosesForCodigo1 = docusInternosesForCodigo1;
    }
    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo() {
        return this.tramiteMovimientosForCodigo;
    }
    
    public void setTramiteMovimientosForCodigo(Set<TramiteMovimiento> tramiteMovimientosForCodigo) {
        this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
    }
    public Set<TramiteDatos> getTramiteDatoses() {
        return this.tramiteDatoses;
    }
    
    public void setTramiteDatoses(Set<TramiteDatos> tramiteDatoses) {
        this.tramiteDatoses = tramiteDatoses;
    }




}


