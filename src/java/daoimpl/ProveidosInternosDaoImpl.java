/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.ProveidosInternosDao;
import java.util.ArrayList;
import java.util.List;
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
