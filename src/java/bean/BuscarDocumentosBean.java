/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import dao.DocumentoDAO;
import dao.SeguimientoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.SeguimientoDaoImpl;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class BuscarDocumentosBean implements Serializable {

    private String codigosdepe;
    private DocumentoDAO dd;
    private DerivarDAO deriv;
    private List docus, nuevaBusq, nuevaBusqOgpl, nuevabusqogplkp, otrosdocus, docselec3;
    private String numerotramite = "";
    private String nuevotramite = "";
    private String nuevoasunto = "";
    private String derivadoa;
    private String codigo;
    private String anio;
    private String asunto;
    private String mes;
    private String ofi1 = "--", ofi2 = "--", ofi3 = "--", ofi4 = "--", ofi5 = "--", ofi6 = "--", ofi7 = "--";
    private String di1 = "--", di2 = "--", di3 = "--", di4 = "--", di5 = "--", di6 = "--", di7 = "--", di8 = "--";
    private String de1 = "--", de2 = "--", de3 = "--", de4 = "--", de5 = "--", de6 = "--", de7 = "--";
    private String r1 = "---", r2 = "---", r3 = "---", r4 = "---", r5 = "---", r6 = "---", r7 = "---", r8 = "---", r9 = "---";
    private List filtro, filtro2;
    private boolean aparece;
    private List seglista;
    private Map<String, String> seleccion, seleccion2, seleccion3;
    private List seguimientolista;
    private SeguimientoDAO sgd;
    private List docselec, anios, docselec2;
    private FacesContext faceContext;
    private Usuario usu;
    private List listadependencias;
    private String nombre;

    public BuscarDocumentosBean() {
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        dd = new DocumentoDaoImpl();
        deriv = new DerivarDaoImpl();
        docus = new ArrayList<Map<String, String>>();
        seglista = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        nuevabusqogplkp = new ArrayList<Map<String, String>>();
        this.nuevaBusq = new ArrayList<Map<String, String>>();
        nuevaBusqOgpl = new ArrayList<Map<String, String>>();
        listadependencias = new ArrayList<String>();
        sgd = new SeguimientoDaoImpl();
        anios = new ArrayList<String>();
        aparece = false;
        //mostrarAnios();
        /*mostrarListaBusquedaOGPL() ;
         mostrarListaBusqueda();*/
        mostrarDependencias();
    }

    public void MostrarDetalle() {
        System.out.println("detalle");
        Map<String, String> hm = (HashMap<String, String>) docselec3.get(0);
        System.out.println(hm.get("expediente").toString().toUpperCase());
        if (hm.get("expediente").toString().toUpperCase().indexOf("OFICIO") != -1 || hm.get("expediente").toString().toUpperCase().indexOf("OGPL") != -1 &&
               hm.get("expediente").toString().toUpperCase().indexOf("DIRECTIVAS") == -1 ) {
            System.out.println("1");
            ObtenerOficios(hm.get("expediente").toString());
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('DetalleOficios').show()");
        } else {
            if (hm.get("expediente").toString().toUpperCase().indexOf("CARTA") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("ARCHIVO") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("DIRECTIVAS") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("REPORTE") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("EXPEDIENTE") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("OFICIO") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("MEMORANDUM") != -1
                    || hm.get("expediente").toString().toUpperCase().indexOf("INFORME") != -1) {
                
                System.out.println("2");                
                ObtenerDocusInternos(hm.get("expediente").toString());
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('DetalleDI').show()");
            } else {
                if (hm.get("expediente").toString().toUpperCase().indexOf("PROVEIDOS") != -1
                        || hm.get("expediente").toString().toUpperCase().indexOf("OFICIO") != -1) {
                    
                    System.out.println("3");
                    ObtenerDocusExtInt(hm.get("expediente").toString());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('DetalleDE').show()");
                } else {
                    System.out.println("4");
                    ObtenerDatosExpediente();
                    ObtenerDatosRespuesta();
                    ObtenerDatosOFicios();
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('itemDialog').show()");
                }
            }

        }

    }

    public void ObtenerOficios(String docu) {
        try {
            List lista = new ArrayList();
            lista = dd.query5(docu);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                ofi1 = String.valueOf(obj[0]);
                ofi2 = String.valueOf(obj[1]);
                ofi3 = String.valueOf(obj[2]);
                ofi4 = String.valueOf(obj[3]) + " " + String.valueOf(obj[4]);
                ofi5 = String.valueOf(obj[5]);
                ofi6 = String.valueOf(obj[6]);
                ofi7 = String.valueOf(obj[7]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ObtenerDocusExtInt(String docu) {
        try {
            List lista = new ArrayList();
            lista = dd.query7(docu);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[7];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                this.de1 = String.valueOf(obj[0]);
                this.de2 = String.valueOf(obj[1]);
                this.de3 = String.valueOf(obj[2]);
                this.de4 = String.valueOf(obj[3]);
                this.de5 = String.valueOf(obj[4]);
                this.de6 = String.valueOf(obj[5]);
                this.de7 = String.valueOf(obj[6]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ObtenerDocusInternos(String docu) {
        try {
            List lista = new ArrayList();
            lista = dd.query6(docu);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                this.di1 = String.valueOf(obj[0]);
                this.di2 = String.valueOf(obj[1]);
                this.di3 = String.valueOf(obj[2]);
                this.di4 = String.valueOf(obj[3]);
                this.di5 = String.valueOf(obj[4]);
                this.di6 = String.valueOf(obj[5]);
                this.di7 = String.valueOf(obj[6]);
                this.di8 = String.valueOf(obj[7]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ObtenerDatosExpediente() {
        Map<String, String> hm = (HashMap<String, String>) docselec3.get(0);
        try {
            List lista = new ArrayList();
            lista = dd.query1(hm.get("expediente").toString(), hm.get("tramfecha").toString());
            if (lista.isEmpty()) {
                lista = dd.query2(hm.get("expediente").toString(), hm.get("tramfecha").toString());
            }
            Iterator ite = lista.iterator();
            Object obj[] = new Object[3];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                r1 = String.valueOf(obj[0]);
                r2 = String.valueOf(obj[1]);
                r3 = String.valueOf(obj[2]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ObtenerDatosRespuesta() {
        Map<String, String> hm = (HashMap<String, String>) docselec3.get(0);
        try {
            List lista = new ArrayList();
            lista = dd.query3(hm.get("expediente").toString(), hm.get("tramfecha").toString());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[3];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                r4 = String.valueOf(obj[0]);
                r5 = String.valueOf(obj[1]);
                r6 = String.valueOf(obj[2]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ObtenerDatosOFicios() {
        Map<String, String> hm = (HashMap<String, String>) docselec3.get(0);
        try {
            List lista = new ArrayList();
            lista = dd.query4(hm.get("expediente").toString(), hm.get("tramfecha").toString());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[3];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                r7 = String.valueOf(obj[0]);
                r8 = String.valueOf(obj[1]);
                r9 = String.valueOf(obj[2]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarDependencias() {
        listadependencias.clear();
        try {
            listadependencias = dd.getDependencias();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error");
        }
    }

    public void mostrarNuevaListaBusqOGPL() {
        nuevabusqogplkp.clear();
        if (derivadoa.equals("")) {
            derivadoa = "";
        } else {
            derivadoa = deriv.getCodigoDep(derivadoa);
        }

        System.out.println("listando");
        try {
            List lista = new ArrayList();
            lista = dd.nuevaBusqAvanzada(nuevotramite, nuevoasunto, derivadoa);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[5];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("expediente", String.valueOf(obj[0]));
                listaaux.put("tramfecha", String.valueOf(obj[1]));
                listaaux.put("fechaing", String.valueOf(obj[2]));
                listaaux.put("derivadoA", String.valueOf(obj[3]));
                listaaux.put("asunto", String.valueOf(obj[4]));
                nuevabusqogplkp.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        limpiar();
    }

    public void limpiar() {
        nuevotramite = "";
        nuevoasunto = "";
        derivadoa = "";
    }

    public void mostrarListaBusquedaOGPL() {
        nuevaBusqOgpl.clear();
        System.out.println("listando");
        try {
            List lista = new ArrayList();
            lista = dd.busquedaAvanzada2();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[9];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("fechaingreso", String.valueOf(obj[0]));
                listaaux.put("numerotramite", String.valueOf(obj[1]));
                listaaux.put("derivadoA1", String.valueOf(obj[2]));
                listaaux.put("fecharegistro", String.valueOf(obj[3]));
                listaaux.put("respuesta", String.valueOf(obj[4]));
                listaaux.put("deriivadoA2", String.valueOf(obj[5]));
                listaaux.put("fechaoficio", String.valueOf(obj[6]));
                listaaux.put("oficio", String.valueOf(obj[7]));
                listaaux.put("derivadoA3", String.valueOf(obj[8]));
                nuevaBusqOgpl.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarListaBusqueda() {
        nuevaBusq.clear();
        System.out.println("listando");
        try {
            List lista = new ArrayList();
            lista = dd.busquedaAvanzada(deriv.getCodigoUsuario(usu.getUsu()));
            Iterator ite = lista.iterator();
            Object obj[] = new Object[9];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("fechaingreso", String.valueOf(obj[0]));
                listaaux.put("numerotramite", String.valueOf(obj[1]));
                listaaux.put("derivadoA1", String.valueOf(obj[2]));
                listaaux.put("fecharegistro", String.valueOf(obj[3]));
                listaaux.put("respuesta", String.valueOf(obj[4]));
                listaaux.put("deriivadoA2", String.valueOf(obj[5]));
                listaaux.put("fechaoficio", String.valueOf(obj[6]));
                listaaux.put("oficio", String.valueOf(obj[7]));
                listaaux.put("derivadoA3", String.valueOf(obj[8]));
                nuevaBusq.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Buscar() {
        docus.clear();
        System.out.println("listando");
        System.out.println(numerotramite + "-" + codigosdepe + "-" + anio + "-" + asunto + "-" + mes);
        try {
            List lista = new ArrayList();
            lista = dd.getBusqueda(numerotramite, codigosdepe, anio, asunto, mes);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("mov", String.valueOf(obj[1]));
                listaaux.put("fecha", String.valueOf(obj[2]));
                listaaux.put("observacion", String.valueOf(obj[3]));
                listaaux.put("descripcion", String.valueOf(obj[4]));
                listaaux.put("departorigen", String.valueOf(obj[5]));
                docus.add(listaaux);
            }
            aparece = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarAnios() {
        Date anio = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        int i = Integer.parseInt(sdf.format(anio));
        while (i >= 1990) {
            anios.add(String.valueOf(i));
            i--;
        }

    }

    public List Detalles() {
        System.out.println("listando detalles busqparam");
        seglista.clear();
        int i = -1;
        try {
            List lista = new ArrayList();
            System.out.println(seleccion.get("numerotramite").toString() + "---" + "este es");
            if (seleccion.get("numerotramite").toString().indexOf("OGPL") != -1) {
                System.out.println(seleccion.get("numerotramite").toString());
                lista = dd.getDetalle(seleccion.get("numerotramite").toString());
                System.out.println("1");
                i = 1;
            } else {
                if (seleccion.get("numerotramite").toString().indexOf("OGPL") == -1) {
                    System.out.println(seleccion.get("numerotramite").toString());
                    lista = dd.getDeatalle2(seleccion.get("numerotramite").toString(), seleccion.get("mov"));
                    System.out.println("0");
                    i = 0;
                }
            }
            Iterator ite = lista.iterator();
            Object obj[] = new Object[3];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("usuario", String.valueOf(obj[0]));
                listaaux.put("oficina", String.valueOf(obj[1]));
                listaaux.put("docunombre", String.valueOf(obj[2]));
                seglista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return seglista;
    }

    public List Detalles2() {
        System.out.println("listando detalles busqparam");
        seglista.clear();
        int i = -1;
        try {
            List lista = new ArrayList();
            System.out.println(seleccion2.get("numerotramite").toString() + "---" + "este es");
            if (seleccion2.get("numerotramite").toString().indexOf("OGPL") != -1) {
                System.out.println(seleccion2.get("numerotramite").toString());
                lista = dd.getDetalle(seleccion2.get("numerotramite").toString());
                System.out.println("1");
                i = 1;
            } else {
                if (seleccion2.get("numerotramite").toString().indexOf("OGPL") == -1) {
                    System.out.println(seleccion2.get("numerotramite").toString());
                    lista = dd.getDeatalle2(seleccion2.get("numerotramite").toString(), seleccion2.get("mov"));
                    System.out.println("0");
                    i = 0;
                }
            }
            Iterator ite = lista.iterator();
            Object obj[] = new Object[3];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("usuario", String.valueOf(obj[0]));
                listaaux.put("oficina", String.valueOf(obj[1]));
                listaaux.put("docunombre", String.valueOf(obj[2]));
                seglista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return seglista;
    }

    public void RecorrerLista2() {
        System.out.println("entra a recorrer lista");
        Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
        System.out.println(hm.get("numerotramite"));
        MostrarSeguimiento(hm.get("numerotramite"));
        docselec.clear();
    }

    public void RecorrerLista() {
        System.out.println("entra a recorrer lista");
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        System.out.println(hm.get("numerotramite"));
        MostrarSeguimiento(hm.get("numerotramite"));
        docselec.clear();
    }

    public void MostrarSeguimiento(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista.clear();
        try {
            System.out.println("entra");
            List lista = new ArrayList();
            lista = sgd.getSeguimiento(tramnum);
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
                seguimientolista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println("buuuuu");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public String getNumerotramite() {
        return numerotramite;
    }

    public void setNumerotramite(String numerotramite) {
        this.numerotramite = numerotramite;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List getDocus() {
        return docus;
    }

    public void setDocus(List docus) {
        this.docus = docus;
    }

    public String getCodigosdepe() {
        return codigosdepe;
    }

    public void setCodigosdepe(String codigosdepe) {
        this.codigosdepe = codigosdepe;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List getFiltro() {
        return filtro;
    }

    public void setFiltro(List filtro) {
        this.filtro = filtro;
    }

    public boolean isAparece() {
        return aparece;
    }

    public void setAparece(boolean aparece) {
        this.aparece = aparece;
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

    public List getAnios() {
        return anios;
    }

    public void setAnios(List anios) {
        this.anios = anios;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }

    public List getNuevaBusq() {
        return nuevaBusq;
    }

    public void setNuevaBusq(List nuevaBusq) {
        this.nuevaBusq = nuevaBusq;
    }

    public List getNuevaBusqOgpl() {
        return nuevaBusqOgpl;
    }

    public void setNuevaBusqOgpl(List nuevaBusqOgpl) {
        this.nuevaBusqOgpl = nuevaBusqOgpl;
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

    public List getFiltro2() {
        return filtro2;
    }

    public void setFiltro2(List filtro2) {
        this.filtro2 = filtro2;
    }

    public List getDocselec2() {
        return docselec2;
    }

    public void setDocselec2(List docselec2) {
        this.docselec2 = docselec2;
    }

    public Map<String, String> getSeleccion2() {
        return seleccion2;
    }

    public void setSeleccion2(Map<String, String> seleccion2) {
        this.seleccion2 = seleccion2;
    }

    public String getNuevotramite() {
        return nuevotramite;
    }

    public void setNuevotramite(String nuevotramite) {
        this.nuevotramite = nuevotramite;
    }

    public String getNuevoasunto() {
        return nuevoasunto;
    }

    public void setNuevoasunto(String nuevoasunto) {
        this.nuevoasunto = nuevoasunto;
    }

    public String getDerivadoa() {
        return derivadoa;
    }

    public void setDerivadoa(String derivadoa) {
        this.derivadoa = derivadoa;
    }

    public List getNuevabusqogplkp() {
        return nuevabusqogplkp;
    }

    public void setNuevabusqogplkp(List nuevabusqogplkp) {
        this.nuevabusqogplkp = nuevabusqogplkp;
    }

    public List getOtrosdocus() {
        return otrosdocus;
    }

    public void setOtrosdocus(List otrosdocus) {
        this.otrosdocus = otrosdocus;
    }

    public List getDocselec3() {
        return docselec3;
    }

    public void setDocselec3(List docselec3) {
        this.docselec3 = docselec3;
    }

    public List getListadependencias() {
        return listadependencias;
    }

    public void setListadependencias(List listadependencias) {
        this.listadependencias = listadependencias;
    }

    public Map<String, String> getSeleccion3() {
        return seleccion3;
    }

    public void setSeleccion3(Map<String, String> seleccion3) {
        this.seleccion3 = seleccion3;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    public String getR3() {
        return r3;
    }

    public void setR3(String r3) {
        this.r3 = r3;
    }

    public String getR4() {
        return r4;
    }

    public void setR4(String r4) {
        this.r4 = r4;
    }

    public String getR5() {
        return r5;
    }

    public void setR5(String r5) {
        this.r5 = r5;
    }

    public String getR6() {
        return r6;
    }

    public void setR6(String r6) {
        this.r6 = r6;
    }

    public String getR7() {
        return r7;
    }

    public void setR7(String r7) {
        this.r7 = r7;
    }

    public String getR8() {
        return r8;
    }

    public void setR8(String r8) {
        this.r8 = r8;
    }

    public String getR9() {
        return r9;
    }

    public void setR9(String r9) {
        this.r9 = r9;
    }

    public String getOfi1() {
        return ofi1;
    }

    public void setOfi1(String ofi1) {
        this.ofi1 = ofi1;
    }

    public String getOfi2() {
        return ofi2;
    }

    public void setOfi2(String ofi2) {
        this.ofi2 = ofi2;
    }

    public String getOfi3() {
        return ofi3;
    }

    public void setOfi3(String ofi3) {
        this.ofi3 = ofi3;
    }

    public String getOfi4() {
        return ofi4;
    }

    public void setOfi4(String ofi4) {
        this.ofi4 = ofi4;
    }

    public String getOfi5() {
        return ofi5;
    }

    public void setOfi5(String ofi5) {
        this.ofi5 = ofi5;
    }

    public String getOfi6() {
        return ofi6;
    }

    public void setOfi6(String ofi6) {
        this.ofi6 = ofi6;
    }

    public String getOfi7() {
        return ofi7;
    }

    public void setOfi7(String ofi7) {
        this.ofi7 = ofi7;
    }

    public String getDi1() {
        return di1;
    }

    public void setDi1(String di1) {
        this.di1 = di1;
    }

    public String getDi2() {
        return di2;
    }

    public void setDi2(String di2) {
        this.di2 = di2;
    }

    public String getDi3() {
        return di3;
    }

    public void setDi3(String di3) {
        this.di3 = di3;
    }

    public String getDi4() {
        return di4;
    }

    public void setDi4(String di4) {
        this.di4 = di4;
    }

    public String getDi5() {
        return di5;
    }

    public void setDi5(String di5) {
        this.di5 = di5;
    }

    public String getDi6() {
        return di6;
    }

    public void setDi6(String di6) {
        this.di6 = di6;
    }

    public String getDi7() {
        return di7;
    }

    public void setDi7(String di7) {
        this.di7 = di7;
    }

    public String getDi8() {
        return di8;
    }

    public void setDi8(String di8) {
        this.di8 = di8;
    }

    public String getDe1() {
        return de1;
    }

    public void setDe1(String de1) {
        this.de1 = de1;
    }

    public String getDe2() {
        return de2;
    }

    public void setDe2(String de2) {
        this.de2 = de2;
    }

    public String getDe3() {
        return de3;
    }

    public void setDe3(String de3) {
        this.de3 = de3;
    }

    public String getDe4() {
        return de4;
    }

    public void setDe4(String de4) {
        this.de4 = de4;
    }

    public String getDe5() {
        return de5;
    }

    public void setDe5(String de5) {
        this.de5 = de5;
    }

    public String getDe6() {
        return de6;
    }

    public void setDe6(String de6) {
        this.de6 = de6;
    }

    public String getDe7() {
        return de7;
    }

    public void setDe7(String de7) {
        this.de7 = de7;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
