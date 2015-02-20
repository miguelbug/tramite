/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.apache.log4j.Logger;
//import org.hibernate.impl.SessionFactoryImpl;

//import edu.unmsm.quipucamayoc.comedor.common.listener.ContextLoaderListener;
import modelo.Reporte;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import java.util.Locale;

public class ReporteController {

    private String XLS_EXTENSION = ".xls";
    private String PDF_EXTENSION = ".pdf";
    private static Reporte reportParam;
    private String URL = "/reportes/";
//	private static Logger log = Logger.getLogger(ReporteController.class);
    private static ReporteController reporteController;
    private Connection conexion;
    private int tipoFormato = 0;

    /*public ReporteController(String nombre) {
     reportParam=new Reporte();
     reportParam.setNombreReport(nombre);
     reportParam.setQueryParams(new HashMap<String,Object>());
     // TODO Auto-generated constructor stub
     }*/
    public static ReporteController getInstance(String nombre) {
        if (reporteController == null) {
            System.out.println("creando nuevo reporte controller "+nombre);
            reporteController = new ReporteController();
        }
        reporteController.setReportParam(ReporteController.getInstanceReporte(nombre));
        return reporteController;
    }

    private static Reporte getInstanceReporte(String nombre) {
        if (reportParam == null) {
            System.out.println("creando nuevo reporte "+nombre);
            reportParam = new Reporte();
        }
        reportParam.setQueryParams(new HashMap<String, Object>());
        reportParam.setNombreReport(nombre);
        return reportParam;
    }

    private static Reporte getReportParam() {
        return reportParam;
    }

    private static void setReportParam(Reporte reportParam) {
        ReporteController.reportParam = reportParam;
    }

    private void preparedExporterXls(JRXlsExporter xlsExporter, JasperPrint jasperPrint, ByteArrayOutputStream baos) {
        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
    }

    private void preparedResponse(HttpServletResponse response, ByteArrayOutputStream baos) throws IOException {
        byte[] output;
        output = baos.toByteArray();
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "inline");
        response.setContentLength(output.length);
        response.getOutputStream().write(output);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    private void exportarReporteaExcel(JasperPrint jasperPrint, HttpServletResponse response, HttpServletRequest request) throws IOException {

        String outfile = request.getRealPath("/");// archivo de salida
        outfile += XLS_EXTENSION;
        JRXlsExporter xlsExporter = new JRXlsExporter();
       // xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            preparedExporterXls(xlsExporter, jasperPrint, baos);
            xlsExporter.exportReport();
            preparedResponse(response, baos);
            //JasperExportManager.exportReportToPdfFile(jasperPrint,outfile);

        } catch (JRException e) {
            StringWriter stringWriter = new StringWriter();
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());
            // log.error(e);
        }
    }

    private void exportarReporteaPDF(JasperPrint jasperPrint, FacesContext context, HttpServletResponse response, HttpServletRequest request) throws IOException {

        byte[] pdf;
        try {

            pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            System.out.println("nombre report:" + reportParam.getNombreReport());
            response.addHeader("Content-disposition", "attachment;filename=" + reportParam.getNombreReport() + ".pdf");
            response.setContentLength(pdf.length);
            response.getOutputStream().write(pdf);
            response.setContentType("application/pdf");
            context.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    private void exportarReporteaXLS(JasperPrint jasperPrint, FacesContext context, HttpServletResponse response, HttpServletRequest request) throws IOException {

        JRXlsExporter xlsExporter = new JRXlsExporter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] xls;

        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        try {

            xlsExporter.exportReport();
            xls = baos.toByteArray();

            System.out.println("nombre report:" + reportParam.getNombreReport());
            response.addHeader("Content-disposition", "attachment;filename=" + reportParam.getNombreReport() + ".xls");
            response.setContentLength(xls.length);
            response.getOutputStream().write(xls);
            response.setContentType("application/vnd.ms-excel");
            context.responseComplete();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean ejecutaReporte(FacesContext context, ServletContext sc) {
        try {
            //initReporteParams();
            //beforeExecuteReporte(reportParam);  
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            JasperPrint jasperPrint = execReport(context, sc);
            if (jasperPrint != null) {
                List lista = jasperPrint.getPages();
                jasperPrint.setLocaleCode("en_US");
                if (lista != null && lista.size() > 0) {
                    System.out.println("nro de paginas: " + lista.size());
                    if (tipoFormato == 0) {
                        System.out.println("TIPO DE FORMATO ES 0");
                        exportarReporteaPDF(jasperPrint, context, response, request);
                    } else {
                        System.out.println("EXPORTAR A XLS");
                        exportarReporteaXLS(jasperPrint, context, response, request);
                    }
                    System.out.println("Tiene hojas");
                    return true;
                }else{
                    System.out.println("lista es null");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR EJECUTAR REPORTE");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("No tiene hojas");
        return false;
    }

    /* private void beforeExecuteReporte(ReportBean reportParam) {		 
     reportParam.addQueryParam("parametro1","1");	
     reportParam.addQueryParam("parametro2","2");		
     }*/
    public void addMapParam(Map map) {

        map.put("REPORT_LOCALE", Locale.ENGLISH);
        reportParam.setQueryParams(map);
    }

    public void addQueryParam(String hash, Object value) {
        reportParam.addQueryParam(hash, value);
    }

    /*
     private void initReporteParams() {
     reportParam.setJasperFileName(reportParam.getNombreReport() + JASPER_EXTENSION);	        
     }
     */
    public JasperPrint execReport(FacesContext context, ServletContext sc) {
        try {
            JasperReport report = null;

            String archivo;
            archivo = sc.getRealPath(URL + reportParam.getNombreReport() + ".jrxml");

            System.out.println("ARCHIVO A EXPORTAR:" + archivo);

            if (!archivo.equals("")) {
                report = JasperCompileManager.compileReport(archivo);

            } else {
                System.out.println("NO EXISTE EL ARCHIVO:" + archivo);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParam.getQueryParams(), conexion);
            System.out.println("SIZE DEL JASPERPRINT: "+jasperPrint.getPages().size());
            System.out.println("NOMBRE DEL JASPERPRINT: "+jasperPrint.getName());
            System.out.println("NOMBRE DE LA COEXION: "+conexion.toString());
            System.out.println("SE HA CREADO");
            return jasperPrint;

        } catch (JRException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return new JasperPrint();
    }

    /*public Connection getConnection(){
     //SessionFactoryImpl s=(SessionFactoryImpl)ContextLoaderListener.getBean("sessionFactory");
     try {
     return getSqlMapClientTemplate().getDataSource().getConnection();
     //s.getConnectionProvider().getConnection();
     } catch (SQLException e) {
     //log.error(e);
     e.printStackTrace();
     }
     return null;
     }*/
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void setTipoFormato(int tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public int getTipoFormato() {
        return tipoFormato;
    }
}
