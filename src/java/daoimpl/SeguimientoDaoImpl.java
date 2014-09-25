/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.SeguimientoDAO;
import java.util.ArrayList;
import java.util.List;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class SeguimientoDaoImpl implements SeguimientoDAO {

    Session session;

    @Override
    public List getSeguimientoGrande(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entragetseguimiento grande");
            session.beginTransaction();
            Query query = session.createSQLQuery("select TRAM_NUM,\n"
                    + "MOVI_NUM,\n"
                    + "MOVI_ORIGEN,\n"
                    + "MOVI_DESTINO,\n"
                    + "DECODE(to_char(MOVI_FEC_ENV, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(MOVI_FEC_ENV, 'dd/MM/yyyy HH:mm:ss')) AS FECHAENVIO,\n"
                    + "DECODE(to_char(MOVI_FEC_ING, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(MOVI_FEC_ING, 'dd/MM/yyyy HH:mm:ss')) AS FECHAING,\n"
                    + "INDI_NOMBRE,\n"
                    + "DECODE(MOVI_OBS,NULL,' ',MOVI_OBS) AS OBSERVACION, \n"
                    + "ESTA_NOMBRE\n"
                    + "from vw_ogpl002@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "' \n"
                    + "order by movi_num desc");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problemasseguimiento grande");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        System.out.println("retorna grande");
        return codigos;
    }

    @Override
    public List getSeguimiento(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entragetseguimiento");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TM.TRAM_NUM,\n"
                    + "TM.MOVI_NUM,\n"
                    + "M1.NOMBRE as NOMBRE1,\n"
                    + "M2.NOMBRE as NOMBRE2,\n"
                    + "DECODE(to_char(TM.FECHA_ENVIO, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(TM.FECHA_ENVIO, 'dd/MM/yyyy HH:mm:ss')) AS FECHAENVIO,\n"
                    + "DECODE(to_char(TM.FECHA_INGR, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(TM.FECHA_INGR, 'dd/MM/yyyy HH:mm:ss')) AS FECHAINGRESO,\n"
                    + "I.INDI_NOMBRE,\n"
                    + "DECODE(TM.MOVI_OBS,NULL,' ',TM.MOVI_OBS) AS MOVI,\n"
                    + "TM.ESTA_NOMBRE\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA M1, DEPENDENCIA M2, INDICADOR I\n"
                    + "WHERE TM.TRAM_NUM='" + tramnum + "' \n"
                    + "AND TM.CODIGO=M1.CODIGO\n"
                    + "AND TM.CODIGO1=M2.CODIGO\n"
                    + "AND TM.INDI_COD=I.INDI_COD\n"
                    + "ORDER BY TM.FECHA_INGR DESC");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problemasseguimiento");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        System.out.println("retorna");
        return codigos;
    }

    @Override
    public List seguimientoUser(String oficina) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TM.TRAM_NUM,\n"
                    + "TM.MOVI_NUM,\n"
                    + "M1.NOMBRE AS ORIGEN,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "DECODE(to_char(TM.FECHA_ENVIO, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(TM.FECHA_ENVIO, 'dd/MM/yyyy HH:mm:ss')) AS FECHAENVIO,\n"
                    + "DECODE(to_char(TM.FECHA_INGR, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(TM.FECHA_INGR, 'dd/MM/yyyy HH:mm:ss')) AS FECHAINGRESO,\n"
                    + "I.INDI_NOMBRE,\n"
                    + "DECODE(TM.MOVI_OBS,NULL,' ',TM.MOVI_OBS) AS MOVI,\n"
                    + "TM.ESTA_NOMBRE\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA M1, DEPENDENCIA M2, INDICADOR I\n"
                    + "WHERE TM.CODIGO1='" + oficina + "' \n"
                    + "AND TM.CODIGO=M1.CODIGO\n"
                    + "AND TM.CODIGO1=M2.CODIGO\n"
                    + "AND TM.INDI_COD=I.INDI_COD\n"
                    + "AND TM.estad_confrirm IS NULL\n"
                    + "ORDER BY TM.FECHA_INGR DESC");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró1111");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return codigos;
    }

    @Override
    public void GuadarTramiteDatos(TramiteDatos td,TipoDocu tdocu) {
        GuardarTD(td);
        GuardarTDoc(tdocu);
    }

    @Override
    public void GuardarTramiteMovimiento(TramiteMovimiento tm) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tm);
            session.getTransaction().commit();
            System.out.println("se ha guardado tramite movimiento");
        } catch (Exception e) {
            System.err.println("falló guardado tramitedatos" + e);
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void GuardarTD(TramiteDatos td) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(td);
            session.getTransaction().commit();
            System.out.println("se ha guardado tramite datos");
        } catch (Exception e) {
            System.err.println("falló guardado tramitedatos" + e);
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void GuardarTDoc(TipoDocu tdocu) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tdocu);
            session.getTransaction().commit();
            System.out.println("se ha guardado tramite documentos");
        } catch (Exception e) {
            System.err.println("falló guardado tipodocus" + e);
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    @Override
    public List tramiteDatos(String tramnum) {
        List td=null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TRAM_NUM,TRAM_FECHA,DEPE_COD,TRAM_OBS,ESTA_DESCRIP,USU\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND DOCU_PRIC='1'");
            td=(List)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problem oficina");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return td;
    }

    @Override
    public List TiposDocus(String tramnum) {
        List td=null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TRAM_NUM,DOCU_NOMBRE,DOCU_NUM,DOCU_PRIC,DOCU_SIGLAS,DOCU_ANIO\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND DOCU_PRIC='1'");
            td=(List)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problem tiposdocus");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return td;
    }

}
