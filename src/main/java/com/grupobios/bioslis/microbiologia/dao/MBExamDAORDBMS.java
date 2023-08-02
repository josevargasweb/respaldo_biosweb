package com.grupobios.bioslis.microbiologia.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBTest;

public class MBExamDAORDBMS implements MBExamDAO {
    
    static Logger log = LogManager.getLogger(MBExamDAORDBMS.class.getName());

	private String sqlQuerySelect = "SELECT BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN, BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN, BIOSLIS.CFG_EXAMENES.CE_ABREVIADO,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTIPOMUESTRA, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDMUESTRA, BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDBACESTADO,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHAINICIO, BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHAINICIO2,  BIOSLIS.DATOS_FICHASEXAMENES.DFE_EXAMENESURGENTE,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAINTERNA, BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAEXAMEN, BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAINFORME,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDUSUARIO, BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHARESULTADO, BIOSLIS.CFG_EXAMENES.CE_ESCULTIVO,BIOSLIS.DATOS_FICHASEXAMENES.DFE_CODIGOESTADOEXAMEN  "
			+ ", NVL(CFG_BACGRUPOSTEST.CBGT_DESCRIPCION, ' ') ,CFG_ESTADOSEXAMENES.CEE_DESCRIPCIONESTADOEXAMEN, BIOSLIS.CFG_EXAMENES.CE_CODIGOEXAMEN  "
                        + "FROM BIOSLIS.DATOS_FICHASEXAMENES  INNER JOIN BIOSLIS.CFG_EXAMENES  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN = BIOSLIS.CFG_EXAMENES.CE_IDEXAMEN  "
			+ "INNER JOIN BIOSLIS.DATOS_FICHASEXAMENESTEST  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN AND BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN  "
			+ "left JOIN BIOSLIS.CFG_MUESTRAS  "
			+ "ON  BIOSLIS.CFG_EXAMENES.CE_IDTIPOMUESTRA = BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA  "
			+ "left JOIN BIOSLIS.CFG_TEST  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = BIOSLIS.CFG_TEST.CT_IDTEST "
			+ "left JOIN BIOSLIS.CFG_BACGRUPOSTEST  "
			+ "ON CFG_TEST.CT_IDBACGRUPOTEST =  BIOSLIS.CFG_BACGRUPOSTEST.CBGT_IDBACGRUPOTEST  "
			+ " LEFT  JOIN CFG_ESTADOSEXAMENES "
			+ "	ON CFG_ESTADOSEXAMENES.CEE_CODIGOESTADOEXAMEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_CODIGOESTADOEXAMEN  "
			+ "WHERE BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN = :orderId AND BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN = :examId";
	private String sqlQuerySelect_Estados_Sin_Examenid = "SELECT BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN, BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN, BIOSLIS.CFG_EXAMENES.CE_ABREVIADO,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTIPOMUESTRA, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDMUESTRA, BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDBACESTADO,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHAINICIO, BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHAINICIO2,  BIOSLIS.DATOS_FICHASEXAMENES.DFE_EXAMENESURGENTE,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAINTERNA, BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAEXAMEN, BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAINFORME,  "
			+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDUSUARIO, BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHARESULTADO, BIOSLIS.CFG_EXAMENES.CE_ESCULTIVO,BIOSLIS.DATOS_FICHASEXAMENES.DFE_CODIGOESTADOEXAMEN  "
			+ ",CFG_BACGRUPOSTEST.CBGT_DESCRIPCION,CFG_ESTADOSEXAMENES.CEE_DESCRIPCIONESTADOEXAMEN, BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDSECCION "
			+ "FROM BIOSLIS.DATOS_FICHASEXAMENES  INNER JOIN BIOSLIS.CFG_EXAMENES  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN = BIOSLIS.CFG_EXAMENES.CE_IDEXAMEN  "
			+ "INNER JOIN BIOSLIS.DATOS_FICHASEXAMENESTEST  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN AND BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN  "
			+ "INNER JOIN BIOSLIS.CFG_MUESTRAS  "
			+ "ON  BIOSLIS.CFG_EXAMENES.CE_IDTIPOMUESTRA = BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA  "
			+ "INNER JOIN BIOSLIS.DATOS_FICHASEXAMENESTEST  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN = BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN AND BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN = BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN  "
			+ "INNER JOIN BIOSLIS.CFG_TEST  "
			+ "ON BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST = BIOSLIS.CFG_TEST.CT_IDTEST "
			+ "LEFT JOIN BIOSLIS.CFG_BACGRUPOSTEST  "
			+ "ON CFG_TEST.CT_IDBACGRUPOTEST =  BIOSLIS.CFG_BACGRUPOSTEST.CBGT_IDBACGRUPOTEST "
			+ " LEFT  JOIN CFG_ESTADOSEXAMENES "
			+ "	ON CFG_ESTADOSEXAMENES.CEE_CODIGOESTADOEXAMEN = BIOSLIS.DATOS_FICHASEXAMENES.DFE_CODIGOESTADOEXAMEN  "
			+ "WHERE BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN = :orderId ";

