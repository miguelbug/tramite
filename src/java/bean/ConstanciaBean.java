/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ConstanciaDAO;
import dao.DerivarDAO;
import daoimpl.ConstanciaDaoImpl;
import daoimpl.DerivarDaoImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Constancias;
import maping.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class ConstanciaBean {

    private String correlativo;
    private String escogido;
    private List empleados;
    private String tipocontrato = "";
    private Date desde;
    private Date hasta;
    private Date fechaemision;
    private String auxanio;
    private DerivarDAO deriv;
    private Date fecha;
    private String fechaemisionaux;
    private ConstanciaDAO cons;
    private FacesContext faceContext;
    private Usuario usu;
    private List constancias;
    private List otrosdocus,otrosdocus1;
    private Date fechaactual;
    private static String correlativo2;
    private boolean ver,nover;

    public ConstanciaBean() {
        fechaactual= new Date();
        deriv = new DerivarDaoImpl();
        cons = new ConstanciaDaoImpl();
        empleados = new ArrayList<String>();
        constancias= new ArrayList<Map<String, String>>();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean isconstancias = (currentPage.lastIndexOf("Constancias.xhtml") > -1);
        if(isconstancias){
            mostrarConstancias();
        }
        
    }

    public void abrirconstancia() {
        getAnio();
        fechaactual();
        generarCorrelativo2();
        mostrarJefatura();
        correlativo2=correlativo;
    }

    public void cambio() {
        tipocontrato = cons.getContrato(escogido);
    }

    public void mostrarJefatura() {
        empleados.clear();
        try {
            System.out.println("entra a seguimiento3");
            List lista = new ArrayList();
            lista = cons.getJefatura();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[2];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                //Map<String, String> listaaux = new HashMap<String, String>();
                //listaaux.put("nombre", String.valueOf(obj[0])+" "+String.valueOf(obj[1]));
                empleados.add(String.valueOf(obj[0]) + " " + String.valueOf(obj[1]));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //empleados=cons.getJefatura();
    }

    public void getAnio() {
        System.out.println("entra getanio");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        fechaemision = new Date();
        auxanio = sdf.format(fechaemision);
        System.out.println(auxanio);
    }

    public void fechaactual() {
        System.out.println("entra fechaactual");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaemision = new Date();
        fechaemisionaux = sdf.format(fechaemision);
        System.out.println(fechaemisionaux);
    }

    public void generarCorrelativo2() {
        int corr = 0;
        String aux = "";
        try {
            if (auxanio.equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(cons.getIndice());
                System.out.println("aumentando el correlativo: " + corr);
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
            } else {
                System.out.println("lleno 2");
                corr = corr + 1;
                aux = "0000" + corr;
            }

        } catch (Exception e) {
            System.out.println("no lleno");
            corr = corr + 1;
            aux = "0000" + corr;
            System.out.println(corr);
            System.out.println(aux);
        }
        correlativo = aux;
    }

    public void guardarconstancia() {
        FacesMessage message = null;
        Constancias c = new Constancias();
        c.setCorrelativo(correlativo);
        c.setDesde(desde);
        c.setHasta(hasta);
        c.setFechaEmision(fechaemision);
        c.setDrigidoA(escogido);
        c.setTipoContrato(tipocontrato);
        c.setUsuario(usu);
        c.setFechaRegistro(this.fechaactual);
        try {
            cons.guardarConstancia(c);
            ver=true;
            nover=false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ver=false;
            nover=true;
        }

    }
    public void mostrarConstancias(){
        System.out.println("listando documentos");
        constancias.clear();
        try {
            List lista = new ArrayList();
            lista = cons.getConstancias();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[7];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", String.valueOf(obj[0]));
                listaaux.put("fechaemi", String.valueOf(obj[1]));
                listaaux.put("dirigidoa", String.valueOf(obj[2]));
                listaaux.put("tipocontrato", String.valueOf(obj[3]));
                listaaux.put("desde", String.valueOf(obj[4]));
                listaaux.put("hasta", String.valueOf(obj[5]));
                listaaux.put("usu", String.valueOf(obj[6]));
                constancias.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getEscogido() {
        return escogido;
    }

    public void setEscogido(String escogido) {
        this.escogido = escogido;
    }

    public List getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List empleados) {
        this.empleados = empleados;
    }

    public String getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(String tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }

    public String getAuxanio() {
        return auxanio;
    }

    public void setAuxanio(String auxanio) {
        this.auxanio = auxanio;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaemisionaux() {
        return fechaemisionaux;
    }

    public void setFechaemisionaux(String fechaemisionaux) {
        this.fechaemisionaux = fechaemisionaux;
    }

    public ConstanciaDAO getCons() {
        return cons;
    }

    public void setCons(ConstanciaDAO cons) {
        this.cons = cons;
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

    public List getConstancias() {
        return constancias;
    }

    public void setConstancias(List constancias) {
        this.constancias = constancias;
    }

    public List getOtrosdocus() {
        return otrosdocus;
    }

    public void setOtrosdocus(List otrosdocus) {
        this.otrosdocus = otrosdocus;
    }

    public Date getFechaactual() {
        return fechaactual;
    }

    public void setFechaactual(Date fechaactual) {
        this.fechaactual = fechaactual;
    }

    public static String getCorrelativo2() {
        return correlativo2;
    }

    public static void setCorrelativo2(String correlativo2) {
        ConstanciaBean.correlativo2 = correlativo2;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public boolean isNover() {
        return nover;
    }

    public void setNover(boolean nover) {
        this.nover = nover;
    }

    public List getOtrosdocus1() {
        return otrosdocus1;
    }

    public void setOtrosdocus1(List otrosdocus1) {
        this.otrosdocus1 = otrosdocus1;
    }

}
