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
    public List getCircularesOficInternaXtipo(String usu, String tipo) {
        List circulares = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get circulares");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N°-'||OFI.CORRELA_OFICIC||'-'||oficina.siglas||'-'||to_char(OFI.fecha,'YYYY') as documento,\n"
                    + "OFI.ASUNTO,\n"
                    + "to_char(OFI.FECHA,'DD/MM/YYYY HH:mm:ss') as fecha,\n"
                    + "D1.NOMBRE as origen,\n"
                    + "OFI.FIRMA,\n"
                    + "OFI.RESPONSABLE\n"
                    + "FROM OFIC_CIRC OFI, DEPENDENCIA D1, Oficina oficina, TIPOS_DOCUMENTOS TD\n"
                    + "WHERE OFI.CODIGO=D1.CODIGO\n"
                    + "and D1.Nombre=oficina.nombre_oficina\n"
                    + "AND OFI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND OFI.RESPONSABLE='" + usu + "'\n"
                    + "AND OFI.ID_DOCUMENTO='" + tipo + "'\n"
                    + "ORDER BY OFI.CORRELA_OFICIC DESC");
            circulares = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal circulares");
            System.out.println(e.getMessage());
        }
        return circulares;
    }
    
    @Override
    public List getCircularesOficInterna(String usu) {
        List circulares = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get circulares");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N°-'||OFI.CORRELA_OFICIC||'-'||oficina.siglas||'-'||to_char(OFI.fecha,'YYYY') as documento,\n"
                    + "OFI.ASUNTO,\n"
                    + "to_char(OFI.FECHA,'DD/MM/YYYY HH:mm:ss') as fecha,\n"
                    + "D1.NOMBRE as origen,\n"
                    + "OFI.FIRMA,\n"
                    + "OFI.RESPONSABLE\n"
                    + "FROM OFIC_CIRC OFI, DEPENDENCIA D1, Oficina oficina, TIPOS_DOCUMENTOS TD\n"
                    + "WHERE OFI.CODIGO=D1.CODIGO\n"
                    + "and D1.Nombre=oficina.nombre_oficina\n"
                    + "AND OFI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND OFI.RESPONSABLE='" + usu + "'\n"
                    + "ORDER BY OFI.CORRELA_OFICIC DESC");
            circulares = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal circulares");
            System.out.println(e.getMessage());
        }
        return circulares;
    }
    
    @Override
    public List getDocInternosXtipo(String usu, String tipo) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP,\n"
                    + "       TD.NOMBRE_DOCU||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "       DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "       D1.NOMBRE AS ORIGEN,\n"
                    + "       D2.NOMBRE AS DESTINO,\n"
                    + "       USUA.USU_NOMBRE\n"
                    + "       FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "       WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "       AND DI.CODIGO=D1.CODIGO\n"
                    + "       AND DI.CODIGO1=D2.CODIGO\n"
                    + "       AND DI.USU=USUA.USU\n"
                    + "       AND DI.DOCU_SIGLASINT='"+usu+"'\n"
                    + "       AND DI.ID_DOCUMENTO='"+tipo+"'\n"
                    + "       AND DI.TRAM_NUM IS NULL\n"
                    + "       ORDER BY DI.DOCU_CORRELAINT DESC");
            /*Query query = session.createSQLQuery("SELECT DOIF.ID_DOCOFINT,\n"
                    + "TD.NOMBRE_DOCU||' N°-'||DOIF.CORRELATIVO_DOCOFINT||'-'||DOIF.SIGLAS||'-'||TO_CHAR(DOIF.FECHA,'YYYY') AS DOCUMENTO,\n"
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
                    + "AND DOIF.USU='" + usu + "'\n"
                    + "AND DOIF.ID_DOCUMENTO='" + tipo + "'\n"
                    + "ORDER BY DOIF.CORRELATIVO_DOCOFINT DESC");*/
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
    public List getDocInternos(String siglas) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            /*Query query = session.createSQLQuery("SELECT DOIF.ID_DOCOFINT,\n"
             + "TD.NOMBRE_DOCU||' N°-'||DOIF.CORRELATIVO_DOCOFINT||'-'||DOIF.SIGLAS||'-'||TO_CHAR(DOIF.FECHA,'YYYY') AS DOCUMENTO,\n"
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
             + "AND DOIF.USU='" + usu + "'\n"
             + "ORDER BY DOIF.CORRELATIVO_DOCOFINT DESC");*/
            Query query = session.createSQLQuery("SELECT DI.IDTIP,\n"
                    + "       TD.NOMBRE_DOCU||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "       DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "       D1.NOMBRE AS ORIGEN,\n"
                    + "       D2.NOMBRE AS DESTINO,\n"
                    + "       USUA.USU_NOMBRE\n"
                    + "       FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "       WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "       AND DI.CODIGO=D1.CODIGO\n"
                    + "       AND DI.CODIGO1=D2.CODIGO\n"
                    + "       AND DI.USU=USUA.USU\n"
                    + "       AND DI.DOCU_SIGLASINT='"+siglas+"'\n"
                    + "       AND DI.TRAM_NUM IS NULL\n"
                    + "       ORDER BY DI.DOCU_CORRELAINT DESC");
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
    public List getDocusInternos(String siglas) {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNO");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP,\n"
                    + "DI.DOCU_CORRELAINT,\n"
                    + "DI.DOCU_NOMBREINT,\n"
                    + "DI.DOCU_SIGLASINT,\n"
                    + "DI.DOCU_ANIOINT,\n"
                    + "TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "DI.TRAM_NUM,\n"
                    + "DI.DOCU_ASUNTO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TRAMITE_MOVIMIENTO TM\n"
                    + "WHERE DI.DOCU_SIGLASINT='" + siglas + "'\n"
                    + "AND TM.USU=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NOT NULL\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
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
