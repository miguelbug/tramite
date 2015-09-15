/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.DocumentoUsuarioBean.correla_exportar;
import static bean.DocumentoUsuarioBean.fecha_exportar;
import static bean.DocumentoUsuarioBean.movimiento_exportar;
import static bean.DocumentoUsuarioBean.tramnum_exportar;
import dao.DerivarDAO;
import dao.DocumentoDAO;
import dao.DocusInternosDAO;
import dao.OficioDAO;
import dao.SeguimientoDAO;
import daoimpl.DerivarDaoImpl;
import daoimpl.DocumentoDaoImpl;
import daoimpl.DocusInternosDaoImpl;
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
import maping.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class Expedientes_Ogpl {

    private List seguimientolista, seguimientolista2, designados, tiposdocus, detalle;
    private List otrosdocus;
    private List docselec;
    private DocumentoDAO dd;
    private Usuario usu;
    private final FacesContext faceContext;
    private SeguimientoDAO sgd;
    private DerivarDAO deriv;
    private DocusInternosDAO di;
    private OficioDAO ofi;
    private Map<String, String> seleccion;
    private String fechaconfirmar;
    private String fechahora2;
    private String numtramaux;
    private String siglasdocus;
    private String correlativo;
    private Date anio;
    private Date fecha;
    private String fechadia;
    private String fechahora;
    private String fechanio;
    private String docunombre;
    private String motivo;
    private String asunto;
    private String usuario;
    private String asignado;
    private String estado, nombOficina;
    private String codinterno;
    private String correlativoaux;

    public Expedientes_Ogpl() {
        seguimientolista = new ArrayList<Map<String, String>>();
        seguimientolista2 = new ArrayList<Map<String, String>>();
        designados = new ArrayList<String>();
        detalle = new ArrayList<Map<String, String>>();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        dd = new DocumentoDaoImpl();
        di = new DocusInternosDaoImpl();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean isdocumentosExpOgpl = (currentPage.lastIndexOf("ExpedientesOgpl.xhtml") > -1);
        if (isdocumentosExpOgpl) {
            MostrarParaUsuario();
        }
    }

    public void RecorrerLista() {
        System.out.println("recorrer");
        Map<String, String> hm = (HashMap<String, String>) docselec.get(0);
        System.out.println(hm.get("numerotramite"));
        MostrarSeguimiento(hm.get("numerotramite"));
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

    public void MostrarParaUsuario() {
        System.out.println("listando documentos2");
        seguimientolista.clear();
        Date anio = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        String auxanio = sdf.format(anio);
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            System.out.println(usu.getOficina().getIdOficina());
            lista = sgd.seguimientoUser(usu.getOficina().getIdOficina(), deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu()));
            Iterator ite = lista.iterator();
            Object obj[] = new Object[15];
            String respuesta = "";
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("numerotramite", String.valueOf(obj[0]));
                listaaux.put("tramfecha", String.valueOf(obj[1]));
                listaaux.put("movimnum", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("destino", String.valueOf(obj[4]));
                listaaux.put("fechaenvio", String.valueOf(obj[5]));
                listaaux.put("fechaingr", String.valueOf(obj[6]));
                listaaux.put("indicador", String.valueOf(obj[7]));
                listaaux.put("movimiento", String.valueOf(obj[8]));
                listaaux.put("estado", String.valueOf(obj[9]));
                listaaux.put("estadoConfirm", String.valueOf(obj[10]));
                listaaux.put("usuario",String.valueOf(obj[11]));
                listaaux.put("origPrincipal",String.valueOf(obj[12]));
                listaaux.put("docuprincipal",String.valueOf(obj[13]));
                listaaux.put("estado2",String.valueOf(obj[14]));
                
                seguimientolista.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void abrirAsignacion() {
        String fechavacia = "nf", fechallena = "nf";
        fechaconfirmar = "";
        this.fechahora2 = "";
        for (int i = 0; i < docselec.size(); i++) {
            Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
            if (hm.get("fechaingr").toString().equals(" ")) {
                fechavacia = hm.get("fechaingr").toString();
            } else {
                if (!hm.get("fechaingr").toString().equals(" ")) {
                    fechallena = hm.get("fechaingr").toString();
                }
            }
        }
        llenarDesignados();
        if (fechavacia.equals(" ") && fechallena.equals("nf")) {
            Date nuevafecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            StringTokenizer tokens = new StringTokenizer(sdf.format(nuevafecha), " ");
            while (tokens.hasMoreTokens()) {
                if (fechaconfirmar.equals("")) {
                    fechaconfirmar = tokens.nextToken();
                }
                if (fechahora2.equals("")) {
                    fechahora2 = tokens.nextToken();
                }
            }
        } else {
            if (!fechallena.equals(" ")) {
                StringTokenizer tokens = new StringTokenizer(fechallena, " ");
                while (tokens.hasMoreTokens()) {
                    if (fechaconfirmar.equals("")) {
                        fechaconfirmar = tokens.nextToken();
                    }
                    if (fechahora2.equals("")) {
                        fechahora2 = tokens.nextToken();
                    }
                }
            }
        }
        System.out.println("FECHA Y HORA: " + fechaconfirmar + "-" + fechahora2);
    }

    public void llenarDesignados() {
        designados = sgd.getDesignados(usu.getOficina().getIdOficina());
    }

    public void Derivar() {
        numtramaux = "";
        boolean deriva = true;
        FacesMessage message = null;
        for (int i = 0; i < docselec.size(); i++) {
            HashMap<String, String> hm = (HashMap<String, String>) docselec.get(i);
            if (hm.get("estadDoc").toString().equals("SIN CONFIRMAR") || hm.get("estadDoc").toString().equals("DERIVADO") || hm.get("estadDoc").toString().equals("ARCHIVADO")) {
                deriva = false;
                System.out.println("ENTRÓ");
                break;
            }
        }
        if (deriva) {
            try {
                getTipoDocumentos();
                System.out.println("entra a getsiglas");
                siglasdocus = deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu());
                correlativo = generarCorrelativo();
                System.out.println("entra a iniciar fecha");
                IniciarFecha();
                System.out.println("entra a motivo");
                Motivo();
                System.out.println("entra a usuarioselec");
                UsuarioSelec();
                HashMap<String, String> hm = (HashMap<String, String>) docselec.get(0);
                tramnum_exportar = hm.get("numerotramite").toString();
                fecha_exportar = hm.get("fechaenvio").toString();
                movimiento_exportar = hm.get("movimnum").toString();
                System.out.println("ESTA ES LA FECHA DE ENVIO: " + fecha_exportar + " Mov: " + movimiento_exportar);
                estado = "EN PROCESO";
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('derivarDialog').show()");
            } catch (Exception e) {
                System.out.println("error derivar");
                System.out.println(e.getMessage());
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ADVERTENCIA", "PRIMERO DEBE CONFIRMAR/DOCUMENTO YA TRABAJADO");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

    }

    public void ConfirmarAsignar() {
        Confirmar();
        System.out.println("\nENTRA A ASIGNAR\n");
        Asignar();
        System.out.println("\nSALE DE ASIGNAR\n");
        docselec.clear();
        asignado = " ";
    }

    public void Asignar() {
        System.out.println("DIMENSION DEL DOCSELEC: " + docselec.size());
        FacesMessage message = null;
        try {
            String ntram = "";
            int movi = 0;
            for (int i = 0; i < docselec.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                ntram = hm.get("numerotramite").toString();
                movi = Integer.parseInt(hm.get("movimnum").toString());
                System.out.println("entra a actualizar usuario");
                deriv.ActualizarUsuario(ntram, String.valueOf(movi), getusuario(asignado));
                System.out.println("sale de actualizar usuario");
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Realizado", "Se ha confirmado y asignado");
            MostrarParaUsuario();
        } catch (Exception e) {
            System.out.println("ERROR ASIGNAR");
            System.out.println(e.getMessage());
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA REALIZADO LA ACCION");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public Usuario getusuario(String nombre) {
        Usuario usu = new Usuario();
        List lista = new ArrayList();
        lista = deriv.getUsu(nombre);
        Iterator ite = lista.iterator();
        Object obj[] = new Object[5];
        while (ite.hasNext()) {
            obj = (Object[]) ite.next();
            usu.setUsu(String.valueOf(obj[0]));
            usu.setUsuNombre(String.valueOf(obj[1]));
            usu.setClave(String.valueOf(obj[2]));
            usu.setEstado(String.valueOf(obj[3]));
            usu.setOficina(deriv.getOficina(String.valueOf(obj[4])));
        }
        return usu;
    }

    public List Detalles() {
        System.out.println("listando detalles");
        detalle.clear();
        Date anio = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            System.out.println(seleccion.get("numerotramite").toString());
            System.out.println(seleccion.get("fechaenvio").toString());
            System.out.println(seleccion.get("movimnum").toString());
            System.out.println(seleccion.get("fechaingr").toString());
            Map<String, String> listaaux = new HashMap<String, String>();
            listaaux.put("FECHAENVIO", seleccion.get("fechaenvio").toString());
            listaaux.put("FECHAINGR", seleccion.get("fechaingr").toString());
            listaaux.put("RESP", di.getRespuesta(seleccion.get("numerotramite").toString(), seleccion.get("movimnum").toString()));
            listaaux.put("OFICIO", "N° " + ofi.getOficioDocumento(seleccion.get("numerotramite").toString()) + "-OGPL-" + sdf.format(anio));
            detalle.add(listaaux);
            // }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return detalle;
    }

    public void Confirmar() {
        try {
            System.out.println("ENTRA A CONFIRMAR");
            String ntram = "";
            int movi = 0;
            FacesMessage message = null;
            for (int i = 0; i < docselec.size(); i++) {
                System.out.println("entra al bucle for");
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                ntram = hm.get("numerotramite").toString();
                movi = Integer.parseInt(hm.get("movimnum").toString());
                Date nuevFech = new Date();
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                nuevFech = sdf2.parse(fechaconfirmar + " " + fechahora2);
                System.out.println("Movimiento: " + movi);
                deriv.ConfirmarTramites(ntram, movi, nuevFech);
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

    public void UsuarioSelec() {
        try {
            usuario = usu.getUsuNombre();
        } catch (Exception e) {
            System.out.println("errorusuario");
            System.out.println(e.getMessage());
        }
    }

    public void Motivo() {
        try {
            for (int i = 0; i < docselec.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                if (i == 0) {
                    System.out.println(hm.get("numerotramite").toString());
                    numtramaux = numtramaux + " " + hm.get("numerotramite").toString();
                    motivo = dd.getMotivo(hm.get("numerotramite").toString(), hm.get("tramfecha").toString());
                    asunto = motivo;
                } else {
                    numtramaux = numtramaux + " " + hm.get("numerotramite").toString();
                }
            }

        } catch (Exception e) {
            System.out.println("errormotivo");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String generarCorrelativo() {
        int corr = 0;
        String aux = "";
        anio = new Date();
        try {
            if (getAnio().equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getIndice(siglasdocus, docunombre, getAnio()));
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

    public void IniciarFecha() {
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat formato2 = new SimpleDateFormat("yyyy");
        fecha = new Date();
        fechadia = "";
        fechahora = "";
        this.fechanio = "";
        StringTokenizer tokens = new StringTokenizer(formato.format(fecha), " ");
        while (tokens.hasMoreTokens()) {
            if (fechadia.equals("")) {
                fechadia = tokens.nextToken();
            }
            if (fechahora.equals("")) {
                fechahora = tokens.nextToken();
            }
        }
        fechanio = formato2.format(fecha);
    }

    public void RealizarCambio() {
        if (docunombre.equals("ARCHIVO")) {
            this.estado = "FINALIZADO";
            codinterno = deriv.getCodigoUsuario(usu.getUsu());
        } else {
            this.estado = "EN PROCESO";
            codinterno = "100392";
        }
        correlativo = generarCorrelativo();
        correla_exportar = correlativo;
        this.correlativoaux = correlativo;
        System.out.println("correlativo exportar: " + correla_exportar);

    }

    public void Guardar() {
        String mensaje = docunombre + " N° " + correlativo;
        FacesMessage message = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            System.out.println("entra a guardar");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            fecha = sdf2.parse(fechadia + " " + fechahora);
            DateFormat d = new SimpleDateFormat("yyyy");
            System.out.println("entra a confirmar true");
            System.out.println(docunombre);
            Indicador in = deriv.getIndic(docunombre);
            String codigo = deriv.getCodigoUsuario(usu.getUsu());
            System.out.println("El codigo es: " + codigo);
            for (int i = 0; i < docselec.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec.get(i);
                deriv.ActualizarTramite(hm.get("numerotramite").toString(), String.valueOf(deriv.getMovimiento(hm.get("numerotramite").toString())), fecha, docunombre);
                deriv.InsertarMovimiento(usu, deriv.getMovimiento(hm.get("numerotramite").toString()) + 1, fecha, asunto, hm.get("estado").toString(), hm.get("numerotramite").toString(), hm.get("tramfecha"), getNombOficina(), codinterno, in);
                deriv.InsertarTipoDocus(correlativo, docunombre, 1, siglasdocus, d.format(fecha), hm.get("numerotramite").toString(), hm.get("tramfecha").toString(), fecha, usu, asunto, hm.get("movimnum").toString(), deriv.getDependencia2(codigo), deriv.getDependencia2(codinterno));
            }

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL " + mensaje.toUpperCase());
            RequestContext.getCurrentInstance().showMessageInDialog(message);

        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL " + mensaje.toUpperCase());
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        limpiar();
        MostrarParaUsuario();
    }

    public void limpiar() {
        numtramaux = "";
        asunto = "";
        estado = "";
        codinterno = "";
        docunombre = "";
        siglasdocus = "";
    }

    public void getTipoDocumentos() {
        try {
            tiposdocus = dd.getTipoDocu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List getSeguimientolista() {
        return seguimientolista;
    }

    public void setSeguimientolista(List seguimientolista) {
        this.seguimientolista = seguimientolista;
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

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public SeguimientoDAO getSgd() {
        return sgd;
    }

    public void setSgd(SeguimientoDAO sgd) {
        this.sgd = sgd;
    }

    public DerivarDAO getDeriv() {
        return deriv;
    }

    public void setDeriv(DerivarDAO deriv) {
        this.deriv = deriv;
    }

    public DocusInternosDAO getDi() {
        return di;
    }

    public void setDi(DocusInternosDAO di) {
        this.di = di;
    }

    public OficioDAO getOfi() {
        return ofi;
    }

    public void setOfi(OficioDAO ofi) {
        this.ofi = ofi;
    }

    public List getSeguimientolista2() {
        return seguimientolista2;
    }

    public void setSeguimientolista2(List seguimientolista2) {
        this.seguimientolista2 = seguimientolista2;
    }

    public List getDesignados() {
        return designados;
    }

    public void setDesignados(List designados) {
        this.designados = designados;
    }

    public List getTiposdocus() {
        return tiposdocus;
    }

    public void setTiposdocus(List tiposdocus) {
        this.tiposdocus = tiposdocus;
    }

    public List getDetalle() {
        return detalle;
    }

    public void setDetalle(List detalle) {
        this.detalle = detalle;
    }

    public Map<String, String> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Map<String, String> seleccion) {
        this.seleccion = seleccion;
    }

    public String getFechaconfirmar() {
        return fechaconfirmar;
    }

    public void setFechaconfirmar(String fechaconfirmar) {
        this.fechaconfirmar = fechaconfirmar;
    }

    public String getFechahora2() {
        return fechahora2;
    }

    public void setFechahora2(String fechahora2) {
        this.fechahora2 = fechahora2;
    }

    public String getNumtramaux() {
        return numtramaux;
    }

    public void setNumtramaux(String numtramaux) {
        this.numtramaux = numtramaux;
    }

    public String getSiglasdocus() {
        return siglasdocus;
    }

    public void setSiglasdocus(String siglasdocus) {
        this.siglasdocus = siglasdocus;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
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

    public String getFechanio() {
        return fechanio;
    }

    public void setFechanio(String fechanio) {
        this.fechanio = fechanio;
    }

    public String getDocunombre() {
        return docunombre;
    }

    public void setDocunombre(String docunombre) {
        this.docunombre = docunombre;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombOficina() {
        return nombOficina;
    }

    public void setNombOficina(String nombOficina) {
        this.nombOficina = nombOficina;
    }

    public String getCodinterno() {
        return codinterno;
    }

    public void setCodinterno(String codinterno) {
        this.codinterno = codinterno;
    }

    public String getCorrelativoaux() {
        return correlativoaux;
    }

    public void setCorrelativoaux(String correlativoaux) {
        this.correlativoaux = correlativoaux;
    }
}
