/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import dao.DocumentoDAO;
import dao.LoginDao;
import dao.SeguimientoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.LoginDaoImpl;
import daoimpl.SeguimientoDaoImpl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import maping.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class SeguimientoBean {

    private List seguimientolista;
    private List seguimientolista2;
    private SeguimientoDAO sgd;
    private DocumentoDAO dd;
    private List docselec;
    private Usuario usu;
    private Date fecha;
    private String fechadia;
    private String fechahora;
    private String motivo = "";
    private String usuario = "";
    private final FacesContext faceContext;
    private String codinterno;
    private DerivarDAO deriv;
    private List tdaux;
    private LoginDao log;
    private List tdaux2;
    private List docusinternos;

    public SeguimientoBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        log = new LoginDaoImpl();
        docusinternos = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        seguimientolista2 = new ArrayList<Map<String, String>>();
        tdaux = new ArrayList<Map<String, String>>();
        tdaux2 = new ArrayList<Map<String, String>>();
        boolean isdocumentosuser = (currentPage.lastIndexOf("documentos_user.xhtml") > -1);
        boolean isdocumentosrspta = (currentPage.lastIndexOf("documentos_respta.xhtml") > -1);
        if(isdocumentosuser){
            MostrarParaUsuario();
        }else{
            if(isdocumentosrspta){
                MostrarDocusInternos();
            }
        }
        
        

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

    public void MostrarSeguimiento2(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista.clear();
        try {
            List lista = new ArrayList();
            List lista1 = new ArrayList();
            lista = sgd.getSeguimientoGrande1(tramnum);
            lista1 = sgd.getSeguimientoGrande2(tramnum);
            System.out.println(lista.size());
            Iterator ite = lista.iterator();
            Iterator ite2 = lista1.iterator();
            Object obj[] = new Object[9];
            Object obj1[] = new Object[9];
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
            while (ite2.hasNext()) {
                System.out.println("ola");
                obj1 = (Object[]) ite2.next();
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

    //seguimiento para un cierto tramite
    public void MostrarSeguimiento(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista.clear();
        try {
            List lista = new ArrayList();
            lista = sgd.getSeguimiento(tramnum);
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
                System.out.println("------sale-----------");
            }

        }
        docselec.clear();

    }

    public void RecorrerLista() {
        System.out.println("entra a recorrer");
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            if (e.getKey().toString().equals("numerotramite")) {
                System.out.println(e.getValue().toString());
                System.out.println("------entra---------");
                MostrarSeguimiento(e.getValue().toString());
                System.out.println("------sale-----------");
            }

        }
        docselec.clear();

    }
    //

    //mostrar el total de seguimientos  
    public void MostrarParaUsuario() {
        System.out.println("listando documentos2");
        seguimientolista2.clear();
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            System.out.println(usu.getOficina().getIdOficina());
            lista = sgd.seguimientoUser(usu.getOficina().getIdOficina());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[9];
            while (ite.hasNext()) {
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
                seguimientolista2.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Derivar() {
        System.out.println("entra a derivar");
        IniciarFecha();
        Motivo();
        UsuarioSelec();
    }

    public void IniciarFecha() {
        System.out.println("entra a fecha");
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fecha = new Date();
        fechadia = "";
        fechahora = "";
        StringTokenizer tokens = new StringTokenizer(formato.format(fecha), " ");
        while (tokens.hasMoreTokens()) {
            if (fechadia.equals("")) {
                fechadia = tokens.nextToken();
            }
            if (fechahora.equals("")) {
                fechahora = tokens.nextToken();
            }
        }
    }

    public void Motivo() {
        System.out.println("entra a motivo");
        try {
            Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getKey().toString().equals("numerotramite")) {
                    System.out.println(e.getValue().toString());
                    motivo = dd.getMotivo(hm.get("numerotramite").toString());
                }

            }
            docselec.clear();
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void UsuarioSelec() {
        System.out.println("entra a usuario");
        try {
            usuario = usu.getUsuNombre();
        } catch (Exception e) {
            System.out.println("error2");
            System.out.println(e.getMessage());
        }
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
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date nf = new Date();
                System.out.println(hm.get("fecha").toString().substring(0, 19));
                nf = formato.parse(hm.get("fecha").toString());
                nuevoid.setTramFecha(nf);
                System.out.println("llena codigo td");
                nuevo.setDependencia(deriv.getDependencia2(hm.get("codigo").toString()));
                System.out.println("llena obsv. td");
                nuevo.setTramObs(hm.get("observacion").toString());
                System.out.println("llena estado td");
                nuevo.setEstaDescrip(hm.get("estado").toString());
                System.out.println("llena usuario td");
                nuevo.setUsuario(log.getUniqeUsuario(hm.get("usuario").toString()));
            }
            System.out.println("Se obtiene numero tramite:" + nuevoid.getTramNum());
        } catch (Exception e) {
            System.out.println("error get tramitedatos");
            System.out.println(e.getMessage());
        }

        return nuevo;
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
                maping.TramiteDatosId nuevoid= new maping.TramiteDatosId();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    if (e.getKey().toString().equals("numerotramite")) {
                        aux = e.getValue().toString();
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

                    } else {
                        if (aux.indexOf("OGPL") == -1) {
                            System.out.println("ENTRA A DEPENDENCIAS EXTERNAS");
                            if (e.getKey().toString().equals("numerotramite")) {
                                nuevoid.setTramNum(e.getValue().toString());
                                //td.setTramNum(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("fenvio")) {
                                System.out.println("entra a fecha envio");
                                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date nf = new Date();
                                nf = formato.parse(e.getValue().toString());
                                //td.setTramFecha(nf);
                                nuevoid.setTramFecha(nf);
                                System.out.println("sale fecha envio");
                            }
                            td.setId(nuevoid);
                            if (e.getKey().toString().equals("observacion")) {
                                td.setTramObs(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("estado")) {
                                td.setEstaDescrip(e.getValue().toString());
                            }
                            if (e.getKey().toString().equals("origen")) {
                                td.setDependencia(deriv.getDependencia(e.getValue().toString()));
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

    public List getDocselec() {
        return docselec;
    }

    public void setDocselec(List docselec) {
        this.docselec = docselec;
    }

    public List getSeguimientolista2() {
        return seguimientolista2;
    }

    public void setSeguimientolista2(List seguimientolista2) {
        this.seguimientolista2 = seguimientolista2;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechadia() {
        return fechadia;
    }

    public void setFechadia(String fechadia) {
        this.fechadia = fechadia;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodinterno() {
        return codinterno;
    }

    public void setCodinterno(String codinterno) {
        this.codinterno = codinterno;
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

    public LoginDao getLog() {
        return log;
    }

    public void setLog(LoginDao log) {
        this.log = log;
    }

    public List getTdaux2() {
        return tdaux2;
    }

    public void setTdaux2(List tdaux2) {
        this.tdaux2 = tdaux2;
    }

    public List getDocusinternos() {
        return docusinternos;
    }

    public void setDocusinternos(List docusinternos) {
        this.docusinternos = docusinternos;
    }

}
