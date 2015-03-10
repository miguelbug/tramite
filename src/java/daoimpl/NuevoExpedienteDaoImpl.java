/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.NuevoExpedienteDAO;
import java.util.ArrayList;
import java.util.List;
import maping.Dependencia;
import maping.Indicador;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class NuevoExpedienteDaoImpl implements NuevoExpedienteDAO {

    Session session;

    @Override
    public String getCodigoDepe(String nombre) {
        System.out.println("codigo depe");
        String codigo = null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT CODIGO FROM DEPENDENCIA WHERE NOMBRE='" + nombre + "'";
        try {
            session.beginTransaction();
            codigo = String.valueOf(session.createSQLQuery(sql).uniqueResult());
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal codigo depe");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return codigo;
    }

    @Override
    public List getDependencias(String tipo) {
        List listadepes = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT NOMBRE \n"
                + "FROM DEPENDENCIA\n"
                + "WHERE TIPODEPE = '" + tipo + "' \n"
                + "ORDER BY NOMBRE\n";
        try {
            session.beginTransaction();
            listadepes = (List) session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getDependencias");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return listadepes;
    }

    @Override
    public String getSigla(String codigo) {
        String sigla = "";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT FLAC \n"
                + "FROM DEPENDENCIA\n"
                + "WHERE CODIGO = '" + codigo + "' \n"
                + "ORDER BY NOMBRE\n";
        try {
            session.beginTransaction();
            sigla = (String) session.createSQLQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getSigla");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return sigla;
    }

    @Override
    public List getTipoDocumento() {
        List listadepes = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT INDI_NOMBRE \n"
                + "FROM INDICADOR\n"
                + "ORDER BY INDI_NOMBRE ASC";
        try {
            session.beginTransaction();
            listadepes = (List) session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getTipoDocumento");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return listadepes;
    }

    @Override
    public String getCodigoTipoDocu(String nombre) {
        String codigo = "";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT INDI_COD \n"
                + "FROM INDICADOR\n"
                + "WHERE INDI_NOMBRE = '" + nombre + "' \n";
        try {
            session.beginTransaction();
            codigo = (String) session.createSQLQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getCodigoTipoDocu");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return codigo;
    }

    @Override
    public Dependencia getDepe(String nombre) {
        Dependencia depe=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Dependencia where nombre='"+nombre+"'\n";
        try {
            session.beginTransaction();
            depe = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getDepe");
            System.out.println(e.getMessage());
            e.printStackTrace();
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depe;
    }

    @Override
    public List getDependenciasOficina() {
        List listadepes = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT NOMBRE \n"
                + "FROM DEPENDENCIA\n"
                + "WHERE CODIGO IN ('1001871','1001870','1001872','1001868','1001869','100392')\n"
                + "ORDER BY NOMBRE ASC";
        try {
            session.beginTransaction();
            listadepes = (List) session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getDependenciasOficina");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return listadepes;
    }

    @Override
    public Indicador getIndicador(String n) {
        Indicador indi=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Indicador where indiNombre='"+n+"'\n";
        try {
            session.beginTransaction();
            indi = (Indicador) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getIndicador");
            System.out.println(e.getMessage());
            e.printStackTrace();
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return indi;
    }

    @Override
    public Dependencia getDepePorNombre(String n) {
        Dependencia depe=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Dependencia where nombre='"+n+"'\n";
        try {
            session.beginTransaction();
            depe = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getDepePorNombre");
            e.printStackTrace();
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depe;
    }

    @Override
    public Dependencia getDepePorCodigo(String n) {
        Dependencia depe=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Dependencia where codigo='"+n+"'\n";
        try {
            session.beginTransaction();
            depe = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getDepePorCodigo");
            e.printStackTrace();
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depe;
    }

    @Override
    public void guardarTramiteDatos(TramiteDatos td) {
        System.out.println("GUARDAR TRAMITE DATOS");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(td);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló GUARDAR TRAMITE DATOS." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void guardarTipoDocu(TipoDocu tdc) {
        System.out.println("GUARDAR TIPO DOCU");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(tdc);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló GUARDAR TRAMITE DATOS." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void guardarTramiteMovimiento(TramiteMovimiento tm) {
        System.out.println("GUARDAR TRAMITE MOVIMIENTO");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(tm);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló GUARDAR TRAMITE DATOS." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

}
