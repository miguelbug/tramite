package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;





public class Util {
	


	public static String obtenerRutaRaiz(HttpServletRequest request){
		String rutaRaiz;
		rutaRaiz=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		return rutaRaiz;
	}
	
	public static void subirSesion(String clave,Object objeto){
		FacesContext context=FacesContext.getCurrentInstance();
		ExternalContext exContext=context.getExternalContext();
		Map<String,Object> mapaSesion=exContext.getSessionMap();
		mapaSesion.put(clave, objeto);
	}
	
	public static void agregarMensaje(FacesContext context,String idComponente,String summary,String detail,Severity severidad){
		 FacesMessage msg;
		 msg=new FacesMessage(summary,detail);
		 msg.setSeverity(severidad);
		 context.addMessage(idComponente,msg); 	
	}
	
	public static Object cargarSesion(String clave){
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(clave);
	}
	
	public static void eliminarSesion(String clave){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(clave);
	}
	
	public static void invalidarSesionCompleta(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request=(HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession sesion=request.getSession();
		sesion.invalidate();
	}
	
	public static int convertirMinutos(String hora){
		int minutosTotales = 0;
		int horas = Integer.parseInt(hora.substring(0, 2));
		int minutos=Integer.parseInt(hora.substring(3, 5));
		if(hora.endsWith("AM"))
		{
			minutosTotales= horas*60+ minutos;
		}
		else
		{
			minutosTotales= (horas+12)*60+minutos;
		}
		return minutosTotales;
	}
	
	public static String formatMinutos(int totalMinutos){
		int horas=totalMinutos/60;
		int minutos=totalMinutos%60;
		String cadena="";
		String cadenaMinutos="";
		if(minutos<10){
			cadenaMinutos="0"+minutos;
		}else{
			cadenaMinutos=String.valueOf(minutos);
		}
		
		if(horas<12){
			cadena=horas+":"+cadenaMinutos+" AM";
			if(horas<10){
				cadena="0"+cadena;
			}
		}else{
			if(horas==12){
				cadena=horas+":"+cadenaMinutos+" PM";
			}else{
				horas=horas-12;
				cadena=horas+":"+cadenaMinutos+" PM";
				if(horas<10){
					cadena="0"+cadena;
				}
			}
		}
		return cadena;
	}
	
	public static String obtenerNombreMes(int num){
		
		String nombreMes="";
		
		switch(num){
		case 1 :{ nombreMes="enero"; break; } 
		case 2 :{ nombreMes="febrero"; break; } 
		case 3 :{ nombreMes="marzo"; break; } 
		case 4 :{ nombreMes="abril"; break; } 
		case 5 :{ nombreMes="mayo"; break; } 
		case 6 :{ nombreMes="junio"; break; } 
		case 7 :{ nombreMes="julio"; break; }
		case 8 :{ nombreMes="agosto"; break; } 
		case 9 :{ nombreMes="septiembre"; break; } 
		case 10 :{ nombreMes="octubre"; break; } 
		case 11 :{ nombreMes="noviembre"; break; } 
		case 12 :{ nombreMes="diciembre"; break; } 
		
		default:{ nombreMes="";break;}
		
	}
	return nombreMes;
	}
	
	public static String obtenerNombreDeDia(String idDia){
		int numDia=0;
		String nombreDia="";
		try{
			numDia=Integer.parseInt(idDia);
		}catch(NumberFormatException nfe){
			numDia=0;
		}
		
		switch(numDia){
			case 1 :{ nombreDia="Lunes"; break; } 
			case 2 :{ nombreDia="Martes"; break; } 
			case 3 :{ nombreDia="Mi�rcoles"; break; } 
			case 4 :{ nombreDia="Jueves"; break; } 
			case 5 :{ nombreDia="Viernes"; break; } 
			case 6 :{ nombreDia="S�bado"; break; } 
			case 7 :{ nombreDia="Domingo"; break; }
			default:{ nombreDia="";break;}
			
		}
		return nombreDia;
	}

	/**
	 * recibe fecha tipo Date y retorna fecha tipo String
	 * @param fecha
	 * @return 
	 */
	public static String formatearFecha(Date fecha,String formato){
		/**
		 * Formato puede ser dd/MM/yyyy o dd-MM-yyyy
		 */
			/*
	       String formato = "%td/%tm/%tY";
	       String fechaFormateada="";
	       fechaFormateada = String.format(formato, fecha, fecha, fecha);
	       return fechaFormateada;
	       */
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(formato);
		String rs=formatoDeFecha.format(fecha);
		return rs;
    }
	
	public static String formatearFecha(Date fecha){
		String formato="dd-MM-yyyy";
		return Util.formatearFecha(fecha, formato);
    }
	
	/**
	 * Recibe fecha tipo String y fecha tipo Date
	 * @param fecha
	 * @return
	 */
	
	/*
	public static Date formatearFechaString(String fecha) {
	       DateFormat df = new SimpleDateFormat(Constantes.formatoFecha);
	       Date today = null;
	       try {
	           today = df.parse(fecha);
	       } catch (ParseException e) {
	       // TODO Auto-generated catch block
	           e.printStackTrace();
	       }  
	           return today;
	 }
	*/
	public static Object obtenerObjetoSession(String alias) {
		
		Object	objetoEnSession = new Object();
		try{
			ExternalContext context = 
			FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = 
	    	(HttpServletRequest) context.getRequest();
			objetoEnSession = (Object) request.getSession().getAttribute(alias); 
		}
		catch(Exception ex){
		}
	    return objetoEnSession;
	}
	
	
	public static String fechaActual(){
		java.util.Date fecha = new Date();
		String fechaActual= Util.formatearFecha(fecha,"dd/MM/yyyy");
		return fechaActual;
	}

	public static String obtenerRutaServidor(HttpServletRequest request) {
		String rutaServidor;
		rutaServidor=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		return rutaServidor;
	}
}

