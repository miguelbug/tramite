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
import dao.LoginDao;
import dao.SeguimientoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.LoginDaoImpl;
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
import maping.DocusExt;
import maping.DocusExtint;
import maping.Oficios;
import maping.Proveido;
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

    private List documentos, seglista, docselec, seguimientolista, tdaux, tdaux2, documentosprov, dependenciasprov, detalprov, documentos_confirmados, documentos_corregir, otrosdocus, docusinternos;
    private DocumentoDAO dd;
    private boolean mostrar = false, hecho, nohecho,ver,no_ver;
    private Map<String, String> seleccion;
    private Usuario usu;
    private final FacesContext faceContext;
    private SeguimientoDAO sgd;
    private DerivarDAO deriv;
    private LoginDao log;
    private Date fechaprov, aux, fecha;
    private String documento, origen, asunto_prov, origen_prov, asunto, tramnum, fechaaux, destino_ofic, correlativo_oficio, referencia, correlativo_proveido, destino_prov, codinterno, tipodestino;
    public static String tranum;

    public DocumentosBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();

        documentos = new ArrayList<Map<String, String>>();
        this.documentos_corregir = new ArrayList<Map<String, String>>();
        seglista = new ArrayList<Map<String, String>>();
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        log = new LoginDaoImpl();
        fechaprov = new Date();
        fecha = new Date();
        documentosprov = new ArrayList<Map<String, String>>();
        documentos_confirmados = new ArrayList<Map<String, String>>();
        dependenciasprov = new ArrayList<Map<String, String>>();
        docusinternos = new ArrayList<Map<String, String>>();
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
        } else {
            if (isdocusinternos) {
                MostrarDocusInternos();
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

    public void generarCorrelativo_proveido() {
        int corr = 0;
        String aux = "";
        try {
            if (getAnio().equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getCorreProv());
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
        System.out.println(docselec);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaaux = sdf.format(fechaprov);
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        tramnum = obtenerNumeroTramite();
        generarCorrelativo_proveido();
        origen_prov = hm.get("origen").toString();
        destino_prov = hm.get("destino").toString();
        tranum=correlativo_proveido;
    }

    public void Guardar_prov() {
        System.out.println("ENTRA LA P....");
        DocusExt de = new DocusExt();
        DocusExtint di = new DocusExtint();
        Proveido p = new Proveido();
        FacesMessage message = null;
        try {
            System.out.println(asunto);
            di.setNumerodoc(tramnum);
            di.setTramiteDatos(deriv.getTramite(tramnum));
            di.setAsunto(asunto_prov);
            di.setFecha(fecha);
            di.setDependenciaByCodigo(deriv.getDep(origen_prov));
            di.setDependenciaByCodigo1(deriv.getDep(destino_prov));
            di.setMovimientoDext(Long.parseLong("1"));
            de = deriv.getDocuExt(documento);
            di.setDocusExt(de);
            di.setUsuario(usu);

            p.setCorrelativod(correlativo_proveido);
            p.setDependenciaByCodigo(deriv.getDep(origen_prov));
            p.setDependenciaByCodigo1(deriv.getDep(destino_prov));
            p.setDocusExtint(di);
            p.setFechaenvio(fechaprov);
            p.setFecharegistro(fechaprov);//en un primero momento la fecha de ingreso y de envio del proveido será igual después al derivarse será nulo

            deriv.guardarDocusExt(di);
            deriv.GuardarProveido(p);
            
            /*message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha guardado el documento");     
            RequestContext.getCurrentInstance().showMessageInDialog(message);*/
            MostrarDocusInternos();
            ver=true;
            no_ver=false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ver=false;
            no_ver=true;
            /*message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Problemas al guardar");
            RequestContext.getCurrentInstance().showMessageInDialog(message);*/
        }
    }

    public void limpiar_prov() {
        asunto = "";
        codinterno = "100392";
    }

    public void mostrarOficio() {
        fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaaux = sdf.format(fecha);
        tramnum = obtenerNumeroTramite();
        correlativo_oficio = generarCorrelativo();
        referencia = dd.getMotivo(tramnum);
    }

    public void Eliminar() {
        FacesMessage message = null;
        try {
            Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
            dd.EliminarTramite(hm.get("numerotramite").toString(), hm.get("fenvio").toString().substring(0, 10));
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
        try {
            String ntram = "";
            int movi = 0;
            for (int i = 0; i < docselec.size(); i++) {
                Oficios ofi = new Oficios();
                ofi.setAsuntoOficio(asunto);
                ofi.setFechaOficio(fecha);
                ofi.setDependenciaByCodigo(deriv.getDep("OFICINA GENERAL DE PLANIFICACION"));
                ofi.setDependenciaByCodigo1(deriv.getDep(this.destino_ofic));
                ofi.setCorrelativoOficio(correlativo_oficio);
                ofi.setReferenciaOficio(dd.getMotivo(tramnum));
                ofi.setTramiteDatos(deriv.getTramite(tramnum));
                ofi.setUsuario(usu);
                ofi.setTiposDocumentos(deriv.getTipoDoc("OFICIO"));
                System.out.println("\\\\\\\\ENTRA A GUARDAR OFIICO¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                dd.guardarOficio(ofi, tramnum, obtenerMovimiento());
                System.out.println("\\\\\\\\SALE DE GUARDAR OFIICO¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                System.out.println("ESTE ES EL DOCSELEC: " + docselec);
                ntram = hm.get("numerotramite").toString();
                movi = Integer.parseInt(hm.get("movimiento").toString());
                Date nuevFech = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                System.out.println("confirmaar tramite entre");
                deriv.Confirmar(ntram, movi);
                System.out.println("confirmaar tramite sale");
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL OFICIO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            limpiar();
            MostrarDocusInternos();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL OFICIO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println("mal guardar oficio");
            System.out.println(e.getMessage());
            e.printStackTrace();
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
                corr = Integer.parseInt(deriv.getCorrelativoOficio());
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
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        numerotramite = hm.get("numerotramite").toString();
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

    public List Detalles() {
        System.out.println("listando detalles");
        seglista.clear();
        try {
            List lista = new ArrayList();
            System.out.println(seleccion.get("numerotramite").toString());
            lista = dd.getDetalle(seleccion.get("numerotramite").toString());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("fecha", String.valueOf(obj[0]));
                listaaux.put("origen", String.valueOf(obj[1]));
                listaaux.put("observacion", String.valueOf(obj[2]));
                listaaux.put("estado", String.valueOf(obj[3]));
                listaaux.put("docnomb", String.valueOf(obj[4]) + "N° " + String.valueOf(obj[5]) + "-" + String.valueOf(obj[6]) + "-" + String.valueOf(obj[7]));
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
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("fecha", String.valueOf(obj[1]));
                listaaux.put("codigo", String.valueOf(obj[2]));
                listaaux.put("observacion", String.valueOf(obj[3]));
                listaaux.put("estado", String.valueOf(obj[4]));
                listaaux.put("usuario", String.valueOf(obj[5]));
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
                System.out.println("entra a fecha envio td");
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date nf = new Date();
                System.out.println(hm.get("fecha").toString().substring(0, 19));
                nf = formato.parse(hm.get("fecha").toString().substring(0, 19));
                nuevoid.setTramFecha(nf);
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
                    tdoc = getTipodocumento(aux, td);
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
                    t.setFecha(nf);
                    System.out.println("sale fecha envio");
                    System.out.println("entra a fecha ing");
                    if (hm.get("fing").toString().equals(" ")) {
                        movimiento.setFechaIngr(null);
                    } else {
                        SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date nf2 = new Date();
                        nf2 = formato2.parse(hm.get("fing").toString().toString());
                        movimiento.setFechaIngr(nf2);
                    }
                    System.out.println("sale fecha fing");
                    System.out.println("entra a indicador");
                    movimiento.setIndicador(deriv.getIndic(hm.get("indicador").toString()));
                    System.out.println("sle indicador");
                    movimiento.setMoviObs(hm.get("observacion").toString());
                    t.setAsunto(hm.get("observacion").toString());
                    t.setTiposdocumentos(hm.get("docunomb").toString());
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
                        t.setTiposdocumentos(hm.get("docunomb").toString());
                    }
                    System.out.println("entra a indicador");
                    movimiento.setIndicador(deriv.getIndic(hm.get("indicador").toString()));
                    System.out.println("sale indicador");
                    movimiento.setMoviObs(hm.get("observacion").toString());
                }

                t.setSiglas(deriv.getSiglas2(dd.getOficina(usu)));
                System.out.println("---------entra a guardar tramite dato---------");
                sgd.GuadarTramiteDatos(td, tdoc);
                System.out.println("---------sale de guardar tramite dato---------");
                System.out.println("---------entra a guardar tramite movimiento---------");
                t.setImpreso(BigDecimal.valueOf(1));
                sgd.temporal(t);
                movimiento.setEstadConfrirm("SIN CONFIRMAR");
                sgd.GuardarTramiteMovimiento(movimiento);
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

}
