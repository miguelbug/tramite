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
import maping.Oficina;
import maping.Proveido;
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
    public String getDocExt(String n) {
        System.out.println("get doc ext");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(docuCorrela) from DocusInternos where docuSiglasint='" + n + "' and docuNombreint='" + n + "'";
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
    public String getIndice(String tramnum, String td) {
        System.out.println("getindice");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(docuCorrelaint) from DocusInternos where docuSiglasint='" + tramnum + "' and docuNombreint='" + td + "'";
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
    public String getCorrelativoOficio() {
        System.out.println("getCorreOficio");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correlativoOficio) from Oficios";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getCorreOficio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public String getSiglas(String ofi, String usu) {
        System.out.println("oficina: " + ofi);
        System.out.println("getssiglas");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select ofi.siglas\n"
                + "from Oficina ofi, Usuario usua\n"
                + "where usua.oficina.idOficina='" + ofi + "'\n"
                + "and usua.oficina.idOficina=ofi.idOficina\n"
                + "and usua.usu='" + usu + "'";
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
        String sql = "select ofi.siglas\n"
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
    public void ActualizarTramite(String tramaux, String movimiento) {
        System.out.println("actualizar tramite movimiento");
        String sql = "Update TRAMITE_MOVIMIENTO SET ESTAD_CONFRIRM='DERIVADO' , ESTA_NOMBRE='FINALIZADO' WHERE TRAM_NUM='" + tramaux + "' AND MOVI_NUM='" + Integer.parseInt(movimiento) + "'";
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
        System.out.println("se ha actualizado: " + i);
    }

    @Override
    public void InsertarMovimiento(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino, Indicador i) {
        try {
            System.out.println(movimiento + " " + fechaenvio + " " + asunto + " " + estado + " " + numtram + " " + origen + " " + destino + " " + i);
            System.out.println("entra a guardado insertmovi");
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
            tm.setEstadConfrirm("EN PROCESO");
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
        } catch (Exception ex) {
            System.err.println("falló guardado movimiento." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas, String anio, String numtram, Date fecharegistro, Usuario usu, String asunto) {
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
            di.setAsunto(asunto);
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
        Dependencia dep = null;
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
    public String getCodigoDep(String nombdepe) {
        System.out.println("codigo depe");
        String codigo=null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT CODIGO FROM DEPENDENCIA WHERE NOMBRE='"+nombdepe+"'";
        try {
            session.beginTransaction();
            codigo = String.valueOf(session.createSQLQuery(sql).uniqueResult());
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal codigo depe");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return codigo;
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
    public List getUsu(String nombre) {
        System.out.println("entra a getusu");
        List<String> usu = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT USU,USU_NOMBRE,CLAVE,ESTADO,ID_OFICINA FROM USUARIO WHERE USU_NOMBRE='" + nombre + "'";
        try {
            session.beginTransaction();
            usu= (List<String>)session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal usu");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usu;
    }

    @Override
    public Usuario transformar(String nombre) {
        List<String> lista= (List<String>)getUsu(nombre);
        System.out.println(lista);
        Usuario usua= new Usuario();
        usua.setUsu(String.valueOf(lista.get(0)));
        usua.setUsuNombre(String.valueOf(lista.get(1)));
        usua.setClave(String.valueOf(lista.get(2)));
        usua.setEstado(String.valueOf(lista.get(3)));
        usua.setOficina(getOficina(String.valueOf(lista.get(4))));
        return usua;
    }

    @Override
    public TramiteMovimiento getTramiteMovimiento(String numtram, String movi) {
        System.out.println("entra a tm");
        TramiteMovimiento tm= null;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM TramiteMovimiento where tramiteDatos.tramNum='"+numtram+"' and moviNum='"+Short.valueOf(movi)+"'";
        try {
            session.beginTransaction();
            tm=(TramiteMovimiento) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get tm");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tm;
    }

    @Override
    public void ActualizarUsuario(String tramnum, String movi, Usuario usua) {
        System.out.println("entra a actualizar");
        TramiteMovimiento tm=getTramiteMovimiento(tramnum,movi);
        tm.setUsuario(usua);
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(tm);
            session.beginTransaction().commit();
        }
        catch(Exception e) {
            System.out.println("mal confirmar");
            System.out.println(e.getMessage());
        }finally {
            session.close();
        }
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
        String sql = "Update TramiteMovimiento set estadConfrirm='CONFIRMADO',"
                + " fechaIngr=to_date('" + fechita + "','DD/MM/YYYY HH24:MI:SS') where tramiteDatos.tramNum='" + numtram + "' and moviNum='" + Short.parseShort(String.valueOf(movimiento)) + "'";
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
                    + "AND MI.ESTAD_CONFRIRM='DERIVADO'\n"
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
        String anio = "";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(anio) from Anios";
        try {
            session.beginTransaction();
            anio = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indice get anio");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return anio;
    }

    @Override
    public void GuardarProveido(Proveido p) {
        System.out.println("entra a guardar proevido");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
            System.out.println("terminó guardar proveido");
        } catch (Exception e) {
            System.out.println("mal proveido");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public String getCorreProv() {
        System.out.println("get proveido");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(correlativod) from Proveido";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal get corre prov");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return index;
    }

    @Override
    public String getCorre(Usuario usuario, String tipo) {
        System.out.println("get correlativo");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(numerodoc) from DocusExtint where usu='" + usuario.getUsu() + "' and docusExt.iddoc='" + Long.parseLong(tipo) + "' ";
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

    @Override
    public List listandoUsuario(String nombdepe) {
        System.out.println("get usuario");
        List<String> index = new ArrayList<String>();
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT USUA.USU, USUA.USU_NOMBRE, USUA.CLAVE, USUA.ESTADO, USUA.ID_OFICINA FROM USUARIO USUA, JEFATURA JEFA\n"
                + "WHERE USUA.ID_OFICINA='"+nombdepe+"' AND USUA.ESTADO='activo' AND USUA.USU=JEFA.USU AND JEFA.CARGO='JEFE DE ÁREA'";
        try {
            session.beginTransaction();
            index = (List<String>) session.createSQLQuery(sql).list();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal getusuario");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        System.out.println(index);
        return index;
    }

    @Override
    public Oficina getOficina(String nombre) {
        Oficina ofi=null;
        System.out.println("getofics");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "FROM Oficina where idOficina='"+nombre+"'";
        try{
            session.beginTransaction();
            ofi = (Oficina) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        }catch(Exception e){
            System.out.println("mal getofics");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
        } finally {
            session.close();
        }
        return ofi;
    }

    @Override
    public Usuario getUsuario(String oficina) {
        List<String> lista=listandoUsuario(oficina);
        System.out.println(lista);
        Usuario usu= new Usuario();
        usu.setUsu(String.valueOf(lista.get(0)));
        usu.setUsuNombre(String.valueOf(lista.get(1)));
        usu.setClave(String.valueOf(lista.get(2)));
        usu.setEstado(String.valueOf(lista.get(3)));
        usu.setOficina(getOficina(String.valueOf(lista.get(4))));
        return usu;
    }

}
