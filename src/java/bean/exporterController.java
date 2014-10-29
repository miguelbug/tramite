/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author USUARIO
 */
import java.io.Serializable;
import java.lang.Boolean;
import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

//import org.primefaces.extensions.showcase.model.Distance;
/**
 * ExporterController
 *
 * @author Sudheer Jonna / last modified by $Author$
 * @version $Revision$
 * @since 1.0
 */
@ManagedBean
@ApplicationScoped
public class exporterController implements Serializable {

    private static final long serialVersionUID = 20120316L;

    private Boolean customExporter;

    public exporterController() {
        customExporter = false;
    }

    public Boolean getCustomExporter() {
        return customExporter;
    }

    public void setCustomExporter(Boolean customExporter) {
        this.customExporter = customExporter;
    }
}
