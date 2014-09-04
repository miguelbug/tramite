/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import dao.SeguimientoDAO;
import daoimpl.SeguimientoDaoImpl;
import java.util.ArrayList;
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
public class SeguimientoBean {

    private List seguimientolista;
    private SeguimientoDAO sgd;
    private List docselec;
    
    public SeguimientoBean() {
        sgd= new SeguimientoDaoImpl();
        seguimientolista= new ArrayList<Map<String,String>>();
    }
    public void MostrarSeguimiento(String tramnum){
        System.out.println("listando documentos");
        seguimientolista.clear();
        try {
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
                listaaux.put("fechaenvio",String.valueOf(obj[4]));
                listaaux.put("fechaingr",String.valueOf(obj[5]));
                listaaux.put("indicador",String.valueOf(obj[6]));
                listaaux.put("observacion",String.valueOf(obj[7]));
                listaaux.put("estado",String.valueOf(obj[8]));
                seguimientolista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void RecorrerLista(){
        for(int i=0;i<docselec.size();i++){
            MostrarSeguimiento(docselec.get(i).toString());            
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
    
}
