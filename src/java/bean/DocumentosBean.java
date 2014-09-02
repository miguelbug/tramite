/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

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
import daoimpl.DocumentoDaoImpl;
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
<<<<<<< HEAD
=======


>>>>>>> 39b427df186bae3ea018d1c32e595439eb6d519d
    private List otrosdocus;
    private List docselec;
    private boolean mostrar=false;
    private List seglista;
    private String seleccion;

    public DocumentosBean() {
        dd = new DocumentoDaoImpl();
        documentos = new ArrayList<Map<String,String>>();
        docselec = new ArrayList<Map<String,String>>();
        MostrarDocumentos();
        
    }

    public void MostrarDocumentos() {
        System.out.println("listando documentos");
        documentos.clear();
        try {
            List lista = new ArrayList();
            lista = dd.getDocumentos();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[4];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("fecha", String.valueOf(obj[1]));
                listaaux.put("observacion", String.valueOf(obj[2]));
                listaaux.put("descripcion", String.valueOf(obj[3]));
                documentos.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public List Detalles(){
        System.out.println("listando detalles");
        //seglista.clear();
        try {
            List lista = new ArrayList();
            System.out.println(seleccion);
            lista = dd.getDetalle(seleccion);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("usuario", String.valueOf(obj[0]));
                listaaux.put("usunombre", String.valueOf(obj[1]));
                listaaux.put("oficina", String.valueOf(obj[2]));
                listaaux.put("depnombre", String.valueOf(obj[3]));
                listaaux.put("docunombre", String.valueOf(obj[4]));
                listaaux.put("docunumero", String.valueOf(obj[5]));
                listaaux.put("docusiglas", String.valueOf(obj[6]));
                listaaux.put("docuanio", String.valueOf(obj[7]));
                seglista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return seglista;
    }
    public void cambiar(){
        mostrar=true;
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

    public List getDocselec() {
        return docselec;
    }

    public void setDocselec(List docselec) {
        this.docselec = docselec;
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

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }
    
}
