/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import dao.DocumentoDAO;
import dao.IndicadorDAO;
import dao.LoginDao;
import dao.OficioDAO;
import dao.SeguimientoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.IndicadorDaoImpl;
import daoimpl.LoginDaoImpl;
import daoimpl.OficioDaoImpl;
import daoimpl.SeguimientoDaoImpl;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import maping.DocusExtint;
import maping.Indicador;
import maping.Oficios;
import maping.Temporal;
import maping.TipoDocu;
import maping.TiposDocumentos;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import maping.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class DocumentosBean implements Serializable {

    private List destinos, otrosdocus3, docselec3, tiposdocus, oficiosConExp, otrosdocus2, docselec2, documentosOfiInt, documentos, seglista, docselec, seguimientolista, tdaux, tdaux2, documentosprov, dependenciasprov, detalprov, documentos_confirmados, documentos_corregir, otrosdocus, docusinternos;
    private DocumentoDAO dd;
    private boolean mostrar = false, hecho, nohecho, ver, no_ver;
    private Map<String, String> seleccion;
    private Usuario usu;
    private final FacesContext faceContext;
    private SeguimientoDAO sgd;
    private DerivarDAO deriv;
    private LoginDao log;
    private IndicadorDAO indicador;
    private Date fechaprov, aux, fecha;
    private String anio = "", siglasdocus, documento, origen, asunto_prov, origen_prov, asunto, tramnum, fechaaux, destino_ofic, correlativo_oficio, referencia, correlativo_proveido, destino_prov, codinterno, tipodestino, responsable;
    public static String tranum;
    private OficioDAO od;
    private String siglasdocus2;
    private String origen2;
    private String asunto2;
    private String destino2;
    private String correlativo2;
    private String auxanio2;
    private String partedocu2;
    private String arearesponsable2;
    private List areasResp;
    private String auxfecha;
    private String auxanio;
    private String fechadia2;
    private String fechahora;
    private boolean escogido2;
    private Date anio2;

    public DocumentosBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        tiposdocus = new ArrayList<String>();
        documentos = new ArrayList<Map<String, String>>();
        this.documentosOfiInt = new ArrayList<Map<String, String>>();
        this.documentos_corregir = new ArrayList<Map<String, String>>();
        seglista = new ArrayList<Map<String, String>>();
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        log = new LoginDaoImpl();
        indicador = new IndicadorDaoImpl();
        fechaprov = new Date();
        fecha = new Date();
        od = new OficioDaoImpl();
        documentosprov = new ArrayList<Map<String, String>>();
        documentos_confirmados = new ArrayList<Map<String, String>>();
        dependenciasprov = new ArrayList<Map<String, String>>();
        docusinternos = new ArrayList<Map<String, String>>();
        oficiosConExp = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        detalprov = new ArrayList<Map<String, String>>();
        tdaux = new ArrayList<Map<String, String>>();
        tdaux2 = new ArrayList<Map<String, String>>();
        boolean isdocumentos = (currentPage.lastIndexOf("documentos.xhtml") > -1);
        boolean isdocusinternos = (currentPage.lastIndexOf("documentos_respta.xhtml") > -1);
        boolean isdocumentosconfirm = (currentPage.lastIndexOf("documentos_perdidos.xhtml") > -1);
        boolean isdocumentoscorregir = (currentPage.lastIndexOf("documentos_corregir.xhtml") > -1);
        if (isdocumentos) {
            MostrarDocumentos();
            mostrar_documentosOfInt();
        } else {
            if (isdocusinternos) {
                System.out.println("INICIAL");
                MostrarDocusInternos();
                mostrarOficioConExp();
            } else {
                if (isdocumentosconfirm) {
                    this.MostrarDocumentosConfirmados();
                } else {
                    if (isdocumentoscorregir) {
                        this.Mostrar_corregir();
                    }
                }
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("DOCUMENTOS INTERNOS - OFICINAS OGPL")) {
            MostrarDocusInternos();
        }else{
            if (event.getTab().getTitle().equals("OFICIOS - OGPL")) {
                mostrarOficioConExp();
            }
        }
    }

    /////////////////////OFICIOS//////////////////////////
    public void onEdit3(RowEditEvent event) {
        String correlativo = String.valueOf(((HashMap) event.getObject()).get("correlativo"));
        String asunto = String.valueOf(((HashMap) event.getObject()).get("asunto"));
        String destino = String.valueOf(((HashMap) event.getObject()).get("destino"));
        String asignado = String.valueOf(((HashMap) event.getObject()).get("asignado"));
        System.out.println(correlativo + " " + asunto + " " + destino + " " + asignado);
        if (asunto.indexOf("SIN REFERENCIA -") != -1) {
            asunto = asunto.substring(17, asunto.length());
        }
        try {
            od.ActualizarOficio2(correlativo.substring(10, 15), asunto, destino, asignado);
            mostrarOficioConExp();
            FacesMessage message = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EDICION REALIZADA", String.valueOf(((HashMap) event.getObject()).get("correlativo")));
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            FacesMessage message = null;
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR NO SE ACTUALIZÓ", String.valueOf(((HashMap) event.getObject()).get("correlativo")));
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EDICION CANCELADA", String.valueOf(((HashMap) event.getObject()).get("correlativo")));
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void mostrarOficioConExp() {
        System.out.println("listando oficios");
        oficiosConExp.clear();
        try {
            List lista = new ArrayList();
            lista = od.getOficioUnicoExpediente();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", String.valueOf(obj[0]));
                listaaux.put("tramnum", String.valueOf(obj[1]));
                listaaux.put("fecha", String.valueOf(obj[2]));
                listaaux.put("asunto", String.valueOf(obj[3]) + " - " + String.valueOf(obj[4]));
                listaaux.put("origen", String.valueOf(obj[5]));
                listaaux.put("destino", String.valueOf(obj[6]));
                listaaux.put("asignado", String.valueOf(obj[7]));
                oficiosConExp.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void agregardestinos() {
        destinos = dd.getDependencias(tipodestino);
    }

    public void abrirDocumentoUnico() {
        arearesponsable2 = "OFICINA GENERAL DE PLANIFICACION";
        getAnio2();
        generarFecha4();
        generarCorrelativoOfiUnico();
        ObtenerTiposDocus();
        ObtenerAreasResp();
        siglasdocus2 = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        origen2 = dd.getOficina(usu);
        asunto2 = " ";
        destino2 = " ";
        this.partedocu2 = "Oficio N° " + correlativo2 + "-" + siglasdocus2 + "-" + auxanio2;
    }

    public void getAnio2() {
        System.out.println("entra getanio");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        anio2 = new Date();
        auxanio2 = sdf.format(anio2);
        System.out.println(auxanio2);
    }

    public void ObtenerAreasResp() {
        this.areasResp = od.getDependencias("1");
    }

    public void generarFecha4() {
        fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        auxfecha = sdf.format(fecha);
    }

    public void generarCorrelativoOfiUnico() {
        int corr = 0;
        String aux = "";
        try {
            if (auxanio2.equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getCorrelativoOficio(auxanio2));
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
        correlativo2 = aux;
    }

    public void ObtenerTiposDocus() {
        System.out.println("listando tipos docus");
        tiposdocus.clear();
        try {
            System.out.println("OBTENER TIPOS DOCUS");
            tiposdocus = od.gettipos("0");
        } catch (Exception e) {
            System.out.println("obtener tipo doccus ERROR 2");
            System.out.println(e.getMessage());
        }
        System.out.println(tiposdocus);
    }

    public void delete() {
        FacesMessage message = null;
        try {
            for (int i = 0; i < docselec3.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec3.get(i);
                od.DeleteOficio(hm.get("correlativo").toString().substring(10, 15));
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO", "SE HA(N) ELIMINADO EL(LOS) OFICIO(S)");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            mostrarOficioConExp();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!!!ERROR¡¡¡", "SE HA(N) ELIMINADO EL(LOS) OFICIO(S)");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println(e.getMessage());
            System.out.println("ERROR AL ELMINAR");
        }

    }

    public boolean validar_oficio(String documento) {

        boolean encuentra = false;

        for (int i = 0; i < oficiosConExp.size(); i++) {
            Map<String, String> hm = (HashMap<String, String>) oficiosConExp.get(i);
            if (hm.get("correlativo").toString().equals(documento)) {
                encuentra = true;
                break;
            }
        }
        return encuentra;
    }

    public void guardar_oficiounico() {
        FacesMessage message = null;
        String cadena = " N°" + " " + correlativo2 + " " + siglasdocus2 + " " + auxanio;
        if (validar_oficio(partedocu2)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "CORRELATIVO YA ESTA SIENDO USADO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {
            if (asunto2.equals(" ") || destino2.equals(" ")) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR TODOS LOS DATOS");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            } else {
                try {
                    Oficios ofi = new Oficios();
                    ofi.setAsuntoOficio(asunto2.toUpperCase());
                    ofi.setCorrelativoOficio(partedocu2.substring(10, 15));
                    ofi.setFechaOficio(fecha);
                    ofi.setDependenciaByCodigo(deriv.getDep(origen2));
                    ofi.setDependenciaByCodigo1(deriv.getDep(this.destino2));
                    ofi.setReferenciaOficio(null);
                    ofi.setTramiteDatos(null);
                    ofi.setUsuario(usu);
                    ofi.setResponsable(arearesponsable2);
                    System.out.println(escogido2);
                    ofi.setTiposDocumentos(od.getTipoDocu("OFICIO"));
                    dd.guardarOficio2(ofi);
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL OFICIO" + cadena);
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    mostrarOficioConExp();
                } catch (Exception e) {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL OFICIO");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    System.out.println("mal guardar oficiounico");
                    System.out.println(e.getMessage());
                }
            }
        }

        getAnio();
        generarFecha();
        this.abrirDocumentoUnico();
        this.asunto2 = "";
        this.arearesponsable2 = " ";
    }

    public void generarFecha() {
        System.out.println("entra fechaactual");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fecha = new Date();
        fechadia2 = "";
        fechahora = "";

        StringTokenizer tokens = new StringTokenizer(sdf.format(fecha), " ");
        while (tokens.hasMoreTokens()) {
            if (fechadia2.equals("")) {
                fechadia2 = tokens.nextToken();
            }
            if (fechahora.equals("")) {
                fechahora = tokens.nextToken();
            }
        }
        auxfecha = sdf.format(fecha);
        System.out.println("FECHAS: " + fechadia2 + "-" + fechahora);
    }
/////////////////////OFICIOS//////////////////////////

    public void mostrar_documentosOfInt() {
        System.out.println("listando documentos corregir");
        this.documentosOfiInt.clear();
        try {
            List lista = new ArrayList();
            lista = dd.mostrar_DocumentosOfInt();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("id", String.valueOf(obj[0]));
                listaaux.put("documento", String.valueOf(obj[1]));
                listaaux.put("tramnum", String.valueOf(obj[2]));
                listaaux.put("fecha", String.valueOf(obj[3]));
                listaaux.put("asunto", String.valueOf(obj[4]));
                listaaux.put("origen", String.valueOf(obj[5]));
                listaaux.put("destino", String.valueOf(obj[6]));
                listaaux.put("asignado", String.valueOf(obj[7]));
                listaaux.put("docu_princ", String.valueOf(obj[8]));
                listaaux.put("origen_princ", String.valueOf(obj[9]));
                documentosOfiInt.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generarCorrelativo_proveido() {
        int corr = 0;
        String aux = "";
        try {
            if (getAnio().equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getCorreProv(getAnio()));
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
        correlativo_proveido = aux;
    }

    public void mostrarProveido() {
        fechaprov = new Date();
        System.out.println(docselec);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY");
        fechaaux = sdf.format(fechaprov);
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        tramnum = obtenerNumeroTramite();
        generarCorrelativo_proveido();
        origen_prov = hm.get("origen").toString();
        destino_prov = hm.get("destino").toString();
        tranum = correlativo_proveido;
        siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        anio = sdf2.format(fechaprov);
    }

    public void limpiar_prov() {
        asunto = "";
        codinterno = "100392";
    }

    public void mostrarOficio() {
        referencia = "";
        for (int i = 0; i < docselec.size(); i++) {
            Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
            referencia = referencia + "/" + hm.get("observacion");
            responsable = hm.get("origen").toString();
        }

        fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY");
        fechaaux = sdf.format(fecha);
        tramnum = obtenerNumeroTramite();
        correlativo_oficio = generarCorrelativo();
        siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        anio = sdf1.format(fecha);

    }

    public void mostrarOficio_oficinaDocus() {
        Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
        fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY");
        fechaaux = sdf.format(fecha);
        tramnum = hm.get("documento");
        correlativo_oficio = generarCorrelativo();
        referencia = hm.get("asunto");
        siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        anio = sdf1.format(fecha);
        responsable = hm.get("origen").toString();

    }

    public void Eliminar() {
        FacesMessage message = null;
        try {
            Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
            dd.EliminarTramite(hm.get("numerotramite").toString(), hm.get("fenvio").toString().substring(0, 10), hm.get("movimiento").toString());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA ELIMINADO EL EXPEDIENTE");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            MostrarDocusInternos();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODODO ELIMINAR EL EXPEDIENTE");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println(e.getMessage());
        }
    }

    public void guardaroficio() {
        FacesMessage message = null;
        System.out.println("guardar oficio");
        if (tipodestino.equals(" ")) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR EL DESTINO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println("TIENEN QUE INGRESAR EL DESTINO");
        } else {
            System.out.println("NADA ES NULL");
            try {
                String ntram = "";
                int movi = 0;
                for (int i = 0; i < docselec.size(); i++) {
                    Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                    Oficios ofi = new Oficios();
                    ofi.setAsuntoOficio(asunto);
                    ofi.setFechaOficio(fecha);
                    ofi.setDependenciaByCodigo(deriv.getDep("OFICINA GENERAL DE PLANIFICACION"));
                    ofi.setDependenciaByCodigo1(deriv.getDep(this.destino_ofic));
                    ofi.setCorrelativoOficio(correlativo_oficio);
                    ofi.setReferenciaOficio(dd.getMotivo(hm.get("numerotramite"), dd.getTram_Fecha(hm.get("numerotramite"), hm.get("movimiento"))));
                    ofi.setTramiteDatos(deriv.getTramite(hm.get("numerotramite"), dd.getTram_Fecha(hm.get("numerotramite"), hm.get("movimiento"))));
                    ofi.setUsuario(usu);
                    ofi.setTiposDocumentos(deriv.getTipoDoc("OFICIO"));
                    ofi.setResponsable(responsable);
                    System.out.println("\\\\\\\\ENTRA A GUARDAR OFIICO¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                    dd.guardarOficio(ofi, tramnum, obtenerMovimiento());
                    System.out.println("\\\\\\\\SALE DE GUARDAR OFIICO¡¡¡¡¡¡¡¡¡¡¡¡¡¡");

                    System.out.println("ESTE ES EL DOCSELEC: " + docselec);
                    ntram = hm.get("numerotramite").toString();
                    movi = Integer.parseInt(hm.get("movimiento").toString());
                    System.out.println("confirmaar tramite entre");
                    deriv.Confirmar(ntram, movi, fecha);
                    deriv.cambiarEstado(ntram, hm.get("movimiento").toString());
                    System.out.println("confirmaar tramite sale");
                }
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL OFICIO N°:" + correlativo_oficio);
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                MostrarDocusInternos();
                limpiar();

            } catch (Exception e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL OFICION°:" + correlativo_oficio);
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                System.out.println("mal guardar oficio");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public void limpiar() {
        this.tramnum = "";
        this.referencia = "";
        this.asunto = "";
        this.fechaaux = "";
        this.destino_ofic = "";
    }

    public String getAnio() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(fecha);
    }

    public String generarCorrelativo() {
        int corr = 0;
        String aux = "";
        try {
            if (getAnio().equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getCorrelativoOficio(getAnio()));
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
                dd.guardarNuevoAnio(getAnio());
                System.out.println("lleno 2");
                corr = corr + 1;
                aux = "0000" + corr;
            }
        } catch (Exception e) {
            System.out.println("no lleno");
            corr = corr + 1;
            aux = "0000" + corr;
        }
        return aux;
    }

    public void getDependencias() {
        dependenciasprov = dd.getDependencias(tipodestino);
    }

    public void ObtenerDepIndic() {
        documentosprov = dd.getIndicadores();
        dependenciasprov = dd.getDependencias(tipodestino);
    }

    public String fechaactual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(fechaprov);

    }

    public void Mostrar_corregir() {
        System.out.println("listando documentos corregir");
        this.documentos_corregir.clear();
        try {
            List lista = new ArrayList();
            lista = dd.documentosCorregir();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("movimiento", String.valueOf(obj[1]));
                listaaux.put("fenvio", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("fing", String.valueOf(obj[4]));
                listaaux.put("destino", String.valueOf(obj[5]));
                listaaux.put("observacion", String.valueOf(obj[6]));
                listaaux.put("estado", String.valueOf(obj[7]));
                listaaux.put("indicador", String.valueOf(obj[8]));
                listaaux.put("estadodoc", String.valueOf(obj[9]));
                documentos_corregir.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void MostrarSeguimiento2(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista.clear();
        try {
            List lista = new ArrayList();
            lista = sgd.getSeguimientoGrande(tramnum);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[9];
            while (ite.hasNext()) {
                System.out.println("ola");
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("movimnum", String.valueOf(obj[1]));
                listaaux.put("origen", String.valueOf(obj[2]));
                listaaux.put("destino", String.valueOf(obj[3]));
                listaaux.put("fechaenvio", String.valueOf(obj[4]));
                listaaux.put("fechaingr", String.valueOf(obj[5]));
                listaaux.put("indicador", String.valueOf(obj[6]));
                listaaux.put("observacion", String.valueOf(obj[7]));
                listaaux.put("estado", String.valueOf(obj[8]));
                seguimientolista.add(listaaux);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String obtenerMovimiento() {
        String numerotramite = "";
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        numerotramite = hm.get("movimiento").toString();
        return numerotramite;
    }

    public String obtenerNumeroTramite() {
        String numerotramite = "";
        for (int i = 0; i < docselec.size(); i++) {
            Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
            numerotramite = numerotramite + "\n" + hm.get("numerotramite").toString();
        }
        return numerotramite;
    }

    public void RecorrerLista2() {
        System.out.println("entra a recorrer lista 2");
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        System.out.println(hm.get("numerotramite").toString());
        System.out.println("------entra---------");
        MostrarSeguimiento2(hm.get("numerotramite").toString());
        tranum = hm.get("numerotramite").toString();
        System.out.println("------sale-----------");
        docselec.clear();
    }

    public void MostrarDocusInternos() {
        System.out.println("listando documentos");
        docusinternos.clear();
        try {
            List lista = new ArrayList();
            lista = dd.getDocusInternos();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[11];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("movimiento", String.valueOf(obj[1]));
                listaaux.put("fenvio", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("fing", String.valueOf(obj[4]));
                listaaux.put("destino", String.valueOf(obj[5]));
                listaaux.put("observacion", String.valueOf(obj[6]));
                listaaux.put("estado", String.valueOf(obj[7]));
                listaaux.put("indicador", String.valueOf(obj[8]));
                listaaux.put("estadodoc", String.valueOf(obj[9]));
                listaaux.put("fechatramite", String.valueOf(obj[10]));
                docusinternos.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void MostrarDocumentosConfirmados() {
        System.out.println("listando documentos");
        this.documentos_confirmados.clear();
        try {
            List lista = new ArrayList();
            lista = dd.getDocumentos_Confirm();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("movimiento", String.valueOf(obj[1]));
                listaaux.put("origen", String.valueOf(obj[2]));
                listaaux.put("destino", String.valueOf(obj[3]));
                listaaux.put("fenvio", String.valueOf(obj[4]));
                listaaux.put("fing", String.valueOf(obj[5]));
                listaaux.put("indicador", String.valueOf(obj[6]));
                listaaux.put("observacion", String.valueOf(obj[7]));
                listaaux.put("docunomb", String.valueOf(obj[8]));
                listaaux.put("estado", String.valueOf(obj[9]));
                documentos_confirmados.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void MostrarDocumentos() {
        System.out.println("listando documentos");
        documentos.clear();
        try {
            List lista = new ArrayList();
            lista = dd.getDocumentos();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("movimiento", String.valueOf(obj[1]));
                listaaux.put("origen", String.valueOf(obj[2]));
                listaaux.put("destino", String.valueOf(obj[3]));
                listaaux.put("fenvio", String.valueOf(obj[4]));
                listaaux.put("fing", String.valueOf(obj[5]));
                listaaux.put("indicador", String.valueOf(obj[6]));
                listaaux.put("observacion", String.valueOf(obj[7]));
                listaaux.put("docunomb", String.valueOf(obj[8]));
                listaaux.put("estado", String.valueOf(obj[9]));
                documentos.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List Detalle_documento() {
        System.out.println("listando detalles");
        detalprov.clear();
        try {
            List lista = new ArrayList();
            lista = dd.getProveidos(seleccion.get("numerotramite").toString());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("nombredoc", String.valueOf(obj[1]) + " N°" + String.valueOf(obj[0]) + "-" + String.valueOf(obj[2]) + "-" + String.valueOf(obj[3]));
                listaaux.put("usuario", String.valueOf(obj[4]));
                listaaux.put("fecha", String.valueOf(obj[5]));
                detalprov.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return detalprov;
    }

    public String partir(String fecha) {
        StringTokenizer tokens = new StringTokenizer(fecha, " ");
        String[] cadena = new String[2];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            cadena[i] = tokens.nextToken();
            i++;
        }
        return cadena[0];
    }

    public List Detalles() {
        System.out.println("listando detalles");
        seglista.clear();
        int movimiento = -1;
        String valor;
        try {
            List lista = new ArrayList();
            System.out.println(seleccion.get("numerotramite").toString());
            if (seleccion.get("numerotramite").toString().indexOf("OGPL") != -1) {
                lista = dd.getDetalleOGPL(seleccion.get("numerotramite").toString(), partir(seleccion.get("fenvio").toString()));
            } else {
                if (seleccion.get("numerotramite").toString().indexOf("OGPL") == -1) {
                    valor = seleccion.get("movimiento");
                    if (Integer.parseInt(seleccion.get("movimiento")) > 1) {
                        movimiento = Integer.parseInt(seleccion.get("movimiento")) - 1;
                        valor = String.valueOf(movimiento);
                    }
                    lista = dd.getDetalleNoOGPL(seleccion.get("numerotramite").toString(), valor);
                }
            }
            Iterator ite = lista.iterator();
            Object obj[] = new Object[4];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("fecha", String.valueOf(obj[0]));
                listaaux.put("origen", String.valueOf(obj[1]));
                listaaux.put("descrip", String.valueOf(obj[2]));
                listaaux.put("documento", String.valueOf(obj[3]));
                seglista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return seglista;
    }

    public void ObtenerTipoDocu(String tramnum) {
        System.out.println("obtener tipo docu");
        tdaux2.clear();
        try {
            System.out.println("entra a gettipoDOcu");
            List lista = new ArrayList();
            lista = sgd.TiposDocus(tramnum);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("docunombre", String.valueOf(obj[1]));
                listaaux.put("docunumero", String.valueOf(obj[2]));
                listaaux.put("docupric", String.valueOf(obj[3]));
                listaaux.put("docusiglas", String.valueOf(obj[4]));
                listaaux.put("docuanio", String.valueOf(obj[5]));
                tdaux2.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ObtenerTramiteDato(String tramnum) {
        System.out.println("get tramite dato");
        tdaux.clear();
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            lista = sgd.tramiteDatos(tramnum);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[5];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("codigo", String.valueOf(obj[1]));
                listaaux.put("observacion", String.valueOf(obj[2]));
                listaaux.put("estado", String.valueOf(obj[3]));
                listaaux.put("usuario", String.valueOf(obj[4]));
                tdaux.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public TramiteDatos getTramiteDato(String tramnum) {
        ObtenerTramiteDato(tramnum);
        System.out.println("info tramite datos: " + tdaux);
        TramiteDatos nuevo = new TramiteDatos();
        maping.TramiteDatosId nuevoid = new maping.TramiteDatosId();
        try {
            for (int i = 0; i < tdaux.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) tdaux.get(i);
                System.out.println("llena numerotramite");
                nuevoid.setTramNum(hm.get("numerotramite").toString());
                nuevo.setId(nuevoid);
                System.out.println("llena codigo td");
                nuevo.setDependencia(deriv.getDependencia2(hm.get("codigo").toString()));
                System.out.println("llena obsv. td");
                nuevo.setTramObs(hm.get("observacion").toString());
                System.out.println("llena estado td");
                nuevo.setEstaDescrip(hm.get("estado").toString());
                System.out.println("llena usuario td");
                nuevo.setUsuario(usu);
            }
            System.out.println("Se obtiene numero tramite:" + nuevoid.getTramNum());
        } catch (Exception e) {
            System.out.println("error get tramitedatos");
            System.out.println(e.getMessage());
        }
        return nuevo;
    }

    public TipoDocu getTipodocumento(String tramnum, TramiteDatos td) {
        ObtenerTipoDocu(tramnum);
        System.out.println("info tipo docu: " + tdaux2);
        TipoDocu tipo = new TipoDocu();
        try {
            for (int i = 0; i < tdaux2.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) tdaux2.get(i);
                System.out.println("numero tramite tipodocu");
                tipo.setTramiteDatos(td);
                System.out.println("docu nombre tipodocu");
                tipo.setDocuNombre(hm.get("docunombre").toString());
                System.out.println("docunumero tipodocu");
                tipo.setDocuNum(hm.get("docunumero").toString());
                System.out.println("docupric tipodocu");
                tipo.setDocuPric(hm.get("docupric").toString());
                System.out.println("docusiglas tipodocu");
                tipo.setDocuSiglas(hm.get("docusiglas").toString());
                System.out.println("docuanio tipodocu");
                tipo.setDocuAnio(hm.get("docuanio").toString());
            }
            System.out.println("tipo docu info: " + tipo.getDocuNum() + "-" + tipo.getDocuNombre() + "-" + tipo.getDocuAnio());
        } catch (Exception e) {
            System.out.println("mal get tipodocumento");
            System.out.println(e.getMessage());
        }
        return tipo;
    }

    public void Confirmar() {
        //FacesMessage message = null;
        try {
            System.out.println("ENTRA A CONFIRMAR seguimiento");
            String ntram = "";
            int movi = 0;
            String aux = "";
            int contador = sgd.getContadorTemporal();
            int cont = 0;
            if (contador == 0) {
                cont = 1;
            } else {
                cont = contador;
                cont++;
            }
            for (int i = 0; i < docselec.size(); i++) {
                System.out.println("entra al bucle for");
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                TramiteMovimiento movimiento = new TramiteMovimiento();
                TramiteDatos td = new TramiteDatos();
                TipoDocu tdoc = new TipoDocu();
                Temporal t = new Temporal();
                maping.TramiteDatosId nuevoid = new maping.TramiteDatosId();
                aux = hm.get("numerotramite").toString();
                t.setTramNum(hm.get("numerotramite").toString());
                if (aux.indexOf("OGPL") != -1) {
                    System.out.println("ENTRA A OGPL");
                    td = getTramiteDato(aux);
                    movimiento.setTramiteDatos(td);
                    movimiento.setMoviNum(Short.parseShort(hm.get("movimiento").toString()));
                    movimiento.setEstaNombre(hm.get("estado").toString());
                    movimiento.setDependenciaByCodigo(deriv.getDependencia(hm.get("origen").toString()));
                    t.setOrigen(hm.get("origen").toString());
                    movimiento.setDependenciaByCodigo1(deriv.getDependencia(hm.get("destino").toString()));
                    movimiento.setUsuario(getusuario(deriv.getCodigoDep(hm.get("destino").toString())));
                    t.setDestino(hm.get("destino").toString());
                    System.out.println("entra a fecha envio");
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date nf = new Date();
                    System.out.println("LA FECHA DE ENVIO OGPL ES: " + hm.get("fenvio").toString());
                    nf = formato.parse(hm.get("fenvio").toString());
                    movimiento.setFechaEnvio(nf);
                    td.getId().setTramFecha(nf);
                    t.setFecha(nf);
                    System.out.println("sale fecha envio");
                    System.out.println("entra a fecha ing");
                    if (hm.get("fing").toString().equals(" ")) {
                        movimiento.setFechaIngr(null);
                    } else {
                        SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date nf2 = new Date();
                        nf2 = formato2.parse(hm.get("fing").toString());
                        movimiento.setFechaIngr(nf2);
                    }
                    System.out.println("sale fecha fing");
                    System.out.println("entra a indicador");
                    movimiento.setIndicador(deriv.getIndic(hm.get("indicador").toString()));
                    System.out.println("sle indicador");
                    movimiento.setMoviObs(hm.get("observacion").toString());
                    t.setAsunto(hm.get("observacion").toString());
                    t.setTipodocumentos(hm.get("docunomb").toString());
                    tdoc = getTipodocumento(aux, td);
                    //}
                } else {
                    if (aux.indexOf("OGPL") == -1) {
                        System.out.println("ENTRA A DEPENDENCIAS EXTERNAS");
                        nuevoid.setTramNum(hm.get("numerotramite").toString());
                        t.setTramNum(hm.get("numerotramite").toString());
                        System.out.println("entra a fecha envio");
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date nf = new Date();
                        nf = formato.parse(hm.get("fenvio").toString());
                        nuevoid.setTramFecha(nf);
                        td.setId(nuevoid);
                        System.out.println("sale fecha envio");
                        t.setFecha(nf);
                        td.setTramObs(hm.get("observacion").toString());
                        t.setAsunto(hm.get("observacion").toString());
                        td.setEstaDescrip(hm.get("estado").toString());
                        td.setDependencia(deriv.getDependencia(hm.get("origen").toString()));
                        t.setOrigen(hm.get("origen").toString());
                        movimiento.setTramiteDatos(td);
                        movimiento.setMoviNum(Short.parseShort(hm.get("movimiento").toString()));
                        movimiento.setEstaNombre(hm.get("estado").toString());
                        movimiento.setDependenciaByCodigo(deriv.getDependencia(hm.get("origen").toString()));
                        movimiento.setDependenciaByCodigo1(deriv.getDependencia(hm.get("destino").toString()));
                        movimiento.setUsuario(getusuario((deriv.getCodigoDep(hm.get("destino").toString()))));
                        t.setDestino(hm.get("destino").toString());
                        System.out.println("entra a fecha envio");
                        SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date nf2 = new Date();
                        nf2 = formato2.parse(hm.get("fenvio").toString());
                        System.out.println("LA FECHA DE ENVIO NO OGPL ES: " + hm.get("fenvio").toString());
                        movimiento.setFechaEnvio(nf2);
                        System.out.println("sale fecha envio");
                        System.out.println("entra a fecha ing");
                        if (hm.get("fing").toString().equals(" ")) {
                            movimiento.setFechaIngr(null);
                        } else {
                            SimpleDateFormat formato3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date nf3 = new Date();
                            nf3 = formato3.parse(hm.get("fing").toString());
                            movimiento.setFechaIngr(nf);
                        }
                        System.out.println("sale fecha fing");
                        td.setUsuario(usu);
                        tdoc.setTramiteDatos(td);
                        Date fecha2 = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                        tdoc.setDocuAnio(sdf.format(fecha2));
                        tdoc.setDocuNombre("SIN NOMBRE");
                        tdoc.setDocuNum("SIN NUMERO");
                        tdoc.setDocuSiglas(siglas(hm.get("numerotramite").toString()));
                        tdoc.setDocuPric("1");
                        t.setTipodocumentos(hm.get("docunomb").toString());
                    }
                    System.out.println("entra a indicador");
                    movimiento.setIndicador(deriv.getIndic(hm.get("indicador").toString()));
                    System.out.println("sale indicador");
                    movimiento.setMoviObs(hm.get("observacion").toString());
                }
                movimiento.setEstado("0");
                t.setSiglas(deriv.getSiglas2(dd.getOficina(usu)));
                System.out.println("---------entra a guardar tramite dato---------");
                sgd.GuadarTramiteDatos(td, tdoc);/////////////////tramitedatos
                System.out.println("---------sale de guardar tramite dato---------");
                System.out.println("---------entra a guardar tramite movimiento---------");
                t.setImpreso(BigDecimal.valueOf(1));
                t.setContador(String.valueOf(cont));
                sgd.temporal(t);////////////temporal
                movimiento.setEstadConfrirm("SIN CONFIRMAR");
                sgd.GuardarTramiteMovimiento(movimiento);//////////tramitemovimiento
                System.out.println("---------sale de guardar tramite movimiento---------");
                ntram = "";
                hecho = true;
                nohecho = false;

            }
            MostrarDocumentos();
        } catch (Exception e) {
            System.out.println("ERROR CONFIRMAR");
            System.out.println(e.getMessage());
            e.printStackTrace();
            nohecho = true;
            hecho = false;
            //message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Problemas en el confirmado");
            //RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

    }

    public String siglas(String tramnum) {
        StringTokenizer tokens = new StringTokenizer(tramnum, "-");
        int j = 0;
        String cadena[] = new String[3];
        while (tokens.hasMoreTokens()) {
            cadena[j] = tokens.nextToken();
            j++;
        }
        return cadena[1];
    }

    public Usuario getusuario(String nombofic) {
        Usuario usu = new Usuario();
        List lista = new ArrayList();
        lista = deriv.listandoUsuario(nombofic);
        Iterator ite = lista.iterator();
        Object obj[] = new Object[5];
        while (ite.hasNext()) {
            obj = (Object[]) ite.next();
            usu.setUsu(String.valueOf(obj[0]));
            usu.setUsuNombre(String.valueOf(obj[1]));
            usu.setClave(String.valueOf(obj[2]));
            usu.setEstado(String.valueOf(obj[3]));
            usu.setOficina(deriv.getOficina(String.valueOf(obj[4])));
        }
        return usu;
    }

    public void cambiar() {
        mostrar = true;
    }

    public List getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List documentos) {
        this.documentos = documentos;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public List getOtrosdocus() {
        return otrosdocus;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public void setOtrosdocus(List otrosdocus) {
        this.otrosdocus = otrosdocus;
    }

    public List getSeglista() {
        return seglista;
    }

    public void setSeglista(List seglista) {
        this.seglista = seglista;
    }

    public Map<String, String> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Map<String, String> seleccion) {
        this.seleccion = seleccion;
    }

    public List getDocselec() {
        return docselec;
    }

    public void setDocselec(List docselec) {
        this.docselec = docselec;
    }

    public List getSeguimientolista() {
        return seguimientolista;
    }

    public void setSeguimientolista(List seguimientolista) {
        this.seguimientolista = seguimientolista;
    }

    public SeguimientoDAO getSgd() {
        return sgd;
    }

    public void setSgd(SeguimientoDAO sgd) {
        this.sgd = sgd;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }

    public List getTdaux() {
        return tdaux;
    }

    public void setTdaux(List tdaux) {
        this.tdaux = tdaux;
    }

    public List getTdaux2() {
        return tdaux2;
    }

    public void setTdaux2(List tdaux2) {
        this.tdaux2 = tdaux2;
    }

    public LoginDao getLog() {
        return log;
    }

    public void setLog(LoginDao log) {
        this.log = log;
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

    public Date getFechaprov() {
        return fechaprov;
    }

    public void setFechaprov(Date fechaprov) {
        this.fechaprov = fechaprov;
    }

    public List getDocumentosprov() {
        return documentosprov;
    }

    public void setDocumentosprov(List documentosprov) {
        this.documentosprov = documentosprov;
    }

    public List getDependenciasprov() {
        return dependenciasprov;
    }

    public void setDependenciasprov(List dependenciasprov) {
        this.dependenciasprov = dependenciasprov;
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

    public Date getAux() {
        return aux;
    }

    public void setAux(Date aux) {
        this.aux = aux;
    }

    public static String getTranum() {
        return tranum;
    }

    public static void setTranum(String tranum) {
        DocumentosBean.tranum = tranum;
    }

    public List getDetalprov() {
        return detalprov;
    }

    public void setDetalprov(List detalprov) {
        this.detalprov = detalprov;
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

    public String getFechaaux() {
        return fechaaux;
    }

    public void setFechaaux(String fechaaux) {
        this.fechaaux = fechaaux;
    }

    public String getDestino_ofic() {
        return destino_ofic;
    }

    public void setDestino_ofic(String destino_ofic) {
        this.destino_ofic = destino_ofic;
    }

    public String getCorrelativo_oficio() {
        return correlativo_oficio;
    }

    public void setCorrelativo_oficio(String correlativo_oficio) {
        this.correlativo_oficio = correlativo_oficio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean isHecho() {
        return hecho;
    }

    public void setHecho(boolean hecho) {
        this.hecho = hecho;
    }

    public boolean isNohecho() {
        return nohecho;
    }

    public void setNohecho(boolean nohecho) {
        this.nohecho = nohecho;
    }

    public List getDocumentos_confirmados() {
        return documentos_confirmados;
    }

    public void setDocumentos_confirmados(List documentos_confirmados) {
        this.documentos_confirmados = documentos_confirmados;
    }

    public String getCorrelativo_proveido() {
        return correlativo_proveido;
    }

    public void setCorrelativo_proveido(String correlativo_proveido) {
        this.correlativo_proveido = correlativo_proveido;
    }

    public String getDestino_prov() {
        return destino_prov;
    }

    public void setDestino_prov(String destino_prov) {
        this.destino_prov = destino_prov;
    }

    public String getCodinterno() {
        return codinterno;
    }

    public void setCodinterno(String codinterno) {
        this.codinterno = codinterno;
    }

    public List getDocumentos_corregir() {
        return documentos_corregir;
    }

    public void setDocumentos_corregir(List documentos_corregir) {
        this.documentos_corregir = documentos_corregir;
    }

    public String getAsunto_prov() {
        return asunto_prov;
    }

    public void setAsunto_prov(String asunto_prov) {
        this.asunto_prov = asunto_prov;
    }

    public String getOrigen_prov() {
        return origen_prov;
    }

    public void setOrigen_prov(String origen_prov) {
        this.origen_prov = origen_prov;
    }

    public String getTipodestino() {
        return tipodestino;
    }

    public void setTipodestino(String tipodestino) {
        this.tipodestino = tipodestino;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public boolean isNo_ver() {
        return no_ver;
    }

    public void setNo_ver(boolean no_ver) {
        this.no_ver = no_ver;
    }

    public String getSiglasdocus() {
        return siglasdocus;
    }

    public void setSiglasdocus(String siglasdocus) {
        this.siglasdocus = siglasdocus;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public List getDocumentosOfiInt() {
        return documentosOfiInt;
    }

    public void setDocumentosOfiInt(List documentosOfiInt) {
        this.documentosOfiInt = documentosOfiInt;
    }

    public List getOtrosdocus2() {
        return otrosdocus2;
    }

    public void setOtrosdocus2(List otrosdocus2) {
        this.otrosdocus2 = otrosdocus2;
    }

    public List getDocselec2() {
        return docselec2;
    }

    public void setDocselec2(List docselec2) {
        this.docselec2 = docselec2;
    }

    public IndicadorDAO getIndicador() {
        return indicador;
    }

    public void setIndicador(IndicadorDAO indicador) {
        this.indicador = indicador;
    }

    public List getOficiosConExp() {
        return oficiosConExp;
    }

    public void setOficiosConExp(List oficiosConExp) {
        this.oficiosConExp = oficiosConExp;
    }

    public List getOtrosdocus3() {
        return otrosdocus3;
    }

    public void setOtrosdocus3(List otrosdocus3) {
        this.otrosdocus3 = otrosdocus3;
    }

    public List getDocselec3() {
        return docselec3;
    }

    public void setDocselec3(List docselec3) {
        this.docselec3 = docselec3;
    }

    public List getTiposdocus() {
        return tiposdocus;
    }

    public void setTiposdocus(List tiposdocus) {
        this.tiposdocus = tiposdocus;
    }

    public OficioDAO getOd() {
        return od;
    }

    public void setOd(OficioDAO od) {
        this.od = od;
    }

    public String getSiglasdocus2() {
        return siglasdocus2;
    }

    public void setSiglasdocus2(String siglasdocus2) {
        this.siglasdocus2 = siglasdocus2;
    }

    public String getOrigen2() {
        return origen2;
    }

    public void setOrigen2(String origen2) {
        this.origen2 = origen2;
    }

    public String getAsunto2() {
        return asunto2;
    }

    public void setAsunto2(String asunto2) {
        this.asunto2 = asunto2;
    }

    public String getDestino2() {
        return destino2;
    }

    public void setDestino2(String destino2) {
        this.destino2 = destino2;
    }

    public String getCorrelativo2() {
        return correlativo2;
    }

    public void setCorrelativo2(String correlativo2) {
        this.correlativo2 = correlativo2;
    }

    public String getAuxanio2() {
        return auxanio2;
    }

    public void setAuxanio2(String auxanio2) {
        this.auxanio2 = auxanio2;
    }

    public String getPartedocu2() {
        return partedocu2;
    }

    public void setPartedocu2(String partedocu2) {
        this.partedocu2 = partedocu2;
    }

    public String getArearesponsable2() {
        return arearesponsable2;
    }

    public void setArearesponsable2(String arearesponsable2) {
        this.arearesponsable2 = arearesponsable2;
    }

    public List getAreasResp() {
        return areasResp;
    }

    public void setAreasResp(List areasResp) {
        this.areasResp = areasResp;
    }

    public String getAuxfecha() {
        return auxfecha;
    }

    public void setAuxfecha(String auxfecha) {
        this.auxfecha = auxfecha;
    }

    public String getAuxanio() {
        return auxanio;
    }

    public void setAuxanio(String auxanio) {
        this.auxanio = auxanio;
    }

    public String getFechadia2() {
        return fechadia2;
    }

    public void setFechadia2(String fechadia2) {
        this.fechadia2 = fechadia2;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public boolean isEscogido2() {
        return escogido2;
    }

    public void setEscogido2(boolean escogido2) {
        this.escogido2 = escogido2;
    }

    public void setAnio2(Date anio2) {
        this.anio2 = anio2;
    }

    public List getDestinos() {
        return destinos;
    }

    public void setDestinos(List destinos) {
        this.destinos = destinos;
    }

}
