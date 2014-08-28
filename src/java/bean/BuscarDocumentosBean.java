/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DocumentoDAO;
import daoimpl.DocumentoDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class BuscarDocumentosBean implements Serializable{

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

    public BuscarDocumentosBean() {
        dd = new DocumentoDaoImpl();
        docus = new ArrayList<Map<String,String>>();
        aparece=false;
    }

    public void Buscar() {
        docus.clear();
        System.out.println("listando");
        System.out.println(numerotramite+"-"+codigosdepe+"-"+anio+"-"+asunto+"-"+mes);
        try {
            List lista = new ArrayList();
            lista = dd.getBusqueda(numerotramite, codigosdepe, anio, asunto, mes);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("fecha", String.valueOf(obj[1]));
                listaaux.put("observacion", String.valueOf(obj[2]));
                listaaux.put("usuario", String.valueOf(obj[3]));
                listaaux.put("descripcion", String.valueOf(obj[4]));
                listaaux.put("docunombre", String.valueOf(obj[5]));
                listaaux.put("docunumero", String.valueOf(obj[6]));
                listaaux.put("docusiglas", String.valueOf(obj[7]));
                listaaux.put("docuanio", String.valueOf(obj[8]));
                listaaux.put("departorigen", String.valueOf(obj[9]));
                docus.add(listaaux);
            }
            aparece=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(aparece);
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
    
}
