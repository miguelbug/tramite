package maping;
// Generated 16/09/2014 10:36:16 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Dependencia generated by hbm2java
 */
public class Dependencia  implements java.io.Serializable {


     private long codigo;
     private String nombre;
     private Set<TramiteMovimiento> tramiteMovimientosForCodigo = new HashSet<TramiteMovimiento>(0);
     private Set<TramiteMovimiento> tramiteMovimientosForCodigo1 = new HashSet<TramiteMovimiento>(0);
     private Set<MovimientoInterno> movimientoInternosForCodigo1 = new HashSet<MovimientoInterno>(0);
     private Set<TramiteDatos> tramiteDatoses = new HashSet<TramiteDatos>(0);
     private Set<MovimientoInterno> movimientoInternosForCodigo = new HashSet<MovimientoInterno>(0);

    public Dependencia() {
    }

	
    public Dependencia(long codigo) {
        this.codigo = codigo;
    }
    public Dependencia(long codigo, String nombre, Set<TramiteMovimiento> tramiteMovimientosForCodigo, Set<TramiteMovimiento> tramiteMovimientosForCodigo1, Set<MovimientoInterno> movimientoInternosForCodigo1, Set<TramiteDatos> tramiteDatoses, Set<MovimientoInterno> movimientoInternosForCodigo) {
       this.codigo = codigo;
       this.nombre = nombre;
       this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
       this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
       this.movimientoInternosForCodigo1 = movimientoInternosForCodigo1;
       this.tramiteDatoses = tramiteDatoses;
       this.movimientoInternosForCodigo = movimientoInternosForCodigo;
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
    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo() {
        return this.tramiteMovimientosForCodigo;
    }
    
    public void setTramiteMovimientosForCodigo(Set<TramiteMovimiento> tramiteMovimientosForCodigo) {
        this.tramiteMovimientosForCodigo = tramiteMovimientosForCodigo;
    }
    public Set<TramiteMovimiento> getTramiteMovimientosForCodigo1() {
        return this.tramiteMovimientosForCodigo1;
    }
    
    public void setTramiteMovimientosForCodigo1(Set<TramiteMovimiento> tramiteMovimientosForCodigo1) {
        this.tramiteMovimientosForCodigo1 = tramiteMovimientosForCodigo1;
    }
    public Set<MovimientoInterno> getMovimientoInternosForCodigo1() {
        return this.movimientoInternosForCodigo1;
    }
    
    public void setMovimientoInternosForCodigo1(Set<MovimientoInterno> movimientoInternosForCodigo1) {
        this.movimientoInternosForCodigo1 = movimientoInternosForCodigo1;
    }
    public Set<TramiteDatos> getTramiteDatoses() {
        return this.tramiteDatoses;
    }
    
    public void setTramiteDatoses(Set<TramiteDatos> tramiteDatoses) {
        this.tramiteDatoses = tramiteDatoses;
    }
    public Set<MovimientoInterno> getMovimientoInternosForCodigo() {
        return this.movimientoInternosForCodigo;
    }
    
    public void setMovimientoInternosForCodigo(Set<MovimientoInterno> movimientoInternosForCodigo) {
        this.movimientoInternosForCodigo = movimientoInternosForCodigo;
    }




}


