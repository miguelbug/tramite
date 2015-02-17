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
            Query query = session.createSQLQuery("select DE.CORRELATIVOD||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY') as documento,\n"
                    + "DE.NUMERODOC,\n"
                    + "DE.ASUNTO,\n"
                    + "M1.NOMBRE AS ORIGEN,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "to_char(DE.FECHA,'DD/MM/YYY HH:mm:ss') as fecha,\n"
                    + "TD.NOMBRE_DOCU,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "from DOCUS_EXTINT DE, DEPENDENCIA M1, DEPENDENCIA M2, TIPOS_DOCUMENTOS TD, USUARIO USUA, OFICINA oficina\n"
                    + "WHERE DE.CODIGO=M1.CODIGO\n"
                    + "AND DE.CODIGO1=M2.CODIGO\n"
                    + "AND DE.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DE.USU=USUA.USU\n"
                    + "AND USUA.ID_OFICINA=oficina.ID_OFICINA\n"
                    + "AND DE.EXT_INT='pe'\n"
                    + "ORDER BY DE.FECHA DESC");
            proveidos = query.list();
            session.beginTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("mal DOCUSEXT");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return proveidos;
    }

}
