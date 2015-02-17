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
    
    
}
