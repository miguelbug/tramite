/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import dao.Oficio_OficioUnicoDAO;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class Oficio_OficioUnicoBean {

    private String asunto;
    private String tramnum;
    private Date fecha;
    private String auxfecha;
    private String origen;
    private String destino;
    private Oficio_OficioUnicoDAO ofiunico;
    private Usuario usu;
    private FacesContext faceContext;
    
    public Oficio_OficioUnicoBean() {
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
    }
    public void mostrarDialog(){
//        
    }
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTramnum() {
        return tramnum;
    }

    public void setTramnum(String tramnum) {
        this.tramnum = tramnum;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAuxfecha() {
        return auxfecha;
    }

    public void setAuxfecha(String auxfecha) {
        this.auxfecha = auxfecha;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
}