	private String sqlQueryGetEvents = "SELECT BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO, BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDUSUARIO, BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO, "
			+ "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR, BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO "
			+ "FROM BIOSLIS.LOG_EVENTOSFICHAS "
			+ "WHERE BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN = :orderId AND BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEXAMEN = :examId";

	@Override
	public MBExam get_examenes_por_orden(int orderId) {

		MBExam result = null;
		Query query;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			sesion.beginTransaction();

			query = sesion.createSQLQuery(this.sqlQuerySelect_Estados_Sin_Examenid);
			query.setParameter("orderId", orderId);

			List<Object[]> lista = query.list();

			if (!lista.isEmpty()) {

				Object[] examData = (Object[]) (lista.get(0));

				Date resultDate = null;
				Date seedingDate = null;
				Date reseedingDate = null;
				try {
					if (examData[6] != null) {
						seedingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examData[6].toString());
					}
					if (examData[7] != null) {
						reseedingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examData[7].toString());
					}
					if (examData[13] != null) {
						resultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examData[13].toString());
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				HashMap<String, Object> sampleData = getSampleDataAndObservations((BigDecimal) examData[4]);
				
				result = new MBExam(examData[0].toString(), examData[1].toString(), examData[2].toString(),
						examData[3] == null ? "" : decodeSample((BigDecimal) examData[3]),
						examData[3] == null ? "" : examData[3].toString(),
						examData[4] == null ? "" : decodeBodyPart((BigDecimal) examData[4]),
						examData[4] == null ? "" : decodeBodyPartId((BigDecimal) examData[4]),
						examData[5] == null ? "" : decodeExamStatus((BigDecimal) examData[5]), resultDate, seedingDate,
						reseedingDate, decodeIsUrgent((String) examData[8]),
						examData[9] == null ? "" : (String) examData[9],
						examData[10] == null ? "" : (String) examData[10],
						examData[11] == null ? "" : (String) examData[11],
						sampleData.get("samplingObservation").toString(), sampleData.get("samplingStatus").toString(),
						sampleData.get("samplingUser").toString(), (Date) sampleData.get("samplingDate"),
						sampleData.get("receptionObservation").toString(), sampleData.get("receptionStatus").toString(),
						sampleData.get("receptionUser").toString(), (Date) sampleData.get("receptionDate"),
						getUserName((BigDecimal) examData[12]), getEventsForExam(orderId, 1),
						getTestsForExam(orderId, 1), getOptionalTests(1),
						examData[14].toString() == null ? "" : examData[14].toString(),
						examData[15].toString() == null ? "" : examData[15].toString(),
						examData[16].toString() == null ? "" : examData[16].toString(),
						examData[17].toString() == null ? "" : examData[17].toString(), 
						examData[18].toString() == null ? "" : examData[18].toString());
			}

		} finally {
			sesion.close();
		}

		return result;
	}

	@Override
	public MBExam getExamByIds(int orderId, int examId) {	
		MBExam result = null;
		Query query;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
		    query = sesion.createSQLQuery(this.sqlQuerySelect);
			query.setParameter("orderId", orderId);
			query.setParameter("examId", examId);

			List<Object[]> lista = query.list();
	            sesion.close();

			if (!lista.isEmpty()) {

				Object[] examData = (Object[]) (lista.get(0));

				Date resultDate = null;
				Date seedingDate = null;
				Date reseedingDate = null;
				try {
					if (examData[6] != null) {
						seedingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examData[6].toString());
					}
					if (examData[7] != null) {
						reseedingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examData[7].toString());
					}
					if (examData[13] != null) {
						resultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examData[13].toString());
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				HashMap<String, Object> sampleData = getSampleDataAndObservations((BigDecimal) examData[4]);
				 
			
				result = new MBExam(examData[0].toString(), examData[1].toString(), examData[2].toString(),
						examData[3] == null ? "" : decodeSample((BigDecimal) examData[3]),
						examData[3] == null ? "" : examData[3].toString(),
						examData[4] == null ? "" : decodeBodyPart((BigDecimal) examData[4]),
						examData[4] == null ? "" : decodeBodyPartId((BigDecimal) examData[4]),
						examData[5] == null ? "" : decodeExamStatus((BigDecimal) examData[5]), resultDate, seedingDate,
						reseedingDate, decodeIsUrgent((String) examData[8]),
						examData[9] == null ? "" : (String) examData[9],
						examData[10] == null ? "" : (String) examData[10],
						examData[11] == null ? "" : (String) examData[11],
						sampleData.get("samplingObservation").toString(), sampleData.get("samplingStatus").toString(),
						sampleData.get("samplingUser") == null ? "" : sampleData.get("samplingUser").toString()  , 
						(Date) sampleData.get("samplingDate"),					
						sampleData.get("receptionObservation").toString(), sampleData.get("receptionStatus").toString(),
						sampleData.get("receptionUser") == null ? "" : sampleData.get("receptionUser").toString(),
						(Date) sampleData.get("receptionDate"),
						getUserName((BigDecimal) examData[12]), getEventsForExam(orderId, examId),
						getTestsForExam(orderId, examId), getOptionalTests(examId),
						examData[14].toString() == null ? "" : examData[14].toString(),
						examData[15].toString() == null ? "" : examData[15].toString(),
						examData[16].toString() == null ? "" : examData[16].toString(),
						examData[17].toString() == null ? "" : examData[17].toString(),
                                                examData[18].toString() == null ? "" : examData[18].toString());
			}
		}catch(Exception e) {
		    log.error("error en MBEXAM " + e.getMessage());
		    e.printStackTrace();
		    if(sesion.isOpen()) {
		        sesion.close();
		    }
		} 
	
		return result;
	}

	@Override
	public void update(MBExam exam) {
		this.updateNotes(exam);
		this.updateDates(exam);
		this.updateEvents(exam);
	}

	private void updateDates(MBExam exam) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENES "
					+ "SET BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHAINICIO = :seedingDate, "
					+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHAINICIO2 = :reseedingDate, "
					+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_BACFECHARESULTADO = :resultDate "
					+ "WHERE BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN = :idOrder AND BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN = :idExam");
			query.setTimestamp("seedingDate", exam.getSeedingDate());
			query.setTimestamp("reseedingDate", exam.getReseedingDate());
			query.setTimestamp("resultDate", exam.getResultDate());
			query.setParameter("idOrder", Integer.parseInt(exam.getOrderId()));
			query.setParameter("idExam", Integer.parseInt(exam.getExamId()));
			query.executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	private void updateEvents(MBExam exam) {
		for (HashMap<String, String> event : exam.getEvents()) {
			if (this.eventIsNotRegistered(event, Integer.parseInt(exam.getOrderId()),			        
					Integer.parseInt(exam.getExamId()))) {
			    event.put("user", exam.getIdUsuario()); 
				this.registerEvent(event, Integer.parseInt(exam.getOrderId()), Integer.parseInt(exam.getExamId()));
			}
		}
	}

	private Boolean eventIsNotRegistered(HashMap<String, String> event, int orderId, int examId) {
		Session sesion = HibernateUtil.getSessionFactory().openSession();

		Date eventDate = null;
		try {
			eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Boolean result = false;
		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("SELECT * " + "FROM BIOSLIS.LOG_EVENTOSFICHAS "
					+ "WHERE BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN = :orderId AND BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEXAMEN = :examId AND "
					+ "BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDTEST IS NULL AND BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO = :eventDate AND "
					+ "BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO = :fieldName AND "
					+ "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR = :oldValue AND "
					+ "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO = :newValue");
			query.setParameter("orderId", orderId);
			query.setParameter("examId", examId);
			query.setTimestamp("eventDate", eventDate);
			query.setString("fieldName", event.get("field"));
			query.setString("oldValue", event.get("oldValue"));
			query.setString("newValue", event.get("newValue"));
			result = query.list().size() == 0;
		} finally {
			sesion.close();
		}
		return result;
	}

	private void registerEvent(HashMap<String, String> event, int orderId, int examId) {
		Session session = HibernateUtil.getSessionFactory().openSession();

        
		Date eventDate = null;
		try {
			eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//int userId = this.getUserId(event.get("user"));

        int userId = Integer.parseInt(event.get("user")); 
        DatosFichas df = new DatosFichas();
        df = (DatosFichas) session.get(DatosFichas.class ,orderId);
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("INSERT INTO BIOSLIS.LOG_EVENTOSFICHAS "
                    + "(BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDLOGEVENTOFICHA, BIOSLIS.LOG_EVENTOSFICHAS.LEF_NORDEN,"
                    + " BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEXAMEN,BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDPACIENTE , "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAORDEN , BIOSLIS.LOG_EVENTOSFICHAS.LEF_FECHAREGISTRO,"
                    + " BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDEVENTO , BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRETABLA , "
                    + (userId == -1 ? "" : "BIOSLIS.LOG_EVENTOSFICHAS.LEF_IDUSUARIO, ")
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_NOMBRECAMPO, " + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORANTERIOR, "
                    + "BIOSLIS.LOG_EVENTOSFICHAS.LEF_VALORNUEVO) "
                    + "VALUES (BIOSLIS.SEQ_LOG_EVENTOSFICHAS.NEXTVAL, :orderId, :examId, :idPaciente ,"
                    + " :fechaOrden, :eventDate, :idEvento , :nombreTabla , "
                    + (userId == -1 ? "" : ":userId, ") + ":field, :oldValue, :newValue)");

			query.setParameter("orderId", orderId);
            query.setParameter("examId", examId);
            query.setParameter("idPaciente", df.getDatosPacientes());
            query.setTimestamp("fechaOrden", df.getDfFechaorden());
            query.setTimestamp("eventDate", eventDate);
            query.setParameter("idEvento", 2);
            query.setParameter("nombreTabla", "DATOS_FICHASEXAMENES");
            
			if (userId != -1) {
				query.setParameter("userId", userId);
			}
			query.setString("field", event.get("field"));
			query.setString("oldValue", event.get("oldValue"));
			query.setString("newValue", event.get("newValue"));

			query.executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}

	}

	private void updateNotes(MBExam exam) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("UPDATE BIOSLIS.DATOS_FICHASEXAMENES "
					+ "SET BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAINTERNA = :internalNote, "
					+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAEXAMEN = :examNote, "
					+ "BIOSLIS.DATOS_FICHASEXAMENES.DFE_NOTAINFORME = :footerInformNote "
					+ "WHERE BIOSLIS.DATOS_FICHASEXAMENES.DFE_NORDEN = :idOrder AND BIOSLIS.DATOS_FICHASEXAMENES.DFE_IDEXAMEN = :idExam");
			query.setString("internalNote", exam.getInternalNote());
			query.setString("examNote", exam.getExamNote());
			query.setString("footerInformNote", exam.getFooterInformNote());
			query.setParameter("idOrder", Integer.parseInt(exam.getOrderId()));
			query.setParameter("idExam", Integer.parseInt(exam.getExamId()));
			query.executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	private Boolean decodeIsUrgent(String flag) {
		if (flag == null || !"S".equals(flag)) {
			return false;
		} else {
			return true;
		}
	}

	private String decodeBodyPart(BigDecimal sampleId) {
	    Session sesion = HibernateUtil.getSessionFactory().openSession();
		String result = null;
		try {	
			Query query = sesion.createSQLQuery("SELECT BIOSLIS.CFG_BACZONACUERPO.CBZC_DESCRIPCION "
					+ "FROM BIOSLIS.DATOS_FICHASMUESTRAS " + "INNER JOIN BIOSLIS.CFG_BACZONACUERPO "
					+ "ON BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDZONACUERPO = BIOSLIS.CFG_BACZONACUERPO.CBZC_IDZONACUERPO "
					+ "WHERE BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDMUESTRA = :sampleId");
			query.setParameter("sampleId", sampleId);
			result = (String) query.uniqueResult();
			/*
			if (query.list().size() > 0 && query.list().get(0) != null) {
				result = (String) (query.list().get(0));
			}
			*/
		}catch(Exception e) {
		    log.error("error en decodeBodyPart" +e.getMessage());
		    e.printStackTrace();
		} finally {
		sesion.close();
		}
		return result == null ? "-" : result;
	}

	private String decodeBodyPartId(BigDecimal sampleId) {	
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		BigDecimal resu = null;
		String result = null;
		try {
		   
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery(
					"SELECT DFM_IDZONACUERPO FROM BIOSLIS.DATOS_FICHASMUESTRAS WHERE DFM_IDMUESTRA = :sampleId");
			query.setParameter("sampleId", sampleId);
			resu =  (BigDecimal) query.uniqueResult() ;
			/*
			if (query.list().size() > 0 && query.list().get(0) != null) {
				result = (query.list().get(0)).toString();
			}
			*/
		}catch(Exception e){
		    log.error("Error en decodeBodyPartId " + e.getMessage());
		    e.printStackTrace();
		} finally {
			sesion.close();		
		}
		
		if(resu != null) {
		    result = resu.toString();
		}
		
		return result == null ? "" : result;
	}

	private String decodeSample(BigDecimal sampleId) {
		if (sampleId == null) {
			return "-";
		}

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		String result = null;
		try {
		
			Query query = sesion.createSQLQuery("SELECT BIOSLIS.CFG_MUESTRAS.CMUE_DESCRIPCIONABREVIADA "
					+ "FROM BIOSLIS.CFG_MUESTRAS " + "WHERE BIOSLIS.CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = :sampleId");
			query.setParameter("sampleId", sampleId);
			
			result = (String) query.uniqueResult();
			/*
			if (query.list().size() > 0 && query.list().get(0) != null) {
				result = (String) (query.list().get(0));
			}
			*/
		}catch(Exception e) {
		    log.error("error en decodeSample" +e.getMessage());
		} finally {
			sesion.close();	
		}
		return result == null ? "-" : result;
	}

	private String decodeExamStatus(BigDecimal statusFlag) {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		String result = null;
		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("SELECT BIOSLIS.CFG_BACESTADOS.CBE_DESCRIPCION "
					+ "FROM BIOSLIS.CFG_BACESTADOS " + "WHERE BIOSLIS.CFG_BACESTADOS.CBE_IDBACESTADO = :statusId");
			query.setParameter("statusId", statusFlag);
			if (query.list().size() > 0 && query.list().get(0) != null) {
				result = (String) (query.list().get(0));
			}
		} finally {
			sesion.close();
		}
		return result == null ? "-" : result;
	}

	private HashMap<String, Object> getSampleDataAndObservations(BigDecimal sampleId) {
		HashMap<String, Object> result = new HashMap<String, Object>() {
			{
				put("samplingObservation", "");
				put("samplingStatus", "");
				put("samplingUser", "");
				put("samplingDate", null);
				put("receptionObservation", "");
				put("receptionStatus", "");
				put("receptionUser", "");
				put("receptionDate", null);
			}
		};

		if (sampleId != null) {	
			Session sesion = HibernateUtil.getSessionFactory().openSession();
			try {
			Query query = sesion.createSQLQuery(
						"SELECT BIOSLIS.DATOS_FICHASMUESTRAS.DFM_TMOBSERVACION, BIOSLIS.DATOS_FICHASMUESTRAS.DFM_ESTADOTM, BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDUSUARIOTM, BIOSLIS.DATOS_FICHASMUESTRAS.DFM_FECHATM, "
								+ "BIOSLIS.DATOS_FICHASMUESTRAS.DFM_RECEPCIONOBS, BIOSLIS.DATOS_FICHASMUESTRAS.DFM_ESTADORM, BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDUSUARIORM, BIOSLIS.DATOS_FICHASMUESTRAS.DFM_FECHARM "
								+ "FROM BIOSLIS.DATOS_FICHASMUESTRAS "
								+ "WHERE BIOSLIS.DATOS_FICHASMUESTRAS.DFM_IDMUESTRA = :sampleId");
			
				query.setParameter("sampleId", sampleId);
				
				Object[] data = (Object[]) query.list().get(0);
				sesion.close();
		
				if (data != null) {
				 
				   
				    
					//Object[] data = (Object[]) query.list().get(0);
					result.put("samplingObservation", data[0] == null ? "" : data[0].toString());
					result.put("samplingStatus", decodeSampligStatus((String) data[1]));
					result.put("samplingUser", getUserName((BigDecimal) data[2]));
					result.put("samplingDate",  (Date) data[3]);
					result.put("receptionObservation", data[4] == null ? "" : data[4].toString());
					result.put("receptionStatus", decodeReceptionStatus((String) data[5]));
					result.put("receptionUser", getUserName((BigDecimal) data[6]));
					result.put("receptionDate", (Date) data[7]);					
				}
			}catch(Exception e) {
			    log.error("error en getSampleDataAndObservations" + e.getMessage());
			    if(sesion.isOpen()) {			        
			        sesion.close();
			    }
			}
		}

		return result;
	}

	private String getUserName(BigDecimal userId) {
		if (userId == null) {
			return "";
		}

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		String result = "";
		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("SELECT "
			        + " CONCAT( "
			        + "  CONCAT( "
			        + "  CONCAT(BIOSLIS.DATOS_USUARIOS.DU_NOMBRES , ' ' ) , "
			        + "  CONCAT(BIOSLIS.DATOS_USUARIOS.DU_PRIMERAPELLIDO,' ' ) "
			        + "  ), CONCAT(BIOSLIS.DATOS_USUARIOS.DU_SEGUNDOAPELLIDO,' ' )) "
					+ "FROM BIOSLIS.DATOS_USUARIOS " + "WHERE BIOSLIS.DATOS_USUARIOS.DU_IDUSUARIO = :userId");
			query.setParameter("userId", userId);
			
			result = (String) query.uniqueResult();
			/*
			if (query.list().size() > 0) {
				result = (String) (query.list().get(0));
			}
			*/
		}catch(Exception e) {
		    log.error("error en getUserName " +e.getMessage() );
		    e.printStackTrace();
		    return "";
		} finally {
		
			sesion.close();
		}
		return result;
	}

	private int getUserId(String username) {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		int result = -1;
		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("SELECT BIOSLIS.DATOS_USUARIOS.DU_IDUSUARIO "
					+ "FROM BIOSLIS.DATOS_USUARIOS " + "WHERE BIOSLIS.DATOS_USUARIOS.DU_USUARIO = :username");
			query.setString("username", username);
			if (query.list().size() > 0) {
				result = ((BigDecimal) query.list().get(0)).intValue();
			}
		} finally {
			sesion.close();
		}
		return result;
	}

	private String decodeSampligStatus(String flag) {
		if ("T".equals(flag)) {
			return "TOMADA";
		}
		if ("B".equals(flag)) {
			return "BLOQUEADA";
		}
		if ("P".equals(flag)) {
			return "PENDIENTE";
		}
		return "-";
	}

	private String decodeReceptionStatus(String flag) {
		if ("R".equals(flag)) {
			return "RECEPCIONADA";
		}
		if ("P".equals(flag)) {
			return "PENDIENTE";
		}
		return "-";
	}

	private List<HashMap<String, String>> getEventsForExam(int orderId, int examId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {

			Query query = session.createSQLQuery(this.sqlQueryGetEvents);
			query.setParameter("orderId", orderId);
			query.setParameter("examId", examId);
			for (Object event : query.list()) {
				result.add(parseEventData(event));
			}
		}catch(Exception e) {
		    log.error("error en getEventsForExam " +e.getMessage());
		    e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	private HashMap<String, String> parseEventData(Object event) {
		Object[] eventData = (Object[]) event;
		HashMap<String, String> result = new HashMap<String, String>() {
			{
				put("date", eventData[0] == null ? "-" : eventData[0].toString());
				put("user", eventData[1] == null ? "-" : getUserName((BigDecimal) eventData[1]));
				put("field", eventData[2] == null ? "-" : eventData[2].toString());
				put("oldValue", eventData[3] == null ? "-" : eventData[3].toString());
				put("newValue", eventData[4] == null ? "-" : eventData[4].toString());
			}
		};
		return result;
	}

	public List<HashMap<String, Object>> getOptionalTests(int examId) {

		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			Query query = session
					.createSQLQuery("SELECT BIOSLIS.CFG_EXAMENESTEST.CET_IDTEST, BIOSLIS.CFG_TEST.CT_ABREVIADO "
							+ "FROM BIOSLIS.CFG_EXAMENESTEST " + "INNER JOIN BIOSLIS.CFG_TEST "
							+ "ON BIOSLIS.CFG_EXAMENESTEST.CET_IDTEST = BIOSLIS.CFG_TEST.CT_IDTEST "
							+ "WHERE BIOSLIS.CFG_EXAMENESTEST.CET_IDEXAMEN = :examId AND BIOSLIS.CFG_TEST.CT_OPCIONAL = 'S'");
			query.setParameter("examId", examId);
			for (Object optionalTest : query.list()) {
				Object[] optionalTestData = (Object[]) optionalTest;
				result.add(new HashMap<String, Object>() {
					{
						put("id", optionalTestData[0].toString());
						put("name", optionalTestData[1].toString());
					}
				});
			}
		}catch(Exception e) {
		    log.error("error en getOptionalTests " +e.getMessage());
		    e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	private HashMap<String, MBTest> getTestsForExam(int orderId, int examId) {
	    
		MBTestDAO dao = (new DAOFactory()).getDAO("MBTest");
		HashMap<String, MBTest> result = new HashMap<String, MBTest>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		String sqlQueryGetTests = "SELECT BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDTEST "
				+ "FROM BIOSLIS.DATOS_FICHASEXAMENESTEST "
				+ "WHERE BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_NORDEN = :orderId AND BIOSLIS.DATOS_FICHASEXAMENESTEST.DFET_IDEXAMEN = :examId";
		try {
		
			Query query = session.createSQLQuery(sqlQueryGetTests);
			query.setParameter("orderId", orderId);
			query.setParameter("examId", examId);
			 List<Object[]> lista = query.list();
			 session.close();

			for (Object testId : lista) {
				MBTest test = dao.getTestByIds(orderId, examId, ((BigDecimal) testId).intValue());
				result.put(testId.toString(), test);
			}
		}catch(Exception e) {
		    if(session.isOpen()) {
		        session.close();
		    }
		    e.printStackTrace();
		}
		return result;
	}
}
