/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.OficioDAO;
import java.util.ArrayList;
import java.util.List;
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
    public List getOficiosCirculares() {
        List oficioscirc = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get oficioscirculares");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select OFI.CORRELA_OFICIC AS CORRELATIVO, \n"
                    + "OFI.ASUNTO,\n"
                    + "DEP1.NOMBRE AS ORIGEN,\n"
                    + "DEP2.NOMBRE AS DESTINO,\n"
                    + "OFI.FECHA\n"
                    + "from OFIC_CIRC OFI, DETALL_OFICCIRC DET, DEPENDENCIA DEP1, DEPENDENCIA DEP2\n"
                    + "WHERE OFI.ID_OFCIRC=DET.ID_OFCIRC\n"
                    + "AND OFI.CODIGO=DEP1.CODIGO\n"
                    + "AND DET.CODIGO=DEP2.CODIGO");
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
        List<String> depes =new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get dependencias");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select NOMBRE,TIPODEPE\n"
                    + "from DEPENDENCIA");
            depes =(List<String>)query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("mal dependencias");
            System.out.println(e.getMessage());
        }
        return depes;
    }

}
