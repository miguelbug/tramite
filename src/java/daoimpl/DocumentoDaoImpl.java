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
public class DocumentoDaoImpl implements DocumentoDAO{

    Session session;

    @Override
    public List getDocumentos() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entró");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery("SELECT TD.TRAM_NUM,TD.TRAM_FECHA,TD.TRAM_OBS,TD.USUARIO_ID FROM TRAMITE_DATOS TD, USUARIO USU,DEPENDENCIA_ORIGEN DO WHERE TD.USUARIO_ID=USU.USUARIO_ID AND TD.DEPORIG_COD=DO.DEP_ORIG_COD");
            docus = query.list();
            System.out.println("despues de query session");
            if(docus==null){
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

}
