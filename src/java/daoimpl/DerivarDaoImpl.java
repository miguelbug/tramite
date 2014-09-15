/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.DerivarDAO;
import maping.Usuario;
import org.hibernate.classic.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DerivarDaoImpl implements DerivarDAO {

    private Session session;

    @Override
    public String getIndice() {
        System.out.println("loginbuscar");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(id_tipdocint) from docus_internos ";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal indice");
            session.beginTransaction().rollback();
            session.close();
        }
        return index;
    }

    @Override
    public String getSiglas(String ofi) {
        System.out.println("loginbuscar");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select ofi.siglasofi\n"
                + "from oficina ofi, usuario usu\n"
                + "where usu.USU='" + ofi + "'\n"
                + "and usu.ID_OFICINA=ofi.ID_OFICINA;";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal indice");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            session.close();
        }
        return index;
    }

    @Override
    public int getMovimiento(String tramnum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
