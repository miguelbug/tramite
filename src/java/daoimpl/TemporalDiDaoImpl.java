/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoimpl;

import dao.TemporaldiDao;
import maping.TemporalDi;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class TemporalDiDaoImpl implements TemporaldiDao{
    Session session;

    @Override
    public void guardarTemporalDi(TemporalDi tdi) {
        System.out.println("entra a guardar temporalDi");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tdi);
            session.getTransaction().commit();
            System.out.println("terminó guardar temporalDi");
        } catch (Exception e) {
            System.out.println("mal guardar temporalDi");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void actualizarTemporalDi() {
        System.out.println("actualizar TEMPORAL DI");
        String sql = "Update TEMPORAL_DI SET REIMPRESO='1' WHERE IMPRESO='1' AND REIMPRESO='0'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar TEMPORAL DI");
        } catch (Exception e) {
            System.out.println("mal actualizar TEMPORAL DI");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado¡¡¡¡¡¡¡: " + i);
    }
    
    
}
