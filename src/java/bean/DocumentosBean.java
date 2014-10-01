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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import maping.Temporal;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import maping.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class DocumentosBean implements Serializable {

    private List documentos;
    private DocumentoDAO dd;
    private List otrosdocus;
    private boolean mostrar = false;
    private List seglista;
    private Map<String, String> seleccion;
    /*pruebas*/
    private Usuario usu;
    private final FacesContext faceContext;
    private List docselec;
    private List seguimientolista;
    private SeguimientoDAO sgd;
    private DerivarDAO deriv;
    private List tdaux;
    private List tdaux2;
    private LoginDao log;
    private List docusinternos;
    private Date fechaprov;
    private List documentosprov;
    private List dependenciasprov;
    private String documento;
    private String origen;
    private Date aux;
    public static String tranum;
    ////////
    public DocumentosBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        documentos = new ArrayList<Map<String, String>>();
        seglista = new ArrayList<Map<String, String>>();
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        log = new LoginDaoImpl();
        fechaprov= new Date();
        documentosprov=new ArrayList<Map<String, String>>();
        dependenciasprov=new ArrayList<Map<String, String>>();
        docusinternos = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        //seguimientolista2 = new ArrayList<Map<String, String>>();
        tdaux = new ArrayList<Map<String, String>>();
        tdaux2 = new ArrayList<Map<String, String>>();
        MostrarDocumentos();
        MostrarDocusInternos();
        ObtenerDepIndic();
        
    }
    public void ObtenerDepIndic(){
        documentosprov=dd.getIndicadores();
        dependenciasprov=dd.getDependencias();
    }
    public String fechaactual(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        return sdf.format(fechaprov);
        
    }
    public void MostrarSeguimiento2(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista.clear();
        try {
            List lista = new ArrayList();
            lista = sgd.getSeguimientoGrande(tramnum);
            System.out.println(lista.size());
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

    public void RecorrerLista2() {
        System.out.println("entra a recorrer lista 2");
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            if (e.getKey().toString().equals("numerotramite")) {
                System.out.println(e.getValue().toString());
                System.out.println("------entra---------");
                MostrarSeguimiento2(e.getValue().toString());
                tranum=e.getValue().toString();
                System.out.println("------sale-----------");
            }

        }
        docselec.clear();

    }

    public void MostrarDocusInternos() {
        System.out.println("listando documentos");
        docusinternos.clear();
        try {
            List lista = new ArrayList();
            lista = dd.getDocusInternos();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[9];
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
                docusinternos.add(listaaux);
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
            Object obj[] = new Object[9];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                /*listaaux.put("numerotramite", String.valueOf(obj[0]));
                 listaaux.put("fecha", String.valueOf(obj[1]));
                 listaaux.put("observacion", String.valueOf(obj[2]));
                 listaaux.put("descripcion", String.valueOf(obj[3]));
                 listaaux.put("depnombre", String.valueOf(obj[4]));*/
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("movimiento", String.valueOf(obj[1]));
                listaaux.put("origen", String.valueOf(obj[2]));
                listaaux.put("destino", String.valueOf(obj[3]));
                listaaux.put("fenvio", String.valueOf(obj[4]));
                listaaux.put("fing", String.valueOf(obj[5]));
                listaaux.put("indicador", String.valueOf(obj[6]));
                listaaux.put("observacion", String.valueOf(obj[7]));
                listaaux.put("estado", String.valueOf(obj[8]));
                documentos.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List Detalles() {
        System.out.println("listando detalles");
        seglista.clear();
        try {
            List lista = new ArrayList();
            System.out.println(seleccion.get("numerotramite").toString());
            lista = dd.getDetalle(seleccion.get("numerotramite").toString());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[7];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("tramnum", String.valueOf(obj[0]));
                listaaux.put("fecha", String.valueOf(obj[1]));
                listaaux.put("origen", String.valueOf(obj[2]));
                listaaux.put("observacion", String.valueOf(obj[3]));
                listaaux.put("estado", String.valueOf(obj[4]));
                listaaux.put("usuario", String.valueOf(obj[5]));
                listaaux.put("docnomb", String.valueOf(obj[6]) + "-" + String.valueOf(obj[7]) + "-" + String.valueOf(obj[8]) + "-" + String.valueOf(obj[9]));
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
        try {
            for (int i = 0; i < tdaux.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) tdaux.get(i);
                Iterator it = hm.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    if (e.getKey().toString().equals("numerotramite")) {
                        System.out.println("llena numerotramite");
                        nuevo.setTramNum(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("fecha")) {
                        System.out.println("entra a fecha envio td");
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date nf = new Date();
                        System.out.println(e.getValue().toString().substring(0, 19));
                        nf = formato.parse(e.getValue().toString().substring(0, 19));
                        nuevo.setTramFecha(nf);
                    }
                    if (e.getKey().toString().equals("codigo")) {
                        System.out.println("llena codigo td");
                        nuevo.setDependencia(deriv.getDependencia2(e.getValue().toString()));
                    }
                    if (e.getKey().toString().equals("observacion")) {
                        System.out.println("llena obsv. td");
                        nuevo.setTramObs(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("estado")) {
                        System.out.println("llena estado td");
                        nuevo.setEstaDescrip(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("usuario")) {
                        System.out.println("llena usuario td");
                        nuevo.setUsuario(log.getUniqeUsuario(e.getValue().toString()));
                    }
                }
            }
            System.out.println("Se obtiene numero tramite:" + nuevo.getTramNum());
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
                Iterator it = hm.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    if (e.getKey().toString().equals("numerotramite")) {
                        System.out.println("numero tramite tipodocu");
                        tipo.setTramiteDatos(td);
                    }
                    if (e.getKey().toString().equals("docunombre")) {
                        System.out.println("docu nombre tipodocu");
                        tipo.setDocuNombre(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("docunumero")) {
                        System.out.println("docunumero tipodocu");
                        tipo.setDocuNum(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("docupric")) {
                        System.out.println("docupric tipodocu");
                        tipo.setDocuPric(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("docusiglas")) {
                        System.out.println("docusiglas tipodocu");
                        tipo.setDocuSiglas(e.getValue().toString());
                    }
                    if (e.getKey().toString().equals("docuanio")) {
                        System.out.println("docuanio tipodocu");
                        tipo.setDocuAnio(e.getValue().toString());
                    }
                }

            }
            System.out.println("tipo docu info: " + tipo.getIdDocu());
        } catch (Exception e) {
            System.out.println("mal get tipodocumento");
            System.out.println(e.getMessage());
        }
        return tipo;
    }

    public void Confirmar() {
        FacesMessage message = null;
        try {
            System.out.println("ENTRA A CONFIRMAR seguimiento");
            String ntram = "";
            int movi = 0;
            String aux = "";
            for (int i = 0; i < docselec.size(); i++) {
                System.out.println("entra al bucle for");
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                Iterator it = hm.entrySet().iterator();
                TramiteMovimiento movimiento = new TramiteMovimiento();
                TramiteDatos td = new TramiteDatos();
                TipoDocu tdoc = new TipoDocu();
                Temporal t= new Temporal();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    if (e.getKey().toString().equals("numerotramite")) {
                        aux = e.getValue().toString();
                        t.setTramNum(e.getValue().toString());
                    }
                    if (aux.indexOf("OGPL") != -1) {
                        System.out.println("ENTRA A OGPL");
                        //ntram = e.getValue().toString();
                        td = getTramiteDato(aux);
                        tdoc = getTipodocumento(aux, td);
                        movimiento.setTramiteDatos(td);

                        if (e.getKey().toString().equals("movimiento")) {
                            movimiento.setMoviNum(Short.parseShort(e.getValue().toString()));
                            
                        }
                        if (e.getKey().toString().equals("estado")) {
                            movimiento.setEstaNombre(e.getValue().toString());
                           
                        }
                        if (e.getKey().toString().equals("origen")) {
                            movimiento.setDependenciaByCodigo(deriv.getDependencia(e.getValue().toString()));
                            t.setOrigen(e.getValue().toString());
                        }
                        if (e.getKey().toString().equals("destino")) {
                            movimiento.setDependenciaByCodigo1(deriv.getDependencia(e.getValue().toString()));
                        }
                        if (e.getKey().toString().equals("fenvio")) {
                            System.out.println("entra a fecha envio");
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date nf = new Date();
                            nf = formato.parse(e.getValue().toString());
                            movimiento.setFechaEnvio(nf);
                            t.setFecha(nf);
                            System.out.println("sale fecha envio");
                        }
                        if (e.getKey().toString().equals("fing")) {
                            System.out.println("entra a fecha ing");
                            if (e.getValue().toString().equals(" ")) {
                                movimiento.setFechaIngr(null);
                            } else {
                                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date nf = new Date();
                                nf = formato.parse(e.getValue().toString());
                                movimiento.setFechaIngr(nf);
                            }

                            System.out.println("sale fecha fing");
                        }
                        if (e.getKey().toString().equals("indicador")) {
                            System.out.println("entra a indicador");
                            movimiento.setIndicador(deriv.getIndic(e.getValue().toString()));
                            System.out.println("sle indicador");
                        }
                        if (e.getKey().toString().equals("observacion")) {
                            movimiento.setMoviObs(e.getValue().toString());
                            t.setAsunto(e.getValue().toString());
                        }

                    } else {
                        if (aux.indexOf("OGPL") == -1) {
                            System.out.println("ENTRA A DEPENDENCIAS EXTERNAS");
                            if (e.getKey().toString().equals("numerotramite")) {
                                td.setTramNum(e.getValue().toString());
                                t.setTramNum(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("fenvio")) {
                                System.out.println("entra a fecha envio");
                                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date nf = new Date();
                                nf = formato.parse(e.getValue().toString());
                                td.setTramFecha(nf);
                                System.out.println("sale fecha envio");
                                t.setFecha(nf);
                            }
                            if (e.getKey().toString().equals("observacion")) {
                                td.setTramObs(e.getValue().toString());
                                t.setAsunto(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("estado")) {
                                td.setEstaDescrip(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("origen")) {
                                td.setDependencia(deriv.getDependencia(e.getValue().toString()));
                                t.setOrigen(e.getValue().toString());
                            }
                            movimiento.setTramiteDatos(td);
                            if (e.getKey().toString().equals("movimiento")) {
                                movimiento.setMoviNum(Short.parseShort(e.getValue().toString()));
                            }
                            if (e.getKey().toString().equals("estado")) {
                                movimiento.setEstaNombre(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("origen")) {
                                movimiento.setDependenciaByCodigo(deriv.getDependencia(e.getValue().toString()));
                            }
                            if (e.getKey().toString().equals("destino")) {
                                movimiento.setDependenciaByCodigo1(deriv.getDependencia(e.getValue().toString()));
                            }
                            if (e.getKey().toString().equals("fenvio")) {
                                System.out.println("entra a fecha envio");
                                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date nf = new Date();
                                nf = formato.parse(e.getValue().toString());
                                movimiento.setFechaEnvio(nf);
                                System.out.println("sale fecha envio");
                            }
                            if (e.getKey().toString().equals("fing")) {
                                System.out.println("entra a fecha ing");
                                if (e.getValue().toString().equals(" ")) {
                                    movimiento.setFechaIngr(null);
                                } else {
                                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    Date nf = new Date();
                                    nf = formato.parse(e.getValue().toString());
                                    movimiento.setFechaIngr(nf);
                                }

                                System.out.println("sale fecha fing");
                            }
                            if (e.getKey().toString().equals("indicador")) {
                                System.out.println("entra a indicador");
                                movimiento.setIndicador(deriv.getIndic(e.getValue().toString()));
                                System.out.println("sle indicador");
                            }
                            if (e.getKey().toString().equals("observacion")) {
                                movimiento.setMoviObs(e.getValue().toString());
                            }
                        }
                        tdoc = null;
                    }

                }
                System.out.println("---------entra a guardar tramite dato---------");
                sgd.GuadarTramiteDatos(td, tdoc);
                System.out.println("---------sale de guardar tramite dato---------");
                System.out.println("---------entra a guardar tramite movimiento---------");
                sgd.temporal(t);
                sgd.GuardarTramiteMovimiento(movimiento);
                System.out.println("---------sale de guardar tramite movimiento---------");
                ntram = "";

            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha confirmado el documento");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //MostrarConfirmados();
        } catch (Exception e) {
            System.out.println("ERROR CONFIRMAR");
            System.out.println(e.getMessage());
            e.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Problemas en el confirmado");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        MostrarDocumentos();
        MostrarDocusInternos();
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
    
}   
