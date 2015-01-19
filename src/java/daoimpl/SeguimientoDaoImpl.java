/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.SeguimientoDAO;
import java.util.ArrayList;
import java.util.List;
import maping.Temporal;
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
    public void temporal(Temporal t) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
            System.out.println("se ha guardado en el temporal");
        } catch (Exception e) {
            System.err.println("falló guardado en temporal" + e);
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public int getContadorTemporal() {
        int contador = 0;
        String sql = "SELECT MAX(TO_NUMBER(CONTADOR)) AS CONTADOR FROM TEMPORAL";
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            contador = (Integer) session.createSQLQuery(sql).uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problemas get contador");
            System.out.println(e.getMessage());
        }
        return contador;
    }

    @Override
    public List getSeguimientoGrande1(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            String sql = "select TRAM_NUM,\n"
                    + "MOVI_NUM,\n"
                    + "MOVI_ORIGEN AS ORIGEN,\n"
                    + "MOVI_DESTINO AS DESTINO,\n"
                    + "DECODE(to_char(MOVI_FEC_ENV, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(MOVI_FEC_ENV, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAENVIO,\n"
                    + "DECODE(to_char(MOVI_FEC_ING, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(MOVI_FEC_ING, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAING,\n"
                    + "INDI_NOMBRE,\n"
                    + "DECODE(MOVI_OBS,NULL,' ',MOVI_OBS) AS OBSERVACION, \n"
                    + "ESTA_NOMBRE\n"
                    + "from vw_ogpl002@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND MOVI_FEC_ING IS NOT NULL\n"
                    + "AND MOVI_DESTINO NOT LIKE '%OGPL-%'\n"
                    + "order by MOVI_NUM DESC";
            System.out.println("entragetseguimiento grande");
            session.beginTransaction();
            codigos = (List) session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problemasseguimiento grande 1");
            System.out.println(e.getMessage());
        }
        System.out.println("retorna grande");
        return codigos;
    }

    @Override
    public List getSeguimientoGrande2(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        /*String sql = "select M.tramiteDatos.tramNum,\n"
         + "M.moviNum,\n"
         + "M1.nombre AS ORIGEN, \n"
         + "M2.nombre AS DESTINO,\n"
         + "DECODE(to_char(M.fechaEnvio, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(M.fechaEnvio, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAENVIO,\n"
         + "DECODE(to_char(M.fechaIngr, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(M.fechaIngr, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAING,\n"
         + "I.indiNombre,\n"
         + "DECODE(M.moviObs,NULL,' ',M.moviObs) AS OBSERVACION,\n"
         + "M.estaNombre\n"
         + "from TramiteMovimiento M, Indicador I, Dependencia M1, Dependencia M2\n"
         + "where M.tramiteDatos.tramNum='" + tramnum + "'\n"
         + "AND M.dependenciaByCodigo.codigo=M1.codigo\n"
         + "AND M.dependenciaByCodigo1.codigo=M2.codigo\n"
         + "AND M.indicador.indiCod=I.indiCod";*/
        String sql = "select M.TRAM_NUM,\n"
                + "M.MOVI_NUM,\n"
                + "M1.NOMBRE AS ORIGEN, \n"
                + "M2.NOMBRE AS DESTINO,\n"
                + "DECODE(to_char(M.FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(M.FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAENVIO,\n"
                + "DECODE(to_char(M.FECHA_INGR, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(M.FECHA_INGR, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAING,\n"
                + "I.INDI_NOMBRE,\n"
                + "DECODE(M.MOVI_OBS,NULL,' ',M.MOVI_OBS) AS OBSERVACION,\n"
                + "ESTA_NOMBRE\n"
                + "from TRAMITE_MOVIMIENTO M, INDICADOR I, DEPENDENCIA M1, DEPENDENCIA M2\n"
                + "where M.TRAM_NUM='" + tramnum + "'\n"
                + "AND M.CODIGO=M1.CODIGO\n"
                + "AND M.CODIGO1=M2.CODIGO\n"
                + "AND M.INDI_COD=I.INDI_COD\n"
                + "AND M2.NOMBRE NOT LIKE '%OFICINA GENERAL DE PLANIFICACION%'";
        try {

            System.out.println("entragetseguimiento grande");
            session.beginTransaction();
            codigos = (List) session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problemasseguimiento grande 2");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("retorna grande");
        return codigos;
    }

    @Override
    public List getSeguimientoGrande(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entragetseguimiento grande");
            session.beginTransaction();
            Query query = session.createSQLQuery("select TRAM_NUM,\n"
                    + "MOVI_NUM,\n"
                    + "MOVI_ORIGEN AS ORIGEN,\n"
                    + "MOVI_DESTINO AS DESTINO,\n"
                    + "DECODE(to_char(MOVI_FEC_ENV, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(MOVI_FEC_ENV, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAENVIO,\n"
                    + "DECODE(to_char(MOVI_FEC_ING, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(MOVI_FEC_ING, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAING,\n"
                    + "INDI_NOMBRE,\n"
                    + "DECODE(MOVI_OBS,NULL,' ',MOVI_OBS) AS OBSERVACION, \n"
                    + "ESTA_NOMBRE\n"
                    + "from VISTA_2\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND MOVI_FEC_ING IS NOT NULL\n"
                    + "AND MOVI_DESTINO NOT LIKE '%OGPL -%'"
                    + "UNION\n"
                    + "select M.TRAM_NUM,\n"
                    + "        M.MOVI_NUM,\n"
                    + "        M1.NOMBRE AS ORIGEN, \n"
                    + "        M2.NOMBRE AS DESTINO,\n"
                    + "        DECODE(to_char(FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAENVIO,\n"
                    + "        DECODE(to_char(FECHA_INGR, 'dd/MM/yyyy HH24:MI:ss'),NULL,' ',to_char(FECHA_INGR, 'dd/MM/yyyy HH24:MI:ss')) AS FECHAING,\n"
                    + "        I.INDI_NOMBRE,\n"
                    + "        DECODE(M.MOVI_OBS,NULL,' ',M.MOVI_OBS) AS OBSERVACION,\n"
                    + "        ESTA_NOMBRE\n"
                    + "from tramite_movimiento M, INDICADOR I, DEPENDENCIA M1, DEPENDENCIA M2\n"
                    + "where M.TRAM_NUM='" + tramnum + "'\n"
                    + "AND M.CODIGO=M1.CODIGO\n"
                    + "AND M.CODIGO1=M2.CODIGO\n"
                    + "AND M.INDI_COD=I.INDI_COD");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problemasseguimiento grande");
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
                    + "DECODE(to_char(TM.FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:SS'),NULL,' ',to_char(TM.FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:SS')) AS FECHAENVIO,\n"
                    + "DECODE(to_char(TM.FECHA_INGR, 'dd/MM/yyyy HH24:MI:SS'),NULL,' ',to_char(TM.FECHA_INGR, 'dd/MM/yyyy HH24:MI:SS')) AS FECHAINGRESO,\n"
                    + "I.INDI_NOMBRE,\n"
                    + "DECODE(TM.MOVI_OBS,NULL,' ',TM.MOVI_OBS) AS MOVI,\n"
                    + "TM.ESTA_NOMBRE,\n"
                    + "DECODE(TM.ESTAD_CONFRIRM,NULL,'NO CONFIRMADO',TM.ESTAD_CONFRIRM) AS CONFIRMADO,\n"
                    + "USUA.USU_NOMBRE AS NOMBRE,\n"
                    + "D1.NOMBRE AS ORIGEN_PRINCIPAL,\n"
                    + "TDOCU.DOCU_NOMBRE||'-'||TDOCU.DOCU_NUM||'-'||TDOCU.DOCU_SIGLAS||'-'||TDOCU.DOCU_ANIO AS DOCUMENTO_PRINCIPAL\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA M1, DEPENDENCIA M2, INDICADOR I,USUARIO USUA, TIPO_DOCU TDOCU, TRAMITE_DATOS TDATOS, DEPENDENCIA D1\n"
                    + "WHERE TM.CODIGO1='" + oficina + "' \n"
                    + "AND TM.CODIGO=M1.CODIGO\n"
                    + "AND TM.CODIGO1=M2.CODIGO\n"
                    + "AND TM.INDI_COD=I.INDI_COD\n"
                    + "AND TM.USU=USUA.USU\n"
                    + "AND TM.TRAM_NUM||'-'||TM.TRAM_FECHA||'-'||I.INDI_NOMBRE NOT IN (\n"
                    + "                                                        SELECT TM2.TRAM_NUM||'-'||TM2.TRAM_FECHA||'-'||I2.INDI_NOMBRE\n"
                    + "                                                        FROM TRAMITE_MOVIMIENTO TM2, INDICADOR I2\n"
                    + "                                                        WHERE TM2.CODIGO1='"+oficina+"'\n"
                    + "                                                        AND INDI_NOMBRE='ARCHIVO'\n"
                    + "                                                        AND TM.INDI_COD=I.INDI_COD )\n"
                    + "AND TDATOS.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND TDATOS.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND D1.CODIGO=TDATOS.CODIGO\n"
                    + "AND TDOCU.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND TDOCU.TRAM_NUM=TM.TRAM_NUM\n"
                    + "ORDER BY FECHA_ENVIO DESC");
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
    public void GuadarTramiteDatos(TramiteDatos td, TipoDocu tdocu) {
        if (tdocu != null) {
            System.out.println("entra aca 1");
            GuardarTD(td);
            GuardarTDoc(tdocu);
        } else {
            System.out.println("entra aca 2");
            GuardarTD(td);
        }

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
        List td = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TRAM_NUM,DEPE_COD,TRAM_OBS,ESTA_DESCRIP,USU\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND DOCU_PRIC='1'");
            td = (List) query.list();
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
        List td = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TRAM_NUM,DOCU_NOMBRE,DOCU_NUM,DOCU_PRIC,DOCU_SIGLAS,DOCU_ANIO\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND DOCU_PRIC='1'");
            td = (List) query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("problem tiposdocus");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return td;
    }

    @Override
    public List getDesignados(String oficina) {
        List td = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT USU_NOMBRE\n"
                    + "FROM USUARIO\n"
                    + "where ID_OFICINA='" + oficina + "'\n"
                    + "AND ESTADO='activo'");
            td = (List) query.list();
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
