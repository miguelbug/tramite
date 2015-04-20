/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoimpl;

import dao.IndicadorDAO;
import maping.Indicador;
import maping.Usuario;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class IndicadorDaoImpl implements IndicadorDAO {
    Session session;
    @Override
    public Indicador getIndicador(String nombre) {
        System.out.println("getindicador");
        Indicador nuevoIndic = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("nombre");
        String sql = "FROM INDICADOR WHERE INDI_NOMBRE='" + nombre + "'";
        try {
            session.beginTransaction();
            nuevoIndic = (Indicador) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal");
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return nuevoIndic;
    }

    @Override
    public boolean validarIndicador(String nombre) {
        boolean esta=false;
        Indicador nuevoIndic = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("validar Indicador");
        String sql = "FROM Indicador WHERE indiNombre='" + nombre + "'";
        System.out.println(sql);
        try {
            session.beginTransaction();
            nuevoIndic = (Indicador) session.createSQLQuery(sql).uniqueResult();
            session.beginTransaction().commit();
            esta=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("mal");
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return esta;
    }

    @Override
    public void insertarIndicador(Indicador Ind) {
        System.out.println("entra a guardar indicador");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(Ind);
            session.getTransaction().commit();
            System.out.println("terminó guardar indicador");
        } catch (Exception e) {
            System.out.println("mal guardar indicador");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    
}
