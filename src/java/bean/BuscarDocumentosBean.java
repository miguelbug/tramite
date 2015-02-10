/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DocumentoDAO;
import dao.SeguimientoDAO;
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

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class BuscarDocumentosBean implements Serializable {

    private String codigosdepe;
    private DocumentoDAO dd;
    private List docus;
    private String numerotramite;
    private String codigo;
    private String anio;
    private String asunto;
    private String mes;
    private List filtro;
    private boolean aparece;
    private List seglista;
    private Map<String, String> seleccion;
    private List seguimientolista;
    private SeguimientoDAO sgd;
    private List docselec, anios;

    public BuscarDocumentosBean() {
        dd = new DocumentoDaoImpl();
        docus = new ArrayList<Map<String, String>>();
        seglista = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        sgd = new SeguimientoDaoImpl();
        anios = new ArrayList<String>();
        aparece = false;
        mostrarAnios();
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

}
