/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.GestionDependenciaDao;
import daoimpl.GestionDependenciaDaoImpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import maping.Dependencia;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class GestionDependenciaBean {

    private String codigodep;
    private String nombredep;
    private String tipodep;
    private GestionDependenciaDao gdd;

    public GestionDependenciaBean() {
        gdd = new GestionDependenciaDaoImpl();
    }
    public void abrirCuadroNuevaDep(){
        codigodep=String.valueOf(gdd.getMaxCodigo()+1);
    }
    public void guardarDependencia() {
        try {
            Dependencia d = new Dependencia();
            d.setCodigo(Long.valueOf(codigodep));
            d.setNombre(nombredep);
            d.setTipodepe(tipodep);
            gdd.GuardarDependencia(d);
            limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void limpiar() {
        codigodep = "";
        nombredep = "";
        tipodep = "";
    }

    public String getCodigodep() {
        return codigodep;
    }

    public void setCodigodep(String codigodep) {
        this.codigodep = codigodep;
    }

    public String getNombredep() {
        return nombredep;
    }

    public void setNombredep(String nombredep) {
        this.nombredep = nombredep;
    }

    public String getTipodep() {
        return tipodep;
    }

    public void setTipodep(String tipodep) {
        this.tipodep = tipodep;
    }

}
