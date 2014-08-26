/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import dao.LoginDao;
import daoimpl.LoginDaoImpl;
import javax.servlet.http.HttpServletRequest;
import mapeo.Usuario;

/**
 *
 * @author OGPL
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private String usuario;
    private String pass;
    private final LoginDao ld;
    private Usuario usu;
    private HttpServletRequest httpServletRequest;
    private FacesContext contex;
    private String url;

    public LoginBean() {
        contex = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) contex.getExternalContext().getRequest();
        ld = new LoginDaoImpl();
        if (usu == null) {
            usu = new Usuario();
        }
    }

    public String ActionLogin() {
        System.out.println("entra a login");
        String url="index";
        FacesMessage message = null;
        if (usuario != null && pass != null) {
            usu = ld.getUsuario(usuario, pass);
            try {
                contex = FacesContext.getCurrentInstance();
                httpServletRequest = (HttpServletRequest) contex.getExternalContext().getRequest();
                httpServletRequest.getSession().setAttribute("sesionUsuario", usu);
                 message= new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usu.getUsuarioNombre());
                url= "template";
            } catch (Exception e) {
                 message= new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario/Password Incorrecto");
                url= "index";
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe llenar todos los campos");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        return url;
    }

    public String logout() {
        limpiarlabels();
        httpServletRequest.removeAttribute("sesionUsuario");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public void limpiarlabels() {
        this.setUsuario("");
        this.setPass("");
    }

    public void getPathRol() {
        if (usu != null) {
            System.out.println("sale de aca");
            if (usu.getOficina().getIdOficina().equals("10069")) {
                url = "menu_admin.xhtml";
            }
            if (!usu.getOficina().getIdOficina().equals("10069")) {
                url = "menu_user.xhtml";
            }
        }

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public LoginDao getLd() {
        return ld;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
