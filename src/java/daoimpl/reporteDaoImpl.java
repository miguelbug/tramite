/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.reporteDAO;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class reporteDaoImpl implements reporteDAO {

    Session session;

    @Override
    public void ActualizarTemporal() {
        int i=0;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "Update Temporal set reimpreso='0' where impreso='1' and reimpreso is null";
        try {
            System.out.println("entra a begin");
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.beginTransaction().commit();
            System.out.println("sale de begin");
        } catch (Exception e) {
            System.out.println("mal confirmar");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("actualizados: " + i);
    }

    @Override
    public String getfechaderivado(String tramnum, String movi) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT TO_CHAR(FECHA_DERIVACION,'DD/MM/YYYY HH:MM:SS')AS FECHA FROM TRAMITE_MOVIMIENTO WHERE TRAM_NUM='"+tramnum+"' AND MOVI_NUM='"+movi+"' ";
        String fecha="";
        try {
            System.out.println("entra a begin");
            session.beginTransaction();
            fecha=(String)session.createSQLQuery(sql).uniqueResult();
            session.beginTransaction().commit();
            System.out.println("sale de begin");
        } catch (Exception e) {
            System.out.println("mal confirmar");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return fecha;
    }
        

}
