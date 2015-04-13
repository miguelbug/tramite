/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.OficioDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import maping.Dependencia;
import maping.DetallOficcirc;
import maping.DocumentosOfiint;
import maping.DocusInternos;
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
    public List listarTramitesNumeros(String oficina) {
        List tramNum = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get tram numero");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TRAM_NUM||' '||TO_CHAR(TRAM_FECHA,'DD/MM/YYYY HH24:MI:ss')\n"
                    + "FROM TRAMITE_MOVIMIENTO\n"
                    + "WHERE CODIGO='" + oficina + "'"
                    + "ORDER BY TRAM_FECHA DESC");
            tramNum = (List) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal tram numero");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return tramNum;
    }

    @Override
    public void EliminarDocumentosInternosOficinas(String id) {
        String sql = "DELETE FROM DOCUS_INTERNOS WHERE IDTIP='" + id + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó delete docus internos");
        } catch (Exception e) {
            System.out.println("mal delete docus internos");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void ActualizarDocusInternosOficinas(String id, String asunto) {
        String sql = "UPDATE DOCUS_INTERNOS SET DOCU_ASUNTO='" + asunto + "' WHERE IDTIP='" + id + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar DOCUS INTERNOS");
        } catch (Exception e) {
            System.out.println("mal actualizar DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void ActualizarOficioCircularUser(String correla, String asunto) {
        String sql = "UPDATE OFIC_CIRC SET ASUNTO='" + asunto + "' WHERE CORRELA_OFICIC='" + correla + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar oficio CIRCULAR");
        } catch (Exception e) {
            System.out.println("mal actualizar oficio CIRCULAR");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void ActualizarOficioCircular(String correla, String asunto, String origen) {
        String sql = "UPDATE OFIC_CIRC SET ASUNTO='" + asunto + "', CODIGO='" + origen + "' WHERE CORRELA_OFICIC='" + correla + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar oficio CIRCULAR");
        } catch (Exception e) {
            System.out.println("mal actualizar oficio CIRCULAR");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public List getAllDependencias() {
        List lista = new ArrayList();
        String sql = "SELECT NOMBRE FROM DEPENDENCIA\n"
                + "ORDER BY TIPODEPE ASC";
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            lista = (List) query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("mal get all depe");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return lista;
    }

    @Override
    public void ActualizarOficio2(String correla, String asunto, String destino, String asignado) {
        String codigo = String.valueOf(this.getDependencias2(destino).getCodigo());
        String sql = "UPDATE OFICIOS SET ASUNTO_OFICIO='" + asunto + "', CODIGO1='" + codigo + "', "
                + "RESPONSABLE='" + asignado + "' WHERE CORRELATIVO_OFICIO='" + correla + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar oficio");
        } catch (Exception e) {
            System.out.println("mal actualizar oficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void ActualizarOficio(String correla, String asunto, String destino, String asignado, String tramNum, String fecha) {
        String codigo = String.valueOf(this.getDependencias2(destino).getCodigo());
        SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = null;
        try {
            d = formato2.parse(fecha);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        SimpleDateFormat formato3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nuevafecha = formato3.format(d);
        String sql = "UPDATE OFICIOS SET ASUNTO_OFICIO='" + asunto + "', CODIGO1='" + codigo + "', "
                + "TRAM_NUM='" + tramNum + "', TRAM_FECHA=TO_DATE('" + nuevafecha + "','YYYY-MM-DD HH24:MI:SS'), RESPONSABLE='" + asignado + "' WHERE CORRELATIVO_OFICIO='" + correla + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar oficio");
        } catch (Exception e) {
            System.out.println("mal actualizar oficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void DeleteOficio(String correlativo) {
        String sql = "DELETE FROM OFICIOS WHERE CORRELATIVO_OFICIO='" + correlativo + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó delete oficio");
        } catch (Exception e) {
            System.out.println("mal delete oficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void GuardarDocumentoOfiInt(DocusInternos di) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(di);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("falló guardado documentosofiint." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public String getNombreOfi(String usu) {
        String nombreofi = "";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT NOMBRE_OFICINA FROM OFICINA WHERE ID_OFICINA='" + usu + "'";
        try {
            session.beginTransaction();
            nombreofi = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get nombre ofi ");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return nombreofi;
    }

    @Override
    public List getOficOgplUser(String user) {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get firma");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select R.documento,\n"
                    + "       R.tramite,\n"
                    + "       TO_CHAR(R.fecha,'DD/MM/YYYY HH24:MI:ss') AS FECHA,\n"
                    + "       R.referencia,\n"
                    + "       DECODE(R.asunto,NULL,'SIN ASUNTO',UPPER(R.asunto)),\n"
                    + "       R.origen,\n"
                    + "       R.destino,\n"
                    + "       R.responsable\n"
                    + "       From (select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "decode(ofi.TRAM_NUM,NULL,'SIN NUMERO DE TRAMITE',ofi.TRAM_NUM) as tramite,\n"
                    + "ofi.FECHA_OFICIO as fecha,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO) as referencia,\n"
                    + "ofi.ASUNTO_OFICIO as asunto,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino,\n"
                    + "ofi.responsable\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is not null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + "union\n"
                    + "select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "decode(ofi.TRAM_NUM,NULL,'SIN NUMERO DE TRAMITE',ofi.TRAM_NUM) as tramite,\n"
                    + "ofi.FECHA_OFICIO as fecha,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO) as referencia,\n"
                    + "ofi.ASUNTO_OFICIO,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino,\n"
                    + "ofi.responsable\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is null\n"
                    + "and d1.nombre=oficina.nombre_oficina) R\n"
                    + "where R.responsable='" + user + "'\n"
                    + "OR R.destino='" + user + "'"
                    + "order by R.fecha desc");
            depes = (List) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depes;
    }

    @Override
    public TiposDocumentos getTipoDocu(String nombre) {
        System.out.println("get tipodocu");
        TiposDocumentos tipodocu = null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "from TiposDocumentos where nombreDocu='" + nombre + "'";
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
            Query query = session.createSQLQuery("select NOMBRE_DOCU FROM TIPOS_DOCUMENTOS WHERE FLAG2='" + f + "'");
            depes = (List) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal tiposdocus");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depes;
    }

    @Override
    public List gettipos(String g) {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get tipos docus");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE_DOCU FROM TIPOS_DOCUMENTOS WHERE FLAG='" + g + "'");
            depes = (List) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal tiposdocus");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depes;
    }

    @Override
    public List obtenerTiposDocusOfCirc(String f) {
        List depes = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get tipos docus");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE_DOCU FROM TIPOS_DOCUMENTOS WHERE FLAG='" + f + "'");
            depes = (List) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal tiposdocus");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depes;
    }

    @Override
    public String getCorrela(String usu, String tipodocu) {
        System.out.println("get correlat");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        //String sql = "select max(correlativoOficio) from Oficios where usuario.usu='" + usu + "' and tiposDocumentos.nombreDocu='" + tipodocu + "'";
        String sql = "SELECT MAX(OFI.CORRELATIVO_OFICIO) FROM OFICIOS OFI, TIPOS_DOCUMENTOS td, USUARIO USUA\n"
                + "WHERE td.ID_DOCUMENTO=OFI.ID_DOCUMENTO\n"
                + "AND TD.NOMBRE_DOCU='" + tipodocu + "'\n"
                + "AND USUA.USU=OFI.USU\n"
                + "AND OFI.USU='" + usu + "'";
        try {
            session.beginTransaction();
            index = (String) session.createSQLQuery(sql).uniqueResult();
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
            Query query = session.createSQLQuery("select R.documento,\n"
                    + "       R.tramite,\n"
                    + "       TO_CHAR(R.fecha,'DD/MM/YYYY HH24:MI:ss') AS FECHA,\n"
                    + "       R.referencia,\n"
                    + "       DECODE(R.asunto,NULL,'SIN ASUNTO',UPPER(R.asunto)),\n"
                    + "       R.origen,\n"
                    + "       R.destino,\n"
                    + "       UPPER(R.responsable)\n"
                    + "       From (select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "decode(ofi.TRAM_NUM,NULL,'SIN NUMERO DE TRAMITE',ofi.TRAM_NUM) as tramite,\n"
                    + "ofi.FECHA_OFICIO as fecha,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO) as referencia,\n"
                    + "DECODE(ofi.ASUNTO_OFICIO,NULL,'SIN ASUNTO',ofi.ASUNTO_OFICIO) as asunto,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino,\n"
                    + "ofi.responsable\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is not null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + "union\n"
                    + "select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "decode(ofi.TRAM_NUM,NULL,'SIN NUMERO DE TRAMITE',ofi.TRAM_NUM) as tramite,\n"
                    + "ofi.FECHA_OFICIO as fecha,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO) as referencia,\n"
                    + "DECODE(ofi.ASUNTO_OFICIO,NULL,'SIN ASUNTO',ofi.ASUNTO_OFICIO) as asunto,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino,\n"
                    + "ofi.responsable\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + ") R\n"
                    + "order by R.fecha desc");
            depes = (List) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal codigo");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depe;
    }

    @Override
    public String getCorrelativo(String anio) {
        System.out.println("get correlativo");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correla_Oficic) from Ofic_Circ where TO_CHAR(FECHA,'YYYY')='" + anio + "'";
        try {
            session.beginTransaction();
            index = (String) session.createSQLQuery(sql).uniqueResult();
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
    public OficCirc getOficioCircular(String correla, String anio) {
        OficCirc ofi = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("From OficCirc where correlaOficic='" + correla + "' and to_char(fecha,'YYYY')='" + anio + "'");
            ofi = (OficCirc) query.uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get oficio circular");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return ofi;
    }

    @Override
    public Long getIndice(String correlativo, String anio) {
        Long depe = 12321L;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get indice");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select id_Ofcirc\n"
                    + "from Ofic_Circ\n"
                    + "WHERE correla_Oficic='" + correlativo + "'\n"
                    + "AND to_char(fecha,'YYYY')='" + anio + "'");
            depe = (Long) query.uniqueResult();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("mal indice");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
            Query query = session.createQuery("From Dependencia\n"
                    + "WHERE nombre='" + nombre + "'");
            depe = (Dependencia) query.uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal dpee 2");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal dpee");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal firma");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal getresponsable");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal getresponsable");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N° '||OFI.CORRELA_OFICIC||'-'||'OGPL'||'-'||to_char(OFI.fecha,'YYYY') as documento,\n"
                    + "DECODE(UPPER(OFI.ASUNTO),NULL,'SIN ASUNTO',UPPER(OFI.ASUNTO)) AS ASUNTO,\n"
                    + "to_char(OFI.FECHA,'DD/MM/YYYY HH24:MI:ss') as fecha,\n"
                    + "D1.NOMBRE,\n"
                    + "OFI.FIRMA,\n"
                    + "OFI.RESPONSABLE\n"
                    + "FROM OFIC_CIRC OFI, DEPENDENCIA D1, TIPOS_DOCUMENTOS TD\n"
                    + "WHERE OFI.CODIGO=D1.CODIGO\n"
                    + "AND OFI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "ORDER BY OFI.FECHA DESC");
            oficioscirc = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal oficioscirculares");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
                    + "AND OFI.CORRELA_OFICIC='" + correla + "'");
            oficioscirc = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal oficioscirculares");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        } catch (Exception e) {
            System.out.println("mal dependencias");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return depes;
    }

    @Override
    public List getDependencias(String tipo) {
        List<String> depes = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencias 3");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE\n"
                    + "from DEPENDENCIA\n"
                    + "where TIPODEPE='" + tipo + "'");
            depes = (List<String>) query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal dependencias");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
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
        String sql = "SELECT DECODE(CORRELATIVO_OFICIO,NULL,'SIN OFICIO',CORRELATIVO_OFICIO) AS CORRELA"
                + " from OFICIOS where TRAM_NUM='" + tramnum + "'";
        try {
            session.beginTransaction();
            index = (String) session.createSQLQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get oficiodocumento");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("INDEX: " + index);
        if (index == null) {
            index = "SIN OFICIO";
        }
        return index;
    }

}
