/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.NuevoExpedienteDAO;
import daoimpl.NuevoExpedienteDaoImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteDatosId;
import maping.TramiteMovimiento;
import maping.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class NuevoExpedienteBean {

    private String tipoorigen;
    private String origen;
    private List listaorigen;
    private String expediente;
    private String asunto;
    private String sigla;
    private String anio;
    private List anios;
    private String tipodocumento;
    private List listatiposdocus;
    private String numerodocumento;
    private String movi;
    private String destino;
    private List listadestinos;
    private Usuario usu;
    private final FacesContext faceContext;
    private NuevoExpedienteDAO nedao;
    private Date dia;
    private String auxdia;

    public NuevoExpedienteBean() {
        listaorigen = new ArrayList<String>();
        listatiposdocus = new ArrayList<String>();
        anios = new ArrayList<String>();
        listadestinos = new ArrayList<String>();
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        nedao = new NuevoExpedienteDaoImpl();
    }

    public void guardarExpediente() {
        FacesMessage message = null;
        TramiteDatosId tid = new TramiteDatosId();
        TramiteDatos td = new TramiteDatos();
        TipoDocu tdc = new TipoDocu();
        TramiteMovimiento tm = new TramiteMovimiento();
        try {
            tid.setTramNum(expediente + "-" + sigla + "-" + anio);
            tid.setTramFecha(dia);
            td.setId(tid);
            td.setTramObs(asunto);
            td.setEstaDescrip("EN PROCESO");
            td.setUsuario(usu);
            td.setDependencia(nedao.getDepe(origen));
            nedao.guardarTramiteDatos(td);
            //////////////////////////////
            tdc.setTramiteDatos(td);
            tdc.setDocuPric("1");
            tdc.setDocuNombre(tipodocumento);
            tdc.setDocuNum(numerodocumento);
            tdc.setDocuSiglas(sigla);
            tdc.setDocuAnio(anio);
            nedao.guardarTipoDocu(tdc);
            ////////////////////////////
            tm.setTramiteDatos(td);
            tm.setMoviNum(Short.parseShort(movi));
            tm.setFechaEnvio(dia);
            tm.setFechaIngr(null);
            tm.setMoviObs(asunto);
            tm.setEstaNombre("EN PROCESO");
            tm.setEstadConfrirm("SIN CONFIRMAR");
            tm.setIndicador(nedao.getIndicador(tipodocumento));
            tm.setDependenciaByCodigo(nedao.getDepePorCodigo("100392"));
            tm.setDependenciaByCodigo1(nedao.getDepePorNombre(destino));
            tm.setUsuario(usu);
            nedao.guardarTramiteMovimiento(tm);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "SE HA CREADO EL EXPEDIENTE: " + expediente + "-" + sigla + "-" + anio);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE HA CREADO EL EXPEDIENTE: " + expediente + "-" + sigla + "-" + anio);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            System.out.println(e.getMessage());
        }
    }

    public void limpiar() {
        tipoorigen="";
        origen="";
        expediente="";
        sigla="";
        anio="";
        destino="";
        movi="";
        asunto="";
        tipodocumento="";
        numerodocumento="";
    }

    public void abrirNuevoExp() {
        listarTiposDocus();
        listarAnios();
        listarDestinos();
    }

    public void listarOrigen() {
        System.out.println("LISTAR ORIGEN");
        try {
            this.listaorigen = nedao.getDependencias(tipoorigen);
        } catch (Exception e) {
            System.out.println("PROBLEMAS LISTA ORIGEN");
            System.out.println(e.getMessage());
        }
    }

    public void obtenerSiglas() {
        sigla = nedao.getSigla(nedao.getCodigoDepe(origen));
    }

    public void listarTiposDocus() {
        try {
            listatiposdocus = nedao.getTipoDocumento();
        } catch (Exception e) {
            System.out.println("PROBLEMAS LISTA ORIGEN");
            System.out.println(e.getMessage());
        }
    }

    public String getTipoorigen() {
        return tipoorigen;
    }

    public void listarDestinos() {
        try {
            listadestinos = nedao.getDependenciasOficina();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarAnios() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/YYYY");
        dia = new Date();
        auxdia = sdf2.format(dia);
        String ani = sdf.format(dia);
        int i = 0, a = Integer.parseInt(ani);
        while (i < 15) {
            anios.add(String.valueOf(a));
            a--;
            i++;
        }
    }

    public void setTipoorigen(String tipoorigen) {
        this.tipoorigen = tipoorigen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public List getListaorigen() {
        return listaorigen;
    }

    public void setListaorigen(List listaorigen) {
        this.listaorigen = listaorigen;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public List getListatiposdocus() {
        return listatiposdocus;
    }

    public void setListatiposdocus(List listatiposdocus) {
        this.listatiposdocus = listatiposdocus;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public NuevoExpedienteDAO getNedao() {
        return nedao;
    }

    public void setNedao(NuevoExpedienteDAO nedao) {
        this.nedao = nedao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public List getAnios() {
        return anios;
    }

    public void setAnios(List anios) {
        this.anios = anios;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getAuxdia() {
        return auxdia;
    }

    public void setAuxdia(String auxdia) {
        this.auxdia = auxdia;
    }

    public String getMovi() {
        return movi;
    }

    public void setMovi(String movi) {
        this.movi = movi;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List getListadestinos() {
        return listadestinos;
    }

    public void setListadestinos(List listadestinos) {
        this.listadestinos = listadestinos;
    }

}
