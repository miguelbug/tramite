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
    public List getTipoDocu() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT NOMBRE_DOCU FROM TIPOS_DOCUMENTOS WHERE FLAG='0'");
            docus = (List) query.list();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró TIPOS DOCUS");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return docus;
    }

    @Override
    public String getJefe() {
        String jefe = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT J.GRADO||' '||J.NOMBRE||' '||J.APELLIDOS AS JEFE FROM JEFATURA J, USUARIO U"
                    + " WHERE J.USU=U.USU\n"
                    + "AND U.ESTADO='activo'\n"
                    + "AND J.CARGO='JEFATURA'");
            jefe = (String) query.uniqueResult();
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró depenencias");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return jefe;
    }

    @Override
    public List getDependencias() {
        List docus = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select nombre from Dependencia where tipodepe is not null order by nombre");
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
    public List nuevaBusqAvanzada(String expediente, String asunto, String derivadoa) {
        List docusInt = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT UPPER(R.TRAM_NUM),"
                    + "DECODE(R.TFECHA,NULL,'--',TO_CHAR(R.TFECHA,'DD/MM/YYYY HH24:MI:SS')) AS FECHA2,"
                    + "DECODE(TO_CHAR(R.FECHA,'DD/MM/YYYY HH24:MI:SS'),NULL,'--',TO_CHAR(R.FECHA,'DD/MM/YYYY HH24:MI:SS')) AS FECHA,"
                    + "R.DERIVADOA,"
                    + "UPPER(R.ASUNTO) AS ASUNTO\n"
                    + "FROM (SELECT TM.TRAM_NUM AS TRAM_NUM, \n"
                    + "       TM.TRAM_FECHA AS TFECHA,\n"
                    + "       TM.FECHA_INGR AS FECHA,\n"
                    + "       D1.NOMBRE AS DERIVADOA,\n"
                    + "       DECODE(TM.MOVI_OBS,NULL,'--',TM.MOVI_OBS) AS ASUNTO\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + generarPrimeraCondicion(expediente, asunto, derivadoa)
                    + "\nUNION\n"
                    + "SELECT TM.TRAM_NUM AS TRAM_NUM, \n"
                    + "       TM.TRAM_FECHA AS TFECHA,\n"
                    + "       TM.FECHA_INGR AS FECHA,\n"
                    + "       D1.NOMBRE AS DERIVADOA,\n"
                    + "       DECODE(TM.MOVI_OBS,NULL,'--',TM.MOVI_OBS) AS ASUNTO\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, OFICIOS OFI\n"
                    + "WHERE OFI.CODIGO1=D1.CODIGO\n"
                    + "AND TM.CODIGO1=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + generarSegundaCondicion(expediente, asunto, derivadoa)
                    + "\nUNION\n"
                    + "select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "ofi.FECHA_OFICIO as fecha1,\n"
                    + "ofi.FECHA_OFICIO as fecha2,\n"
                    + "d2.nombre as destino,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO)||'  '||DECODE(ofi.ASUNTO_OFICIO,NULL,'SIN ASUNTO',ofi.ASUNTO_OFICIO) as referencia\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is not null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + generarTerceraCondicion(expediente, asunto, derivadoa)
                    + "\nUNION\n"
                    + "select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "ofi.FECHA_OFICIO as fecha1,\n"
                    + "ofi.FECHA_OFICIO as fecha2,\n"
                    + "d2.nombre as destino,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO)||'  '||DECODE(ofi.ASUNTO_OFICIO,NULL,'SIN ASUNTO',ofi.ASUNTO_OFICIO) as referencia\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + generarCuartaCondicion(expediente, asunto, derivadoa)
                    + "\nUNION\n"
                    + "select TD.NOMBRE_DOCU||' N° '||DE.CORRELATIVOD||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY') as documento,\n"
                    + "DE.FECHA as fecha1,\n"
                    + "DE.FECHA as fecha2,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "DECODE(DE.ASUNTO,NULL,'SIN ASUNTO',UPPER(DE.ASUNTO)) as asunto\n"
                    + "from DOCUS_EXTINT DE, DEPENDENCIA M1, DEPENDENCIA M2, TIPOS_DOCUMENTOS TD, USUARIO USUA, OFICINA oficina\n"
                    + "WHERE DE.CODIGO=M1.CODIGO\n"
                    + "AND DE.CODIGO1=M2.CODIGO\n"
                    + "AND DE.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DE.USU=USUA.USU\n"
                    + "AND USUA.ID_OFICINA=oficina.ID_OFICINA\n"
                    + "AND DE.EXT_INT IN ('pe','pi')\n"
                    + generarQuintaCondicion(expediente, asunto, derivadoa)
                    + "\nUNION\n"
                    + "SELECT DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DI.FECHAREGISTRO AS FECHA1,\n"
                    + "DI.FECHAREGISTRO AS FECHA2,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "UPPER(DI.DOCU_ASUNTO) AS ASUNTO\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.USU1=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NULL\n"
                    + "AND DI.DOCU_SIGLASINT='OGPL'\n"
                    + generarSextaCondicion(expediente, asunto, derivadoa) + ")R\n"
                    + "ORDER BY R.FECHA");
            docusInt = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal NUEVA BUSQUEDA");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return docusInt;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public String generarTerceraCondicion(String expediente, String asunto, String derivado) {
        String c1[] = {"AND ofi.CORRELATIVO_OFICIO LIKE", "AND ofi.codigo1 LIKE", "AND UPPER(ofi.REFERENCIA_OFICIO||' '||ofi.ASUNTO_OFICIO) LIKE"};
        String c2[] = {expediente, derivado, asunto.toUpperCase()};
        String retorno = "";
        int i = 0;
        while (i < c2.length) {
            if (!c2[i].equals("")) {
                retorno = retorno + c1[i] + " '%" + c2[i] + "%'";
            }
            i++;
        }
        retorno = retorno + "\n";
        return retorno;
    }

    public String generarCuartaCondicion(String expediente, String asunto, String derivado) {
        String c1[] = {"AND ofi.CORRELATIVO_OFICIO LIKE", "AND ofi.codigo1 LIKE", "AND UPPER(ofi.REFERENCIA_OFICIO||' '||ofi.ASUNTO_OFICIO) LIKE"};
        String c2[] = {expediente, derivado, asunto.toUpperCase()};
        String retorno = "";
        int i = 0;
        while (i < c2.length) {
            if (!c2[i].equals("")) {
                retorno = retorno + c1[i] + " '%" + c2[i] + "%'";
            }
            i++;
        }
        retorno = retorno + "\n";
        return retorno;
    }

    public String generarQuintaCondicion(String expediente, String asunto, String derivado) {
        String c1[] = {"AND DE.CORRELATIVOD LIKE", "AND DE.CODIGO1 LIKE", "AND UPPER(DE.ASUNTO) LIKE"};
        String c2[] = {expediente, derivado, asunto.toUpperCase()};
        String retorno = "";
        int i = 0;
        while (i < c2.length) {
            if (!c2[i].equals("")) {
                retorno = retorno + c1[i] + " '%" + c2[i] + "%'";
            }
            i++;
        }
        retorno = retorno + "\n";
        return retorno;
    }

    public String generarSextaCondicion(String expediente, String asunto, String derivado) {
        String c1[] = {"AND DI.DOCU_CORRELAINT LIKE", "AND DI.CODIGO1 LIKE", "AND UPPER(DI.DOCU_ASUNTO) LIKE"};
        String c2[] = {expediente, derivado, asunto.toUpperCase()};
        String retorno = "";
        int i = 0;
        while (i < c2.length) {
            if (!c2[i].equals("")) {
                retorno = retorno + c1[i] + " '%" + c2[i] + "%'";
            }
            i++;
        }
        retorno = retorno + "\n";
        return retorno;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public String generarSegundaCondicion(String expediente, String asunto, String derivado) {
        String c1[] = {"AND TM.TRAM_NUM LIKE", "AND OFI.CODIGO1 LIKE", "AND UPPER(TM.MOVI_OBS) LIKE"};
        String c2[] = {expediente, derivado, asunto.toUpperCase()};
        String retorno = "";
        int i = 0;
        while (i < c2.length) {
            if (!c2[i].equals("")) {
                retorno = retorno + c1[i] + " '%" + c2[i] + "%'";
            }
            i++;
        }
        retorno = retorno + "\n";
        return retorno;
    }

    public String generarPrimeraCondicion(String expediente, String asunto, String derivado) {
        String c1[] = {"AND TM.TRAM_NUM LIKE", "AND TM.CODIGO1 LIKE", "AND UPPER(TM.MOVI_OBS) LIKE"};
        String c2[] = {expediente, derivado, asunto.toUpperCase()};
        String retorno = "";
        int i = 0;
        while (i < c2.length) {
            if (!c2[i].equals("")) {
                retorno = retorno + c1[i] + " '%" + c2[i] + "%'";
            }
            i++;
        }
        retorno = retorno + "\n";
        return retorno;
    }

    @Override
    public void eliminarDocuInternoOGPL(String id) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM DOCUS_INTERNOS WHERE IDTIP= '" + id + "' ";
        try {
            session.beginTransaction();
            int i = session.createSQLQuery(sql).executeUpdate();
            session.beginTransaction().commit();

            System.out.println("eliminados: " + i);
        } catch (Exception e) {
            System.out.println("mal eliminar docuinternogopl");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List docusInternosOGPL() {
        List docusInt = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DI.IDTIP AS I_D,\n"
                    + "DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "DI.FECHAREGISTRO AS FECHA,\n"
                    + "UPPER(DI.DOCU_ASUNTO) AS ASUNTO,\n"
                    + "D1.NOMBRE AS ORIGEN,\n"
                    + "D2.NOMBRE AS DESTINO,\n"
                    + "USUA.USU_NOMBRE AS ASIGNADO,\n"
                    + "DI.ID_DOCUMENTO AS IDDOCUMENTO\n"
                    + "\n"
                    + "FROM DOCUS_INTERNOS DI, USUARIO USUA, TIPOS_DOCUMENTOS TD, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE DI.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DI.CODIGO=D1.CODIGO\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.USU1=USUA.USU\n"
                    + "AND DI.TRAM_NUM IS NULL\n"
                    + "AND DI.DOCU_SIGLASINT='OGPL'\n"
                    + "ORDER BY DI.FECHAREGISTRO DESC");
            docusInt = query.list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal DOCUS INTERNOS");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return docusInt;
    }

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
    public void guardarOficio(Oficios ofi) {
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
        //ActualizarMov(tramnum, movimiento);
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
                    + "       vista2.DEPE_COD,\n"
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
                    + "and R.TRAM_NUM||'-'||to_char(R.MOVI_FEC_ENV,'dd/MM/yyyy')  not in (select tram_num||'-'||to_char(tram_fecha, 'dd/MM/yyyy') from tramite_datos)\n"
                    + "AND R.DEST_COD IN ('1001868','1001869','1001870','1001871','1001872','100392')\n"
                    + "AND R.DEPE_COD NOT IN ('1001868','1001869','1001870','1001871','1001872')");
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
                    + "tm.ESTAD_CONFRIRM,\n"
                    + "to_char(tm.tram_fecha,'dd/MM/yyyy') as tramfecha\n"
                    + "FROM TRAMITE_MOVIMIENTO tm, INDICADOR I, DEPENDENCIA D1, DEPENDENCIA D2\n"
                    + "WHERE tm.INDI_COD=I.INDI_COD\n"
                    + "and tm.CODIGO=D1.CODIGO\n"
                    + "and tm.CODIGO1=D2.CODIGO\n"
                    + "and D2.NOMBRE='OFICINA GENERAL DE PLANIFICACION'\n"
                    + "AND tm.tram_num||'-'||tm.tram_fecha not in (Select tram_num||'-'||tram_fecha from Oficios)\n"
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
    public List query1(String ex, String f) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DECODE(TM.FECHA_INGR,NULL,'---',TO_CHAR(TM.FECHA_INGR,'DD/MM/YYYY HH24:MI:SS')) as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A \n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND TM.TRAM_NUM='" + ex + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + f + "'\n"
                    + "AND TM.FECHA_INGR IS NOT NULL\n"
                    + "AND TM.CODIGO='100392'");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List query2(String ex, String f) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DECODE(TM.FECHA_INGR,NULL,'---',TO_CHAR(TM.FECHA_INGR,'DD/MM/YYYY HH24:MI:SS')) as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A \n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND TM.TRAM_NUM='" + ex + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + f + "'\n"
                    + "AND TM.FECHA_INGR IS NULL\n"
                    + "AND TM.CODIGO='100392'");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List query3(String ex, String f) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DECODE(DI.FECHAREGISTRO,NULL,'--',TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS')) AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DOCUS_INTERNOS DI\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
                    + "AND DI.TRAM_NUM='" + ex + "'\n"
                    + "AND TO_CHAR(DI.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + f + "'");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List query4(String ex, String f) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DISTINCT DECODE(OFI.FECHA_OFICIO,NULL,'--',TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS')) AS FECHAOFICIO,'OFICIO '||'N° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D3, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND TM.TRAM_NUM='" + ex + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + f + "'");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List query5(String docu) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("Select R.documento,\n"
                    + "R.tramite,\n"
                    + "TO_CHAR(R.fecha,'DD/MM/YYYY HH24:MI:ss') AS FECHA,\n"
                    + "R.referencia,\n"
                    + "DECODE(R.asunto,NULL,'SIN ASUNTO',UPPER(R.asunto)),\n"
                    + "R.origen,\n"
                    + "R.destino,\n"
                    + "UPPER(R.responsable)\n"
                    + "From (select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "decode(ofi.TRAM_NUM,NULL,'SIN NUMERO DE TRAMITE',ofi.TRAM_NUM) as tramite,\n"
                    + "ofi.FECHA_OFICIO as fecha,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO) as referencia,\n"
                    + "DECODE(ofi.ASUNTO_OFICIO,NULL,'SIN ASUNTO',ofi.ASUNTO_OFICIO) as asunto,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino,\n"
                    + "ofi.responsable\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is not null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + "and 'OFICIO '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') ='" + docu + "'\n"
                    + "union\n"
                    + "select 'Oficio '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') AS documento,\n"
                    + "decode(ofi.TRAM_NUM,NULL,'SIN NUMERO DE TRAMITE',ofi.TRAM_NUM) as tramite,\n"
                    + "ofi.FECHA_OFICIO as fecha,\n"
                    + "decode(ofi.REFERENCIA_OFICIO,NULL,'SIN REFERENCIA',ofi.REFERENCIA_OFICIO) as referencia,\n"
                    + "DECODE(ofi.ASUNTO_OFICIO,NULL,'SIN ASUNTO',ofi.ASUNTO_OFICIO) as asunto,\n"
                    + "d1.nombre as origen,\n"
                    + "d2.nombre as destino,\n"
                    + "ofi.responsable\n"
                    + "from OFICIOS ofi, Dependencia d1, Dependencia d2,Oficina oficina\n"
                    + "where d1.codigo=ofi.codigo\n"
                    + "and d2.codigo=ofi.codigo1\n"
                    + "and tram_num is null\n"
                    + "and d1.nombre=oficina.nombre_oficina\n"
                    + "and 'OFICIO '||'N° '||ofi.CORRELATIVO_OFICIO||'-'||oficina.SIGLAS||'-'||TO_CHAR(ofi.FECHA_OFICIO,'YYYY') ='" + docu + "') R\n"
                    + "order by R.fecha desc");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List query6(String docu) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT R.DOCUMENTO,\n"
                    + "       R.TRAM_NUM,\n"
                    + "       R.TRAM_FECHA AS TRAM_FECHA,\n"
                    + "       TO_CHAR(R.FECHA,'DD/MM/YYYY HH:mm:ss') AS FECHA,\n"
                    + "       DECODE(R.ASUNTO,NULL,'SIN ASUNTO',UPPER(R.ASUNTO)),\n"
                    + "       R.ORIGEN,\n"
                    + "       R.DESTINO,\n"
                    + "       R.ASIGNADO\n"
                    + "       FROM (SELECT DI.DOCU_NOMBREINT||' NÂ° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "             DI.TRAM_NUM AS TRAM_NUM,\n"
                    + "             TO_CHAR(DI.TRAM_FECHA,'DD/MM/YYYY') AS TRAM_FECHA,\n"
                    + "             DI.FECHAREGISTRO AS FECHA,\n"
                    + "             DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "             D1.NOMBRE AS ORIGEN,\n"
                    + "             D2.NOMBRE AS DESTINO,\n"
                    + "             USUA.USU_NOMBRE AS ASIGNADO\n"
                    + "             FROM DOCUS_INTERNOS DI, \n"
                    + "                  USUARIO USUA, \n"
                    + "                  TRAMITE_MOVIMIENTO TM, \n"
                    + "                  DEPENDENCIA D1, \n"
                    + "                  DEPENDENCIA D2, \n"
                    + "                  TRAMITE_DATOS TDAT, \n"
                    + "                  DEPENDENCIA D3\n"
                    + "             WHERE TM.USU=USUA.USU\n"
                    + "             AND DI.CODIGO=D1.CODIGO\n"
                    + "             AND DI.CODIGO1=D2.CODIGO\n"
                    + "             AND DI.TRAM_NUM IS NOT NULL\n"
                    + "             AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "             AND DI.NUMERO_MOVI=TM.MOVI_NUM\n"
                    + "             AND DI.TRAM_NUM=TDAT.TRAM_NUM\n"
                    + "             AND DI.TRAM_FECHA=TDAT.TRAM_FECHA\n"
                    + "             AND TDAT.CODIGO=D3.CODIGO\n"
                    + "             AND DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT='" + docu + "'\n"
                    + "             UNION\n"
                    + "             SELECT DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS DOCUMENTO,\n"
                    + "             DECODE(DI.TRAM_NUM,NULL,'SIN EXPEDIENTE',DI.TRAM_NUM) AS TRAM_NUM,\n"
                    + "             DECODE(TO_CHAR(DI.TRAM_FECHA,'DD/MM/YYYY'),NULL,'SIN FECHA',TO_CHAR(DI.TRAM_FECHA,'DD/MM/YYYY')) AS TRAM_FECHA,\n"
                    + "             DI.FECHAREGISTRO AS FECHA,\n"
                    + "             DI.DOCU_ASUNTO AS ASUNTO,\n"
                    + "             D1.NOMBRE AS ORIGEN,\n"
                    + "             D2.NOMBRE AS DESTINO,\n"
                    + "             USUA.USU_NOMBRE AS ASIGNADO\n"
                    + "             FROM DOCUS_INTERNOS DI, DEPENDENCIA D1, DEPENDENCIA D2, USUARIO USUA\n"
                    + "             WHERE DI.CODIGO=D1.CODIGO\n"
                    + "             AND DI.CODIGO1=D2.CODIGO\n"
                    + "             AND DI.USU=USUA.USU\n"
                    + "             AND DI.TRAM_NUM IS NULL\n"
                    + "             AND UPPER(DI.DOCU_NOMBREINT||' N° '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT)='" + docu + "') R\n"
                    + "                                        \n"
                    + "     ORDER BY R.FECHA DESC");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List query7(String docu) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("select TD.NOMBRE_DOCU||' N° '||DE.CORRELATIVOD||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY') as documento,\n"
                    + "DE.NUMERODOC,\n"
                    + "DECODE(DE.ASUNTO,NULL,'SIN ASUNTO',UPPER(DE.ASUNTO)) as asunto,\n"
                    + "M1.NOMBRE AS ORIGEN,\n"
                    + "M2.NOMBRE AS DESTINO,\n"
                    + "to_char(DE.FECHA,'DD/MM/YYYY HH24:MI:ss') as fecha,\n"
                    + "USUA.USU_NOMBRE\n"
                    + "from DOCUS_EXTINT DE, DEPENDENCIA M1, DEPENDENCIA M2, TIPOS_DOCUMENTOS TD, USUARIO USUA, OFICINA oficina\n"
                    + "WHERE DE.CODIGO=M1.CODIGO\n"
                    + "AND DE.CODIGO1=M2.CODIGO\n"
                    + "AND DE.ID_DOCUMENTO=TD.ID_DOCUMENTO\n"
                    + "AND DE.USU=USUA.USU\n"
                    + "AND USUA.ID_OFICINA=oficina.ID_OFICINA\n"
                    + "AND DE.EXT_INT IN ('pe','pi')\n"
                    + "AND TD.NOMBRE_DOCU||' N° '||DE.CORRELATIVOD||'-'||oficina.siglas||'-'||to_char(DE.Fecha,'YYYY')='" + docu + "'\n"
                    + "ORDER BY DE.ID DESC");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List busquedaAvanzada3(String tm, String tf) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT DECODE(R.FECHAINGRESO,NULL,'---',TO_CHAR(R.FECHAINGRESO,'DD/MM/YYYY HH24:MI:SS')) AS FECHA,R.NUMEROTRAMITE,R.DERIVADO_A,R.FECHAREG,R.RESPUESTA,R.DERIVADO_A1,R.FECHAOFICIO,R.documento,R.DERIVADO_A2\n"
                    + "FROM (SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS') AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A1,\n"
                    + "       TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS') AS FECHAOFICIO,'OFICIO '||'NÂ° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DEPENDENCIA D3, DOCUS_INTERNOS DI, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND TM.TRAM_NUM='" + tm + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + tf + "'\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       '---' AS FECHAREG, '---' AS RESPUESTA, '---' AS DERIVADO_A1,\n"
                    + "       '---' AS FECHAOFICIO,'---' AS documento, '---' AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND TM.TRAM_NUM='" + tm + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + tf + "'\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS') AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A,\n"
                    + "       '---' AS FECHAOFICIO ,'---' AS documento, '---' AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DOCUS_INTERNOS DI\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA NOT IN (SELECT OFI.TRAM_NUM||'-'||OFI.TRAM_FECHA FROM OFICIOS OFI)\n"
                    + "AND TM.TRAM_NUM='" + tm + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + tf + "'\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       '---' AS FECHAREG, '---' AS RESPUESTA, '---' AS DERIVADO_A1,\n"
                    + "       TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS') AS FECHAOFICIO,'OFICIO '||'NÂ° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D3, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND OFI.TRAM_NUM||'-'||OFI.TRAM_FECHA NOT IN (SELECT DI.TRAM_NUM||'-'||DI.TRAM_FECHA FROM DOCUS_INTERNOS DI)\n"
                    + "AND TM.TRAM_NUM='" + tm + "'\n"
                    + "AND TO_CHAR(TM.TRAM_FECHA,'DD/MM/YYYY HH24:MI:SS')='" + tf + "')R\n"
                    + "ORDER BY R.FECHAINGRESO DESC");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List busquedaAvanzada2() {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TO_CHAR(R.FECHAINGRESO,'DD/MM/YYYY HH24:MI:SS') AS FECHA,"
                    + "R.NUMEROTRAMITE,"
                    + "R.DERIVADO_A,"
                    + "R.FECHAREG,"
                    + "R.RESPUESTA,"
                    + "R.DERIVADO_A1,"
                    + "R.FECHAOFICIO,"
                    + "R.documento,"
                    + "R.DERIVADO_A2\n"
                    + "FROM (SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS') AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A1,\n"
                    + "       TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS') AS FECHAOFICIO,'OFICIO '||'N° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DEPENDENCIA D3, DOCUS_INTERNOS DI, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS') AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A,\n"
                    + "       '---' AS FECHAOFICIO ,'---' AS documento, '---' AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DOCUS_INTERNOS DI\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA NOT IN (SELECT OFI.TRAM_NUM||'-'||OFI.TRAM_FECHA FROM OFICIOS OFI)\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       '---' AS FECHAREG, '---' AS RESPUESTA, '---' AS DERIVADO_A1,\n"
                    + "       TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS') AS FECHAOFICIO,'OFICIO '||'N° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D3, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND OFI.TRAM_NUM||'-'||OFI.TRAM_FECHA NOT IN (SELECT DI.TRAM_NUM||'-'||DI.TRAM_FECHA FROM DOCUS_INTERNOS DI))R\n"
                    + "WHERE R.FECHAINGRESO IS NOT NULL\n"
                    + "ORDER BY R.FECHAINGRESO DESC");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
    }

    @Override
    public List busquedaAvanzada(String oficina) {
        List docusavanzado = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("get docus avanzado");
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT TO_CHAR(R.FECHAINGRESO,'DD/MM/YYYY HH24:MI:SS') AS FECHA,"
                    + "R.NUMEROTRAMITE,"
                    + "R.DERIVADO_A,"
                    + "R.FECHAREG,"
                    + "R.RESPUESTA,"
                    + "R.DERIVADO_A1,"
                    + "R.FECHAOFICIO,"
                    + "R.documento,"
                    + "R.DERIVADO_A2\n"
                    + "FROM (SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS') AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A1,\n"
                    + "       TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS') AS FECHAOFICIO,'OFICIO '||'N° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DEPENDENCIA D3, DOCUS_INTERNOS DI, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND TM.CODIGO1 IN ('" + oficina + "')\n"
                    + "AND DI.CODIGO IN ('" + oficina + "')\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       TO_CHAR(DI.FECHAREGISTRO,'DD/MM/YYYY HH24:MI:SS') AS FECHAREG, DI.DOCU_NOMBREINT||' '||DI.DOCU_CORRELAINT||'-'||DI.DOCU_SIGLASINT||'-'||DI.DOCU_ANIOINT AS RESPUESTA, D2.NOMBRE AS DERIVADO_A,\n"
                    + "       '---' AS FECHAOFICIO ,'---' AS documento, '---' AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D2, DOCUS_INTERNOS DI\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND DI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND DI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND DI.CODIGO1=D2.CODIGO\n"
                    + "AND TM.CODIGO1 IN ('" + oficina + "')\n"
                    + "AND DI.CODIGO IN ('" + oficina + "')\n"
                    + "AND DI.TRAM_NUM||'-'||DI.TRAM_FECHA NOT IN (SELECT OFI.TRAM_NUM||'-'||OFI.TRAM_FECHA FROM OFICIOS OFI)\n"
                    + "UNION\n"
                    + "SELECT TM.FECHA_INGR as FECHAINGRESO, TM.TRAM_NUM AS NUMEROTRAMITE, D1.NOMBRE AS DERIVADO_A, \n"
                    + "       '---' AS FECHAREG, '---' AS RESPUESTA, '---' AS DERIVADO_A1,\n"
                    + "       TO_CHAR(OFI.FECHA_OFICIO,'DD/MM/YYYY HH24:MI:SS') AS FECHAOFICIO,'OFICIO '||'N° '||OFI.CORRELATIVO_OFICIO||'-'||OFIC.SIGLAS||'-'||TO_CHAR(OFI.FECHA_OFICIO,'YYYY') AS documento, D3.NOMBRE AS DERIVADO_A2\n"
                    + "FROM TRAMITE_MOVIMIENTO TM, DEPENDENCIA D1, DEPENDENCIA D3, OFICIOS OFI, OFICINA OFIC\n"
                    + "WHERE TM.CODIGO1=D1.CODIGO\n"
                    + "AND TM.CODIGO1 IN ('" + oficina + "')\n"
                    + "AND OFI.CODIGO1=D3.CODIGO\n"
                    + "AND OFIC.ID_OFICINA=OFI.CODIGO\n"
                    + "AND OFI.TRAM_NUM=TM.TRAM_NUM\n"
                    + "AND OFI.TRAM_FECHA=TM.TRAM_FECHA\n"
                    + "AND OFI.TRAM_NUM||'-'||OFI.TRAM_FECHA NOT IN (SELECT DI.TRAM_NUM||'-'||DI.TRAM_FECHA FROM DOCUS_INTERNOS DI))R\n"
                    + "WHERE R.FECHAINGRESO IS NOT NULL\n"
                    + "ORDER BY R.FECHAINGRESO DESC");
            docusavanzado = query.list();
            System.out.println("despues de query de gedocus avanzados");
            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("no entró a docus avanzado");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        System.out.println("retorna");
        return docusavanzado;
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
    public List getDetalleOGPL(String tramnum, String fecha) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select TRAM_FECHA,\n"
                    + "DEPE_ORIGEN,\n"
                    + "ESTA_DESCRIP,\n"
                    + "DOCU_NOMBRE||'-'||DOCU_NUM||'-'||DOCU_SIGLAS||'-'||DOCU_ANIO as documento\n"
                    + "FROM vw_ogpl001@TRAMITEDBLINK\n"
                    + "where TRAM_NUM='" + tramnum + "'\n"
                    + "AND TO_CHAR(TRAM_FECHA,'DD/MM/YYYY')='" + fecha + "'"
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
            Query query = session.createSQLQuery("SELECT MOVI_FEC_ENV,MOVI_ORIGEN,ESTA_NOMBRE,TRAM_NUM\n"
                    + "FROM vw_ogpl002@TRAMITEDBLINK\n"
                    + "WHERE TRAM_NUM='"+tramnum+"'\n"
                    + "AND MOVI_NUM='"+movi+"'");
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
    public void EliminarTramite(String tramnum, String fecha, String movi) {
        this.EliminarTramMov(tramnum, fecha, movi);
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
    public void EliminarTramMov(String tramnum, String fecha, String movi) {
        System.out.println("ENTRA A ELIMINAR TM");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "DELETE FROM TRAMITE_MOVIMIENTO WHERE TRAM_NUM='" + tramnum + "' AND to_char(FECHA_ENVIO,'dd/MM/yyyy')='" + fecha + "' AND MOVI_NUM='" + movi + "'";
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
