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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.Indicador;
import maping.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class DerivarBean {

    private List docselec2;
    private String numtramaux;
    private boolean aparecer;
    private boolean confirmar = false;
    private DerivarDAO deriv;
    private Usuario usu;
    private String correlativo;
    private String siglasdocus;
    private Date fecha;
    private String fechadia;
    private String fechahora;
    private String usuario = "";
    private DocumentoDAO dd;
    private String motivo = "";
    private final FacesContext faceContext;
    private String docunombre;
    private String asunto;
    private String codinterno;
    private String estado;
    private List seguimientolista2;
    private SeguimientoDAO sgd;
    public List confirmadosderivados;
    public Date fechaIng;
    private Date anio;

    public DerivarBean() {
        dd = new DocumentoDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        this.estado = "EN PROCESO";
        deriv = new DerivarDaoImpl();
        sgd = new SeguimientoDaoImpl();
        anio = new Date();
        seguimientolista2 = new ArrayList<Map<String, String>>();
        confirmadosderivados = new ArrayList<Map<String, String>>();
        MostrarConfirmadosDerivados();
    }

    public String getNombOficina() {
        String oficina = dd.getOficina(usu);
        return oficina;
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
                siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina());
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

            docselec2.clear();
        } catch (Exception e) {
            System.out.println("error fecha");
            System.out.println(e.getMessage());
        }
        return fecha;
    }

    public void Motivo() {
        try {
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

    public void UsuarioSelec() {
        try {
            usuario = usu.getUsuNombre();
        } catch (Exception e) {
            System.out.println("errorusuario");
            System.out.println(e.getMessage());
        }
    }

    public String getAnio() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(anio);
    }

    public String generarCorrelativo() {
        int corr = 0;
        String aux = "";
        try {
            if (getAnio().equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getIndice(siglasdocus, docunombre));
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

    public void Guardar() {
        FacesMessage message = null;
        try {
            System.out.println("entra a guardar");

            DateFormat d = new SimpleDateFormat("yyyy");
            System.out.println("entra a confirmar true");
            System.out.println(docunombre);
            Indicador in = deriv.getIndic(docunombre);
            deriv.InsertarMovimiento(deriv.getMovimiento(numtramaux) + 1, fecha, asunto, estado, numtramaux, getNombOficina(), codinterno, in);
            deriv.InsertarTipoDocus(correlativo, docunombre, 1, siglasdocus, d.format(fecha), numtramaux, fecha, usu);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA DERIVADO EL DOCUMENTO: " + numtramaux);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            limpiar();
            MostrarConfirmadosDerivados();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO DERIVAR");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
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

    public void MostrarConfirmadosDerivados() {
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

    public void MostrarSeguimiento(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista2.clear();
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
                seguimientolista2.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void RealizarCambio() {
        if (docunombre.equals("ARCHIVO")) {
            this.estado = "FINALIZADO";
        } else {
            this.estado = "EN PROCESO";
        }
        correlativo = generarCorrelativo();
    }

    public List getDocselec2() {
        return docselec2;
    }

    public void setDocselec2(List docselec2) {
        this.docselec2 = docselec2;
    }

    public String getNumtramaux() {
        return numtramaux;
    }

    public void setNumtramaux(String numtramaux) {
        this.numtramaux = numtramaux;
    }

    public boolean isAparecer() {
        return aparecer;
    }

    public void setAparecer(boolean aparecer) {
        this.aparecer = aparecer;
    }

    public boolean isConfirmar() {
        return confirmar;
    }

    public void setConfirmar(boolean confirmar) {
        this.confirmar = confirmar;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getSiglasdocus() {
        return siglasdocus;
    }

    public void setSiglasdocus(String siglasdocus) {
        this.siglasdocus = siglasdocus;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDocunombre() {
        return docunombre;
    }

    public void setDocunombre(String docunombre) {
        this.docunombre = docunombre;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCodinterno() {
        return codinterno;
    }

    public void setCodinterno(String codinterno) {
        this.codinterno = codinterno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;

    }

    public List getSeguimientolista2() {
        return seguimientolista2;
    }

    public void setSeguimientolista2(List seguimientolista2) {
        this.seguimientolista2 = seguimientolista2;
    }

    public SeguimientoDAO getSgd() {
        return sgd;
    }

    public void setSgd(SeguimientoDAO sgd) {
        this.sgd = sgd;
    }

    public List getConfirmadosderivados() {
        return confirmadosderivados;
    }

    public void setConfirmadosderivados(List confirmadosderivados) {
        this.confirmadosderivados = confirmadosderivados;
    }

}
