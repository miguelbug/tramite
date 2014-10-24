/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoimpl;

import dao.ConstanciaDAO;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class ConstanciaDaoImpl implements ConstanciaDAO {
    Session session;

    @Override
    public String getIndice() {
        System.out.println("getConstanciaoficio");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correlativo) from Constancias";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getConstanciaoficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }
    
}
