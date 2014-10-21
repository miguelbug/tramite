/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import dao.OficioDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.OficioDaoImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@RequestScoped
public class OficioBean {

    private String tipodepe;
    private List oficioscirculares;
    private OficioDAO od;
    private Date anio;
    private String auxanio;
    private DerivarDAO deriv;
    private String correlativo = "";
    private List otrosdocus;
    private List docselec;
    public List depe2;
    private List depe;
    private String[] escojidos;
    private String asunto;
    private Date fecha;
    private String firma;
    private String responsable;
    private String arearesponsable;

    public OficioBean() {
        od = new OficioDaoImpl();
        oficioscirculares = new ArrayList<Map<String, String>>();
        depe2 = new ArrayList<Map<String, String>>();
        deriv = new DerivarDaoImpl();
        depe = new ArrayList<Map<String, String>>();
        mostrarofCirc();
        llenardepes();
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

    public void llenar(ActionEvent ex) {
        System.out.println(tipodepe);
        boolean valor=false;
        String tipo="";
        String nombre="";
        for (int i = 0; i < depe.size(); i++) {
            HashMap hashMap = (HashMap) depe.get(i);
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                System.out.println("key: "+e.getKey().toString()+"----"+"Value: "+e.getValue().toString());
                if(e.getKey().toString().equals("nombre")){
                    nombre=e.getValue().toString();
                }
                if(e.getKey().toString().equals("tipo")){
                    tipo=e.getValue().toString();
                }
            }
            System.out.println("\n");
            if(tipodepe.equals(tipo)){
                System.out.println("ENTRA A GUARDAR EN DEPE2");
                Map<String, String> listaaux=new HashMap<String,String>();
                listaaux.put("nombre",nombre);
                depe2.add(listaaux);
            }
            valor=false;
        }
    }
    

    public void guardar() {

    }

    public void mostrarofCirc() {
        System.out.println("listando documentos");
        oficioscirculares.clear();
        try {
            List lista = new ArrayList();
            lista = od.getOficiosCirculares();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[5];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", String.valueOf(obj[0]));
                listaaux.put("asunto", String.valueOf(obj[1]));
                listaaux.put("origen", String.valueOf(obj[2]));
                listaaux.put("destino", String.valueOf(obj[3]));
                listaaux.put("fecha", String.valueOf(obj[4]));
                oficioscirculares.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void generarFecha(){
        fecha= new Date();
    }
    public void crearOficio() {
        generarFecha();
        getAnio();
        generarCorrelativo();
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

}
