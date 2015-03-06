/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoimpl;

import dao.TemporaldiDao;
import maping.TemporalCargos;
import maping.TemporalDi;
import maping.TemporalUser;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class TemporalDiDaoImpl implements TemporaldiDao{
    Session session;

    @Override
    public void actualizarTemporalCargo() {
        System.out.println("actualizar TEMPORAL DU");
        String sql = "Update TEMPORALCARGOS SET REIMPRESO='1' WHERE IMPRESO='1' AND REIMPRESO='0'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar TEMPORAL DU");
        } catch (Exception e) {
            System.out.println("mal actualizar TEMPORAL DU");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado¡¡¡¡¡¡¡: " + i);
    }

    @Override
    public void guardarCargos(TemporalCargos tc) {
        System.out.println("entra a guardar temporalcargos");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tc);
            session.getTransaction().commit();
            System.out.println("terminó guardar temporalcargos");
        } catch (Exception e) {
            System.out.println("mal guardar temporalcargos");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void guardarTemporalUser(TemporalUser tu) {
        System.out.println("entra a guardar temporalDuser");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tu);
            session.getTransaction().commit();
            System.out.println("terminó guardar temporalDuser");
        } catch (Exception e) {
            System.out.println("mal guardar temporalDuser");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void actualizarTemporalUser() {
        System.out.println("actualizar TEMPORAL DU");
        String sql = "Update TEMPORAL_USER SET REIMPRESO='1' WHERE IMPRESO='1' AND REIMPRESO='0'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar TEMPORAL DU");
        } catch (Exception e) {
            System.out.println("mal actualizar TEMPORAL DU");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado¡¡¡¡¡¡¡: " + i);
    }

    @Override
    public void guardarTemporalDi(TemporalDi tdi) {
        System.out.println("entra a guardar temporalDi");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
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
