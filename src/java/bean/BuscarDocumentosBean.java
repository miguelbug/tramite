/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import dao.DocumentoDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author OGPL
 */
@ManagedBean
@RequestScoped
public class BuscarDocumentosBean {

    private String codigosdepe;
    private DocumentoDAO dd;
    private List docus;
    private String numerotramite;
    private String codigo;
    private String anio;
    private String asunto;
    private String mes;
    
    
    public BuscarDocumentosBean() {
        
    }
    public void Buscar(ActionEvent ev){
        
       

    }
    public int Casos(){
        int caso=0;
        
        return caso;
    }

    public DocumentoDAO getDd() {
        return dd;
    }

    public void setDd(DocumentoDAO dd) {
        this.dd = dd;
    }

    public String getNumerotramite() {
        return numerotramite;
    }

    public void setNumerotramite(String numerotramite) {
        this.numerotramite = numerotramite;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List getDocus() {
        return docus;
    }

    public void setDocus(List docus) {
        this.docus = docus;
    }

    public String getCodigosdepe() {
        return codigosdepe;
    }

    public void setCodigosdepe(String codigosdepe) {
        this.codigosdepe = codigosdepe;
    }
    
}
