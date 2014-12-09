/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.DocusInternosDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                    + "USU,"
                    + "DOCU_ASUNTO\n"
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

    @Override
    public String getRespuesta(String tramnum) {
        String correlaresp="";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get respuestas");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DOCU_NOMBREINT||' N°'||DOCU_CORRELAINT||'-'||DOCU_SIGLASINT||'-'||DOCU_ANIOINT\n"
                    + "FROM DOCUS_INTERNOS\n"
                    + "WHERE TRAM_NUM='"+tramnum+"'\n"
                    + "ORDER BY DOCU_CORRELAINT DESC");
            correlaresp = (String)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal get respuestas");
            System.out.println(e.getMessage());
        }
        return correlaresp;
    }

}
