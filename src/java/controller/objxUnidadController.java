/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//ESTE ES PARA EL REPORTE
package controller;

import bean.ConstanciaBean;
import bean.DocumentoUsuarioBean;
import bean.DocumentosBean;
import bean.DocusExternosBean;
import bean.OficioBean;
import dao.DocumentoDAO;
import dao.reporteDAO;
import daoimpl.DocumentoDaoImpl;
import daoimpl.reporteDaoImpl;
import maping.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.servlet.ServletContext;
import bean.ProveidosInternosBean;
import dao.DerivarDAO;
import dao.OficioDAO;
import dao.SeguimientoDAO;
import dao.TemporaldiDao;
import daoimpl.DerivarDaoImpl;
import daoimpl.OficioDaoImpl;
import daoimpl.SeguimientoDaoImpl;
import daoimpl.TemporalDiDaoImpl;
import java.sql.SQLException;
import java.text.ParseException;
//
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Usuario;
import maping.Temporalcargos;
import org.primefaces.context.RequestContext;
//

@ManagedBean
@ViewScoped
public class objxUnidadController implements Serializable {

    private static final long serialVersionUID = 8797816477254175229L;
    FacesContext context;
    ServletContext serveltcontext;
    private int anioActual;
    private String docueliminar;
    private String ideliminar;
    private int opcionFormato;
    private int mesInicio;
    private int mesFin;
    private int mesActual;
    private DocumentoDAO dd;
    private DerivarDAO deriv;
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private Date date5;
    private Date date6;
    private String USUARIO;
    private reporteDAO rpda;
    private String tipodocumento, tipodocumento1, tipodocumento2;
    private List docselec, docselec1, docselec2, docselec3, docselec4;
    private TemporaldiDao tdi;
    private String loteinput;
    private SeguimientoDAO sgd;
    private OficioDAO ofi;

    public objxUnidadController() {
        dd = new DocumentoDaoImpl();
        rpda = new reporteDaoImpl();
        tdi = new TemporalDiDaoImpl();
        deriv = new DerivarDaoImpl();
        sgd = new SeguimientoDaoImpl();
        ofi = new OficioDaoImpl();
    }

    

    

