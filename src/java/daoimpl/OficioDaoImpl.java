/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.OficioDAO;
import java.util.ArrayList;
import java.util.List;
import maping.Dependencia;
import maping.DetallOficcirc;
import maping.OficCirc;
import maping.Oficina;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class OficioDaoImpl implements OficioDAO {

    Session session;

    @Override
    public Long getCodigo(String nombre) {
        Long depe=12321L;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get codigo");
        try {
            session.beginTransaction();
            Query query = session.createQuery("select codigo\n"
                    + "from Dependencia\n"
                    + "WHERE nombre='"+nombre+"'");
            depe =(Long)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal codigo");
            System.out.println(e.getMessage());
        }
        return depe;
    }

    @Override
    public String getCorrelativo() {
        System.out.println("get correlativo");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correlaOficic) from OficCirc";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get corre ");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public OficCirc getOficioCircular(String correla) {
        OficCirc ofi=null;
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.createQuery("From OficCirc where correlaOficic='"+correla+"'");
            ofi=(OficCirc)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        }catch(Exception e){
            System.out.println("mal get oficio circular");
            System.out.println(e.getMessage());
        }
        return ofi;
    }

    @Override
    public Long getIndice(String correlativo) {
        Long depe=12321L;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get indice");
        try {
            session.beginTransaction();
            Query query = session.createQuery("select idOfcirc\n"
                    + "from OficCirc\n"
                    + "WHERE correlaOficic='"+correlativo+"'");
            depe =(Long)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal indice");
            System.out.println(e.getMessage());
        }
        return depe;
    }

    @Override
    public Dependencia getDependencias2(String nombre) {
        Dependencia depe=null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencia 2");
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Dependencia\n"
                    + "WHERE nombre='"+nombre+"'");
            depe =(Dependencia)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal dpee 2");
            System.out.println(e.getMessage());
        }
        return depe;
    }

    @Override
    public Dependencia getDependencia(String nombre) {
        Dependencia depe=null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencia");
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Dependencia\n"
                    + "WHERE codigo='"+nombre+"'");
            depe =(Dependencia)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal dpee");
            System.out.println(e.getMessage());
        }
        return depe;
    }

    @Override
    public void guardarOficioCircular(OficCirc ofi) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(ofi);
            session.getTransaction().commit();
        }catch (Exception ex) {
            System.err.println("falló guardado oficiocircular." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List getFirma() {
        List depes =new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get firma");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select J.NOMBRE, J.APELLIDOS\n"
                    + "from JEFATURA J, USUARIO U\n"
                    + "where J.CARGO='JEFATURA'\n"
                    + "and U.ESTADO='activo'\n"
                    + "and J.USU=U.USU");
            depes =(List)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public String getResponsable(String usuario) {
        String depes ="";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get responsable");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select USU_NOMBRE\n"
                    + "from USUARIO\n"
                    + "where USU='"+usuario+"'\n"
                    + "and ESTADO='activo'");
            depes =(String)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal getresponsable");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public String getAreaResponsable(String usuario) {
        String depes ="";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get responsable");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE_OFICINA\n"
                    + "from OFICINA\n"
                    + "where ID_OFICINA='"+usuario+"'");
            depes =(String)query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal getresponsable");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public List getOficiosCirculares() {
        List oficioscirc = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get oficioscirculares");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select OFI.CORRELA_OFICIC AS CORRELATIVO, \n"
                    + "OFI.ASUNTO,\n"
                    + "DEP1.NOMBRE AS ORIGEN,\n"
                    + "DEP2.NOMBRE AS DESTINO,\n"
                    + "OFI.FECHA,\n"
                    + "OFI.FIRMA,\n"
                    + "OFI.RESPONSABLE\n"
                    + "from OFIC_CIRC OFI, DETALL_OFICCIRC DET, DEPENDENCIA DEP1, DEPENDENCIA DEP2\n"
                    + "WHERE OFI.ID_OFCIRC=DET.ID_OFCIRC\n"
                    + "AND OFI.CODIGO=DEP1.CODIGO\n"
                    + "AND DET.CODIGO=DEP2.CODIGO\n"
                    + "order by OFI.CORRELA_OFICIC DESC");
            oficioscirc = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal oficioscirculares");
            System.out.println(e.getMessage());
        }
        return oficioscirc;
    }

    @Override
    public List<String> getDependencias() {
        List<String> depes =new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencias");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE,TIPODEPE\n"
                    + "from DEPENDENCIA");
            depes =(List<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal dependencias");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public void guardarDetalleOfCirc(DetallOficcirc deofi) {
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(deofi);
            session.getTransaction().commit();
        }catch (Exception ex) {
            System.err.println("falló guardado detalleoficio." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

}
