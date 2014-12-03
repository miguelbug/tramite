/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//ESTE ES PARA EL REPORTE
package controller;

import bean.ConstanciaBean;
import bean.DocumentosBean;
import bean.OficioBean;
import dao.DocumentoDAO;
import dao.reporteDAO;
import daoimpl.DocumentoDaoImpl;
import daoimpl.reporteDaoImpl;
import maping.*;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.servlet.ServletContext;

//
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Usuario;
//

@ManagedBean
@ViewScoped
public class objxUnidadController implements Serializable {

    private static final long serialVersionUID = 8797816477254175229L;
    FacesContext context;
    ServletContext serveltcontext;
    private int anioActual;
    private int opcionFormato;
    private int mesInicio;
    private int mesFin;
    private int mesActual;
    private DocumentoDAO dd;
    private Date date1;
    private Date date2;
    private String USUARIO;
    private reporteDAO rpda;

    public objxUnidadController() {
        dd = new DocumentoDaoImpl();
        rpda = new reporteDaoImpl();
    }

    public void mostrarReporRegModPres() {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("prueba3");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("USUARIO", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);

        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void mostrarReporteNotasDeriv() {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("NotasDerivadas");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        //parametros.put("oficina","oficina oli");
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void ejecutarReporteDeriv() {
        try {
            System.out.println("IMPRIMIRÁ");
            mostrarReporteNotasDeriv();
            System.out.println("SE IMPRIMIIO");
            rpda.ActualizarTemporal();
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void mostrarReporteDocumentos() {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("reporteDocumentos");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        //parametros.put("oficina","oficina oli");
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fechain", getDate1());
        parametros.put("fechafin", getDate2());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void mostrarRepProveido() {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("repProveido");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        System.out.println(DocumentosBean.tranum);
        parametros.put("usuario", getUSUARIO());
        parametros.put("correlativo", DocumentosBean.tranum);
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void mostrarReporteFecha() {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("prueba3");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        //parametros.put("oficina","oficina oli");
        System.out.println(getDate1() + " - " + getDate2());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fecha", getDate1());
        parametros.put("fechain", getDate1());
        parametros.put("fechafin", getDate2());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void mostrarReporteSeguimiento() {
        String tramite = "";
        tramite = DocumentosBean.tranum;
        System.out.println(DocumentosBean.tranum);
        System.out.printf("PARAMETRO DEL TRAM NUM  %s", tramite);
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        System.out.println(DocumentosBean.tranum);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("RepSeguimiento");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fecha", getDate1());
        parametros.put("tramite", tramite);
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void mostrarReporteConstancia() {
        String tramite = "";
        tramite = DocumentosBean.tranum;
        System.out.println(DocumentosBean.tranum);
        System.out.printf("PARAMETRO DEL TRAM NUM  %s", tramite);
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        System.out.println(DocumentosBean.tranum);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("constancias");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("correlativo",ConstanciaBean.getCorrelativo2());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void mostrarReporteOficioCircular() {
        String tramite = "";
        System.out.printf("PARAMETRO DEL TRAM NUM  %s", OficioBean.getCorrelativo_exportar());
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        System.out.println(DocumentosBean.tranum);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("oficioCircular");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("correlativo",OficioBean.getCorrelativo_exportar());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void setAnioActual(int anioActual) {
        this.anioActual = anioActual;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public ServletContext getServeltcontext() {
        return serveltcontext;
    }

    public void setServeltcontext(ServletContext serveltcontext) {
        this.serveltcontext = serveltcontext;
    }

    public int getOpcionFormato() {
        return opcionFormato;
    }

    public void setOpcionFormato(int opcionFormato) {
        this.opcionFormato = opcionFormato;
    }

    public int getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(int mesInicio) {
        this.mesInicio = mesInicio;
    }

    public int getMesFin() {
        return mesFin;
    }

    public void setMesFin(int mesFin) {
        this.mesFin = mesFin;
    }

    public int getMesActual() {
        return mesActual;
    }

    public void setMesActual(int mesActual) {
        this.mesActual = mesActual;
    }

    public String getUSUARIO() {
        String nombre = "";
        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sesionUsuario");
        nombre = usu.getUsuNombre();
        return nombre;
    }

    public String getOficina() {
        String nomOfi = "";
        Oficina ofi;
        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sesionUsuario");
        System.out.println("acaaaaaaaaaaaaaaa");
        nomOfi = dd.getOficina(usu);
        System.out.println(nomOfi);
        return nomOfi;
    }

    public String getLogo() {
        String logo = "";
        logo = serveltcontext.getRealPath("/resources/img/" + "escudo_reporte" + ".jpg");
        return logo;
    }

    /* public String obtenerUsuario(){
	
     String nombre="";
	
     try{
     ExternalContext context = 
     FacesContext.getCurrentInstance().getExternalContext();
     HttpServletRequest request = 
     (HttpServletRequest) context.getRequest();
		
     Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");  
     nombre = usuario.getUsuNombre();
		
     }catch(Exception e){
		
     }
	
     return nombre;
	
	
     }*/
    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

}
