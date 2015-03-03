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
    private List docus, nuevaBusq, nuevaBusqOgpl,nuevabusqogplkp, otrosdocus, docselec3;
    private String numerotramite;
    private String nuevotramite;
    private String nuevoasunto;
    private String derivadoa;
    private String codigo;
    private String anio;
    private String asunto;
    private String mes;
    private List filtro, filtro2;
    private boolean aparece;
    private List seglista;
    private Map<String, String> seleccion, seleccion2;
    private List seguimientolista;
    private SeguimientoDAO sgd;
    private List docselec, anios, docselec2;
    private FacesContext faceContext;
    private Usuario usu;

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
        sgd = new SeguimientoDaoImpl();
        anios = new ArrayList<String>();
        aparece = false;
        //mostrarAnios();
        mostrarListaBusquedaOGPL() ;
        mostrarListaBusqueda();
    }
    public void mostrarNuevaListaBusqOGPL(){
        nuevabusqogplkp.clear();
        System.out.println("listando");
        try {
            List lista = new ArrayList();
            lista = dd.nuevaBusqAvanzada(nuevotramite,nuevoasunto,derivadoa);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[4];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("expediente", String.valueOf(obj[0]));
                listaaux.put("fechaing", String.valueOf(obj[1]));
                listaaux.put("asunto", String.valueOf(obj[2]));
                listaaux.put("derivadoA", String.valueOf(obj[3]));
                nuevabusqogplkp.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

}
