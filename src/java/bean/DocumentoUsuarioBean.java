/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import dao.DocumentoDAO;
import dao.DocusInternosDAO;
import dao.OficioDAO;
import dao.SeguimientoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.DocusInternosDaoImpl;
import daoimpl.OficioDaoImpl;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Indicador;
import maping.TramiteMovimiento;
import maping.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class DocumentoUsuarioBean {

    private List seguimientolista2, seguimientolista, confirmados, otrosdocus, docselec, detalle, docselec2, confirmadosderivados;
    private Map<String, String> seleccion;
    private DocumentoDAO dd;
    private Date fecha, anio;
    private Usuario usu;
    private String fechadia, fechahora, motivo = "", usuario = "", codinterno, numtramaux, asunto, siglasdocus, correlativo = "", docunombre, estado, tramaux,llego,confirme,docresp,docofic;
    private final FacesContext faceContext;
    private SeguimientoDAO sgd;
    private DerivarDAO deriv;
    private boolean confirmar = false, aparecer;
    private DocusInternosDAO di;
    private OficioDAO ofi;

    public DocumentoUsuarioBean() {
        dd = new DocumentoDaoImpl();
        ofi= new OficioDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        seguimientolista2 = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        confirmados = new ArrayList<Map<String, String>>();
        detalle = new ArrayList<Map<String, String>>();
        di = new DocusInternosDaoImpl();
        confirmadosderivados = new ArrayList<Map<String, String>>();
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        MostrarParaUsuario();
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Sin Confirmar")) {
            MostrarParaUsuario();

        }

    }

    /*public void MostrarConfirmadosDerivados() {
     System.out.println("CONFIRMADOS DERIVADOS¡¡¡¡¡");
     confirmadosderivados.clear();
     try {
     System.out.println("entra a seguimiento3");
     List lista = new ArrayList();
     System.out.println(usu.getOficina().getIdOficina());
     lista = deriv.getConfDeriv(usu.getOficina().getIdOficina());
     Iterator ite = lista.iterator();
     Object obj[] = new Object[9];
     while (ite.hasNext()) {
     obj = (Object[]) ite.next();
     Map<String, String> listaaux = new HashMap<String, String>();
     listaaux.put("movimnum", String.valueOf(obj[0]));
     listaaux.put("numerotramite", String.valueOf(obj[1]));
     listaaux.put("origen", String.valueOf(obj[2]));
     listaaux.put("destino", String.valueOf(obj[3]));
     listaaux.put("fechaenvio", String.valueOf(obj[4]));
     listaaux.put("fechaingr", String.valueOf(obj[5]));
     listaaux.put("observacion", String.valueOf(obj[6]));
     listaaux.put("estado", String.valueOf(obj[7]));
     listaaux.put("indicador", String.valueOf(obj[8]));
     confirmadosderivados.add(listaaux);
     }
     } catch (Exception e) {
     System.out.println(e.getMessage());
     }
     }

     public void MostrarConfirmados() {
     System.out.println("CONFIRMADOS¡¡¡¡¡");
     confirmados.clear();
     try {
     System.out.println("entra a seguimiento2");
     List lista = new ArrayList();
     System.out.println(usu.getOficina().getIdOficina());
     lista = deriv.getConfirmados(usu.getOficina().getIdOficina());
     Iterator ite = lista.iterator();
     Object obj[] = new Object[9];
     while (ite.hasNext()) {
     obj = (Object[]) ite.next();
     Map<String, String> listaaux = new HashMap<String, String>();
     listaaux.put("movimnum", String.valueOf(obj[0]));
     listaaux.put("numerotramite", String.valueOf(obj[1]));
     listaaux.put("origen", String.valueOf(obj[2]));
     listaaux.put("destino", String.valueOf(obj[3]));
     listaaux.put("fechaenvio", String.valueOf(obj[4]));
     listaaux.put("fechaingr", String.valueOf(obj[5]));
     listaaux.put("observacion", String.valueOf(obj[6]));
     listaaux.put("estado", String.valueOf(obj[7]));
     listaaux.put("indicador", String.valueOf(obj[8]));
     confirmados.add(listaaux);
     }
     } catch (Exception e) {
     System.out.println(e.getMessage());
     }
     }*/
    public void MostrarParaUsuario() {
        System.out.println("listando documentos2");
        seguimientolista2.clear();
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            System.out.println(usu.getOficina().getIdOficina());
            lista = sgd.seguimientoUser(usu.getOficina().getIdOficina());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
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
                listaaux.put("estadDoc", String.valueOf(obj[9]));
                listaaux.put("docgene", di.getRespuesta(String.valueOf(obj[0])));
                seguimientolista2.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List Detalles() {
        System.out.println("listando detalles");
        detalle.clear();
        try {
            System.out.println(seleccion.get("numerotramite").toString());
            System.out.println(seleccion.get("fechaenvio").toString());
            System.out.println(seleccion.get("fechaingr").toString());
            //lista = dd.getDetalle(seleccion.get("numerotramite").toString());
            //Iterator ite = lista.iterator();
            //Object obj[] = new Object[8];
            //while (ite.hasNext()) {
                //obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("FECHAENVIO", seleccion.get("fechaenvio").toString());
                listaaux.put("FECHAINGR", seleccion.get("fechaingr").toString());
                listaaux.put("RESP", di.getRespuesta(seleccion.get("numerotramite").toString()));
                listaaux.put("OFICIO", ofi.getOficioDocumento(seleccion.get("numerotramite").toString()));
                detalle.add(listaaux);
           // }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return detalle;
    }
    /*public String llego(){
        if(seleccion.get("fechaenvio").toString().equals(null)){
            llego="";
        }else{
            llego=seleccion.get("fechaenvio").toString();
        }
        
        return llego;
    }
    public String confirme(){
        if(seleccion.get("fechaingr").toString().equals(null)){
            confirme="";
        }
        else{
            confirme=seleccion.get("fechaingr").toString();
        }
        
        return confirme;
    }
    public String docresp(){
        if(seleccion.get("tramnnum").toString().equals(null)){
            docresp="";
        }else{
            docresp=di.getRespuesta(seleccion.get("tramnnum").toString());
        }
        
        return docresp;
    }
    public String docofic(){
        if(seleccion.get("tramnnum").toString().equals(null)){
            docofic="";
        }else{
            docofic=ofi.getOficioDocumento(seleccion.get("tramnnum").toString());
        }
        
        return docofic;
    }
    /*
    public void Detalle(){
        llego=seleccion.get("fechaenvio").toString();
        confirme=seleccion.get("fechaingr").toString();
        docresp=di.getRespuesta(seleccion.get("tramnnum").toString());
        docofic=ofi.getOficioDocumento(seleccion.get("tramnnum").toString());
    }*/

    public void Confirmar() {
        try {
            System.out.println("ENTRA A CONFIRMAR");
            String ntram = "";
            int movi = 0;
            FacesMessage message = null;
            for (int i = 0; i < docselec2.size(); i++) {
                System.out.println("entra al bucle for");
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
                Iterator it = hm.entrySet().iterator();
                TramiteMovimiento tm = new TramiteMovimiento();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    if (e.getKey().toString().equals("numerotramite")) {
                        ntram = e.getValue().toString();

                    }
                    if (e.getKey().toString().equals("movimnum")) {
                        movi = Integer.parseInt(e.getValue().toString());

                    }
                }
                Date nuevFech = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                deriv.ConfirmarTramites(ntram, movi, formato2.parse(formato.format(nuevFech)));
                ntram = "";
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Realizado", "Se ha confirmado el documento");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            MostrarParaUsuario();
        } catch (Exception e) {
            System.out.println("ERROR CONFIRMAR");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void limpiar() {
        numtramaux = "";
        asunto = "";
        estado = "";
        codinterno = "";
        correlativo = "";
        docunombre = "";
        siglasdocus = "";
    }

    public String getNombOficina() {
        String oficina = dd.getOficina(usu);
        return oficina;
    }

    public void IniciarFecha() {
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

    public void UsuarioSelec() {
        try {
            usuario = usu.getUsuNombre();
        } catch (Exception e) {
            System.out.println("errorusuario");
            System.out.println(e.getMessage());
        }
    }

    /*----DERIVACION---------*/
    public void RecorrerLista() {
        System.out.println(docselec2);
        Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            if (e.getKey().toString().equals("numerotramite")) {
                System.out.println(e.getValue().toString());
                MostrarSeguimiento(e.getValue().toString());
            }

        }
        docselec2.clear();

    }

    public void MostrarSeguimiento(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista2.clear();
        try {
            List lista = new ArrayList();
            lista = sgd.getSeguimientoGrande(tramnum);
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
        numtramaux = "";
        FacesMessage message = null;
        try {
            if (getFechaIngr().equals(" ")) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Primero debe Confirmar");
                System.out.println("entra a derivar null");
                aparecer = false;

            } else {
                System.out.println("entra a getsiglas");
                siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
                correlativo = generarCorrelativo();
                System.out.println("entra a iniciar fecha");
                IniciarFecha();
                System.out.println("entra a motivo");
                Motivo();
                System.out.println("entra a usuarioselec");
                UsuarioSelec();
            }
        } catch (Exception e) {
            System.out.println("error derivar");
            System.out.println(e.getMessage());
        }

    }

    public String generarCorrelativo() {
        int corr = 0;
        String aux = "";
        anio = new Date();
        try {
            if (getAnio().equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getIndice(siglasdocus, docunombre));
                System.out.println("numerocorrelativo:" + corr);
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
        }
        return aux;
    }

    public String getAnio() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(anio);
    }

    public void RealizarCambio() {
        if (docunombre.equals("ARCHIVO")) {
            this.estado = "FINALIZADO";
        } else {
            this.estado = "EN PROCESO";
        }
        correlativo = generarCorrelativo();
    }

    public void Guardar() {
        FacesMessage message = null;
        try {
            System.out.println("entra a guardar");

            DateFormat d = new SimpleDateFormat("yyyy");
            System.out.println("entra a confirmar true");
            System.out.println(docunombre);
            Indicador in = deriv.getIndic(docunombre);
            /////actualizarmovimiento
            /*deriv.ActualizarTramite(numtramaux, String.valueOf(deriv.getMovimiento(numtramaux)));
             deriv.InsertarMovimiento(deriv.getMovimiento(numtramaux) + 1, fecha, asunto, estado, numtramaux, getNombOficina(), codinterno, in);
             deriv.InsertarTipoDocus(correlativo, docunombre, 1, siglasdocus, d.format(fecha), numtramaux, fecha, usu);*/
            for (int i = 0; i < docselec2.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
                deriv.ActualizarTramite(hm.get("numerotramite").toString(), String.valueOf(deriv.getMovimiento(hm.get("numerotramite").toString())));
                deriv.InsertarMovimiento(deriv.getMovimiento(hm.get("numerotramite").toString()) + 1, fecha, asunto, hm.get("estado").toString(), hm.get("numerotramite").toString(), getNombOficina(), codinterno, in);
                deriv.InsertarTipoDocus(correlativo, docunombre, 1, siglasdocus, d.format(fecha), hm.get("numerotramite").toString(), fecha, usu, asunto);
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "DERIVADO: " + numtramaux, "DOCUMENTO DE RESPUESTA: " + correlativo);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            limpiar();
            MostrarParaUsuario();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO DERIVAR");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Date getFechaIng() {
        Date fecha = null;
        try {
            Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getKey().toString().equals("fechaingr")) {
                    System.out.println(e.getValue().toString());
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    fecha = formato.parse(e.getValue().toString());
                }

            }

            //docselec2.clear();
        } catch (Exception e) {
            System.out.println("error fecha");
            System.out.println(e.getMessage());
        }
        return fecha;
    }

    public void Motivo() {
        /*try {
         Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
         Iterator it = hm.entrySet().iterator();
         while (it.hasNext()) {
         Map.Entry e = (Map.Entry) it.next();
         if (e.getKey().toString().equals("numerotramite")) {
         System.out.println(e.getValue().toString());
         numtramaux = e.getValue().toString();
         motivo = dd.getMotivo(e.getValue().toString());
         }

         }

         docselec2.clear();
         } catch (Exception e) {
         System.out.println("errormotivo");
         System.out.println(e.getMessage());
         e.printStackTrace();
         }*/
        try {
            for (int i = 0; i < docselec2.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
                if (i == 0) {
                    System.out.println(hm.get("numerotramite").toString());
                    numtramaux = numtramaux + " / " + hm.get("numerotramite").toString();
                    motivo = dd.getMotivo(hm.get("numerotramite").toString());
                    asunto=motivo;
                } else {
                    numtramaux = numtramaux + " / " + hm.get("numerotramite").toString();
                }

                /*Iterator it = hm.entrySet().iterator();
                 while (it.hasNext()) {
                 Map.Entry e = (Map.Entry) it.next();
                 if (e.getKey().toString().equals("numerotramite") && i == 0) {
                 System.out.println(e.getValue().toString());
                 //numtramaux = e.getValue().toString();
                 motivo = dd.getMotivo(e.getValue().toString());
                 }
                 if (e.getKey().toString().equals("numerotramite")) {
                 System.out.println(e.getValue().toString());
                 numtramaux = numtramaux + " / " + e.getValue().toString();
                 }
                 }*/
            }

            //docselec2.clear();
        } catch (Exception e) {
            System.out.println("errormotivo");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getFechaIngr() {
        String fecha = "";
        System.out.println(docselec2);
        try {
            Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getKey().toString().equals("fechaingr")) {
                    System.out.println("fecha aca");
                    System.out.println(e.getValue().toString());
                    fecha = e.getValue().toString();
                }
            }
        } catch (Exception e) {
            System.out.println("errorfecha");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return fecha;
    }
    /*----DERIVACION---------*/

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

    public List getSeguimientolista2() {
        return seguimientolista2;
    }

    public void setSeguimientolista2(List seguimientolista2) {
        this.seguimientolista2 = seguimientolista2;
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

    public Map<String, String> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Map<String, String> seleccion) {
        this.seleccion = seleccion;
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

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
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

    public List getDetalle() {
        return detalle;
    }

    public void setDetalle(List detalle) {
        this.detalle = detalle;
    }

    public SeguimientoDAO getSgd() {
        return sgd;
    }

    public void setSgd(SeguimientoDAO sgd) {
        this.sgd = sgd;
    }

    public List getSeguimientolista() {
        return seguimientolista;
    }

    public void setSeguimientolista(List seguimientolista) {
        this.seguimientolista = seguimientolista;
    }

    public String getNumtramaux() {
        return numtramaux;
    }

    public void setNumtramaux(String numtramaux) {
        this.numtramaux = numtramaux;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getSiglasdocus() {
        return siglasdocus;
    }

    public void setSiglasdocus(String siglasdocus) {
        this.siglasdocus = siglasdocus;
    }

    public String getDocunombre() {
        return docunombre;
    }

    public void setDocunombre(String docunombre) {
        this.docunombre = docunombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isConfirmar() {
        return confirmar;
    }

    public void setConfirmar(boolean confirmar) {
        this.confirmar = confirmar;
    }

    public boolean isAparecer() {
        return aparecer;
    }

    public void setAparecer(boolean aparecer) {
        this.aparecer = aparecer;
    }

    public List getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(List confirmados) {
        this.confirmados = confirmados;
    }

    public List getDocselec2() {
        return docselec2;
    }

    public void setDocselec2(List docselec2) {
        this.docselec2 = docselec2;
    }

    public List getConfirmadosderivados() {
        return confirmadosderivados;
    }

    public void setConfirmadosderivados(List confirmadosderivados) {
        this.confirmadosderivados = confirmadosderivados;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
    }

    public String getTramaux() {
        return tramaux;
    }

    public void setTramaux(String tramaux) {
        this.tramaux = tramaux;
    }

    public String getLlego() {
        return llego;
    }

    public void setLlego(String llego) {
        this.llego = llego;
    }

    public String getConfirme() {
        return confirme;
    }

    public void setConfirme(String confirme) {
        this.confirme = confirme;
    }

    public String getDocresp() {
        return docresp;
    }

    public void setDocresp(String docresp) {
        this.docresp = docresp;
    }

    public String getDocofic() {
        return docofic;
    }

    public void setDocofic(String docofic) {
        this.docofic = docofic;
    }

    public DocusInternosDAO getDi() {
        return di;
    }

    public void setDi(DocusInternosDAO di) {
        this.di = di;
    }

}
