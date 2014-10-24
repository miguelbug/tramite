/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import dao.DerivarDAO;
import daoimpl.DerivarDaoImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class ConstanciaBean {

    private String correlativo;
    private String escogido;
    private List empleados;
    private String tipocontrato;
    private Date desde;
    private Date hasta;
    private Date fechaemision;
    private String auxanio;
    private DerivarDAO deriv;
    private Date fecha;
    private String fechaemisionaux;
    
    public ConstanciaBean() {
        deriv = new DerivarDaoImpl();
    }
    public void abrirconstancia(){
        getAnio();
        fechaactual();
        generarCorrelativo2();
    }
    public void getAnio() {
        System.out.println("entra getanio");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        fechaemision = new Date();
        auxanio = sdf.format(fechaemision);
        System.out.println(auxanio);
    }
    public void fechaactual() {
        System.out.println("entra fechaactual");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaemision = new Date();
        fechaemisionaux = sdf.format(fechaemision);
        System.out.println(fechaemisionaux);
    }
    public void generarCorrelativo2() {
        int corr = 0;
        String aux = "";
        try {
            if (auxanio.equals(deriv.getAnio())) {
                System.out.println("lleno 1");
                corr = Integer.parseInt(deriv.getCorrelativoOficio());
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
    public void guardarconstancia(){
        
    }
    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getEscogido() {
        return escogido;
    }

    public void setEscogido(String escogido) {
        this.escogido = escogido;
    }

    public List getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List empleados) {
        this.empleados = empleados;
    }

    public String getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(String tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaemisionaux() {
        return fechaemisionaux;
    }

    public void setFechaemisionaux(String fechaemisionaux) {
        this.fechaemisionaux = fechaemisionaux;
    }
    
}
