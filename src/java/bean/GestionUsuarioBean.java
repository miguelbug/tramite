/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.GestionUsuarioDAO;
import daoimpl.GestionUsuarioDaoImpl;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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

    public GestionUsuarioBean() {
        gu = new GestionUsuarioDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
    }

    public void Guardar() {
        FacesMessage message = null;
        Usuario aux = gu.ValidarClave(antiguapass, usu.getUsu());
        if (aux == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Datos erroneos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            Limpiar();
        } else {
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
        }
    }

    public void Limpiar() {
        antiguapass = " ";
        nuevapass = " ";
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

}
