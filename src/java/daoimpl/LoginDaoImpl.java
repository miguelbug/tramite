/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoimpl;

import org.hibernate.Session;
import dao.LoginDao;
import util.HibernateUtil;
import maping.Usuario;

/**
 *
 * @author OGPL
 */
public class LoginDaoImpl implements LoginDao{
    
    Session session;
    
    @Override
    public Usuario getUsuario(String usu, String pass) {
        System.out.println("loginbuscar");
        Usuario nuevoUsu = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: "+usu+" "+pass);
        String sql = "FROM Usuario WHERE USU = '" + usu + "' AND CLAVE='" + pass + "'";
        try {
            session.beginTransaction();
            nuevoUsu = (Usuario) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal");
            session.beginTransaction().rollback();
            session.close();
        }
        return nuevoUsu;
    }
    
}
