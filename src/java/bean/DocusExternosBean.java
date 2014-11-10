/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DerivarDAO;
import dao.DocumentoDAO;
import dao.DocusExtDAO;
import dao.OficioDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.DocusExtDaoImpl;
import daoimpl.OficioDaoImpl;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.DocusExt;
import maping.DocusExtint;
import maping.Proveido;
import maping.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class DocusExternosBean implements Serializable {

    private String documento;
    private String origen;
    private String destino;
    private List dependenciasprov;
    private Date fechaprov;
    private DocumentoDAO dd;
    private FacesContext faceContext;
    private Usuario usu;
    private DerivarDAO deriv;
    private String correlativo = "";
    private String asunto;
    private Date anio;
    private String codigoexp;
    public boolean a1;
    public boolean a2;
    public String auxfecha;
    public String auxanio;
    public List documentosext;
    private DocusExtDAO ded;
    private List otrosdocus;
    private List docselec;
    private List tiposdocus;
    private OficioDAO od;

    public DocusExternosBean() {
        dd = new DocumentoDaoImpl();
        ded = new DocusExtDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        dependenciasprov = new ArrayList<Map<String, String>>();
        documentosext = new ArrayList<Map<String, String>>();
        deriv = new DerivarDaoImpl();
        dd = new DocumentoDaoImpl();
        od = new OficioDaoImpl();
        tiposdocus= new ArrayList<String>();
        ObtenerDepIndic();
        a1 = true;
        a2 = false;
        MostrarDocusExt();
    }

    public void MostrarDocusExt() {
        System.out.println("mostrar docus extint");
        documentosext.clear();
        try {
            List lista = new ArrayList();
            lista = ded.getDocusExt();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("correlativo", getCadenaCorr(String.valueOf(obj[0])));
                listaaux.put("numerodoc", String.valueOf(String.valueOf(obj[6]) + " - " + obj[1]));
                listaaux.put("movimiento", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("destino", String.valueOf(obj[4]));
                listaaux.put("fecha", String.valueOf(obj[5]));
                listaaux.put("usuario", String.valueOf(obj[7]));
                documentosext.add(listaaux);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Limpiar() {
        documento = " ";
        asunto = " ";
        origen = " ";
        destino = " ";

    }

    public String getCadenaCorr(String c) {
        String cadena = " ";
        if (c.length() == 1) {
            cadena = "0000" + c;
        } else if (c.length() == 2) {
            cadena = "000" + c;
        } else if (c.length() == 3) {
            cadena = "00" + c;
        } else if (c.length() == 4) {
            cadena = "0" + c;
        } else {
            cadena = c;
        }

        return cadena;
    }

    public void ObtenerTiposDocus() {
        System.out.println("listando tipos docus");
        tiposdocus.clear();
        try {
            tiposdocus = od.getTiposDocus("0");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Proveidoo() {
        System.out.println("entra aca 1");
        fechaactual();
        System.out.println("entra aca 2");
        getAnio();
        System.out.println("entra aca 3");
        generarCorrelativo();
        ObtenerTiposDocus();
    }

    public void ObtenerDepIndic() {
        dependenciasprov = dd.getDependencias();
    }

    public void getAnio() {
        System.out.println("entra getanio");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        anio = new Date();
        auxanio = sdf.format(anio);
        System.out.println(auxanio);
    }

    public void fechaactual() {
        System.out.println("entra fechaactual");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaprov = new Date();
        auxfecha = sdf.format(fechaprov);
        System.out.println(auxfecha);
    }

    public void Cambios() {

        correlativo = " ";
        if (documento != null) {
            if (documento.equals("4")) {
                a1 = true;
                a2 = false;
                System.out.println("cambia correlativo");
                generarCorrelativo();
            } else {
                if (!documento.equals("4")) {
                    a1 = false;
                    a2 = true;
                }
            }
        }
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

    public void realizarCambio() {
        if (documento != null) {
            if (this.documento.equals("4")) {
                a1 = true;
                a2 = false;
            } else {
                if (!this.documento.equals("4")) {
                    a1 = false;
                    a2 = true;
                }
            }
        }

    }

    public void guardar() {
        DocusExt de = new DocusExt();
        DocusExtint di = new DocusExtint();
        Proveido p = new Proveido();
        FacesMessage message = null;
        try {
            di.setNumerodoc(codigoexp);
            di.setAsunto(asunto);
            di.setFecha(fechaprov);
            di.setDependenciaByCodigo(deriv.getDep(origen));
            di.setDependenciaByCodigo1(deriv.getDep(destino));
            di.setMovimientoDext(Long.parseLong("1"));
            de = deriv.getDocuExt(documento);
            di.setDocusExt(de);
            di.setUsuario(usu);

            p.setCorrelativod(correlativo);
            p.setDependenciaByCodigo(deriv.getDep(origen));
            p.setDependenciaByCodigo1(deriv.getDep(destino));
            p.setDocusExtint(di);
            p.setFechaenvio(fechaprov);
            p.setFecharegistro(fechaprov);//en un primero momento la fecha de ingreso y de envio del proveido será igual después al derivarse será nulo

            deriv.guardarDocusExt(di);
            deriv.GuardarProveido(p);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha guardado el documento");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            Limpiar();
            MostrarDocusExt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Problemas al guardar");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List getDependenciasprov() {
        return dependenciasprov;
    }

    public void setDependenciasprov(List dependenciasprov) {
        this.dependenciasprov = dependenciasprov;
    }

    public Date getFechaprov() {
        return fechaprov;
    }

    public void setFechaprov(Date fechaprov) {
        this.fechaprov = fechaprov;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
    }

    public String getCodigoexp() {
        return codigoexp;
    }

    public void setCodigoexp(String codigoexp) {
        this.codigoexp = codigoexp;
    }

    public boolean isA1() {
        return a1;
    }

    public void setA1(boolean a1) {
        this.a1 = a1;
    }

    public boolean isA2() {
        return a2;
    }

    public void setA2(boolean a2) {
        this.a2 = a2;
    }

    public String getAuxfecha() {
        return auxfecha;
    }

    public void setAuxfecha(String auxfecha) {
        this.auxfecha = auxfecha;
    }

    public String getAuxanio() {
        return auxanio;
    }

    public void setAuxanio(String auxanio) {
        this.auxanio = auxanio;
    }

    public List getDocumentosext() {
        return documentosext;
    }

    public void setDocumentosext(List documentosext) {
        this.documentosext = documentosext;
    }

    public DocusExtDAO getDed() {
        return ded;
    }

    public void setDed(DocusExtDAO ded) {
        this.ded = ded;
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

    public List getTiposdocus() {
        return tiposdocus;
    }

    public void setTiposdocus(List tiposdocus) {
        this.tiposdocus = tiposdocus;
    }

    public OficioDAO getOd() {
        return od;
    }

    public void setOd(OficioDAO od) {
        this.od = od;
    }

}