    public void delete() {
        for (int i = 0; i < docselec1.size(); i++) {
            Map<String, String> hm = (HashMap<String, String>) docselec1.get(i);
            String cadena=ofi.getTramNUm_TramFecha(hm.get("iddoc"));
            String cadena2=ofi.getTramNUm_TramFecha2(hm.get("iddoc"));
            ofi.EliminarDocumentosInternosOficinas(hm.get("iddoc"));
            ofi.ELiminarTramite(cadena);
            
            ofi.ActualizarTramite(cadena2);
        }
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO", "SE HA(N) ELIMINADO EL(LOS) DOCUMENTO(S)");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void GuardarDatos() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < docselec.size(); i++) {
            TemporalDi tldi = new TemporalDi();
            Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
            tldi.setAsunto(hm.get("asunto").toString());
            tldi.setFechaReg(formatter.parse(hm.get("fechareg").toString()));
            tldi.setNombDocu(hm.get("numerotramite").toString());
            tldi.setTramNum(hm.get("tramnum").toString());
            System.out.println(hm.get("tramnum").toString());
            tldi.setUsuario(hm.get("asignado").toString());

            tldi.setImpreso("1");
            tldi.setReimpreso("0");
            tdi.guardarTemporalDi(tldi);
        }
    }

    public void guardarDatos2() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < docselec1.size(); i++) {
            TemporalUser tluser = new TemporalUser();
            Map<String, String> hm = (HashMap<String, String>) docselec1.get(i);
            tluser.setDocumento(hm.get("documento").toString());
            tluser.setTramNum(hm.get("expediente").toString());
            tluser.setDocumentoPrinc(hm.get("docuprinc").toString());
            tluser.setFecha(formatter.parse(hm.get("fecha").toString()));
            tluser.setOrigenPrinc(hm.get("origenprinc").toString());
            tluser.setCodigo(dd.getFlag(hm.get("origenprinc").toString()));
            tluser.setImpreso("1");
            tluser.setReimpreso("0");
            tdi.guardarTemporalUser(tluser);

        }
    }

    public void guardarDatos3() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < docselec1.size(); i++) {
            Temporalcargos tc = new Temporalcargos();
            Map<String, String> hm = (HashMap<String, String>) docselec1.get(i);
            tc.setDocumento(hm.get("documento").toString());
            tc.setFecha(formatter.parse(hm.get("fecha").toString()));
            tc.setNumexped(hm.get("expediente").toString());
            tc.setAsunto(hm.get("asunto").toString());
            tc.setAsignado(hm.get("asignado").toString());
            tc.setImpreso("1");
            tc.setReimpreso("0");
            tc.setUsu(getUsu());
            tc.setDestino(hm.get("destino").toString());
            tdi.guardarCargos(tc);
        }
    }

    public void ImpresionSeleccionadosUser() throws ParseException, SQLException {
        guardarDatos2();
        mostrarReporSeleccionadosUser();
        tdi.actualizarTemporalUser();
    }

    public void ImpresionCargos() throws ParseException, SQLException {
        guardarDatos3();
        mostrarCargos();
        tdi.actualizarTemporalCargo();
    }

    public void ImpresionAsignado() throws ParseException, SQLException {
        mostrarAsignados();
    }

    public void mostrarAsignados() throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("CargosDocAreasAsignado");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("usu", getUsu());
        parametros.put("asignado", getUSUARIO());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);

        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarCargos() throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("CargosDocAreas");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("usu", getUsu());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);

        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void ImpresionSeleccionados() throws ParseException, SQLException {
        GuardarDatos();
        mostrarReporSeleccionados();
        tdi.actualizarTemporalDi();
    }

    public void abrirReimpresion() {
        this.loteinput = String.valueOf(sgd.getContadorTemporal());
    }

    public void mostrarReimpresion() throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("Reimpresion");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("lote", loteinput);
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);

        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporSeleccionadosUser() throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        //repor = ReporteController.getInstance("reporteDocumentosSeleccionadosUser");
        repor = ReporteController.getInstance("seleccionados2");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("USUARIO", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("usu", getUsu());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);

        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporSeleccionados() throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("reporteDocumentosSeleccionados");
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
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporRegModPres() throws SQLException {
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
        categoriaServicio.CerrandoConexion();
    }

    public String getFechaDerivado() {
        String fecha = this.rpda.getfechaderivado(DocumentoUsuarioBean.tramnum_exportar, DocumentoUsuarioBean.movimiento_exportar);
        return fecha;
    }

    public void mostrarReporteNotasDeriv() throws SQLException {

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
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
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

    public void mostrarReporteDocumentos() throws SQLException {

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
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarRepProveido3() throws SQLException {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("RepProveido");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        System.out.println(DocumentosBean.tranum);
        parametros.put("usuario", this.getUsu());
        parametros.put("correlativo", DocusExternosBean.correlativo_impresion);
        parametros.put("fecha", partir(DocusExternosBean.fecha_auxiliar));
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("jefe", dd.getJefe());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public String partir(String nombre) {
        String[] cadena = new String[2];
        int i = 0;
        StringTokenizer tokens = new StringTokenizer(nombre);
        while (tokens.hasMoreTokens()) {
            cadena[i] = tokens.nextToken();
            i++;
        }
        return cadena[0];

    }

    public void mostrarRepProveido2() throws SQLException {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("RepProveido");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        System.out.println(DocumentosBean.tranum);
        parametros.put("usuario", this.getUsu());
        parametros.put("correlativo", ProveidosInternosBean.correlativo_impresion);
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fecha", partir(ProveidosInternosBean.fecha_auxiliar));
        parametros.put("jefe", dd.getJefe());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarRepProveido() throws SQLException {

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
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarRepOficio() throws SQLException {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("reporte_Oficio");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        System.out.println(DocumentosBean.tranum);
        parametros.put("usuario", getUSUARIO());
        parametros.put("correlativo", OficioBean.getCorrelativo_exportar());
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
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporteFecha3() throws SQLException {

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(sdf.format(date1));
        System.out.println(sdf.format(date2));
        System.out.println(tipodocumento);
        System.out.println(getUSUARIO());
        parametros.put("usuario", this.getUsu());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fechain", sdf.format(date5));
        parametros.put("fechafin", sdf.format(date6));
        parametros.put("usuario", getUSUARIO());
        parametros.put("tipo", tipodocumento);
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporteFecha2() throws SQLException {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sesionUsuario");
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("reporteDocumentosUserFechas");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        String siglas = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        boolean rpt = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fechain", sdf.format(date3));
        parametros.put("fechafin", sdf.format(date4));
        parametros.put("usuario", this.getUsu());
        parametros.put("tipo", tipodocumento1);
        parametros.put("siglas", siglas);
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporteFecha() throws SQLException {

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(sdf.format(date1));
        System.out.println(sdf.format(date2));
        System.out.println(tipodocumento);
        System.out.println(getUSUARIO());
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fechain", sdf.format(date1));
        parametros.put("fechafin", sdf.format(date2));
        parametros.put("usuario", getUSUARIO());
        parametros.put("tipo", tipodocumento);
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    //////////////////////////////////////
    public void mostrarReporteTodos() throws SQLException {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sesionUsuario");
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("reporteDocumentosTodos");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        String siglas = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("usuario", getUSUARIO());
        parametros.put("siglas", siglas);
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporteTodos2() throws SQLException {

        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sesionUsuario");
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("reporteDocumentosUserTodos2");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        String siglas = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        boolean rpt = false;
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("usuario", this.getUsu());
        parametros.put("siglas", siglas);
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    /////////////////////////////////////
    public void mostrarReporteSeguimiento() throws SQLException {
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
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporteConstancia() throws SQLException {
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
        parametros.put("correlativo", ConstanciaBean.getCorrelativo2());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void mostrarReporteOficioCircular2() throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("oficioCircular2");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        Map<String, String> hm = (HashMap<String, String>) docselec3.get(0);
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("correlativo", hm.get("documento").substring(19,24));
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }
    
    public void mostrarReporteOficioCircular() throws SQLException {
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
        repor = ReporteController.getInstance("oficioCircular2");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        parametros.put("usuario", getUSUARIO());
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("correlativo", OficioBean.getCorrelativo_exportar());
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
    }

    public void ReimprimirProveido(String docu, String fecha) throws SQLException {
        context = FacesContext.getCurrentInstance();
        serveltcontext = (ServletContext) context.getExternalContext().getContext();
        ReporteController repor;
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.clear();
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("context" + context);
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();
        System.out.println("sc = " + sc.getRealPath("/reportes/"));
        repor = ReporteController.getInstance("repProveido2");
        categoriaServicio categoriaServicio = new categoriaServicio();
        repor.setConexion(categoriaServicio.getConexion());
        repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
        FacesMessage message = null;
        boolean rpt = false;
        System.out.println("PROVEIDO A EXPORTAR " + docu);
        System.out.println("FECHA A EXPORTAR " + partir(fecha));
        parametros.put("correlativo", docu);
        parametros.put("logo", getLogo());
        parametros.put("oficina", getOficina());
        parametros.put("fecha", partir(fecha));
        parametros.put("jefe", dd.getJefe());
        // parametros.put("USUARIO","miguel" ); 
        repor.addMapParam(parametros);
        rpt = repor.ejecutaReporte(context, serveltcontext);
        if (!rpt && message == null) {
            //no tiene hojas	
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "No hay datos para generar reporte");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        categoriaServicio.CerrandoConexion();
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
        System.out.println(nombre);
        return nombre;
    }

    public String getUsu() {
        String nombre = "";
        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Usuario usu = (Usuario) session.getAttribute("sesionUsuario");
        nombre = usu.getUsu();
        return nombre;
    }

    public String getOficina() {
        String nomOfi = "";
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

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public Date getDate5() {
        return date5;
    }

    public void setDate5(Date date5) {
        this.date5 = date5;
    }

    public Date getDate6() {
        return date6;
    }

    public void setDate6(Date date6) {
        this.date6 = date6;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public reporteDAO getRpda() {
        return rpda;
    }

    public void setRpda(reporteDAO rpda) {
        this.rpda = rpda;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public List getDocselec() {
        return docselec;
    }

    public void setDocselec(List docselec) {
        this.docselec = docselec;
    }

    public String getTipodocumento1() {
        return tipodocumento1;
    }

    public void setTipodocumento1(String tipodocumento1) {
        this.tipodocumento1 = tipodocumento1;
    }

    public String getTipodocumento2() {
        return tipodocumento2;
    }

    public void setTipodocumento2(String tipodocumento2) {
        this.tipodocumento2 = tipodocumento2;
    }

    public TemporaldiDao getTdi() {
        return tdi;
    }

    public void setTdi(TemporaldiDao tdi) {
        this.tdi = tdi;
    }

    public List getDocselec1() {
        return docselec1;
    }

    public void setDocselec1(List docselec1) {
        this.docselec1 = docselec1;
    }

    public List getDocselec2() {
        return docselec2;
    }

    public void setDocselec2(List docselec2) {
        this.docselec2 = docselec2;
    }

    public List getDocselec3() {
        return docselec3;
    }

    public void setDocselec3(List docselec3) {
        this.docselec3 = docselec3;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }

    public String getLoteinput() {
        return loteinput;
    }

    public void setLoteinput(String loteinput) {
        this.loteinput = loteinput;
    }

    public SeguimientoDAO getSgd() {
        return sgd;
    }

    public void setSgd(SeguimientoDAO sgd) {
        this.sgd = sgd;
    }

    public List getDocselec4() {
        return docselec4;
    }

    public void setDocselec4(List docselec4) {
        this.docselec4 = docselec4;
    }

    public OficioDAO getOfi() {
        return ofi;
    }

    public void setOfi(OficioDAO ofi) {
        this.ofi = ofi;
    }

    public String getDocueliminar() {
        return docueliminar;
    }

    public void setDocueliminar(String docueliminar) {
        this.docueliminar = docueliminar;
    }

    public String getIdeliminar() {
        return ideliminar;
    }

    public void setIdeliminar(String ideliminar) {
        this.ideliminar = ideliminar;
    }

}
