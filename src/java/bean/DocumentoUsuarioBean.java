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
import java.text.ParseException;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class DocumentoUsuarioBean {

    private List otrosdocus3,docusInternosOGPL, oficios3, oficios2, detallecirc2, designados, seguimientolista2, seguimientolista3, seguimientolista, confirmados, otrosdocus, otrosdocus2, docselec, detalle, docselec2, docselec3, docselec4, confirmadosderivados, listadocspropios, listadocpropioscir;
    private Map<String, String> seleccion, seleccion2;
    private DocumentoDAO dd;
    private Date fecha, anio;
    private Usuario usu;
    private String tipodocupropio, tipodocupropio2, asignado, fechaconfirmar, fechahora2, fechadia, fechanio = "", fechahora, motivo = "", usuario = "", codinterno, numtramaux, asunto, siglasdocus, correlativo = "", correlativoaux, docunombre, estado, tramaux, llego, confirme, docresp, docofic;
    private final FacesContext faceContext;
    private SeguimientoDAO sgd;
    private DerivarDAO deriv;
    private boolean confirmar = false, aparecer, ver, nover;
    private DocusInternosDAO di;
    private OficioDAO ofi;
    public static String correla_exportar, tramnum_exportar, fecha_exportar, movimiento_exportar;

    public DocumentoUsuarioBean() {
        dd = new DocumentoDaoImpl();
        ofi = new OficioDaoImpl();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();
        seguimientolista2 = new ArrayList<Map<String, String>>();
        designados = new ArrayList<String>();
        detallecirc2 = new ArrayList<Map<String, String>>();
        this.oficios2 = new ArrayList<Map<String, String>>();
        seguimientolista = new ArrayList<Map<String, String>>();
        seguimientolista3 = new ArrayList<Map<String, String>>();
        this.docusInternosOGPL = new ArrayList<Map<String, String>>();
        confirmados = new ArrayList<Map<String, String>>();
        listadocspropios = new ArrayList<Map<String, String>>();
        listadocpropioscir = new ArrayList<Map<String, String>>();
        detalle = new ArrayList<Map<String, String>>();
        di = new DocusInternosDaoImpl();
        confirmadosderivados = new ArrayList<Map<String, String>>();
        sgd = new SeguimientoDaoImpl();
        deriv = new DerivarDaoImpl();
        boolean isdocumentosUsuario = (currentPage.lastIndexOf("documentos_user.xhtml") > -1);
        boolean isdocusInternosOGPL = (currentPage.lastIndexOf("docusInternosOGPL.xhtml") > -1);
        if (isdocumentosUsuario) {
            MostrarParaUsuario();
        }else{
            if(isdocusInternosOGPL){
                mostrarDocusInternosOGPL();
            }
        }

    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Sin Confirmar")) {
            MostrarParaUsuario();

        }

    }

    public void mostrarDocusInternosOGPL() {
        System.out.println("listando documentos2");
        docusInternosOGPL.clear();
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            lista = dd.docusInternosOGPL();
            Iterator ite = lista.iterator();
            Object obj[] = new Object[8];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("id", String.valueOf(obj[0]));
                listaaux.put("documento", String.valueOf(obj[1]));
                listaaux.put("fecharegistro", String.valueOf(obj[2]));
                listaaux.put("asunto", String.valueOf(obj[3]));
                listaaux.put("origen", String.valueOf(obj[4]));
                listaaux.put("destino", String.valueOf(obj[5]));
                listaaux.put("asignado", String.valueOf(obj[6]));
                listaaux.put("tipodocu", String.valueOf(obj[7]));
                docusInternosOGPL.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void out() {
        System.out.println("SE TIENE QUE PINTAR");
    }

    public void onEdit(RowEditEvent event) {
        String id = String.valueOf(((HashMap) event.getObject()).get("iddoc"));
        String asunto = String.valueOf(((HashMap) event.getObject()).get("asunto"));
        System.out.println(id + " " + asunto);
        ofi.ActualizarDocusInternosOficinas(id, asunto);
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EDICION REALIZADA", "SE HA ACTUALIZADO EL: " + String.valueOf(((HashMap) event.getObject()).get("documento")));
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EDICION CANCELADA", "NO SE HA ACTUALIZADO EL: " + String.valueOf(((HashMap) event.getObject()).get("documento")));
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void Detalles_Circ() {
        System.out.println("listando detalles");
        detallecirc2.clear();
        try {
            List lista = new ArrayList<String>();
            System.out.println(seleccion2.get("documento").toString().substring(19, 24));
            lista = ofi.getOficoCircDetal(partirColumnas(seleccion2.get("documento").toString()));
            for (int i = 0; i < lista.size(); i++) {
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("nombre", lista.get(i).toString());
                detallecirc2.add(listaaux);
            }
            System.out.println("entra aca >.<");
        } catch (Exception e) {
            System.out.println("error aca");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String partirColumnas(String aPartir) {
        List<String> subcadenas = new ArrayList<String>();
        StringTokenizer tokens = new StringTokenizer(aPartir, "-");
        while (tokens.hasMoreTokens()) {
            subcadenas.add(tokens.nextToken());
        }
        return subcadenas.get(1);
    }

    public void listarDocPropiosCircXtipo() {
        System.out.println("listando documentos2");
        listadocpropioscir.clear();
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            lista = di.getCircularesOficInternaXtipo(deriv.getCodigoUsuario(usu.getUsu()), tipodocupropio2);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("documento", String.valueOf(obj[0]));
                listaaux.put("asunto", String.valueOf(obj[1]));
                listaaux.put("fecha", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("firma", String.valueOf(obj[4]));
                listaaux.put("responsable", String.valueOf(obj[5]));
                listadocpropioscir.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarDocPropiosCirc() {
        System.out.println("listando documentos2");
        listadocpropioscir.clear();
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            lista = di.getCircularesOficInterna(deriv.getCodigoUsuario(usu.getUsu()));
            Iterator ite = lista.iterator();
            Object obj[] = new Object[6];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("documento", String.valueOf(obj[0]));
                listaaux.put("asunto", String.valueOf(obj[1]));
                listaaux.put("fecha", String.valueOf(obj[2]));
                listaaux.put("origen", String.valueOf(obj[3]));
                listaaux.put("firma", String.valueOf(obj[4]));
                listaaux.put("responsable", String.valueOf(obj[5]));
                listadocpropioscir.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarDocPropios() {
        System.out.println("listando documentos2");
        listadocspropios.clear();

        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            lista = di.getDocInternos(deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu()));
            Iterator ite = lista.iterator();
            Object obj[] = new Object[10];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("iddoc", String.valueOf(obj[0]));
                listaaux.put("documento", String.valueOf(obj[1]));
                listaaux.put("expediente", String.valueOf(obj[2]));
                listaaux.put("fecha", String.valueOf(obj[3]));
                listaaux.put("asunto", String.valueOf(obj[4]));
                listaaux.put("origen", String.valueOf(obj[5]));
                listaaux.put("destino", String.valueOf(obj[6]));
                listaaux.put("asignado", String.valueOf(obj[7]));
                listaaux.put("docuprinc", String.valueOf(obj[8]));
                listaaux.put("origenprinc", String.valueOf(obj[9]));
                listadocspropios.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarDocPropiosXtipo() {
        System.out.println("listando documentos2");
        listadocspropios.clear();

        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            lista = di.getDocInternosXtipo(deriv.getSiglas(usu.getOficina().getIdOficina(), usu.getUsu()), this.tipodocupropio);
            Iterator ite = lista.iterator();
            Object obj[] = new Object[11];
            while (ite.hasNext()) {
                obj = (Object[]) ite.next();
                Map<String, String> listaaux = new HashMap<String, String>();
                listaaux.put("iddoc", String.valueOf(obj[0]));
                listaaux.put("documento", String.valueOf(obj[1]));
                listaaux.put("expediente", String.valueOf(obj[2]));
                listaaux.put("fecha", String.valueOf(obj[3]));
                listaaux.put("asunto", String.valueOf(obj[4]));
                listaaux.put("origen", String.valueOf(obj[5]));
                listaaux.put("destino", String.valueOf(obj[6]));
                listaaux.put("asignado", String.valueOf(obj[7]));
                listaaux.put("iddocu", String.valueOf(obj[8]));
                listaaux.put("docuprinc", String.valueOf(obj[9]));
                listaaux.put("origenprinc", String.valueOf(obj[10]));
                listadocspropios.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        tipodocupropio = " ";
    }

    public void abrirAsignacion() {
        String fechavacia = "nf", fechallena = "nf";
        fechaconfirmar = "";
        this.fechahora2 = "";
        for (int i = 0; i < docselec2.size(); i++) {
            Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
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
        /*Date nuevafecha = new Date();
         fechaconfirmar = "";
         this.fechahora2 = "";
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
         StringTokenizer tokens = new StringTokenizer(sdf.format(nuevafecha), " ");
         while (tokens.hasMoreTokens()) {
         if (fechaconfirmar.equals("")) {
         fechaconfirmar = tokens.nextToken();
         }
         if (fechahora2.equals("")) {
         fechahora2 = tokens.nextToken();
         }
         }*/
        System.out.println("FECHA Y HORA: " + fechaconfirmar + "-" + fechahora2);

    }

    public void ConfirmarAsignar() {
        Confirmar();
        System.out.println("\nENTRA A ASIGNAR\n");
        Asignar();
        System.out.println("\nSALE DE ASIGNAR\n");
        docselec2.clear();
        asignado = " ";
    }

    public void llenarDesignados() {
        designados = sgd.getDesignados(usu.getOficina().getIdOficina());
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

    public void Asignar() {
        System.out.println(docselec2);
        FacesMessage message = null;
        try {

            String ntram = "";
            int movi = 0;
            for (int i = 0; i < docselec2.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
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

    public void MostrarParaUsuario() {
        System.out.println("listando documentos2");
        seguimientolista2.clear();
        Date anio = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        String auxanio = sdf.format(anio);
        try {
            System.out.println("entra a seguimiento2");
            List lista = new ArrayList();
            System.out.println(usu.getOficina().getIdOficina());
            lista = sgd.seguimientoUser(usu.getOficina().getIdOficina());
            Iterator ite = lista.iterator();
            Object obj[] = new Object[13];
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
                listaaux.put("docgene", di.getRespuesta(String.valueOf(obj[0]), String.valueOf(obj[1])));
                listaaux.put("usuario", String.valueOf(obj[10]));
                listaaux.put("origenp", String.valueOf(obj[11]));
                listaaux.put("docup", String.valueOf(obj[12]));
                seguimientolista2.add(listaaux);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            for (int i = 0; i < docselec2.size(); i++) {
                System.out.println("entra al bucle for");
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
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

    public void limpiar() {
        numtramaux = "";
        asunto = "";
        estado = "";
        codinterno = "";
        docunombre = "";
        siglasdocus = "";
    }

    public String getNombOficina() {
        String oficina = dd.getOficina(usu);
        return oficina;
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
        System.out.println("recorrer");
        Map<String, String> hm = (HashMap<String, String>) docselec2.get(0);
        System.out.println(hm.get("numerotramite"));
        MostrarSeguimiento(hm.get("numerotramite"));
    }

    public void MostrarSeguimiento(String tramnum) {
        System.out.println("listando documentos");
        seguimientolista3.clear();
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
                seguimientolista3.add(listaaux);
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
                HashMap<String, String> hm = (HashMap<String, String>) docselec2.get(0);
                tramnum_exportar = hm.get("numerotramite").toString();
                fecha_exportar = hm.get("fechaenvio").toString();
                movimiento_exportar = hm.get("movimnum").toString();
                System.out.println("ESTA ES LA FECHA DE ENVIO: " + fecha_exportar + " Mov: " + movimiento_exportar);
                estado = "EN PROCESO";

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
            for (int i = 0; i < docselec2.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
                deriv.ActualizarTramite(hm.get("numerotramite").toString(), String.valueOf(deriv.getMovimiento(hm.get("numerotramite").toString())), fecha, docunombre);
                deriv.InsertarMovimiento(usu, deriv.getMovimiento(hm.get("numerotramite").toString()) + 1, fecha, asunto, hm.get("estado").toString(), hm.get("numerotramite").toString(), hm.get("fechaenvio"), getNombOficina(), codinterno, in);
                deriv.InsertarTipoDocus(correlativo, docunombre, 1, siglasdocus, d.format(fecha), hm.get("numerotramite").toString(), hm.get("fechaenvio").toString(), fecha, usu, asunto, hm.get("movimnum").toString(), deriv.getDependencia2(codigo), deriv.getDependencia2(codinterno));
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA GUARDADO EL " + docunombre);
            RequestContext.getCurrentInstance().showMessageInDialog(message);

        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA PODIDO GUARDAR EL " + docunombre);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        limpiar();
        MostrarParaUsuario();
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

        } catch (Exception e) {
            System.out.println("error fecha");
            System.out.println(e.getMessage());
        }
        return fecha;
    }

    public void Motivo() {
        try {
            for (int i = 0; i < docselec2.size(); i++) {
                Map<String, String> hm = (HashMap<String, String>) docselec2.get(i);
                if (i == 0) {
                    System.out.println(hm.get("numerotramite").toString());
                    numtramaux = numtramaux + " " + hm.get("numerotramite").toString();
                    motivo = dd.getMotivo(hm.get("numerotramite").toString(), hm.get("fechaenvio").toString());
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

    public List getDesignados() {
        return designados;
    }

    public void setDesignados(List designados) {
        this.designados = designados;
    }

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }

    public OficioDAO getOfi() {
        return ofi;
    }

    public void setOfi(OficioDAO ofi) {
        this.ofi = ofi;
    }

    public String getFechanio() {
        return fechanio;
    }

    public void setFechanio(String fechanio) {
        this.fechanio = fechanio;
    }

    public List getListadocspropios() {
        return listadocspropios;
    }

    public void setListadocspropios(List listadocspropios) {
        this.listadocspropios = listadocspropios;
    }

    public List getListadocpropioscir() {
        return listadocpropioscir;
    }

    public void setListadocpropioscir(List listadocpropioscir) {
        this.listadocpropioscir = listadocpropioscir;
    }

    public String getTipodocupropio() {
        return tipodocupropio;
    }

    public void setTipodocupropio(String tipodocupropio) {
        this.tipodocupropio = tipodocupropio;
    }

    public List getOtrosdocus2() {
        return otrosdocus2;
    }

    public void setOtrosdocus2(List otrosdocus2) {
        this.otrosdocus2 = otrosdocus2;
    }

    public String getTipodocupropio2() {
        return tipodocupropio2;
    }

    public void setTipodocupropio2(String tipodocupropio2) {
        this.tipodocupropio2 = tipodocupropio2;
    }

    public List getDocselec3() {
        return docselec3;
    }

    public void setDocselec3(List docselec3) {
        this.docselec3 = docselec3;
    }

    public List getDocselec4() {
        return docselec4;
    }

    public void setDocselec4(List docselec4) {
        this.docselec4 = docselec4;
    }

    public Map<String, String> getSeleccion2() {
        return seleccion2;
    }

    public void setSeleccion2(Map<String, String> seleccion2) {
        this.seleccion2 = seleccion2;
    }

    public List getDetallecirc2() {
        return detallecirc2;
    }

    public void setDetallecirc2(List detallecirc2) {
        this.detallecirc2 = detallecirc2;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public boolean isNover() {
        return nover;
    }

    public void setNover(boolean nover) {
        this.nover = nover;
    }

    public String getCorrelativoaux() {
        return correlativoaux;
    }

    public void setCorrelativoaux(String correlativoaux) {
        this.correlativoaux = correlativoaux;
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

    public List getSeguimientolista3() {
        return seguimientolista3;
    }

    public void setSeguimientolista3(List seguimientolista3) {
        this.seguimientolista3 = seguimientolista3;
    }

    public List getOficios2() {
        return oficios2;
    }

    public void setOficios2(List oficios2) {
        this.oficios2 = oficios2;
    }

    public List getOficios3() {
        return oficios3;
    }

    public void setOficios3(List oficios3) {
        this.oficios3 = oficios3;
    }

    public List getDocusInternosOGPL() {
        return docusInternosOGPL;
    }

    public void setDocusInternosOGPL(List docusInternosOGPL) {
        this.docusInternosOGPL = docusInternosOGPL;
    }

    public List getOtrosdocus3() {
        return otrosdocus3;
    }

    public void setOtrosdocus3(List otrosdocus3) {
        this.otrosdocus3 = otrosdocus3;
    }

}
