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
            Query query = session.createSQLQuery("SELECT TD.NOMBRE_DOCU,\n"
                    + "DOIF.CORRELATIVO_DOCOFINT||'-'||DOIF.SIGLAS||'-'||TO_CHAR(DOIF.FECHA,'YYYY') AS DOCUMENTO,\n"
                    + "DOIF.ASUNTO,\n"
                    + "TO_CHAR(DOIF.FECHA,'DD/MM/YYYY HH:MI:SS') AS FECHA,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "U.USU_NOMBRE\n"
                    + "FROM DOCUMENTOS_OFIINT DOIF, USUARIO U, DEPENDENCIA D1, DEPENDENCIA D2, TIPOS_DOCUMENTOS TD\n"
                    + "WHERE DOIF.USU=U.USU\n"
                    + "AND DOIF.CODIGO=D1.CODIGO\n"
                    + "AND DOIF.CODIGO1=D2.CODIGO\n"
                    + "AND DOIF.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
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
            Query query = session.createSQLQuery("select TD.NOMBRE_DOCU||' N°-'||DE.CORRELATIVOD||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY') as documento,\n"
                    + "DE.NUMERODOC,\n"
                    + "DE.ASUNTO,\n"
                    + "M1.NOMBRE AS ORIGEN,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "to_char(DE.FECHA,'DD/MM/YYY HH:mm:ss') as fecha,\n"
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
            session.close();
        } catch (Exception e) {
            System.out.println("mal get docus internos (oficios)");
            System.out.println(e.getMessage());
        }
        return proveidos;
    }

}
