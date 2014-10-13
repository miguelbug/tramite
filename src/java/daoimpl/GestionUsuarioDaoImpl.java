/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.GestionUsuarioDAO;
import maping.Usuario;
import org.hibernate.classic.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class GestionUsuarioDaoImpl implements GestionUsuarioDAO {

    private Session session;

    @Override
    public Usuario ValidarClave(String clave, String usua) {
        System.out.println("validar clave");
        Usuario aux = null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Usuario WHERE clave='" + clave + "' and usu='" + usua+ "'";
        try {
            session.beginTransaction();
            aux = (Usuario) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal validar");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return aux;
    }

    @Override
    public void Cambiar(Usuario usu) {
        System.out.println("cambiar usu");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(usu);
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal actualizar usuario");
            System.out.println(e.getMessage());
        } finally {
            session.close();

        }
    }

}
