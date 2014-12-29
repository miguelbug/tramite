/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.GestionUsuarioDAO;
import daoimpl.GestionUsuarioDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Jefatura;
import maping.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class GestionUsuarioBean implements Serializable {

    private Usuario usu;
    private final FacesContext faceContext;
    private GestionUsuarioDAO gu;
    private String antiguapass;
    private String nuevapass;
    private String confirmarcontra;
    ///////////////
    private String nuevonomb;
    private String nuevoapell;
    private String nuevodni;
    private String nuevoofi;
    private String nuevaprof;
    private String nuevocontrato;
    private String nuevousuario;
    private String nuevotelefono;
    private String nuevoanexo;
    private String nuevocargo;
    private String nuevogrado;
    private String nuevocorreo;
    private List oficinas, profesion, contrato;
    //////////////

    public GestionUsuarioBean() {
        gu = new GestionUsuarioDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        oficinas = new ArrayList<String>();
        profesion = new ArrayList<String>();
        contrato = new ArrayList<String>();
    }

    public void Guardar() {
        FacesMessage message = null;
        Usuario aux = gu.ValidarClave(antiguapass, usu.getUsu());
        if (aux == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Datos erroneos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            Limpiar();
        } else {
            if (nuevapass.equals(confirmarcontra)) {
                try {
                    aux.setClave(nuevapass);
                    gu.Cambiar(aux);
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado sus datos");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                } catch (Exception e) {
                    message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mal", "no se ha guardado");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    System.out.println(e.getMessage());
                }
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Contraseñas no coinciden");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        }
    }

    public void listar() {
        listarOficinas();
        listarProfesion();
        listarContrato();
    }

    public void guardarJefatura() {
        FacesMessage message = null;
        try {
            Jefatura jefatura = new Jefatura();
            Usuario usuario = new Usuario();
            usuario.setUsuNombre(nuevoapell + ", " + nuevonomb);
            usuario.setClave("123");
            usuario.setOficina(gu.getOficina(nuevoofi));
            usuario.setEstado("activo");
            usuario.setUsu(nuevousuario);
            gu.GuardarUsuario(usuario);
            jefatura.setNombre(nuevonomb);
            jefatura.setApellidos(nuevoapell);
            jefatura.setTelefono(nuevotelefono);
            jefatura.setAnexo(nuevoanexo);
            jefatura.setCargo(nuevocargo);
            jefatura.setGrado(nuevogrado);
            jefatura.setCorreo(nuevocorreo);
            jefatura.setDni(nuevodni);
            jefatura.setUsuario(gu.getUsuario(nuevoapell + ", " + nuevonomb));
            jefatura.setProfesion(gu.getProfesion(nuevaprof));
            jefatura.setDependencia(gu.getDependencia(nuevoofi));
            jefatura.setTipoContrato(gu.getContrato(nuevocontrato));
            gu.GuardarJefe(jefatura);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Realizado", "Se ha Guardado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA REALIZADO LA ACCION");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void listarOficinas() {
        oficinas = gu.getOficinas();
    }

    public void listarProfesion() {
        profesion = gu.getProfesion();
    }

    public void listarContrato() {
        contrato = gu.getContrato();
    }

    public void Limpiar() {
        antiguapass = " ";
        nuevapass = " ";
    }

    public String getNuevonomb() {
        return nuevonomb;
    }

    public void setNuevonomb(String nuevonomb) {
        this.nuevonomb = nuevonomb;
    }

    public String getNuevoapell() {
        return nuevoapell;
    }

    public void setNuevoapell(String nuevoapell) {
        this.nuevoapell = nuevoapell;
    }

    public String getNuevodni() {
        return nuevodni;
    }

    public void setNuevodni(String nuevodni) {
        this.nuevodni = nuevodni;
    }

    public String getNuevoofi() {
        return nuevoofi;
    }

    public void setNuevoofi(String nuevoofi) {
        this.nuevoofi = nuevoofi;
    }

    public String getNuevaprof() {
        return nuevaprof;
    }

    public void setNuevaprof(String nuevaprof) {
        this.nuevaprof = nuevaprof;
    }

    public String getNuevocontrato() {
        return nuevocontrato;
    }

    public void setNuevocontrato(String nuevocontrato) {
        this.nuevocontrato = nuevocontrato;
    }

    public String getNuevousuario() {
        return nuevousuario;
    }

    public void setNuevousuario(String nuevousuario) {
        this.nuevousuario = nuevousuario;
    }

    public String getNuevotelefono() {
        return nuevotelefono;
    }

    public void setNuevotelefono(String nuevotelefono) {
        this.nuevotelefono = nuevotelefono;
    }

    public String getNuevoanexo() {
        return nuevoanexo;
    }

    public void setNuevoanexo(String nuevoanexo) {
        this.nuevoanexo = nuevoanexo;
    }

    public String getNuevocargo() {
        return nuevocargo;
    }

    public void setNuevocargo(String nuevocargo) {
        this.nuevocargo = nuevocargo;
    }

    public String getNuevogrado() {
        return nuevogrado;
    }

    public void setNuevogrado(String nuevogrado) {
        this.nuevogrado = nuevogrado;
    }

    public String getNuevocorreo() {
        return nuevocorreo;
    }

    public void setNuevocorreo(String nuevocorreo) {
        this.nuevocorreo = nuevocorreo;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public GestionUsuarioDAO getGu() {
        return gu;
    }

    public void setGu(GestionUsuarioDAO gu) {
        this.gu = gu;
    }

    public String getAntiguapass() {
        return antiguapass;
    }

    public void setAntiguapass(String antiguapass) {
        this.antiguapass = antiguapass;
    }

    public String getNuevapass() {
        return nuevapass;
    }

    public void setNuevapass(String nuevapass) {
        this.nuevapass = nuevapass;
    }

    public String getConfirmarcontra() {
        return confirmarcontra;
    }

    public void setConfirmarcontra(String confirmarcontra) {
        this.confirmarcontra = confirmarcontra;
    }

    public List getOficinas() {
        return oficinas;
    }

    public void setOficinas(List oficinas) {
        this.oficinas = oficinas;
    }

    public List getProfesion() {
        return profesion;
    }

    public void setProfesion(List profesion) {
        this.profesion = profesion;
    }

    public List getContrato() {
        return contrato;
    }

    public void setContrato(List contrato) {
        this.contrato = contrato;
    }

}
