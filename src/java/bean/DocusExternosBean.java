/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import dao.DocumentoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class DocusExternosBean {

    private String documento;
    private String origen;
    private String destino;
    private List dependenciasprov;
    private Date fechaprov;
    private DocumentoDAO dd;
    private FacesContext faceContext;
    private Usuario usu;
    private DerivarDAO deriv;

    public DocusExternosBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        dependenciasprov = new ArrayList<Map<String, String>>();
        fechaprov = new Date();
        deriv= new DerivarDaoImpl();
        dd = new DocumentoDaoImpl();
        ObtenerDepIndic();
    }

    public void ObtenerDepIndic() {
        dependenciasprov = dd.getDependencias();
    }

    public String fechaactual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(fechaprov);
    }

    public String generarCorrelativo() {
        int corr = 0;
        String aux = "";
        try {
            System.out.println("lleno");
            corr = Integer.parseInt(deriv.getCorre(usu));
            corr = corr + 1;
            if (corr < 10) {
                aux = "0000" + corr;
            }
            if (corr > 9 && corr < 100) {
                aux = "000" + corr;
            }
            if (corr > 99 && corr < 1000) {
                aux = "00" + corr;
            }
            if (corr > 999 && corr < 10000) {
                aux = "0" + corr;
            }
            if (corr > 10000) {
                aux = String.valueOf(corr);
            }

        } catch (Exception e) {
            System.out.println("no lleno");
            corr = corr + 1;
            aux = "0000" + corr;
        }
        return aux;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public List getDependenciasprov() {
        return dependenciasprov;
    }

    public void setDependenciasprov(List dependenciasprov) {
        this.dependenciasprov = dependenciasprov;
    }

    public Date getFechaprov() {
        return fechaprov;
    }

    public void setFechaprov(Date fechaprov) {
        this.fechaprov = fechaprov;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public FacesContext getFaceContext() {
        return faceContext;
    }

    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

}
