/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.TreeNode;


@ManagedBean
@ViewScoped
public class objxUnidadController implements Serializable{
    
    
private static final long serialVersionUID = 8797816477254175229L;
FacesContext context;
ServletContext serveltcontext;
private int anioActual;
private int opcionFormato = 1;
private int mesInicio;
private int mesFin ;
private int mesActual ;

public void mostrarReporRegModPres(){

context = FacesContext.getCurrentInstance (); 
serveltcontext = (ServletContext) context.getExternalContext().getContext();
ReporteController repor;
HashMap<String, Object> parametros = new HashMap<String, Object>();
parametros.clear();
//parametros.put("anio", 2014/*getAnioActual()*/);
/*
Calendar fecha = new GregorianCalendar();
int mes = fecha.get(Calendar.MONTH);

int año = fecha.get(Calendar.YEAR);
mes++;
int dia = fecha.get(Calendar.DAY_OF_MONTH);
int hora = fecha.get(Calendar.HOUR_OF_DAY);
int minuto = fecha.get(Calendar.MINUTE);
int segundo = fecha.get(Calendar.SECOND);
System.out.println("Fecha Actual: "
                   + dia + "/" + (mes+1) + "/" + año);
System.out.printf("Hora Actual: %02d:%02d:%02d %n",
                                      hora, minuto, segundo);
*/

FacesContext context = FacesContext.getCurrentInstance (); 
System.out.println("context"+context);
ServletContext sc = (ServletContext) context.getExternalContext ().getContext();
//System.out.println("sc = "+sc.getRealPath ("escudo.jpg"));
//String imagen=sc.getRealPath ("/pages/images/escudo.jpg");
//String subreport=sc.getRealPath ("/reportes/planes_presupuesto/");
System.out.println("sc = "+sc.getRealPath ("/reportes/"));
//parametros.put("SUBREPORT_DIR", subreport);
//parametros.put("logo", imagen);
//parametros.put("subrep3", true);

/*if( fuenteElegido.equals("99") ){
	 repor= ReporteController.getInstance("reporteCN_TF");
}else{*/
//parametros.put("fuente", getFuenteElegido() );
repor= ReporteController.getInstance("prueba3");
//}
categoriaServicio categoriaServicio= new categoriaServicio();
repor.setConexion(categoriaServicio.getConexion() );
repor.setTipoFormato(opcionFormato);   /// para tIPO FORMATO  08/05
FacesMessage message=null;
boolean rpt=false;	

	
		
	/*parametros.put("udid", udIdElegido );
	parametros.put("udcod", udCodElegido );
	parametros.put("uddsc", dependenciaService.obtenerDepxUdcod(udCodElegido).getDescripcion() );
	parametros.put("usuario", obtenerUsuario() );
	parametros.put("logo", obtenerLogo() );
	parametros.put("SUBREPORT_DIR", obtenerReporteDir() );	*/
 
        parametros.put("usuario","miguel" ); 
	repor.addMapParam(parametros);
	rpt=repor.ejecutaReporte(context,serveltcontext);	
		

        
if(!rpt && message==null){
	//no tiene hojas	
	message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje","No hay datos para generar reporte");
	FacesContext.getCurrentInstance().addMessage(null, message);
}
}


/*
public int getAnioActual() {
	
	anioEstado = (AnioEstadoPresupuestal) Util.obtenerObjetoSession("anioGlobal");
	anioActual= anioEstado.getAnio();

	return anioActual;
}*/

public void setAnioActual(int anioActual) {
	this.anioActual = anioActual;
}

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public ServletContext getServeltcontext() {
        return serveltcontext;
    }

    public void setServeltcontext(ServletContext serveltcontext) {
        this.serveltcontext = serveltcontext;
    }

    public int getOpcionFormato() {
        return opcionFormato;
    }

    public void setOpcionFormato(int opcionFormato) {
        this.opcionFormato = opcionFormato;
    }

    public int getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(int mesInicio) {
        this.mesInicio = mesInicio;
    }

    public int getMesFin() {
        return mesFin;
    }

    public void setMesFin(int mesFin) {
        this.mesFin = mesFin;
    }

    public int getMesActual() {
        return mesActual;
    }

    public void setMesActual(int mesActual) {
        this.mesActual = mesActual;
    }


}