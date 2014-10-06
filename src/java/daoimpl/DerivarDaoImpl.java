/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.DerivarDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import maping.Dependencia;
import maping.DocusExt;
import maping.DocusExtint;
import maping.DocusInternos;
import maping.Indicador;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.TramiteMovimiento;
import maping.Usuario;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DerivarDaoImpl implements DerivarDAO {

    private Session session;

    @Override
    public String getIndice(String tramnum) {
        System.out.println("getindice");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(docuCorrela) from DocusInternos where docuSiglasint='" + tramnum + "'";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indice");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public String getSiglas(String ofi) {
        System.out.println("getssiglas");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select ofi.siglasofi\n"
                + "from Oficina ofi, Usuario usua\n"
                + "where usua.oficina.idOficina='" + ofi + "'\n"
                + "and usua.oficina.idOficina=ofi.idOficina";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indice");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public String getSiglas2(String nombofi) {
        System.out.println("getssiglas 2");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select ofi.siglasofi\n"
                + "from Oficina ofi\n"
                + "where nombreOficina='" + nombofi + "'\n";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indice");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;

    }

    @Override
    public int getMovimiento(String tramnum) {
        System.out.println("getmovimiento");
        int movimiento = 0;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(moviNum) from TramiteMovimiento WHERE TRAM_NUM='" + tramnum + "'";
        try {
            session.beginTransaction();
            Short movimiento1 = (Short) session.createQuery(sql).uniqueResult();
            String aux = String.valueOf(movimiento1);
            movimiento = Integer.parseInt(aux);
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("mal indice");
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return movimiento;
    }

    @Override
    public void InsertarMovimiento(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino, Indicador i) {
        try {
            System.out.println(movimiento + " " + fechaenvio + " " + asunto + " " + estado + " " + numtram + " " + origen + " " + destino + " " + i);
            System.out.println("entra a guardado insertmovi");
            /*MovimientoInterno mi = new MovimientoInterno();
             mi.setMoviNumint(movimiento);
             mi.setFechaEnvint(fechaenvio);
             mi.setObsMovint(asunto);
             mi.setEstadInt(estado);
             TramiteDatos td = getTramite(numtram);
             mi.setTramiteDatos(td);
             mi.setDependenciaByCodigo(getDependencia(origen));
             mi.setDependenciaByCodigo1(getDependencia2(destino));
             mi.setIndicador(i);
             mi.setEstadoConfirmado("confirmado");*/
            TramiteMovimiento tm = new TramiteMovimiento();
            tm.setMoviNum(Short.parseShort(String.valueOf(movimiento)));
            tm.setFechaEnvio(fechaenvio);
            tm.setMoviObs(asunto);
            tm.setEstaNombre(estado);
            TramiteDatos td = getTramite(numtram);
            tm.setTramiteDatos(td);
            tm.setDependenciaByCodigo(getDependencia(origen));
            tm.setDependenciaByCodigo1(getDependencia2(destino));
            tm.setIndicador(i);
            tm.setEstadDerivado("derivado");
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tm);
            session.getTransaction().commit();
            System.out.println("terminó movimiento");
        } catch (Exception ex) {
            System.err.println("falló guardado movimiento." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void InsertarMovimiento2(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino) {
        try {
            System.out.println("entra a guardado insertmovi");
            /*MovimientoInterno mi = new MovimientoInterno();
             mi.setMoviNumint(movimiento);
             mi.setFechaEnvint(fechaenvio);
             mi.setObsMovint(asunto);
             mi.setEstadInt(estado);
             TramiteDatos td = getTramite(numtram);
             mi.setTramiteDatos(td);
             mi.setDependenciaByCodigo(getDependencia(origen));
             mi.setDependenciaByCodigo1(getDependencia2(destino));
             session = HibernateUtil.getSessionFactory().openSession();
             session.beginTransaction();
             session.save(mi);
             session.getTransaction().commit();
             System.out.println("terminó movimiento");*/
        } catch (Exception ex) {
            System.err.println("falló guardado movimiento." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas, String anio, String numtram, Date fecharegistro, Usuario usu) {
        try {
            System.out.println("entra a guardar tipo docus");
            DocusInternos di = new DocusInternos();
            di.setDocuCorrelaint(aux);
            di.setDocuNombreint(nombre);
            di.setDocuPricint(String.valueOf(pric));
            di.setDocuSiglasint(siglas);
            di.setDocuAnioint(anio);
            di.setTramiteDatos(getTramite(numtram));
            di.setFecharegistro(fecharegistro);
            di.setUsuario(usu);
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(di);
            session.getTransaction().commit();
            System.out.println("terminó tipodocus");
        } catch (Exception ex) {
            System.err.println("falló guardado tipodocus." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public TramiteDatos getTramite(String tramite) {
        System.out.println("gettramite");
        TramiteDatos td = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: " + tramite);
        String sql = "FROM TramiteDatos WHERE tramNum='" + tramite + "'";
        try {
            session.beginTransaction();
            td = (TramiteDatos) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return td;
    }

    @Override
    public Dependencia getDep(String nombre) {
        Dependencia dep=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Dependencia WHERE nombre='" + nombre + "'";
        try {
            session.beginTransaction();
            dep = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("maldep");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return dep;
    }
    
    @Override
    public Dependencia getDependencia(String nombre) {
        Dependencia dep = null;
        String nombre2 = getCodOficina(nombre);
        System.out.println(nombre2);
        System.out.println("dependencia");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Dependencia WHERE codigo='" + nombre2 + "'";
        try {
            session.beginTransaction();
            dep = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("maldep");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return dep;
    }

    @Override
    public Dependencia getDependencia2(String codigo) {
        System.out.println("depende2");
        Dependencia dep = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: " + codigo);
        String sql = "FROM Dependencia WHERE codigo='" + codigo + "'";
        try {
            session.beginTransaction();
            dep = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("maldep2");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return dep;
    }

    @Override
    public String getCodOficina(String nombreofi) {
        System.out.println("oficina");
        String ofi = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: " + nombreofi);
        String sql = "Select idOficina FROM Oficina WHERE nombreOficina='" + nombreofi + "'";
        try {
            session.beginTransaction();
            ofi = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("malofi");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return ofi;
    }

    @Override
    public void ConfirmarTramites(String numtram, int movimiento, Date fechaing) {
        Confirmar(numtram, movimiento);
        //GuardarConfirmados(movi);
    }

    @Override
    public Date getFecha() {
        Date fechanueva = null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select sysdate from sys.dual";
        try {
            session.beginTransaction();
            fechanueva = (Date) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal fecha");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return fechanueva;
    }

    @Override
    public List getConfirmados(String oficina) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select mi.MOVI_NUM, \n"
                    + "mi.TRAM_NUM, \n"
                    + "M1.NOMBRE AS ORIGEN, \n"
                    + "M2.NOMBRE AS DESTINO, \n"
                    + "DECODE(to_char(mi.FECHA_ENVIO, 'dd/MM/yyyy HH:MI:SS'),NULL,' ',to_char(mi.FECHA_ENVIO, 'dd/MM/yyyy HH:MI:SS')) AS FECHAENVIO, \n"
                    + "DECODE(to_char(mi.FECHA_INGR, 'dd/MM/yyyy HH:MI:SS'),NULL,' ',to_char(mi.FECHA_INGR, 'dd/MM/yyyy HH:MI:SS')) AS FECHAINGRESO, \n"
                    + "DECODE(mi.MOVI_OBS,NULL,' ',mi.MOVI_OBS) AS MOVI, \n"
                    + "mi.ESTA_NOMBRE, \n"
                    + "IND.INDI_NOMBRE \n"
                    + "from TRAMITE_MOVIMIENTO mi, DEPENDENCIA M1, DEPENDENCIA M2, INDICADOR IND \n"
                    + "where mi.CODIGO=M1.CODIGO \n"
                    + "AND mi.CODIGO1=M2.CODIGO \n"
                    + "and mi.CODIGO1='" + oficina + "' \n"
                    + "and mi.INDI_COD=IND.INDI_COD \n"
                    + "and mi.FECHA_INGR is not null \n"
                    + "group by mi.MOVI_NUM,mi.TRAM_NUM,M1.NOMBRE,M2.NOMBRE,mi.FECHA_ENVIO,mi.FECHA_INGR,mi.MOVI_OBS,mi.ESTA_NOMBRE,IND.INDI_NOMBRE\n "
                    + "ORDER BY mi.FECHA_INGR DESC");
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
    public void Confirmar(String numtram, int movimiento) {
        int i = 0;
        Date nuevFech = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechita = formato.format(nuevFech);
        System.out.println(fechita);
        System.out.println("entra a confirmar tramites");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "Update TramiteMovimiento set estadConfrirm='confirmado',"
                + " fechaIngr=to_date('" + fechita + "','DD/MM/YYYY HH:MI:SS') where tramiteDatos.tramNum='" + numtram + "' and moviNum='" + Short.parseShort(String.valueOf(movimiento)) + "'";
        try {
            System.out.println("entra a begin");
            session.beginTransaction();
            i = session.createQuery(sql).executeUpdate();
            session.beginTransaction().commit();
            System.out.println("sale de begin");
        } catch (Exception e) {
            System.out.println("mal confirmar");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("actualizados: " + i);
    }

    /*@Override
     public void GuardarConfirmados(MovimientoInterno movi) {
     try {
     session = HibernateUtil.getSessionFactory().openSession();
     session.beginTransaction();
     session.save(movi);
     session.getTransaction().commit();
     } catch (Exception e) {
     System.err.println("falló guardado movimiento." + e);
     System.out.println(e.getMessage());
     session.getTransaction().rollback();
     } finally {
     session.close();
     }

     }*/
    @Override
    public Indicador getIndic(String nombre) {
        System.out.println("indicador");
        Indicador indi = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: " + nombre);
        String sql = "FROM Indicador WHERE indiNombre='" + nombre + "'";
        try {
            session.beginTransaction();
            indi = (Indicador) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indic");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return indi;
    }

    /*@Override
     public MovimientoInterno getMoviTram(String codigot) {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }*/
    @Override
    public List getConfDeriv(String oficina) {
        List codigos = new ArrayList();
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createSQLQuery("select mi.MOVI_NUM, \n"
                    + "mi.TRAM_NUM, \n"
                    + "M1.NOMBRE AS ORIGEN, \n"
                    + "M2.NOMBRE AS DESTINO, \n"
                    + "DECODE(to_char(mi.FECHA_ENVIO, 'dd/MM/yyyy HH:MI:SS'),NULL,' ',to_char(mi.FECHA_ENVIO, 'dd/MM/yyyy HH:MI:SS')) AS FECHAENVIO, \n"
                    + "DECODE(to_char(mi.FECHA_INGR, 'dd/MM/yyyy HH:MI:SS'),NULL,' ',to_char(mi.FECHA_INGR, 'dd/MM/yyyy HH:MI:SS')) AS FECHAINGRESO, \n"
                    + "DECODE(mi.MOVI_OBS,NULL,' ',mi.MOVI_OBS) AS MOVI, \n"
                    + "mi.ESTA_NOMBRE, \n"
                    + "IND.INDI_NOMBRE \n"
                    + "from TRAMITE_MOVIMIENTO mi, DEPENDENCIA M1, DEPENDENCIA M2, INDICADOR IND \n"
                    + "where mi.CODIGO=M1.CODIGO \n"
                    + "AND mi.CODIGO1=M2.CODIGO \n"
                    + "and mi.CODIGO='" + oficina + "' \n"
                    + "and mi.INDI_COD=IND.INDI_COD \n"
                    + "AND MI.ESTAD_DERIVADO='derivado'\n"
                    + "group by mi.MOVI_NUM,mi.TRAM_NUM,M1.NOMBRE,M2.NOMBRE,mi.FECHA_ENVIO,mi.FECHA_INGR,mi.MOVI_OBS,mi.ESTA_NOMBRE,IND.INDI_NOMBRE\n"
                    + "ORDER BY mi.FECHA_INGR DESC");
            codigos = query.list();
            session.beginTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("no entro getconfirmadosderivados");
            session.beginTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return codigos;
    }

    @Override
    public String getAnio() {
        String anio="";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(anio) from Anios";
        try{
            session.beginTransaction();
            anio=(String)session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        }catch(Exception e){
            System.out.println("mal indice get anio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return anio;
    }
    
    @Override
    public String getCorre(Usuario usuario, String tipo) {
        System.out.println("get correlativo");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(numerodoc) from DocusExtint where usu='" + usuario.getUsu() + "' and docusExt.iddoc='"+Long.parseLong(tipo) +"' ";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get corre");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public DocusExt getDocuExt(String codigo) {
        System.out.println("get docu ext");
        DocusExt index = null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM DocusExt where iddoc='" + Long.parseLong(codigo) + "'";
        try {
            session.beginTransaction();
            index = (DocusExt) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getdocuext");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public void guardarDocusExt(DocusExtint de) {
        System.out.println("entra a guardardocusext");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(de);
            session.getTransaction().commit();
            System.out.println("terminó guardardocusext");
        } catch (Exception e) {
            System.out.println("mal docus ext");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

}
