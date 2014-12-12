/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.DocusExtDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DocusExtDaoImpl implements DocusExtDAO {

    Session session;

    @Override
    public List getDocusExt() {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUSEXT");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select DE.ID||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY') as documento,\n"
                    + "DE.NUMERODOC,\n"
                    + "DE.MOVIMIENTO_DEXT,\n"
                    + "M1.NOMBRE AS ORIGEN,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "to_char(DE.FECHA,'DD/MM/YYY HH:mm:ss') as fecha,\n"
                    + "DEXT.NOMBDOCU,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "from DOCUS_EXTINT DE, DEPENDENCIA M1, DEPENDENCIA M2, DOCUS_EXT DEXT, USUARIO USUA, OFICINA oficina\n"
                    + "WHERE DE.CODIGO=M1.CODIGO\n"
                    + "AND DE.CODIGO1=M2.CODIGO\n"
                    + "AND DE.IDDOC=DEXT.IDDOC\n"
                    + "AND DE.USU=USUA.USU\n"
                    + "AND USUA.ID_OFICINA=oficina.ID_OFICINA\n"
                    + "ORDER BY DE.ID DESC");
            proveidos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal DOCUSEXT");
            System.out.println(e.getMessage());
        }
        return proveidos;
    }

}
