/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.ProveidosInternosDao;
import java.util.ArrayList;
import java.util.List;
import maping.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class ProveidosInternosDaoImpl implements ProveidosInternosDao {

    Session session;

    @Override
    public void Elminiar(String documento) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM DOCUS_EXTINT WHERE CORRELATIVOD='" + documento + "' ";
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó delete DOCUS_EXTINT");
        } catch (Exception e) {
            System.out.println("mal delete DOCUS_EXTINT");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public String getCodigoDepe(String nombre) {
        String nombredepe = "";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT CODIGO FROM DEPENDENCIA WHERE NOMBRE='" + nombre + "' AND TIPODEPE IS NOT NULL";
        try {
            session.beginTransaction();
            nombredepe = String.valueOf(session.createSQLQuery(sql).uniqueResult());
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get nombre DEPE ");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return nombredepe;
    }

    @Override
    public void EditarProveidos(String documento, String asunto, String origen, String destino) {
        String orig = this.getCodigoDepe(origen);
        String dest = this.getCodigoDepe(destino);
        String sql = "UPDATE DOCUS_EXTINT SET ASUNTO='" + asunto + "', CODIGO='" + orig + "', CODIGO1='" + dest + "' WHERE CORRELATIVOD='" + documento + "'";
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
    public List getDependencias() {
        List dependencias = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get docus internos (oficios)");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT NOMBRE FROM DEPENDENCIA WHERE TIPODEPE IS NOT NULL");
            dependencias = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get docus internos (oficios)");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return dependencias;
    }

    @Override
    public List getDocumentosInternos() {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get docus internos (oficios)");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.DOCU_NOMBREINT||' N° '|| DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + " TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:ss') AS FECHA,\n"
                    + " D1.NOMBRE AS ORIGEN,\n"
                    + " DECODE(DI.DOCU_ASUNTO,NULL,'SIN ASUNTO',UPPER(DI.DOCU_ASUNTO)) AS ASUNTO\n"
                    + " FROM DOCUS_INTERNOS DI, DEPENDENCIA D1, TIPOS_DOCUMENTOS TD\n"
                    + " WHERE DI.CODIGO=D1.CODIGO\n"
                    + " AND DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + " AND DI.DOCU_NOMBREINT||' N° '|| DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT NOT IN(SELECT NUMERODOC FROM DOCUS_EXTINT)\n"
                    + " AND DI.CODIGO1='100392'\n"
                    + " ORDER BY DI.FECHAREGISTRO DESC");
            proveidos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get docus internos (oficios)");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return proveidos;
    }

    @Override
    public Usuario getUsuario(String nombre) {
        Usuario usuario = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get usuario");
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Usuario where usuNombre='" + nombre + "'");
            usuario = (Usuario) query.uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get usuario");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally{
            session.close();
        }
        return usuario;
    }

    @Override
    public List getProveidosinternos() {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get docus internos (oficios)");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select TD.NOMBRE_DOCU||' N° '||DE.CORRELATIVOD||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY') as documento,\n"
                    + "DE.NUMERODOC,\n"
                    + "DECODE(DE.ASUNTO,NULL,'SIN ASUNTO',UPPER(DE.ASUNTO)) as asunto,\n"
                    + "M1.NOMBRE AS ORIGEN,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "to_char(DE.FECHA,'DD/MM/YYY HH24:MI:ss') as fecha,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "from DOCUS_EXTINT DE, DEPENDENCIA M1, DEPENDENCIA M2, TIPOS_DOCUMENTOS TD, USUARIO USUA, OFICINA oficina\n"
                    + "WHERE DE.CODIGO=M1.CODIGO\n"
                    + "AND DE.CODIGO1=M2.CODIGO\n"
                    + "AND DE.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DE.USU=USUA.USU\n"
                    + "AND USUA.ID_OFICINA=oficina.ID_OFICINA\n"
                    + "AND DE.EXT_INT IN ('pe','pi')\n"
                    + "ORDER BY DE.ID DESC");
            proveidos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get docus internos (oficios)");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        }
        finally{
            session.close();
        }
        return proveidos;
    }

}
