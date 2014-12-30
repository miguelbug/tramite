/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.GestionUsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import maping.Dependencia;
import maping.Jefatura;
import maping.Oficina;
import maping.Profesion;
import maping.TipoContrato;
import maping.Usuario;
import org.hibernate.Query;
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

    @Override
    public List getOficinas() {
        List<String> oficinas = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get oficinas");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT NOMBRE_OFICINA FROM OFICINA");
            oficinas = (ArrayList<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal GET OFICINAS");
            System.out.println(e.getMessage());
        }
        return oficinas;
    }

    @Override
    public List getProfesion() {
        List<String> profesion = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get profesion");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT PROFESION_NOMBRE FROM PROFESION");
            profesion = (ArrayList<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal GET profesion");
            System.out.println(e.getMessage());
        }
        return profesion;
    }

    @Override
    public List getContrato() {
        List<String> contrato = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get contrato");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT NOMBRE_CONTRATO FROM TIPO_CONTRATO");
            contrato = (ArrayList<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal GET contrato");
            System.out.println(e.getMessage());
        }
        return contrato;
    }

    @Override
    public List listarJefesUser(String id) {
        List<String> jefesuser = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get JEFES");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT APELLIDOS||', '||NOMBRE FROM JEFATURA WHERE CARGO='JEFE DE UNIDAD' AND CODIGO='"+id+"'");
            jefesuser = (ArrayList<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal GET JEFES");
            System.out.println(e.getMessage());
        }
        return jefesuser;
    }
    
    @Override
    public List listarJefes() {
        List<String> jefes = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get JEFES");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT APELLIDOS||', '||NOMBRE FROM JEFATURA");
            jefes = (ArrayList<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal GET JEFES");
            System.out.println(e.getMessage());
        }
        return jefes;
    }

    @Override
    public Usuario getUsuario(String nombre) {
        Usuario usuario = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get contrato");
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Usuario where usuNombre='"+nombre+"'");
            usuario = (Usuario)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal GET contrato");
            System.out.println(e.getMessage());
        }
        return usuario;
    }

    @Override
    public Oficina getOficina(String nombre) {
        System.out.println("gettramite");
        Oficina oficina = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("nombre oficina: " + nombre);
        String sql = "FROM Oficina WHERE nombreOficina='" + nombre + "'";
        try {
            session.beginTransaction();
            oficina = (Oficina) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get oficina");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return oficina;
    }

    @Override
    public Dependencia getDependencia(String nombre) {
        System.out.println("gettramite");
        Dependencia dependencia = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("nombre oficina: " + nombre);
        String sql = "FROM Dependencia WHERE nombre='" + nombre + "'";
        try {
            session.beginTransaction();
            dependencia = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get oficina");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return dependencia;
    }

    @Override
    public Profesion getProfesion(String nombre) {
        System.out.println("gettramite");
        Profesion profesion = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("nombre profesion: " + nombre);
        String sql = "FROM Profesion WHERE profesionNombre='" + nombre + "'";
        try {
            session.beginTransaction();
            profesion = (Profesion) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get profesion");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return profesion;
    }

    @Override
    public TipoContrato getContrato(String nombre) {
        System.out.println("gettramite");
        TipoContrato tc = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("nombre contrato: " + nombre);
        String sql = "FROM TipoContrato WHERE nombreContrato='" + nombre + "'";
        try {
            session.beginTransaction();
            tc = (TipoContrato) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get contrato");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return tc;
    }

    @Override
    public void GuardarJefatura(Jefatura jefatura, Usuario usu) {
        GuardarUsuario(usu);
        GuardarJefe(jefatura);
    }

    @Override
    public void GuardarUsuario(Usuario usu) {
        System.out.println("guardar usuario");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(usu);
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal guardar usuario");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void GuardarJefe(Jefatura jefe) {
        System.out.println("guardar jefe");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(jefe);
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal guardar jefe");
            System.out.println(e.getMessage());
        }
    }

}
