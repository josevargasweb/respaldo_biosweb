package com.grupobios.bioslis.dto;

import java.math.BigDecimal;

public class NotificaResultadosDTO {

	private BigDecimal nroOrden;
	private BigDecimal idExamen;
	private BigDecimal idTest;
	private String result;
	private String criticalResult;
	private String physician;
	private String patient;
	private String patientPhone;
	private String physicianPhone;
	private String patientEmail;
	private String physicianEmail;
	private String message;
	private String notificationsSendTo;

	public NotificaResultadosDTO() {
	}

	/**
	 * @return the nroOrden
	 */
	public BigDecimal getNroOrden() {
		return nroOrden;
	}

	/**
	 * @param nroOrden the nroOrden to set
	 */
	public void setNroOrden(BigDecimal nroOrden) {
		this.nroOrden = nroOrden;
	}

	/**
	 * @return the idExamen
	 */
	public BigDecimal getIdExamen() {
		return idExamen;
	}

	/**
	 * @param idExamen the idExamen to set
	 */
	public void setIdExamen(BigDecimal idExamen) {
		this.idExamen = idExamen;
	}

	/**
	 * @return the idTest
	 */
	public BigDecimal getIdTest() {
		return idTest;
	}

	/**
	 * @param idTest the idTest to set
	 */
	public void setIdTest(BigDecimal idTest) {
		this.idTest = idTest;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the criticalResult
	 */
	public String getCriticalResult() {
		return criticalResult;
	}

	/**
	 * @param criticalResult the criticalResult to set
	 */
	public void setCriticalResult(String criticalResult) {
		this.criticalResult = criticalResult;
	}

	/**
	 * @return the physician
	 */
	public String getPhysician() {
		return physician;
	}

	/**
	 * @param physician the physician to set
	 */
	public void setPhysician(String physician) {
		this.physician = physician;
	}

	/**
	 * @return the patient
	 */
	public String getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(String patient) {
		this.patient = patient;
	}

	/**
	 * @return the patientPhone
	 */
	public String getPatientPhone() {
		return patientPhone;
	}

	/**
	 * @param patientPhone the patientPhone to set
	 */
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	/**
	 * @return the physicianPhone
	 */
	public String getPhysicianPhone() {
		return physicianPhone;
	}

	/**
	 * @param physicianPhone the physicianPhone to set
	 */
	public void setPhysicianPhone(String physicianPhone) {
		this.physicianPhone = physicianPhone;
	}

	/**
	 * @return the patientEmail
	 */
	public String getPatientEmail() {
		return patientEmail;
	}

	/**
	 * @param patientEmail the patientEmail to set
	 */
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	/**
	 * @return the physicianEmail
	 */
	public String getPhysicianEmail() {
		return physicianEmail;
	}

	/**
	 * @param physicianEmail the physicianEmail to set
	 */
	public void setPhysicianEmail(String physicianEmail) {
		this.physicianEmail = physicianEmail;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the notificationsSendTo
	 */
	public String getNotificationsSendTo() {
		return notificationsSendTo;
	}

	/**
	 * @param notificationsSendTo the notificationsSendTo to set
	 */
	public void setNotificationsSendTo(String notificationsSendTo) {
		this.notificationsSendTo = notificationsSendTo;
	}

}
