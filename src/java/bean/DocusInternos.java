/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import dao.DerivarDAO;
import dao.DocusInternosDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocusInternosDaoImpl;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
public class DocusInternos implements Serializable{

    private List docusinternos;
    private Usuario usu;
    private final FacesContext faceContext;
    private DocusInternosDAO did;
    private List otrosdocus;
    public  List docselec;
    private Date fecha1_1;
    private Date fecha2_2;
    public static Date fecha1;
    public static Date fecha2;
    private String tipodocumento;
    private DerivarDAO deriv;
    
    
    public DocusInternos() {
        did= new DocusInternosDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        docusinternos = new ArrayList<HashMap<String,String>>();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        deriv = new DerivarDaoImpl();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean isdocumentosrspta2= (currentPage.lastIndexOf("pruebafecha.xhtml")>-1);
        boolean isdocumentosrspta = (currentPage.lastIndexOf("documentos_respta.xhtml") > -1);
        if(isdocumentosrspta){
            mostrarDocusInternos();
        }else{
            if(isdocumentosrspta2){
                mostrarDocusInternos();
            }
        }
        
        
    }
    public void actualizarLista(){
        
    }
    public void mostrarDocusInternos(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        System.out.println("listando docus internos");
        docusinternos.clear();
        try {
            System.out.println(usu.getOficina().getIdOficina()+"-"+usu.getUsu());
            System.out.println(deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu()));
            List lista = new ArrayList();
            lista = did.getDocusInternos(deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu()));
            Iterator ite = lista.iterator();
            Object obj[] = new Object[9];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("posi", String.valueOf(obj[0]));
                listaaux.put("numerotramite",String.valueOf(obj[2])+"-"+String.valueOf(obj[1])+"-"+String.valueOf(obj[3])+"-"+String.valueOf(obj[4]));
                listaaux.put("fechareg", String.valueOf(obj[5]));
                listaaux.put("tramnum", String.valueOf(obj[6]));
                listaaux.put("asunto", String.valueOf(obj[7]));
                listaaux.put("asignado", String.valueOf(obj[8]));
                docusinternos.add(listaaux);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public List getDocusinternos() {
        return docusinternos;
    }

    public void setDocusinternos(List docusinternos) {
        this.docusinternos = docusinternos;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public DocusInternosDAO getDid() {
        return did;
    }

    public void setDid(DocusInternosDAO did) {
        this.did = did;
    }

    public List getOtrosdocus() {
        return otrosdocus;
    }

    public void setOtrosdocus(List otrosdocus) {
        this.otrosdocus = otrosdocus;
    }

    public List getDocselec() {
        return docselec;
    }

    public void setDocselec(List docselec) {
        this.docselec = docselec;
    }

    

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public static Date getFecha1() {
        return fecha1;
    }

    public static void setFecha1(Date fecha1) {
        DocusInternos.fecha1 = fecha1;
    }

    public static Date getFecha2() {
        return fecha2;
    }

    public static void setFecha2(Date fecha2) {
        DocusInternos.fecha2 = fecha2;
    }

    public Date getFecha1_1() {
        return fecha1_1;
    }

    public void setFecha1_1(Date fecha1_1) {
        this.fecha1_1 = fecha1_1;
    }

    public Date getFecha2_2() {
        return fecha2_2;
    }

    public void setFecha2_2(Date fecha2_2) {
        this.fecha2_2 = fecha2_2;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }
    
}
