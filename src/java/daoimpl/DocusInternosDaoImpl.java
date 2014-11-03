/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.DocusInternosDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DocusInternosDaoImpl implements DocusInternosDAO {

    Session session;

    @Override
    public List getDocusInternos(String usu) {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNO");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DOCU_CORRELAINT,"
                    + "DOCU_NOMBREINT,"
                    + "DOCU_SIGLASINT,"
                    + "DOCU_ANIOINT,"
                    + "FECHAREGISTRO,"
                    + "TRAM_NUM,"
                    + "USU\n"
                    + "FROM DOCUS_INTERNOS\n"
                    + "WHERE USU='"+usu+"'\n"
                    + "ORDER BY DOCU_CORRELAINT DESC");
            proveidos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal getproveidos");
            System.out.println(e.getMessage());
        }
        return proveidos;
    }

}
