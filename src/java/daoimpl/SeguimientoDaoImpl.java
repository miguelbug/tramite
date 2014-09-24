/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.SeguimientoDAO;
import java.util.ArrayList;
import java.util.List;
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

}
