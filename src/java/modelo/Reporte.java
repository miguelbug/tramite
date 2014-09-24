/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.Map;

public class Reporte {
	
	 private String jasperFileName;
	 private String nombreReport;
	 private Map<String,Object> queryParams;
	 	 
	public String getNombreReport() {
		return nombreReport;
	}

	public void setNombreReport(String nombreReport) {
		this.nombreReport = nombreReport;
	}

    public void addQueryParam(String key, Object value) {
            queryParams.put(key, value);
    }

    public String getJasperFileName() {
            return jasperFileName;
    }

    public void setJasperFileName(String jasperFileName) {
            this.jasperFileName = jasperFileName;
    }

    public Map<String,Object> getQueryParams() {
            return queryParams;
    }

    public void setQueryParams(Map<String,Object> queryParams) {
            this.queryParams = queryParams;
    }

}
