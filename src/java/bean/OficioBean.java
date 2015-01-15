/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.DocumentosBean.tranum;
import dao.DerivarDAO;
import dao.DocumentoDAO;
import dao.OficioDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.OficioDaoImpl;
import java.text.ParseException;
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
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import maping.DetallOficcirc;
import maping.DocumentosOfiint;
import maping.OficCirc;
import maping.Oficios;
import maping.Usuario;
import maping.DocusInternos;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class OficioBean {

    private String tipodepe;
    private List oficioscirculares;
    private OficioDAO od;
    private Date anio;
    private String auxanio;
    private DerivarDAO deriv;
    private String correlativo = "";
    private String correlativo2 = "";
    private List otrosdocus, otrosdocus1, otrosdocus2;
    private List docselec;
    public List depe2;
    private List depe;
    private String[] escojidos;
    private String asunto;
    private Date fecha;
    private String fechadia;
    private String fechadia2;
    private String fechahora;
    private String firma;
    private String responsable;
    private String arearesponsable;
    private List seleccionados;
    private boolean aparece;
    private String auxfecha;
    private FacesContext faceContext;
    private Usuario usu;
    private String destino;
    private List destinos;
    private DocumentoDAO dd;
    public List oficiosSinExp;
    public List oficiosConExp;
    private String asunto2;
    private List detallecirc;
    ///
    private String origen;
    private Map<String, String> seleccion;
    private String seleccionado;
    private List listausuarios;
    private DualListModel<String> cities;
    List<String> citiesSource = new ArrayList<String>();
    List<String> citiesTarget = new ArrayList<String>();
    //
    private String escogido;
    private String escogido2;
    private List tiposdocus;
    //
    //pruebas
    //
    private String prueba;
    private List<String> cities2;
    private String[] selectedCities;
    private String nombre;
    private String tipodestino;
    private static String correlativo_exportar;
    private boolean ver, nover;
    private String siglasdocus = "";
    private String responsableDI;

    public OficioBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        od = new OficioDaoImpl();
        oficioscirculares = new ArrayList<Map<String, String>>();
        depe2 = new ArrayList<Map<String, String>>();
        depe = new ArrayList<Map<String, String>>();
        deriv = new DerivarDaoImpl();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        listausuarios = new ArrayList<String>();
        oficiosSinExp = new ArrayList<Map<String, String>>();
        oficiosConExp = new ArrayList<Map<String, String>>();
        detallecirc = new ArrayList<Map<String, String>>();
        tiposdocus = new ArrayList<String>();
        cities2 = new ArrayList<String>();

        llenardepes();

        boolean isofcirc = (currentPage.lastIndexOf("Oficios_Circulares.xhtml") > -1);
        boolean isoficsinexp = (currentPage.lastIndexOf("documentosInternos.xhtml") > -1);
        boolean isoficconexp = (currentPage.lastIndexOf("Oficios.xhtml") > -1);
        cities = new DualListModel<String>(citiesSource, citiesTarget);
        if (isofcirc) {
            mostrarofCirc();
        } else {
            if (isoficsinexp) {
                mostrarOficiosSinExp();
            } else {
                if (isoficconexp) {
                    mostrarOficioConExp();
                }
            }
        }
        ObtenerTiposDocus2();

    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("OFICIOS CIRCULARES")) {
            mostrarofCirc();
        }
    }

    public void abrirOficioUnico() {
        getAnio();
        generarFecha();

    }

    public void ObtenerTiposDocus2() {
        System.out.println("listando tipos docus");
        tiposdocus.clear();
        try {
            System.out.println("OBTENER TIPOS DOCUS");
            tiposdocus = od.obtenerTiposDocusOfCirc("1");
        } catch (Exception e) {
            System.out.println("obtener tipo doccus ERROR 2");
            System.out.println(e.getMessage());
        }
        System.out.println(tiposdocus);
    }

    public void ObtenerTiposDocus() {
        System.out.println("listando tipos docus");
        tiposdocus.clear();
        try {
            System.out.println("OBTENER TIPOS DOCUS");
            tiposdocus = od.getTiposDocus("0");
        } catch (Exception e) {
            System.out.println("obtener tipo doccus ERROR 2");
            System.out.println(e.getMessage());
        }
        System.out.println(tiposdocus);
    }

    public void abrirDocumento() {
        System.out.println("get anio");
        getAnio();
        System.out.println("get fecha");
        generarFecha();
        System.out.println("get correlativo");
        generarCorrelativo2();
        System.out.println("get tipos docus");
        ObtenerTiposDocus();
        siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        System.out.println("SIGLAS: " + siglasdocus);
        origen = dd.getOficina(usu);
        listausuarios= deriv.listaUsuarios(usu.getOficina().getIdOficina());

    }

    public void abrirDocumentoUnico() {
        getAnio();
        generarFecha4();
        generarCorrelativoOfiUnico();
        ObtenerTiposDocus();
        siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
        origen = dd.getOficina(usu);
    }
    
    public void generarFecha4(){
        fecha= new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        auxfecha=sdf.format(fecha);
    }

    public void agregardestinos() {
        destinos = dd.getDependencias(tipodestino);
    }

    public void abriroficio() {
        System.out.println("get tiposdocus2");
        ObtenerTiposDocus2();
        System.out.println("get anio");
        getAnio();
        System.out.println("get fecha");
        generarFecha();
        System.out.println("get correlativo");
        generarCorrelativo();
        System.out.println("get responsable");
        responsable();
        System.out.println("get arearesponsable");
        arearesponsable();
        System.out.println("get firma");
        firma();

        System.out.println("ESTAMOS EN ABRIR OFICIO");
        System.out.println(tiposdocus);
        correlativo_exportar = correlativo;
        siglasdocus = "OGPL";

    }

    public List Detalles() {
        System.out.println("listando detalles");
        detallecirc.clear();
        try {
            List lista = new ArrayList<String>();
            System.out.println(seleccion.get("correlativo").toString().substring(19, 24));
            lista = od.getOficoCircDetal(String.valueOf(seleccion.get("correlativo").toString().substring(19, 24)));
            for (int i = 0; i < lista.size(); i++) {
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("nombre", lista.get(i).toString());
                detallecirc.add(listaaux);
            }
            System.out.println("entra aca >.<");
        } catch (Exception e) {
            System.out.println("error aca");
            System.out.println(e.getMessage());
        }
        return detallecirc;
    }

    public void mostrarOficiosSinExp() {
        System.out.println("listando oficios");
        oficiosSinExp.clear();
        try {
            List lista = new ArrayList();
            lista = od.getOficioUnicoNoExp();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[5];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", String.valueOf(obj[0]));
                listaaux.put("fecha", String.valueOf(obj[1]));
                listaaux.put("asunto", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("destino", String.valueOf(obj[4]));
                oficiosSinExp.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarOficioConExp() {
        System.out.println("listando oficios");
        oficiosConExp.clear();
        try {
            List lista = new ArrayList();
            lista = od.getOficioUnicoExpediente();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[7];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", String.valueOf(obj[0]));
                listaaux.put("tramnum", String.valueOf(obj[1]));
                listaaux.put("fecha", String.valueOf(obj[2]));
                listaaux.put("asunto", String.valueOf(obj[3])+" - "+String.valueOf(obj[4]));
                listaaux.put("origen", String.valueOf(obj[5]));
                listaaux.put("destino", String.valueOf(obj[6]));
                oficiosConExp.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generarCorrelativoOfiUnico() {
        int corr = 0;
        String aux = "";
        try {
            if (auxanio.equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getCorrelativoOficio(auxanio));
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

    public void generarCorrelativo2() {
        int corr = 0;
        String aux = "";
        try {
            if (auxanio.equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getIndice(siglasdocus, escogido2, auxanio));
                //corr = Integer.parseInt(deriv.getCorrelativoOficinaInterna(usu, escogido2, auxanio));
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
                dd.guardarNuevoAnio(auxanio);
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

    public void firma() {
        System.out.println("firma");
        try {
            List lista = new ArrayList();
            lista = od.getFirma();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[2];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                firma = String.valueOf(obj[0]) + " " + String.valueOf(obj[1]);
            }
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
        }
    }

    public void responsable() {
        responsable = usu.getUsuNombre();
    }

    public void arearesponsable() {
        arearesponsable = od.getAreaResponsable(usu.getOficina().getIdOficina());
    }

    public void llenardepes() {
        System.out.println("listando depes");
        depe.clear();
        try {
            List lista = new ArrayList();
            lista = od.getDependencias();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[2];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("nombre", String.valueOf(obj[0]));
                listaaux.put("tipo", String.valueOf(obj[1]));
                depe.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void llenar2() {
        cities2.clear();
        String tipo = "";
        String nombre = "";
        for (int i = 0; i < depe.size(); i++) {
            HashMap hashMap = (HashMap) depe.get(i);
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getKey().toString().equals("nombre")) {
                    nombre = e.getValue().toString();
                }
                if (e.getKey().toString().equals("tipo")) {
                    tipo = e.getValue().toString();
                }
            }
            if (prueba.equals(tipo)) {
                cities2.add(nombre);
            }

        }

    }

    public void llenar() {
        citiesSource.clear();
        String tipo = "";
        String nombre = "";
        System.out.println("ESTE ES EL TIPO: " + prueba);
        for (int i = 0; i < depe.size(); i++) {
            HashMap hashMap = (HashMap) depe.get(i);
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getKey().toString().equals("nombre")) {
                    nombre = e.getValue().toString();
                }
                if (e.getKey().toString().equals("tipo")) {
                    tipo = e.getValue().toString();
                }
            }
            if (prueba.equals(tipo)) {
                citiesSource.add(nombre);
            }
        }
        cities.setSource(citiesSource);
    }

    public void mostrar() {
        try {
            Long indice = od.getIndice(correlativo, auxanio);
            System.out.println("entra a mostrar");
            for (int i = 0; i < cities.getTarget().size(); i++) {

                DetallOficcirc dof = new DetallOficcirc();
                dof.setDependencia(od.getDependencias2(cities.getTarget().get(i)));
                dof.setOficCirc(od.getOficioCircular(correlativo, auxanio));
                System.out.println(escogido);
                dof.setTiposDocumentos(od.getTipoDocu(escogido));
                od.guardarDetalleOfCirc(dof);
            }
            this.cities.getTarget().clear();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void guardar_oficiounico() {
        FacesMessage message = null;
        try {
            Oficios ofi = new Oficios();
            ofi.setAsuntoOficio(asunto);
            ofi.setCorrelativoOficio(correlativo2);
            ofi.setFechaOficio(fecha);
            ofi.setDependenciaByCodigo(deriv.getDep(origen));
            ofi.setDependenciaByCodigo1(deriv.getDep(this.destino));
            ofi.setReferenciaOficio(null);
            ofi.setTramiteDatos(null);
            ofi.setUsuario(usu);
            System.out.println(escogido2);
            ofi.setTiposDocumentos(od.getTipoDocu("OFICIO"));
            dd.guardarOficio2(ofi);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL OFICIO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL OFICIO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println("mal guardar oficiounico");
            System.out.println(e.getMessage());
        }
        getAnio();
        generarFecha();
        this.abrirDocumentoUnico();
        this.asunto = "";
    }

    public void guardar_documentoOfiInt() throws ParseException {
        FacesMessage message = null;
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            DocusInternos di = new DocusInternos();
            di.setDocuAsunto(asunto);
            di.setDocuCorrelaint(correlativo2);
            fecha = sdf2.parse(fechadia + " " + fechahora);
            di.setFecharegistro(fecha);
            di.setDependenciaByCodigo(deriv.getDep(origen));
            di.setDependenciaByCodigo1(deriv.getDep(this.destino));
            di.setTiposDocumentos(od.getTipoDocu(escogido2));
            di.setDocuSiglasint(siglasdocus);
            di.setDocuNombreint(escogido2);
            di.setDocuAnioint(auxanio);
            di.setUsuario(usu);
            di.setUsuario1(deriv.getUsuarioDI(responsableDI));
            di.setDocuPricint("1");
            od.GuardarDocumentoOfiInt(di);
            /*DocumentosOfiint doif = new DocumentosOfiint();
             doif.setAsunto(asunto);
             doif.setCorrelativoDocofint(correlativo2);
             doif.setFecha(fecha);
             doif.setDependenciaByCodigo(deriv.getDep(origen));
             doif.setDependenciaByCodigo1(deriv.getDep(this.destino));
             doif.setTiposDocumentos(od.getTipoDocu(escogido2));
             doif.setSiglas(siglasdocus);
             doif.setUsuario(usu);
             od.GuardarDocumentoOfiInt(doif);*/
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL " + escogido2);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL " + escogido2);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println("mal guardar oficiounico");
            System.out.println(e.getMessage());
        }
        getAnio();
        generarFecha();
        generarCorrelativo2();
        this.asunto = "";
        this.escogido2 = " ";
        this.tipodestino = " ";
    }

    public void guardar() throws ParseException {
        FacesMessage message = null;
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("HORA Y FECHA: "+fechadia2+"-"+fechahora);
        try {
            OficCirc ofi = new OficCirc();
            ofi.setCorrelaOficic(correlativo);
            ofi.setAsunto(asunto2);
            ofi.setDependencia(od.getDependencia(usu.getOficina().getIdOficina()));
            fecha = sdf2.parse(fechadia2 + " " + fechahora);
            ofi.setFecha(fecha);
            ofi.setFirma(firma);
            ofi.setResponsable(responsable);
            ofi.setTiposDocumentos(deriv.getTipoDoc(escogido));
            od.guardarOficioCircular(ofi);
            mostrar();
            ver = true;
            nover = false;
        } catch (Exception e) {
            ver = false;
            nover = true;
            System.out.println(e.getMessage());
        }

        limpiar();
        cities.getTarget().clear();

    }

    public void limpiar() {
        asunto2 = "";
        escogido = " ";
        prueba = " ";
    }

    public void mostrarofCirc() {
        System.out.println("listando documentos");
        oficioscirculares.clear();
        try {
            List lista = new ArrayList();
            lista = od.getOficiosCirculares();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", String.valueOf(obj[0]));
                listaaux.put("asunto", String.valueOf(obj[1]));
                listaaux.put("fecha", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("firma", String.valueOf(obj[4]));
                listaaux.put("resp", String.valueOf(obj[5]));
                oficioscirculares.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
    }

    public void crearOficio() {
        generarFecha();
        getAnio();
        generarCorrelativo();
        System.out.println("CAMBIA");
        aparece = true;
    }

    public void getAnio() {
        System.out.println("entra getanio");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        anio = new Date();
        auxanio = sdf.format(anio);
        System.out.println(auxanio);
    }

    public void generarCorrelativo() {
        int corr = 0;
        String aux = "";
        try {
            if (auxanio.equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                if (od.getCorrelativo(auxanio) == null) {
                    corr = 0;
                } else {
                    corr = Integer.parseInt(od.getCorrelativo(auxanio));
                }
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

    public String getTipodepe() {
        return tipodepe;
    }

    public void setTipodepe(String tipodepe) {
        this.tipodepe = tipodepe;
    }

    public List getOficioscirculares() {
        return oficioscirculares;
    }

    public void setOficioscirculares(List oficioscirculares) {
        this.oficioscirculares = oficioscirculares;
    }

    public OficioDAO getOd() {
        return od;
    }

    public void setOd(OficioDAO od) {
        this.od = od;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
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

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
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

    public List getDepe2() {
        return depe2;
    }

    public void setDepe2(List depe2) {
        this.depe2 = depe2;
    }

    public List<String> getDepe() {
        return depe;
    }

    public void setDepe(List<String> depe) {
        this.depe = depe;
    }

    public String[] getEscojidos() {
        return escojidos;
    }

    public void setEscojidos(String[] escojidos) {
        this.escojidos = escojidos;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getArearesponsable() {
        return arearesponsable;
    }

    public void setArearesponsable(String arearesponsable) {
        this.arearesponsable = arearesponsable;
    }

    public List getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List seleccionados) {
        this.seleccionados = seleccionados;
    }

    public boolean isAparece() {
        return aparece;
    }

    public void setAparece(boolean aparece) {
        this.aparece = aparece;
    }

    public String getAuxfecha() {
        return auxfecha;
    }

    public void setAuxfecha(String auxfecha) {
        this.auxfecha = auxfecha;
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

    public String getCorrelativo2() {
        return correlativo2;
    }

    public void setCorrelativo2(String correlativo2) {
        this.correlativo2 = correlativo2;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List getDestinos() {
        return destinos;
    }

    public void setDestinos(List destinos) {
        this.destinos = destinos;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    /////////////////////////////
    public String[] getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(String[] selectedCities) {
        this.selectedCities = selectedCities;
    }

    public String getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(String seleccionado) {
        this.seleccionado = seleccionado;
    }

    public DualListModel<String> getCities() {
        return cities;
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }

    public List<String> getCitiesSource() {
        return citiesSource;
    }

    public void setCitiesSource(List<String> citiesSource) {
        this.citiesSource = citiesSource;
    }

    public List<String> getCitiesTarget() {
        return citiesTarget;
    }

    public void setCitiesTarget(List<String> citiesTarget) {
        this.citiesTarget = citiesTarget;
    }

    public List getOficiosSinExp() {
        return oficiosSinExp;
    }

    public void setOficiosSinExp(List oficiosSinExp) {
        this.oficiosSinExp = oficiosSinExp;
    }

    public List getOficiosConExp() {
        return oficiosConExp;
    }

    public void setOficiosConExp(List oficiosConExp) {
        this.oficiosConExp = oficiosConExp;
    }

    public String getAsunto2() {
        return asunto2;
    }

    public void setAsunto2(String asunto2) {
        this.asunto2 = asunto2;
    }

    public Map<String, String> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Map<String, String> seleccion) {
        this.seleccion = seleccion;
    }

    public List getDetallecirc() {
        return detallecirc;
    }

    public void setDetallecirc(List detallecirc) {
        this.detallecirc = detallecirc;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEscogido() {
        return escogido;
    }

    public void setEscogido(String escogido) {
        this.escogido = escogido;
    }

    public List getTiposdocus() {
        return tiposdocus;
    }

    public void setTiposdocus(List tiposdocus) {
        this.tiposdocus = tiposdocus;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public List<String> getCities2() {
        return cities2;
    }

    public void setCities2(List<String> cities2) {
        this.cities2 = cities2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipodestino() {
        return tipodestino;
    }

    public void setTipodestino(String tipodestino) {
        this.tipodestino = tipodestino;
    }

    public static String getCorrelativo_exportar() {
        return correlativo_exportar;
    }

    public static void setCorrelativo_exportar(String correlativo_exportar) {
        OficioBean.correlativo_exportar = correlativo_exportar;
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

    public String getSiglasdocus() {
        return siglasdocus;
    }

    public void setSiglasdocus(String siglasdocus) {
        this.siglasdocus = siglasdocus;
    }

    public String getEscogido2() {
        return escogido2;
    }

    public void setEscogido2(String escogido2) {
        this.escogido2 = escogido2;
    }

    public List getOtrosdocus1() {
        return otrosdocus1;
    }

    public void setOtrosdocus1(List otrosdocus1) {
        this.otrosdocus1 = otrosdocus1;
    }

    public List getOtrosdocus2() {
        return otrosdocus2;
    }

    public void setOtrosdocus2(List otrosdocus2) {
        this.otrosdocus2 = otrosdocus2;
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

    public List getListausuarios() {
        return listausuarios;
    }

    public void setListausuarios(List listausuarios) {
        this.listausuarios = listausuarios;
    }

    public String getResponsableDI() {
        return responsableDI;
    }

    public void setResponsableDI(String responsableDI) {
        this.responsableDI = responsableDI;
    }

    public String getFechadia2() {
        return fechadia2;
    }

    public void setFechadia2(String fechadia2) {
        this.fechadia2 = fechadia2;
    }

}
