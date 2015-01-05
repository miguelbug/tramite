/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.ConstanciaDAO;
import java.util.ArrayList;
import java.util.List;
import maping.Constancias;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class ConstanciaDaoImpl implements ConstanciaDAO {

    Session session;

    @Override
    public String getIndice(String anio) {
        System.out.println("getConstanciaoficio");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correlativo) from Constancias where to_char(fecha_registro,'YYYY')='"+anio+"'";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getConstanciaoficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public List getJefatura() {
        List jefatura = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT NOMBRE, APELLIDOS FROM JEFATURA");
            jefatura = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entró1111");
            System.out.println(e.getMessage());
        }
        return jefatura;

    }

    @Override
    public String getContrato(String nombre) {
        System.out.println("get contrato");
        String contrato = "";
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select tc.NOMBRE_CONTRATO\n"
                    + "from jefatura j, tipo_contrato tc\n"
                    + "where j.nombre||' '||j.apellidos = '" + nombre + "'\n"
                    + "and j.id_contrato=tc.ID_CONTRATO");
            contrato = (String) query.uniqueResult();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no get contrato");
            System.out.println(e.getMessage());
        }
        return contrato;
    }

    @Override
    public void guardarConstancia(Constancias c) {
        System.out.println("entra a guardar constancias");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(c);
            session.getTransaction().commit();
            System.out.println("terminó guardar constancias");
        } catch (Exception e) {
            System.out.println("mal guardar constancias");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List getConstancias() {
        System.out.println("getconstancias");
        List constancias = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT co.CORRELATIVO||'-'||oficina.siglas||'-'||to_char(CO.fecha_emision,'YYYY') as documento,\n"
                    + "to_char(CO.FECHA_EMISION,'DD/MM/YYYY') AS FECHAEMISION,\n"
                    + "CO.DRIGIDO_A,\n"
                    + "CO.TIPO_CONTRATO,\n"
                    + "to_char(CO.DESDE,'DD/MM/YYYY') AS DESDE,\n"
                    + "TO_CHAR(CO.HASTA,'DD/MM/YYYY') AS HASTA,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "FROM CONSTANCIAS CO, OFICINA oficina, USUARIO USUA\n"
                    + "where oficina.ID_OFICINA = USUA.ID_OFICINA\n"
                    + "AND CO.USU= USUA.USU\n"
                    + "order by correlativo desc");
            constancias = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no getconstancias");
            System.out.println(e.getMessage());
        }
        return constancias;
    }

}
