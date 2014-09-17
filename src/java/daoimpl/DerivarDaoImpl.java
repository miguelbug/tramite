/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimpl;

import dao.DerivarDAO;
import java.util.Date;
import java.util.List;
import maping.Dependencia;
import maping.DocusInternos;
import maping.MovimientoInterno;
import maping.TipoDocu;
import maping.TramiteDatos;
import maping.Usuario;
import org.hibernate.classic.Session;
import util.HibernateUtil;

/**
 *
 * @author OGPL
 */
public class DerivarDaoImpl implements DerivarDAO {

    private Session session;

    @Override
    public String getIndice() {
        System.out.println("loginbuscar");
        String index = " ";
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(idTipdocint) from DocusInternos";
        try {
            session.beginTransaction();
            index = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indice");
            session.beginTransaction().rollback();
        }finally {
            session.close(); 
        }
        return index;
    }

    @Override
    public String getSiglas(String ofi) {
        System.out.println("loginbuscar");
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
        }finally {
            session.close(); 
        }
        return index;
    }

    @Override
    public int getMovimiento(String tramnum) {
        System.out.println("loginbuscar");
        int movimiento=0;
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select max(moviNum) from TramiteMovimiento WHERE TRAM_NUM='" + tramnum + "'";
        try {
            session.beginTransaction();
            movimiento = (Integer) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal indice");
            session.beginTransaction().rollback();
            }finally {
            session.close(); 
        }
        return movimiento;
    }

    @Override
    public void InsertarMovimiento(int movimiento, Date fechaenvio, String asunto, String estado, String numtram, String origen, String destino) {
        try{
            System.out.println("entra a guardado insertmovi");
            MovimientoInterno mi = new MovimientoInterno();
            mi.setMoviNumint(movimiento);
            mi.setFechaEnvint(fechaenvio);
            mi.setObsMovint(asunto);
            mi.setEstadInt(estado);
            TramiteDatos td= getTramite(numtram);
            mi.setTramiteDatos(td);
            mi.setDependenciaByCodigo(getDependencia(origen));
            mi.setDependenciaByCodigo1(getDependencia2(destino));
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(mi);
            session.getTransaction().commit();
            System.out.println("terminó movimiento");
        }catch(Exception ex) {
            System.err.println ("falló guardado movimiento." + ex);
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
        }finally {
            session.close(); 
        }
    }

    @Override
    public void InsertarTipoDocus(String aux, String nombre, int pric, String siglas, String anio, String numtram) {
        try{
            System.out.println("entra a guardar tipo docus");
            DocusInternos di= new DocusInternos();
            di.setIdTipdocint(aux);
            di.setDocuNombreint(nombre);
            di.setDocuPricint(String.valueOf(pric));
            di.setDocuSiglasint(siglas);
            di.setDocuAnioint(anio);
            di.setTramiteDatos(getTramite(numtram));
            
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(di);
            session.getTransaction().commit();
            System.out.println("terminó tipodocus");
        }
       catch(Exception ex) {
            System.err.println ("falló guardado tipodocus." + ex);
            session.getTransaction().rollback();
        }finally {
            session.close(); 
        }
    }

    @Override
    public TramiteDatos getTramite(String tramite) {
        System.out.println("loginbuscar");
        TramiteDatos td = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: "+tramite);
        String sql = "FROM TramiteDatos WHERE tramNum='" + tramite + "'";
        try {
            session.beginTransaction();
            td = (TramiteDatos) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("mal");
            System.out.println(e.getMessage());
            }finally {
            session.close(); 
        }
        return td;
    }

    @Override
    public Dependencia getDependencia(String nombre) {
        Dependencia dep = null;
        String nombre2=getCodOficina(nombre);
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
            session.beginTransaction().rollback();
            }finally {
            session.close(); 
        }
        return dep;
    }

    @Override
    public Dependencia getDependencia2(String codigo) {
        System.out.println("depende2");
        Dependencia dep = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: "+codigo);
        String sql = "FROM Dependencia WHERE codigo='" + codigo + "'";
        try {
            session.beginTransaction();
            dep = (Dependencia) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("maldep2");
            session.beginTransaction().rollback();
            }finally {
            session.close(); 
        }
        return dep;
    }

    @Override
    public String getCodOficina(String nombreofi) {
        System.out.println("oficina");
        String ofi = null;
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("impl: "+nombreofi);
        String sql = "Select idOficina FROM Oficina WHERE nombreOficina='" + nombreofi + "'";
        try {
            session.beginTransaction();
            ofi = (String) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            System.out.println("malofi");
            System.out.println(e.getMessage());
            session.beginTransaction().rollback();
            }finally {
            session.close(); 
        }
        return ofi;
    }

    @Override
    public void ConfirmarTramites(String numtram, Date fecha) {
        int i=0;
        System.out.println("entra a confirmar tramites");
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "Update MovimientoInterno set fechaIngrint ='" + fecha + "' where tramiteDatos.tramNum='" + numtram + "'";
        try {
            System.out.println("entra a begin");
            session.beginTransaction();
            i=session.createQuery(sql).executeUpdate();
            session.beginTransaction().commit();
            System.out.println("sale de begin");
        } catch (Exception e) {
            System.out.println("mal confirmar");
            System.out.println(e.getMessage());
            e.printStackTrace();
            }finally {
            session.close(); 
        }
        System.out.println("actualizados: "+i);
    }

}
