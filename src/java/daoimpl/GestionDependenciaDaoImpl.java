/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoimpl;

import dao.GestionDependenciaDao;
import maping.Dependencia;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class GestionDependenciaDaoImpl implements GestionDependenciaDao{
    Session session;
    @Override
    public void GuardarDependencia(Dependencia d) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(d);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló guardado dependencia" + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
}
