package maping;
// Generated 20/10/2014 11:41:07 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Jefatura generated by hbm2java
 */
public class Jefatura  implements java.io.Serializable {


     private BigDecimal idJefatura;
     private Usuario usuario;
     private Profesion profesion;
     private String nombre;
     private String apellidos;
     private String telefono;
     private String anexo;
     private String cargo;
     private String grado;
     private String correo;
     private String dni;
     private String apellido;
     private String profesion_1;
     private Set<Dependencia> dependencias = new HashSet<Dependencia>(0);

    public Jefatura() {
    }

	
    public Jefatura(BigDecimal idJefatura) {
        this.idJefatura = idJefatura;
    }
    public Jefatura(BigDecimal idJefatura, Usuario usuario, Profesion profesion, String nombre, String apellidos, String telefono, String anexo, String cargo, String grado, String correo, String dni, String apellido, String profesion_1, Set<Dependencia> dependencias) {
       this.idJefatura = idJefatura;
       this.usuario = usuario;
       this.profesion = profesion;
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.telefono = telefono;
       this.anexo = anexo;
       this.cargo = cargo;
       this.grado = grado;
       this.correo = correo;
       this.dni = dni;
       this.apellido = apellido;
       this.profesion_1 = profesion_1;
       this.dependencias = dependencias;
    }
   
    public BigDecimal getIdJefatura() {
        return this.idJefatura;
    }
    
    public void setIdJefatura(BigDecimal idJefatura) {
        this.idJefatura = idJefatura;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Profesion getProfesion() {
        return this.profesion;
    }
    
    public void setProfesion(Profesion profesion) {
        this.profesion = profesion;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getAnexo() {
        return this.anexo;
    }
    
    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }
    public String getCargo() {
        return this.cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getGrado() {
        return this.grado;
    }
    
    public void setGrado(String grado) {
        this.grado = grado;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getDni() {
        return this.dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getProfesion_1() {
        return this.profesion_1;
    }
    
    public void setProfesion_1(String profesion_1) {
        this.profesion_1 = profesion_1;
    }
    public Set<Dependencia> getDependencias() {
        return this.dependencias;
    }
    
    public void setDependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
    }




}


