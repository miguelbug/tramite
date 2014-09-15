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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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

    public SeguimientoBean() {
        dd= new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        sgd = new SeguimientoDaoImpl();
        seguimientolista = new ArrayList<Map<String, String>>();
        seguimientolista2 = new ArrayList<Map<String, String>>();
        MostrarParaUsuario();

    }

    //seguimiento para un cierto tramite
    public void MostrarSeguimiento(String tramnum) {
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

    public void RecorrerLista() {
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            if (e.getKey().toString().equals("numerotramite")) {
                System.out.println(e.getValue().toString());
                MostrarSeguimiento(e.getValue().toString());
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
        fechadia="";
        fechahora="";
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
                    motivo=dd.getMotivo(hm.get("numerotramite").toString());
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
    
}
