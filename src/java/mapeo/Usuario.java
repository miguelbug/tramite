package mapeo;
// Generated 22/08/2014 02:20:23 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private String usuarioId;
     private Oficina oficina;
     private String usuarioNombre;
     private String usuarioPassword;
     private Set<UsuTrammov> usuTrammovs = new HashSet<UsuTrammov>(0);
     private Set<TramiteDatos> tramiteDatoses = new HashSet<TramiteDatos>(0);

    public Usuario() {
    }

	
    public Usuario(String usuarioId, Oficina oficina) {
        this.usuarioId = usuarioId;
        this.oficina = oficina;
    }
    public Usuario(String usuarioId, Oficina oficina, String usuarioNombre, String usuarioPassword, Set<UsuTrammov> usuTrammovs, Set<TramiteDatos> tramiteDatoses) {
       this.usuarioId = usuarioId;
       this.oficina = oficina;
       this.usuarioNombre = usuarioNombre;
       this.usuarioPassword = usuarioPassword;
       this.usuTrammovs = usuTrammovs;
       this.tramiteDatoses = tramiteDatoses;
    }
   
    public String getUsuarioId() {
        return this.usuarioId;
    }
    
    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Oficina getOficina() {
        return this.oficina;
    }
    
    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }
    public String getUsuarioNombre() {
        return this.usuarioNombre;
    }
    
    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
    public String getUsuarioPassword() {
        return this.usuarioPassword;
    }
    
    public void setUsuarioPassword(String usuarioPassword) {
        this.usuarioPassword = usuarioPassword;
    }
    public Set<UsuTrammov> getUsuTrammovs() {
        return this.usuTrammovs;
    }
    
    public void setUsuTrammovs(Set<UsuTrammov> usuTrammovs) {
        this.usuTrammovs = usuTrammovs;
    }
    public Set<TramiteDatos> getTramiteDatoses() {
        return this.tramiteDatoses;
    }
    
    public void setTramiteDatoses(Set<TramiteDatos> tramiteDatoses) {
        this.tramiteDatoses = tramiteDatoses;
    }




}


