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
    public List getDocumentosInternos() {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get docus internos (oficios)");
        try {
            session.beginTransaction();
<<<<<<< HEAD
            Query query = session.createSQLQuery("select td.NOMBRE_DOCU||' '||' N°'||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||to_char(ofi.FECHA_OFICIO,'YYYY') AS DOCUMENTO,\n"
                    + "to_char(ofi.FECHA_OFICIO,'DD/MM/YYY HH:mm:ss') as fecha,\n"
                    + "ofi.ASUNTO_OFICIO,\n"
                    + "d1.NOMBRE as origen,\n"
                    + "d2.NOMBRE as destino,\n"
                    + "usua.USU_NOMBRE,\n"
                    + "td.NOMBRE_DOCU\n"
                    + "from oficios ofi, tipos_documentos td, dependencia d1, dependencia d2, USUARIO usua, Oficina oficina\n"
                    + "where ofi.tram_num is null\n"
                    + "and ofi.codigo <> '100392'\n"
                    + "and ofi.ID_DOCUMENTO=td.ID_DOCUMENTO\n"
                    + "and d1.CODIGO=ofi.CODIGO\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and ofi.USU=usua.USU\n"
                    + "and oficina.NOMBRE_OFICINA=d1.NOMBRE\n"
                    + "order by ofi.CORRELATIVO_OFICIO desc");
=======
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N°-'||DOIF.CORRELATIVO_DOCOFINT||'-'||DOIF.SIGLAS||'-'||TO_CHAR(DOIF.FECHA,'YYYY') AS DOCUMENTO,\n"
                    + "TO_CHAR(DOIF.FECHA,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "DOIF.ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE,\n"
                    + "TD.NOMBRE_DOCU\n"
                    + "FROM DOCUMENTOS_OFIINT DOIF, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2, USUARIO USUA\n"
                    + "WHERE DOIF.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DOIF.CODIGO=D1.CODIGO\n"
                    + "AND DOIF.CODIGO1=D2.CODIGO\n"
                    + "AND DOIF.USU=USUA.USU\n"
                    + "ORDER BY DOIF.CORRELATIVO_DOCOFINT DESC");
            proveidos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal get docus internos (oficios)");
            System.out.println(e.getMessage());
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
            session.close();
        } catch (Exception e) {
            System.out.println("mal get usuario");
            System.out.println(e.getMessage());
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
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU||' N°-'||DE.CORRELATIVOD||'-'||OFI.SIGLAS||'-'||TO_CHAR(DE.FECHA,'YYYY') AS DOCUMENTO,\n"
                    + "DE.NUMERODOC,\n"
                    + "TO_CHAR(DE.FECHA_ENVIO,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "DE.ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "FROM DOCUS_EXTINT DE, DEPENDENCIA D1, DEPENDENCIA D2, USUARIO USUA, TIPOS_DOCUMENTOS TD, OFICINA OFI\n"
                    + "WHERE DE.CODIGO=D1.CODIGO\n"
                    + "AND DE.CODIGO1=D2.CODIGO\n"
                    + "AND DE.USU=USUA.USU\n"
                    + "AND DE.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND USUA.ID_OFICINA=OFI.ID_OFICINA\n"
                    + "ORDER BY DE.CORRELATIVOD DESC");
>>>>>>> origin/master
            proveidos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal get docus internos (oficios)");
            System.out.println(e.getMessage());
        }
        return proveidos;
    }

}
