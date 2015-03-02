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
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N° '||OFI.CORRELA_OFICIC||'-'||'OGPL'||'-'||to_char(OFI.fecha,'YYYY') as documento,\n"
                    + "DECODE(OFI.ASUNTO,NULL,'SIN ASUNTO',UPPER(OFI.ASUNTO)) AS ASUNTO,\n"
                    + "to_char(OFI.FECHA,'DD/MM/YYYY HH:mm:ss') as fecha,\n"
                    + "D1.NOMBRE as origen,\n"
                    + "OFI.FIRMA,\n"
                    + "OFI.RESPONSABLE\n"
                    + "FROM OFIC_CIRC OFI, DEPENDENCIA D1, Oficina oficina, TIPOS_DOCUMENTOS TD\n"
                    + "WHERE OFI.CODIGO=D1.CODIGO\n"
                    + "and D1.Nombre=oficina.nombre_oficina\n"
                    + "AND OFI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND OFI.ID_DOCUMENTO='" + tipo + "'\n"
                    + "AND OFI.CODIGO='"+usu+"'\n"
                    + "ORDER BY OFI.CORRELA_OFICIC DESC");
            circulares = query.list();
            session.beginTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("mal circulares");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
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
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N° '||OFI.CORRELA_OFICIC||'-'||'OGPL'||'-'||to_char(OFI.fecha,'YYYY') as documento,\n"
                    + "DECODE(OFI.ASUNTO,NULL,'SIN ASUNTO',UPPER(OFI.ASUNTO)) AS ASUNTO,\n"
                    + "to_char(OFI.FECHA,'DD/MM/YYYY HH:mm:ss') as fecha,\n"
                    + "D1.NOMBRE as origen,\n"
                    + "OFI.FIRMA,\n"
                    + "OFI.RESPONSABLE\n"
                    + "FROM OFIC_CIRC OFI, DEPENDENCIA D1, Oficina oficina, TIPOS_DOCUMENTOS TD\n"
                    + "WHERE OFI.CODIGO=D1.CODIGO\n"
                    + "and D1.Nombre=oficina.nombre_oficina\n"
                    + "AND OFI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND OFI.CODIGO='"+usu+"'\n"
                    + "ORDER BY OFI.CORRELA_OFICIC DESC");
            circulares = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal circulares");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return circulares;
    }

    @Override
    public List getDocInternosXtipo_1(String usu, String tipo) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DI.TRAM_NUM AS TRAM_NUM,\n"
                    + "TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "TDOCU.DOCU_NOMBRE||'-'||TDOCU.DOCU_NUM||'-'||TDOCU.DOCU_SIGLAS||'-'||TDOCU.DOCU_ANIO AS DOCUMENTO_PRINCIPAL,\n"
                    + "D3.NOMBRE AS ORIGEN_PRINCIPAL\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, TIPO_DOCU TDOCU, TRAMITE_DATOS TDAT, DEPENDENCIA D3\n"
                    + "WHERE TM.USU=USUA.USU\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.TRAM_NUM IS NOT NULL\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
                    + "AND DI.TRAM_NUM=TDAT.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TDAT.TRAM_FECHA\n"
                    + "AND TDAT.CODIGO=D3.CODIGO\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA = TDOCU.TRAM_NUM||'-'||TDOCU.TRAM_FECHA\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='" + usu + "'\n"
                    + "AND DI.ID_DOCUMENTO='" + tipo + "'\n"
                    + "ORDER BY DI.FECHAREGISTRO DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return docinternos;
    }

    @Override
    public List getDocInternosXtipo_2(String usu, String tipo) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DECODE(DI.TRAM_NUM,NULL,'SIN EXPEDIENTE',DI.TRAM_NUM) AS TRAM_NUM,\n"
                    + "TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO\n"
                    + "\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.USU1=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NULL\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='" + usu + "'\n"
                    + "AND DI.ID_DOCUMENTO='" + tipo + "'");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return docinternos;
    }

    @Override
    public List getDocInternosXtipo(String usu, String tipo) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT R.I_D,\n"
                    + "       R.DOCUMENTO,\n"
                    + "       R.TRAM_NUM,\n"
                    + "       TO_CHAR(R.FECHA,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "       DECODE(R.ASUNTO,NULL,'SIN ASUNTO',UPPER(R.ASUNTO)) AS ASUNTO,\n"
                    + "       R.ORIGEN,\n"
                    + "       R.DESTINO,\n"
                    + "       R.ASIGNADO,\n"
                    + "       R.IDDOCUMENTO,\n"
                    + "       R.DOCUMENTO_PRINCIPAL,\n"
                    + "       R.ORIGEN_PRINCIPAL\n"
                    + "       FROM (SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DI.TRAM_NUM AS TRAM_NUM,\n"
                    + "DI.FECHAREGISTRO AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "TDOCU.DOCU_NOMBRE||'-'||TDOCU.DOCU_NUM||'-'||TDOCU.DOCU_SIGLAS||'-'||TDOCU.DOCU_ANIO AS DOCUMENTO_PRINCIPAL,\n"
                    + "D3.NOMBRE AS ORIGEN_PRINCIPAL\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, TIPO_DOCU TDOCU, TRAMITE_DATOS TDAT, DEPENDENCIA D3\n"
                    + "WHERE TM.USU=USUA.USU\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.TRAM_NUM IS NOT NULL\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
                    + "AND DI.TRAM_NUM=TDAT.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TDAT.TRAM_FECHA\n"
                    + "AND TDAT.CODIGO=D3.CODIGO\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA = TDOCU.TRAM_NUM||'-'||TDOCU.TRAM_FECHA\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='"+usu+"'\n"
                    + "AND DI.ID_DOCUMENTO='"+tipo+"'\n"
                    + "UNION\n"
                    + "SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DECODE(DI.TRAM_NUM,NULL,'SIN EXPEDIENTE',DI.TRAM_NUM) AS TRAM_NUM,\n"
                    + "DI.FECHAREGISTRO AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "'SIN DOCUMENTO' AS DOCUMENTO_PRINCIPAL,\n"
                    + "'SIN ORIGEN' AS ORIGEN_PRINCIPAL\n"
                    + "\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.USU1=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NULL\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='"+usu+"'\n"
                    + "AND DI.ID_DOCUMENTO='"+tipo+"') R\n"
                    + "\n"
                    + "ORDER BY R.FECHA DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
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
            Query query = session.createSQLQuery("SELECT R.I_D,\n"
                    + "       R.DOCUMENTO,\n"
                    + "       R.TRAM_NUM,\n"
                    + "       TO_CHAR(R.FECHA,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "       DECODE(R.ASUNTO,NULL,'SIN ASUNTO',UPPER(R.ASUNTO)),\n"
                    + "       R.ORIGEN,\n"
                    + "       R.DESTINO,\n"
                    + "       R.ASIGNADO,\n"
                    + "       R.DOCUMENTO_PRINCIPAL,\n"
                    + "       R.ORIGEN_PRINCIPAL\n"
                    + "       FROM (SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DI.TRAM_NUM AS TRAM_NUM,\n"
                    + "DI.FECHAREGISTRO AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "TDOCU.DOCU_NOMBRE||'-'||TDOCU.DOCU_NUM||'-'||TDOCU.DOCU_SIGLAS||'-'||TDOCU.DOCU_ANIO AS DOCUMENTO_PRINCIPAL,\n"
                    + "D3.NOMBRE AS ORIGEN_PRINCIPAL\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, TIPO_DOCU TDOCU, TRAMITE_DATOS TDAT, DEPENDENCIA D3\n"
                    + "WHERE TM.USU=USUA.USU\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.TRAM_NUM IS NOT NULL\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
                    + "AND DI.TRAM_NUM=TDAT.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TDAT.TRAM_FECHA\n"
                    + "AND TDAT.CODIGO=D3.CODIGO\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA = TDOCU.TRAM_NUM||'-'||TDOCU.TRAM_FECHA\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='" + siglas + "'\n"
                    + "UNION\n"
                    + "SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DECODE(DI.TRAM_NUM,NULL,'SIN EXPEDIENTE',DI.TRAM_NUM) AS TRAM_NUM,\n"
                    + "DI.FECHAREGISTRO AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "'SIN DOCUMENTO' AS DOCUMENTO_PRINCIPAL,\n"
                    + "'SIN ORIGEN' AS ORIGEN_PRINCIPAL\n"
                    + "\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.USU1=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NULL\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='" + siglas + "') R\n"
                    + "\n"
                    + "ORDER BY R.FECHA DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return docinternos;
    }

    @Override
    public List getDocInternos1(String usu) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS 1");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DI.TRAM_NUM AS TRAM_NUM,\n"
                    + "TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "TDOCU.DOCU_NOMBRE||'-'||TDOCU.DOCU_NUM||'-'||TDOCU.DOCU_SIGLAS||'-'||TDOCU.DOCU_ANIO AS DOCUMENTO_PRINCIPAL,\n"
                    + "D3.NOMBRE AS ORIGEN_PRINCIPAL\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, TIPO_DOCU TDOCU, TRAMITE_DATOS TDAT, DEPENDENCIA D3\n"
                    + "WHERE TM.USU=USUA.USU\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.TRAM_NUM IS NOT NULL\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
                    + "AND DI.TRAM_NUM=TDAT.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TDAT.TRAM_FECHA\n"
                    + "AND TDAT.CODIGO=D3.CODIGO\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA = TDOCU.TRAM_NUM||'-'||TDOCU.TRAM_FECHA\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='" + usu + "'\n"
                    + "ORDER BY DI.FECHAREGISTRO DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return docinternos;
    }

    @Override
    public List getDocInternos2(String usu) {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DECODE(DI.TRAM_NUM,NULL,'SIN EXPEDIENTE',DI.TRAM_NUM) AS TRAM_NUM,\n"
                    + "TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO\n"
                    + "\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.USU1=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NULL\n"
                    + "AND DI.DOCU_SIGLASINT NOT IN ('OGPL')\n"
                    + "AND DI.DOCU_SIGLASINT='" + usu + "'\n"
                    + "ORDER BY DI.FECHAREGISTRO DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal getproveidos");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return proveidos;
    }

    @Override
    public String getRespuesta(String tramnum, String movi) {
        String correlaresp = "";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get respuestas");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DOCU_NOMBREINT||' N°'||DOCU_CORRELAINT||'-'||DOCU_SIGLASINT||'-'||DOCU_ANIOINT\n"
                    + "FROM DOCUS_INTERNOS\n"
                    + "WHERE TRAM_NUM='" + tramnum + "'\n"
                    + "AND NUMERO_MOVI='" + movi + "'\n"
                    + "ORDER BY DOCU_CORRELAINT DESC");
            correlaresp = (String) query.uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get respuestas");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return correlaresp;
    }

}
