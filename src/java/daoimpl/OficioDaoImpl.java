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
import maping.TiposDocumentos;
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
    public TiposDocumentos getTipoDocu(String nombre) {
        System.out.println("get tipodocu");
        TiposDocumentos tipodocu=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "from TiposDocumentos where nombreDocu='"+nombre+"'";
        try {
            session.beginTransaction();
            tipodocu = (TiposDocumentos) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get correla ");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return tipodocu;
    }



    @Override
    public List getTiposDocus(String f) {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get tipos docus");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE_DOCU FROM TIPOS_DOCUMENTOS WHERE FLAG='"+f+"'");
            depes = (List) query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal tiposdocus");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public String getCorrela(String usu) {
        System.out.println("get correlat");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correlativoOficio) from Oficios where usuario.usu='"+usu+"'";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get correla ");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public List getOficioUnicoExpediente() {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get firma");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select ofi.CORRELATIVO_OFICIO,\n"
                    + "ofi.TRAM_NUM,\n"
                    + "ofi.FECHA_OFICIO,\n"
                    + "ofi.REFERENCIA_OFICIO,\n"
                    + "ofi.ASUNTO_OFICIO,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is not null");
            depes = (List) query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public List getOficioUnicoNoExp() {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get firma");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select ofi.CORRELATIVO_OFICIO,\n"
                    + "ofi.FECHA_OFICIO,\n"
                    + "ofi.ASUNTO_OFICIO,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is null");
            depes = (List) query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
        }
        return depes;
    }

    @Override
    public Long getCodigo(String nombre) {
        Long depe = 12321L;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get codigo");
        try {
            session.beginTransaction();
            Query query = session.createQuery("select codigo\n"
                    + "from Dependencia\n"
                    + "WHERE nombre='" + nombre + "'");
            depe = (Long) query.uniqueResult();
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
        OficCirc ofi = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("From OficCirc where correlaOficic='" + correla + "'");
            ofi = (OficCirc) query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal get oficio circular");
            System.out.println(e.getMessage());
        }
        return ofi;
    }

    @Override
    public Long getIndice(String correlativo) {
        Long depe = 12321L;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get indice");
        try {
            session.beginTransaction();
            Query query = session.createQuery("select idOfcirc\n"
                    + "from OficCirc\n"
                    + "WHERE correlaOficic='" + correlativo + "'");
            depe = (Long) query.uniqueResult();
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
        Dependencia depe = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencia 2");
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Dependencia\n"
                    + "WHERE nombre='" + nombre + "'");
            depe = (Dependencia) query.uniqueResult();
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
        Dependencia depe = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencia");
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Dependencia\n"
                    + "WHERE codigo='" + nombre + "'");
            depe = (Dependencia) query.uniqueResult();
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
        try {
            session.beginTransaction();
            session.save(ofi);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló guardado oficiocircular." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List getFirma() {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get firma");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select J.NOMBRE, J.APELLIDOS\n"
                    + "from JEFATURA J, USUARIO U\n"
                    + "where J.CARGO='JEFATURA'\n"
                    + "and U.ESTADO='activo'\n"
                    + "and J.USU=U.USU");
            depes = (List) query.list();
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
        String depes = "";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get responsable");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select USU_NOMBRE\n"
                    + "from USUARIO\n"
                    + "where USU='" + usuario + "'\n"
                    + "and ESTADO='activo'");
            depes = (String) query.uniqueResult();
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
        String depes = "";
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get responsable");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE_OFICINA\n"
                    + "from OFICINA\n"
                    + "where ID_OFICINA='" + usuario + "'");
            depes = (String) query.uniqueResult();
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
            Query query = session.createSQLQuery("SELECT CORRELA_OFICIC,\n"
                    + "ASUNTO,\n"
                    + "FECHA,\n"
                    + "D1.NOMBRE,\n"
                    + "FIRMA,\n"
                    + "RESPONSABLE\n"
                    + "FROM OFIC_CIRC OFI, DEPENDENCIA D1\n"
                    + "WHERE OFI.CODIGO=D1.CODIGO\n"
                    + "ORDER BY OFI.CORRELA_OFICIC DESC");
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
    public List getOficoCircDetal(String correla) {
        List oficioscirc = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get oficioscirculares");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT D.NOMBRE\n"
                    + "FROM DETALL_OFICCIRC  DO, DEPENDENCIA D, OFIC_CIRC OFI\n"
                    + "WHERE DO.CODIGO=D.CODIGO\n"
                    + "AND DO.ID_OFCIRC=OFI.ID_OFCIRC\n"
                    + "AND OFI.CORRELA_OFICIC='"+correla+"'");
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
        List<String> depes = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencias");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE,TIPODEPE\n"
                    + "from DEPENDENCIA");
            depes = (List<String>) query.list();
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
        try {
            session.beginTransaction();
            session.save(deofi);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló guardado detalleoficio." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public String getOficioDocumento(String tramnum) {
        System.out.println("get oficiodocumento");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select correlativoOficio from Oficios where tramnum='"+tramnum+"'";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get oficiodocumento ");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

}
