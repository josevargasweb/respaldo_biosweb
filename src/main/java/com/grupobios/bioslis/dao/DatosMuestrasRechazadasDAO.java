/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.MuestraRechazadaDTO;
import com.grupobios.bioslis.entity.DatosMuestrasrechazadas;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Marco Caracciolo
 */
public class DatosMuestrasRechazadasDAO {
    
    private static Logger logger = LogManager.getLogger(DatosUsuariosDAO.class);
    
    public DatosMuestrasrechazadas getMuestraRechazadaById(Long idMuestra) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "from DatosMuestrasrechazadas dmr"
                    + " join fetch dmr.cfgCausasrechazosmuestras crm"
                    + " where dmr.dmrIdmuestra = :idMuestra";
            Query query = sesion.createQuery(sql);
            query.setParameter("idMuestra", idMuestra);
            DatosMuestrasrechazadas muestra = (DatosMuestrasrechazadas) query.setMaxResults(1).uniqueResult();
            return muestra;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void guardarMuestraRechazada(DatosMuestrasrechazadas dmr) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            sesion.save(dmr);
            sesion.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public MuestraRechazadaDTO obtenerMuestraRechazada(Long idMuestra) throws BiosLISDAOException{
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            String sql = "SELECT DFM.DFM_IDMUESTRA, DMR.DMR_IDNUEVAMUESTRA, DFM.DFM_ESTADOTM, DMR.DMR_NOTA, DMR.DMR_IDCAUSARECHAZO, DFM.DFM_NROVECESTOMADA, dfnm.DFM_CODIGOBARRA, dfnm.dfm_estadotm DFM_ESTADONUEVAMUESTRA "
                    + "FROM DATOS_FICHASMUESTRAS DFM "
                    + "LEFT JOIN DATOS_MUESTRASRECHAZADAS DMR ON DMR.DMR_IDMUESTRA = DFM.DFM_IDMUESTRA "
                    + "LEFT JOIN DATOS_FICHASMUESTRAS DFNM ON DFNM.DFM_IDMUESTRA = DMR.DMR_IDNUEVAMUESTRA "
                    + "WHERE DFM.DFM_IDMUESTRA = :idMuestra";
            Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(MuestraRechazadaDTO.class));
            query.setParameter("idMuestra", idMuestra);
            MuestraRechazadaDTO muestra = (MuestraRechazadaDTO) query.uniqueResult();

            return muestra;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
}
