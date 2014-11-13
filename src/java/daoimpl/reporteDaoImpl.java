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
        String sql = "Update Temporal set reimpreso='0' where impreso='1'";
        try {
            System.out.println("entra a begin");
            session.beginTransaction();
            i = session.createQuery(sql).executeUpdate();
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

}
