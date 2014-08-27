/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import dao.DocumentoDAO;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DocumentoDaoImpl implements DocumentoDAO {

    Session session;

    @Override
    public List getDocumentos() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entró");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery("SELECT TD.TRAM_NUM,TD.TRAM_FECHA,\n"
                    + " TD.TRAM_OBS,\n"
                    + " TD.USUARIO_ID,\n"
                    + " TD.ESTA_DESCRIP,\n"
                    + " TD.DOCU_NOMBRE,\n"
                    + " TD.DOCU_NUM,\n"
                    + " TD.DOCU_SIGLAS,\n"
                    + " TD.DOCU_ANIO,\n"
                    + " DO.DEP_ORIG_NOMBRE\n"
                    + " FROM TRAMITE_DATOS TD, USUARIO USU,DEPENDENCIA_ORIGEN DO \n"
                    + " WHERE TD.USUARIO_ID=USU.USUARIO_ID \n"
                    + " AND TD.DEPORIG_COD=DO.DEP_ORIG_COD");
            docus = query.list();
            System.out.println("despues de query session");
            if (docus == null) {
                System.out.println("qué carajos?");
            }
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        System.out.println("retorna");
        return docus;
    }

    @Override
    public List getCodigos() {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entra");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery("SELECT REPLACE(R1.TRAM_NUM,'-') FROM ( SELECT DISTINCT SUBSTR(TRAM_NUM,7,3)  AS TRAM_NUM FROM vw_ogpl002@TRAMITEDBLINK ) R1 ORDER BY REPLACE(R1.TRAM_NUM,'-')");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        System.out.println("retorna");
        return codigos;
    }

    @Override
    public List getBusqueda(String n1, String n2, String n3, String n4, String n5) {
        String[] cadena = new String[6];
        cadena[0] = "'%" + n1 + "%'";
        cadena[1] = "'%" + n2 + "%'";
        cadena[2] = "'%" + n3 + "%'";
        cadena[3] = "'%" + n4 + "%'";
        cadena[4] = "'%" + n5 + "%'";
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String CrearAnd(String objeto, int posi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String CrearVariable(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
