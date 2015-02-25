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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import maping.Anios;
import maping.Oficios;
import maping.Usuario;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DocumentoDaoImpl implements DocumentoDAO {

    Session session;

    @Override
    public List obtener_oficios() {
        List oficios = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT UPPER(R.DOCUMENTO)||'  '||R.FECHA       \n"
                    + "       FROM (select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "ofi.FECHA_OFICIO AS FECHA\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is not null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + "union\n"
                    + "select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "ofi.FECHA_OFICIO AS FECHA\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is null\n"
                    + "and d1.nombre=oficina.nombre_oficina)R\n"
                    + "ORDER BY R.FECHA DESC");
            oficios = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return oficios;
    }

    @Override
    public List mostrar_DocumentosOfInt() {
        List docinternos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get DOCUS INTERNOS");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT R.I_D,\n"
                    + "                           R.DOCUMENTO,\n"
                    + "                           R.TRAM_NUM,\n"
                    + "                           TO_CHAR(R.FECHA,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "                           DECODE(R.ASUNTO,NULL,'SIN ASUNTO',UPPER(R.ASUNTO)),\n"
                    + "                           R.ORIGEN,\n"
                    + "                           R.DESTINO,\n"
                    + "                           R.ASIGNADO,\n"
                    + "                           R.DOCUMENTO_PRINCIPAL,\n"
                    + "                           R.ORIGEN_PRINCIPAL\n"
                    + "                           FROM (\n"
                    + "                    SELECT DI.IDTIP AS I_D,\n"
                    + "                    DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "                    DECODE(DI.TRAM_NUM,NULL,'SIN EXPEDIENTE',DI.TRAM_NUM) AS TRAM_NUM,\n"
                    + "                    DI.FECHAREGISTRO AS FECHA,\n"
                    + "                    DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "                    D1.NOMBRE AS ORIGEN,\n"
                    + "                    D2.NOMBRE AS DESTINO,\n"
                    + "                    USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "                    DI.ID_DOCUMENTO AS IDDOCUMENTO,\n"
                    + "                    'SIN DOCUMENTO' AS DOCUMENTO_PRINCIPAL,\n"
                    + "                    'SIN ORIGEN' AS ORIGEN_PRINCIPAL\n"
                    + "                    \n"
                    + "                    FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "                    WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "                    AND DI.CODIGO=D1.CODIGO\n"
                    + "                    AND DI.CODIGO1=D2.CODIGO\n"
                    + "                    AND DI.USU=USUA.USU\n"
                    + "                    AND DI.TRAM_NUM IS NULL\n"
                    + "                    ) R\n"
                    + "                    WHERE R.DESTINO='OFICINA GENERAL DE PLANIFICACION'\n"
                    + "                    GROUP BY R.ORIGEN,R.I_D,R.DOCUMENTO,R.TRAM_NUM,\n"
                    + "                           R.FECHA,\n"
                    + "                           R.ASUNTO,R.DESTINO,\n"
                    + "                           R.ASIGNADO,\n"
                    + "                           R.DOCUMENTO_PRINCIPAL,\n"
                    + "                           R.ORIGEN_PRINCIPAL\n"
                    + "                    ORDER BY R.FECHA DESC");
            docinternos = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return docinternos;
    }

    @Override
    public String getTram_Fecha(String tramnum, String movi) {
        String tramfecha = "";
        String sql = "SELECT TO_CHAR(TRAM_FECHA,'dd/MM/yyyy HH:mm:ss') FROM TRAMITE_MOVIMIENTO WHERE TRAM_NUM= '" + tramnum + "' AND MOVI_NUM='" + movi + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            tramfecha = (String) session.createSQLQuery(sql).uniqueResult();
            session.getTransaction().commit();
            System.out.println("terminó tramfecha");
        } catch (Exception e) {
            System.out.println("mal tramfecha");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return tramfecha;
    }

    @Override
    public String getFlag(String dependencia) {
        System.out.println("get tipodepe");
        String tipodepe = "";
        String sql = "SELECT TIPODEPE FROM DEPENDENCIA WHERE NOMBRE= '" + dependencia + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            tipodepe = (String) session.createSQLQuery(sql).uniqueResult();
            session.getTransaction().commit();
            System.out.println("terminó gettipodepe");
        } catch (Exception e) {
            System.out.println("mal gettipodepe");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return tipodepe;
    }

    @Override
    public void guardarNuevoAnio(String anio) {
        System.out.println("guardar anio");
        Anios anios = new Anios();
        anios.setAnio(anio);
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(anios);
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("mal guardar anio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void guardarOficio2(Oficios ofi) {
        System.out.println("guardar oficio");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(ofi);
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("mal guardar oficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void ActualizarMov(String tramnum, String mov) {
        System.out.println("actualizar tramite movimiento");
        String sql = "Update TRAMITE_MOVIMIENTO SET ESTAD_CONFRIRM='FINALIZADO' , ESTA_NOMBRE='FINALIZADO' WHERE TRAM_NUM='" + tramnum + "' AND MOVI_NUM='" + Integer.parseInt(mov) + "'";
        session = HibernateUtil.getSessionFactory().openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("terminó actualizar tramite");
        } catch (Exception e) {
            System.out.println("mal actualizar tramite");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("se ha actualizado¡¡¡¡¡¡¡: " + i);
    }

    @Override
    public void guardarOficio(Oficios ofi, String tramnum, String movimiento) {
        System.out.println("guardar oficio");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(ofi);
            session.beginTransaction().commit();

            System.out.println("se guardó oficio");
        } catch (Exception e) {
            System.out.println("?????????????mal guardar oficio¡¡¡¡");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println("entra a actualizar movimiento");
        ActualizarMov(tramnum, movimiento);
    }

    @Override
    public List getProveidos(String tramnum) {
        List proveidos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("get proveidos");
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select di.DOCU_CORRELAINT,\n"
                    + "di.DOCU_NOMBREINT,\n"
                    + "di.DOCU_SIGLASINT,\n"
                    + "di.DOCU_ANIOINT,\n"
                    + "usua.usu_nombre,\n"
                    + "DECODE(to_char(di.FECHAREGISTRO, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(di.FECHAREGISTRO, 'dd/MM/yyyy HH:mm:ss')) AS FECHAREGIS\n"
                    + "from docus_internos di, USUARIO usua\n"
                    + "where di.USU=usua.USU\n"
                    + "and di.tram_num='" + tramnum + "'");
            proveidos = query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("mal getproveidos");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return proveidos;
    }

    @Override
    public List getDependencias(String tipo) {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select nombre from Dependencia where tipodepe='" + tipo + "' order by nombre");
            docus = (List) query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró depenencias");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return docus;
    }

    @Override
    public List getIndicadores() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select indi_nombre from Indicador order by indi_nombre");
            docus = (List) query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró indicadores");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return docus;
    }

    @Override
    public List getDocumentos_Confirm() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entró a getdocus confim");
            session.beginTransaction();
            System.out.println("despues de begin confirm");
            Query query = session.createSQLQuery("SELECT R.TRAM_NUM,\n"
                    + "R.MOVI_NUM,\n"
                    + "R.MOVI_ORIGEN,\n"
                    + "R.MOVI_DESTINO,\n"
                    + "R.FECHAENVIO,\n"
                    + "R.FECHAING,\n"
                    + "R.INDI_NOMBRE,\n"
                    + "R.OBSERVACION,\n"
                    + "R.DOCUNOMBRE,\n"
                    + "R.ESTA_NOMBRE,\n"
                    + "R.DOCUPRIC\n"
                    + "  FROM (select vista2.TRAM_NUM,\n"
                    + "       vista2.MOVI_NUM,\n"
                    + "       vista2.MOVI_ORIGEN,\n"
                    + "       vista2.MOVI_DESTINO,\n"
                    + "       vista2.DEST_COD,\n"
                    + "       vista2.MOVI_FEC_ENV,\n"
                    + "       DECODE(to_char(vista2.MOVI_FEC_ENV, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(vista2.MOVI_FEC_ENV, 'dd/MM/yyyy HH:mm:ss')) AS FECHAENVIO,\n"
                    + "       DECODE(to_char(vista2.MOVI_FEC_ING, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(vista2.MOVI_FEC_ING, 'dd/MM/yyyy HH:mm:ss')) AS FECHAING,\n"
                    + "       vista2.INDI_NOMBRE,\n"
                    + "       DECODE(vista2.MOVI_OBS,NULL,' ',vista2.MOVI_OBS) AS OBSERVACION,\n"
                    + "       DECODE(vista1.docu_nombre,NULL,'SIN DOC.',vista1.docu_nombre|| 'N° '||vista1.docu_num||'-'||vista1.docu_siglas||'-'||vista1.docu_anio) as docunombre,\n"
                    + "       DECODE(vista1.docu_pric,null,'3',vista1.docu_pric) as docupric,\n"
                    + "       vista2.ESTA_NOMBRE\n"
                    + "       from vw_ogpl002@TRAMITEDBLINK vista2 left join vw_ogpl001@TRAMITEDBLINK vista1\n"
                    + "       on vista2.tram_num=vista1.tram_num\n"
                    + "       ORDER BY vista2.MOVI_FEC_ENV DESC\n"
                    + "       )R\n"
                    + "WHERE R.FECHAING not in (' ')\n"
                    + "AND R.MOVI_ORIGEN = 'OFICINA GENERAL DE PLANIFICACION'\n"
                    + "and R.TRAM_NUM||'-'||to_char(R.MOVI_FEC_ENV,'dd/MM/yyyy')  not in (select tram_num||'-'||to_char(tram_fecha, 'dd/MM/yyyy') from tramite_datos)\n"
                    + "AND R.DEST_COD IN ('1001868','1001869','1001870','1001871','1001872')\n"
                    + "GROUP BY R.TRAM_NUM,R.MOVI_NUM,R.MOVI_ORIGEN,R.MOVI_DESTINO,R.FECHAENVIO,R.FECHAING,R.INDI_NOMBRE,R.OBSERVACION,R.DOCUNOMBRE,R.ESTA_NOMBRE,R.DOCUPRIC");
            docus = query.list();
            System.out.println("despues de query session");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docus;
    }

    @Override
    public List getDocumentos() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("entró");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery("SELECT R.TRAM_NUM,\n"
                    + "R.MOVI_NUM,\n"
                    + "R.MOVI_ORIGEN,\n"
                    + "R.MOVI_DESTINO,\n"
                    + "R.FECHAENVIO,\n"
                    + "R.FECHAING,\n"
                    + "R.INDI_NOMBRE,\n"
                    + "R.OBSERVACION,\n"
                    + "R.DOCUNOMBRE,\n"
                    + "R.ESTA_NOMBRE,\n"
                    + "R.DOCUPRIC\n"
                    + "  FROM (select vista2.TRAM_NUM,\n"
                    + "       vista2.MOVI_NUM,\n"
                    + "       vista2.MOVI_ORIGEN,\n"
                    + "       vista2.MOVI_DESTINO,\n"
                    + "       vista2.DEST_COD,\n"
                    + "       vista2.MOVI_FEC_ENV,\n"
                    + "       DECODE(to_char(vista2.MOVI_FEC_ENV, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(vista2.MOVI_FEC_ENV, 'dd/MM/yyyy HH:mm:ss')) AS FECHAENVIO,\n"
                    + "       DECODE(to_char(vista2.MOVI_FEC_ING, 'dd/MM/yyyy HH:mm:ss'),NULL,' ',to_char(vista2.MOVI_FEC_ING, 'dd/MM/yyyy HH:mm:ss')) AS FECHAING,\n"
                    + "       vista2.INDI_NOMBRE,\n"
                    + "       DECODE(vista2.MOVI_OBS,NULL,' ',vista2.MOVI_OBS) AS OBSERVACION,\n"
                    + "       DECODE(vista1.docu_nombre,NULL,'SIN DOC.',vista1.docu_nombre|| 'N° '||vista1.docu_num||'-'||vista1.docu_siglas||'-'||vista1.docu_anio) as docunombre,\n"
                    + "       DECODE(vista1.docu_pric,null,'3',vista1.docu_pric) as docupric,\n"
                    + "       vista2.ESTA_NOMBRE\n"
                    + "       from vw_ogpl002@TRAMITEDBLINK vista2 left join vw_ogpl001@TRAMITEDBLINK vista1\n"
                    + "       on vista2.tram_num=vista1.tram_num\n"
                    + "       ORDER BY vista2.MOVI_FEC_ENV DESC\n"
                    + "       )R\n"
                    + "WHERE R.FECHAING in (' ')\n"
                    + "AND R.MOVI_ORIGEN = 'OFICINA GENERAL DE PLANIFICACION'\n"
                    + "and R.TRAM_NUM||'-'||to_char(R.MOVI_FEC_ENV,'dd/MM/yyyy')  not in (select tram_num||'-'||to_char(tram_fecha, 'dd/MM/yyyy') from tramite_datos)\n"
                    + "AND R.DEST_COD IN ('1001868','1001869','1001870','1001871','1001872')\n");
            docus = query.list();
            System.out.println("despues de query session");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docus;
    }

    @Override
    public List documentosCorregir() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("getdocusinternos");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery("select tm.tram_num,"
                    + "tm.movi_num,"
                    + "DECODE(to_char(tm.FECHA_ENVIO,'dd/MM/yyyy HH24:MI:SS'),NULL,' ',to_char(tm.FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:SS')) AS FECHAENVIO,\n"
                    + "D1.NOMBRE AS ORIGEN,"
                    + "DECODE(to_char(tm.FECHA_INGR,'dd/MM/yyyy HH24:MI:SS'),NULL,' ',to_char(tm.FECHA_INGR, 'dd/MM/yyyy HH24:MI:SS')) AS FECHAINGRESO,\n"
                    + "D2.NOMBRE AS DESTINO,"
                    + "DECODE(tm.MOVI_OBS,NULL,' ',tm.MOVI_OBS) AS OBSV,"
                    + "tm.ESTA_NOMBRE,"
                    + "I.INDI_NOMBRE,"
                    + "tm.ESTAD_CONFRIRM,\n"
                    + "tm.tram_fecha\n"
                    + "FROM TRAMITE_MOVIMIENTO tm, INDICADOR I, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE tm.INDI_COD=I.INDI_COD\n"
                    + "and tm.CODIGO=D1.CODIGO\n"
                    + "and tm.CODIGO1=D2.CODIGO\n"
                    + "and D1.NOMBRE='OFICINA GENERAL DE PLANIFICACION'"
                    + "order by D2.NOMBRE");
            docus = query.list();
            System.out.println("despues de query de getdocusinternos");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a getdocusinternos");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docus;
    }

    @Override
    public List getDocusInternos() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("getdocusinternos");
            session.beginTransaction();
            System.out.println("despues de begin");
            Query query = session.createSQLQuery("select tm.tram_num,\n"
                    + "tm.movi_num,\n"
                    + "DECODE(to_char(tm.FECHA_ENVIO,'dd/MM/yyyy HH24:MI:SS'),NULL,' ',to_char(tm.FECHA_ENVIO, 'dd/MM/yyyy HH24:MI:SS')) AS FECHAENVIO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "DECODE(to_char(tm.FECHA_INGR,'dd/MM/yyyy HH24:MI:SS'),NULL,' ',to_char(tm.FECHA_INGR, 'dd/MM/yyyy HH24:MI:SS')) AS FECHAINGRESO,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "DECODE(tm.MOVI_OBS,NULL,' ',UPPER(tm.MOVI_OBS)) AS OBSV,\n"
                    + "tm.ESTA_NOMBRE,\n"
                    + "I.INDI_NOMBRE,\n"
                    + "tm.ESTAD_CONFRIRM\n"
                    + "FROM TRAMITE_MOVIMIENTO tm, INDICADOR I, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE tm.INDI_COD=I.INDI_COD\n"
                    + "and tm.CODIGO=D1.CODIGO\n"
                    + "and tm.CODIGO1=D2.CODIGO\n"
                    + "and D2.NOMBRE='OFICINA GENERAL DE PLANIFICACION'\n"
                    + "order by tm.FECHA_ENVIO desc");
            docus = query.list();
            System.out.println("despues de query de getdocusinternos");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a getdocusinternos");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docus;
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

        } catch (Exception e) {
            System.out.println("no entró");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return busqueda;
    }

    @Override
    public String CrearAnd(String objeto, int posi) {
        return " And " + CrearVariable(posi) + " LIKE UPPER('%" + objeto + "%') ";
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
        String comienzo = "SELECT TD.TRAM_NUM,\n"
                + "TM.MOVI_NUM,\n"
                + "DECODE(to_char(TD.TRAM_FECHA, 'dd/MM/yyyy HH:MI:SS'),NULL,' ',to_char(TD.TRAM_FECHA, 'dd/MM/yyyy HH:MI:SS')) AS FECHA,DECODE(TD.TRAM_OBS,NULL,' ',TD.TRAM_OBS) TRAM_OBS,\n"
                + "TD.ESTA_DESCRIP,\n"
                + "DEP.NOMBRE\n"
                + "FROM TRAMITE_DATOS TD, TRAMITE_MOVIMIENTO TM, DEPENDENCIA DEP \n"
                + "WHERE TD.CODIGO=DEP.CODIGO\n"
                + "AND TD.TRAM_NUM=TM.TRAM_NUM\n"
                + "AND TD.TRAM_FECHA=TM.TRAM_FECHA\n";
        while (i < a.length) {
            if (a[i] != null && a[i].length() != 0) {
                System.out.println(a[i]);
                comienzo += CrearAnd(a[i], i);
            }
            i++;
        }
        comienzo += "\nORDER BY TD.TRAM_FECHA DESC";
        return comienzo;
    }

    @Override
    public List getDetalle(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select USU,\n"
                    + "DEPE_ORIGEN,\n"
                    + "DOCU_NOMBRE||'-'||DOCU_NUM||'-'||DOCU_SIGLAS||'-'||DOCU_ANIO as documento\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'"
            );
            codigos = query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró1111");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return codigos;
    }

    @Override
    public List getDetalleOGPL(String tramnum) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select TRAM_FECHA,\n"
                    + "DEPE_ORIGEN,\n"
                    + "ESTA_DESCRIP,\n"
                    + "DOCU_NOMBRE||'-'||DOCU_NUM||'-'||DOCU_SIGLAS||'-'||DOCU_ANIO as documento\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'"
            );
            codigos = query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró1111");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return codigos;
    }

    @Override
    public List getDetalleNoOGPL(String tramnum, String movi) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.USU,\n"
                    + "OFI.NOMBRE_OFICINA,\n"
                    + "ESTA_DESCRIP,\n"
                    + "DOC.DOCU_NOMBRE||'-'||DOC.DOCU_NUM||'-'||DOC.DOCU_SIGLAS||'-'||DOC.DOCU_ANIO AS DOCUMENTO\n"
                    + "FROM TRAMITE_DATOS TD, USUARIO U, TIPO_DOCU DOC,OFICINA OFI, TRAMITE_MOVIMIENTO TM\n"
                    + "WHERE TD.TRAM_NUM=UPPER('" + tramnum + "') \n"
                    + "AND TD.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND TD.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND TM.MOVI_NUM='" + movi + "'\n"
                    + "AND TD.TRAM_NUM=DOC.TRAM_NUM\n"
                    + "and TM.TRAM_FECHA=DOC.TRAM_FECHA\n"
                    + "AND TM.TRAM_NUM=DOC.TRAM_NUM\n"
                    + "AND U.ID_OFICINA=OFI.ID_OFICINA\n"
                    + "and U.USU=TD.USU");
            codigos = query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró22222");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return codigos;
    }

    @Override
    public List getDeatalle2(String tramnum, String movi) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.USU,\n"
                    + "OFI.NOMBRE_OFICINA,\n"
                    + "DOC.DOCU_NOMBRE||'-'||DOC.DOCU_NUM||'-'||DOC.DOCU_SIGLAS||'-'||DOC.DOCU_ANIO AS DOCUMENTO\n"
                    + "FROM TRAMITE_DATOS TD, USUARIO U, TIPO_DOCU DOC,OFICINA OFI, TRAMITE_MOVIMIENTO TM\n"
                    + "WHERE TD.TRAM_NUM=UPPER('" + tramnum + "') \n"
                    + "AND TD.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND TD.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND TM.MOVI_NUM='" + movi + "'\n"
                    + "AND TD.TRAM_NUM=DOC.TRAM_NUM\n"
                    + "and TM.TRAM_FECHA=DOC.TRAM_FECHA\n"
                    + "AND TM.TRAM_NUM=DOC.TRAM_NUM\n"
                    + "AND U.ID_OFICINA=OFI.ID_OFICINA\n"
                    + "and U.USU=TD.USU");
            codigos = query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró22222");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return codigos;
    }

    @Override
    public String getMotivo(String tramnum, String fecha) {
        String codigos = "";
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TD.TRAM_OBS \n"
                    + "FROM TRAMITE_DATOS TD\n"
                    + "WHERE TD.TRAM_NUM='" + tramnum + "'\n"
                    + "AND TO_CHAR(TD.TRAM_FECHA,'dd/MM/yyyy')='" + partir(fecha) + "'");
            codigos = (String) query.uniqueResult();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("problemasmotivo");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return codigos;
    }

    public String partir(String fecha) {
        String[] cadena = new String[2];
        int i = 0;
        StringTokenizer tokens = new StringTokenizer(fecha);
        while (tokens.hasMoreTokens()) {
            cadena[i] = tokens.nextToken();
            i++;
        }
        return cadena[0];

    }

    @Override
    public String getOficina(Usuario usu) {
        String codigos = "";
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select ofic.nombre_oficina\n"
                    + "from oficina ofic, usuario usu\n"
                    + "where usu.USU='" + usu.getUsu() + "' \n"
                    + "and usu.ID_OFICINA=ofic.ID_OFICINA");
            codigos = (String) query.uniqueResult();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("problem oficina");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return codigos;
    }

    @Override
    public void EliminarTramite(String tramnum, String fecha) {
        this.EliminarTramMov(tramnum, fecha);
        this.EliminarTipDocu(tramnum, fecha);
        this.EliminarTD(tramnum, fecha);
        this.EliminarTemporal(tramnum, fecha);
    }

    @Override
    public void EliminarTD(String tramnum, String fecha) {
        System.out.println("ENTRA A ELIMINAR TD");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM TRAMITE_DATOS WHERE TRAM_NUM= :tramitenum AND ";
        try {
            session.beginTransaction();
            int i = session.createSQLQuery("DELETE FROM TRAMITE_DATOS WHERE TRAM_NUM= '" + tramnum + "' AND to_char(TRAM_FECHA,'dd/MM/yyyy')='" + fecha + "'").executeUpdate();
            session.beginTransaction().commit();

            System.out.println("eliminados: " + i);
        } catch (Exception e) {
            System.out.println("mal eliminar TD");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void EliminarTipDocu(String tramnum, String fecha) {
        System.out.println("ENTRA A ELIMINAR TIPO DOCU");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM TIPO_DOCU WHERE TRAM_NUM='" + tramnum + "' AND to_char(TRAM_FECHA,'dd/MM/yyyy')='" + fecha + "'";
        try {
            session.beginTransaction();
            int i = session.createSQLQuery(sql).executeUpdate();
            session.beginTransaction().commit();

            System.out.println("eliminados: " + i);
        } catch (Exception e) {
            System.out.println("mal eliminar TIPO DOCU");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void EliminarTramMov(String tramnum, String fecha) {
        System.out.println("ENTRA A ELIMINAR TM");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM TRAMITE_MOVIMIENTO WHERE TRAM_NUM='" + tramnum + "' AND to_char(FECHA_ENVIO,'dd/MM/yyyy')='" + fecha + "'";
        try {
            session.beginTransaction();
            int i = session.createSQLQuery(sql).executeUpdate();
            session.beginTransaction().commit();

            System.out.println("eliminados: " + i);
        } catch (Exception e) {
            System.out.println("mal eliminar TM");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void EliminarTemporal(String tramnum, String fecha) {
        System.out.println("ENTRA A ELIMINAR TEMPORAL");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM TEMPORAL WHERE TRAM_NUM='" + tramnum + "' AND to_char(FECHA,'dd/MM/yyyy')='" + fecha + "'";
        try {
            session.beginTransaction();
            int i = session.createSQLQuery(sql).executeUpdate();
            session.beginTransaction().commit();

            System.out.println("eliminados: " + i);
        } catch (Exception e) {
            System.out.println("mal eliminar TEMPORAL");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

}
