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
    public List getCircularesOficInterna(String usu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getDocInternosXtipo(String usu, String tipo) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N°-'||DOIF.CORRELATIVO_DOCOFINT||'-'||DOIF.SIGLAS||'-'||TO_CHAR(DOIF.FECHA,'YYYY') AS DOCUMENTO,\n"
                    + "DOIF.ASUNTO,\n"
                    + "TO_CHAR(DOIF.FECHA,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "FROM DOCUMENTOS_OFIINT DOIF, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DOIF.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DOIF.CODIGO=D1.CODIGO\n"
                    + "AND DOIF.CODIGO1=D2.CODIGO\n"
                    + "AND DOIF.USU=USUA.USU\n"
                    + "AND DOIF.USU='"+usu+"'\n"
                    + "AND DOIF.ID_DOCUMENTO='"+tipo+"'"
                    + "ORDER BY DOIF.CORRELATIVO_DOCOFINT DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
        }
        return docinternos;
    }

    @Override
    public List getDocInternos(String usu) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N°-'||DOIF.CORRELATIVO_DOCOFINT||'-'||DOIF.SIGLAS||'-'||TO_CHAR(DOIF.FECHA,'YYYY') AS DOCUMENTO,\n"
                    + "DOIF.ASUNTO,\n"
                    + "TO_CHAR(DOIF.FECHA,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "FROM DOCUMENTOS_OFIINT DOIF, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DOIF.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DOIF.CODIGO=D1.CODIGO\n"
                    + "AND DOIF.CODIGO1=D2.CODIGO\n"
                    + "AND DOIF.USU=USUA.USU\n"
                    + "AND DOIF.USU='"+usu+"'"
                    + "ORDER BY DOIF.CORRELATIVO_DOCOFINT DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
        }
        return docinternos;
    }

    @Override
    public List getDocusInternos(String usu) {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNO");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP,"
                    + "DI.DOCU_CORRELAINT,"
                    + "DI.DOCU_NOMBREINT,"
                    + "DI.DOCU_SIGLASINT,"
                    + "DI.DOCU_ANIOINT,"
                    + "TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:MI:SS') AS FECHA,"
                    + "DI.TRAM_NUM,"
                    + "USUA.USU_NOMBRE,"
                    + "DI.DOCU_ASUNTO\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA\n"
                    + "WHERE DI.USU='" + usu + "'\n"
                    + "AND DI.USU=USUA.USU\n"
                    + "ORDER BY DI.DOCU_CORRELAINT DESC");
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
        String correlaresp = "";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get respuestas");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DOCU_NOMBREINT||' N°'||DOCU_CORRELAINT||'-'||DOCU_SIGLASINT||'-'||DOCU_ANIOINT\n"
                    + "FROM DOCUS_INTERNOS\n"
                    + "WHERE TRAM_NUM='" + tramnum + "'\n"
                    + "ORDER BY DOCU_CORRELAINT DESC");
            correlaresp = (String) query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal get respuestas");
            System.out.println(e.getMessage());
        }
        return correlaresp;
    }

}
