package maping;
// Generated 23/10/2014 05:20:25 PM by Hibernate Tools 3.6.0

import java.util.HashSet;
import java.util.Set;

/**
 * Dependencia generated by hbm2java
 */
public class Dependencia implements java.io.Serializable {

    private long codigo;
    private String nombre;
    private String tipodepe;
    private String flac;
    private Set<TramiteDatos> tramiteDatoses = new HashSet<TramiteDatos>(0);
    private Set<TramiteMovimiento> tramiteMovimientosForCodigo = new HashSet<TramiteMovimiento>(0);
    private Set<OficCirc> oficCircs = new HashSet<OficCirc>(0);
    private Set<Jefatura> jefaturas = new HashSet<Jefatura>(0);
    private Set<DetallOficcirc> detallOficcircs = new HashSet<DetallOficcirc>(0);
    private Set<TramiteMovimiento> tramiteMovimientosForCodigo1 = new HashSet<TramiteMovimiento>(0);
    private Set<DocusExtint> docusExtintsForCodigo = new HashSet<DocusExtint>(0);
    private Set<Oficios> oficiosesForCodigo1 = new HashSet<Oficios>(0);
    private Set<Oficios> oficiosesForCodigo = new HashSet<Oficios>(0);
    private Set<DocusExtint> docusExtintsForCodigo1 = new HashSet<DocusExtint>(0);

    public Dependencia() {
    }

    public Dependencia(long codigo) {
        this.codigo = codigo;
    }

    public Dependencia(long codigo, String nombre, String tipodepe, String flac, Set<TramiteDatos> tramiteDatoses, Set<TramiteMovimiento> tramiteMovimientosForCodigo, Set<OficCirc> oficCircs, Set<Jefatura> jefaturas, Set<DetallOficcirc> detallOficcircs, Set<TramiteMovimiento> tramiteMovimientosForCodigo1, Set<DocusExtint> docusExtintsForCodigo, Set<Oficios> oficiosesForCodigo1, Set<Oficios> oficiosesForCodigo, Set<DocusExtint> docusExtintsForCodigo1) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipodepe = tipodepe;
        this.flac = flac;
        this.tramiteDatoses = tramiteDatoses;
        this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
        this.oficCircs = oficCircs;
        this.jefaturas = jefaturas;
        this.detallOficcircs = detallOficcircs;
        this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
        this.docusExtintsForCodigo = docusExtintsForCodigo;
        this.oficiosesForCodigo1 = oficiosesForCodigo1;
        this.oficiosesForCodigo = oficiosesForCodigo;
        this.docusExtintsForCodigo1 = docusExtintsForCodigo1;
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

    public Set<TramiteDatos> getTramiteDatoses() {
        return this.tramiteDatoses;
    }

    public void setTramiteDatoses(Set<TramiteDatos> tramiteDatoses) {
        this.tramiteDatoses = tramiteDatoses;
    }

    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo() {
        return this.tramiteMovimientosForCodigo;
    }

    public void setTramiteMovimientosForCodigo(Set<TramiteMovimiento> tramiteMovimientosForCodigo) {
        this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
    }

    public Set<OficCirc> getOficCircs() {
        return this.oficCircs;
    }

    public void setOficCircs(Set<OficCirc> oficCircs) {
        this.oficCircs = oficCircs;
    }

    public Set<Jefatura> getJefaturas() {
        return this.jefaturas;
    }

    public void setJefaturas(Set<Jefatura> jefaturas) {
        this.jefaturas = jefaturas;
    }

    public Set<DetallOficcirc> getDetallOficcircs() {
        return this.detallOficcircs;
    }

    public void setDetallOficcircs(Set<DetallOficcirc> detallOficcircs) {
        this.detallOficcircs = detallOficcircs;
    }

    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo1() {
        return this.tramiteMovimientosForCodigo1;
    }

    public void setTramiteMovimientosForCodigo1(Set<TramiteMovimiento> tramiteMovimientosForCodigo1) {
        this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
    }

    public Set<DocusExtint> getDocusExtintsForCodigo() {
        return this.docusExtintsForCodigo;
    }

    public void setDocusExtintsForCodigo(Set<DocusExtint> docusExtintsForCodigo) {
        this.docusExtintsForCodigo = docusExtintsForCodigo;
    }

    public Set<Oficios> getOficiosesForCodigo1() {
        return this.oficiosesForCodigo1;
    }

    public void setOficiosesForCodigo1(Set<Oficios> oficiosesForCodigo1) {
        this.oficiosesForCodigo1 = oficiosesForCodigo1;
    }

    public Set<Oficios> getOficiosesForCodigo() {
        return this.oficiosesForCodigo;
    }

    public void setOficiosesForCodigo(Set<Oficios> oficiosesForCodigo) {
        this.oficiosesForCodigo = oficiosesForCodigo;
    }

    public Set<DocusExtint> getDocusExtintsForCodigo1() {
        return this.docusExtintsForCodigo1;
    }

    public void setDocusExtintsForCodigo1(Set<DocusExtint> docusExtintsForCodigo1) {
        this.docusExtintsForCodigo1 = docusExtintsForCodigo1;
    }

}
