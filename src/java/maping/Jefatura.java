package maping;
// Generated 23/10/2014 05:20:25 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * Jefatura generated by hbm2java
 */
public class Jefatura  implements java.io.Serializable {


     private BigDecimal idJefatura;
     private Usuario usuario;
     private Dependencia dependencia;
     private Profesion profesion;
     private TipoContrato tipoContrato;
     private String nombre;
     private String apellidos;
     private String telefono;
     private String anexo;
     private String cargo;
     private String grado;
     private String correo;
     private String dni;

    public Jefatura() {
    }

	
    public Jefatura(BigDecimal idJefatura) {
        this.idJefatura = idJefatura;
    }

    public Jefatura(BigDecimal idJefatura, Usuario usuario, Dependencia dependencia, Profesion profesion, TipoContrato tipoContrato, String nombre, String apellidos, String telefono, String anexo, String cargo, String grado, String correo, String dni) {
        this.idJefatura = idJefatura;
        this.usuario = usuario;
        this.dependencia = dependencia;
        this.profesion = profesion;
        this.tipoContrato = tipoContrato;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.anexo = anexo;
        this.cargo = cargo;
        this.grado = grado;
        this.correo = correo;
        this.dni = dni;
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
    public Dependencia getDependencia() {
        return this.dependencia;
    }
    
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
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

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }




}


