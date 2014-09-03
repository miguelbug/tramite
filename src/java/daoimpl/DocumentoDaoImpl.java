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
            Query query = session.createSQLQuery("SELECT TD.TRAM_NUM,TD.TRAM_FECHA,TD.TRAM_OBS,TD.ESTA_DESCRIP\n"
                    + "FROM TRAMITE_DATOS TD");
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
        System.out.println("estoy aca");
        String[] cadena = new String[5];
        cadena[0] = n1;
        cadena[1] = n2;
        cadena[2] = n3;
        cadena[3] = n4;
        cadena[4] = n5;
        for (String cadena1 : cadena) {
            if (cadena1 != null) {
                System.out.println(cadena1 + "-" + cadena1.length());
            }
        }
        String sql = getSQL(cadena);

        List busqueda = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entra");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery(sql);
            busqueda = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        System.out.println("retorna");
        return busqueda;
    }

    @Override
    public String CrearAnd(String objeto, int posi) {
        return " And " + CrearVariable(posi) + " LIKE '%" + objeto + "%' ";
    }

    @Override
    public String CrearVariable(int i) {
        String variable = "";
        if (i == 0) {
            variable = "TD.TRAM_NUM";
        }
        if (i == 1) {
            variable = "TD.TRAM_NUM";
        }
        if (i == 2) {
            variable = "TD.TRAM_NUM";
        }
        if (i == 3) {
            variable = "TD.TRAM_OBS";
        }
        if (i == 4) {
            variable = "TO_CHAR(TD.TRAM_FECHA,'MM')";
        }
        return variable;
    }

    @Override
    public String getSQL(String[] a) {
        int i = 0;
        String comienzo = "SELECT TD.TRAM_NUM,TD.TRAM_FECHA,\n"
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
                + " AND TD.DEPORIG_COD=DO.DEP_ORIG_COD";
        while (i < a.length) {
            if (a[i] != null && a[i].length() != 0) {
                System.out.println(a[i]);
                comienzo += CrearAnd(a[i], i);
            }
            i++;
        }
        return comienzo;
    }

    @Override
    public List getDetalle(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.USU,U.USU_NOMBRE,OFI.NOMBRE_OFICINA,DEP.NOMBRE,\n"
                    + "DOC.DOCU_NOMBRE,\n"
                    + "DOC.DOCU_NUM,\n"
                    + "DOC.DOCU_SIGLAS,\n"
                    + "DOC.DOCU_ANIO\n"
                    + "FROM TRAMITE_DATOS TD, USUARIO U,DEPENDENCIA DEP, TIPO_DOCU DOC,OFICINA OFI\n"
                    + "WHERE TD.TRAM_NUM='" + tramnum + "' \n"
                    + "AND TD.USU=U.USU\n"
                    + "AND TD.CODIGO=DEP.CODIGO\n"
                    + "AND TD.TRAM_NUM=DOC.TRAM_NUM\n"
                    + "AND U.ID_OFICINA=OFI.ID_OFICINA");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró1111");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return codigos;
    }

    @Override
    public List getDeatalle2(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.CODIGO,DEP.NOMBRE\n"
                    + "FROM TRAMITE_DATOS TD, DEPENDENCIA DEP\n"
                    + "WHERE TD.TRAM_NUM='" + tramnum + "' \n"
                    + "AND TD.CODIGO=DEP.CODIGO;");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró1111");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return codigos;
    }

}
