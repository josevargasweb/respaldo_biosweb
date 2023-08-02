package com.grupobios.bioslis.microbiologia.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MBExam {

    private String orderId;
    private String examId;
    private String examName;
    private String sample;
    private String sampleId;
    private String bodyPart;
    private String bodyPartId;
    private String processStatus;
    private Date resultDate;
    private Date seedingDate;
    private Date reseedingDate;
    private Boolean isUrgent;
    private String internalNote;
    private String examNote;
    private String footerInformNote;
    private String samplingObservation;
    private String samplingStatus;
    private String samplingUser;
    private Date samplingDate;
    private String receptionObservation;
    private String receptionStatus;
    private String receptionUser;
    private Date receptionDate;
    private String user;
    private List<HashMap<String, String>> events;
    private List<HashMap<String, Object>> optionalTests;
    private HashMap<String, MBTest> tests;
    //agregado x jan
    private String estado;
    private String cultivo;

    private String tipo_cultivo;
    
    private String descripcionEstado;
    //agregado por cristian 09-01  para agregar idusuario
    private String idUsuario;
    private String codigo;

    public MBExam() {
    }

    public MBExam(String orderId, String examId, String examName, String sample, String sampleId, String bodyPart,
            String bodyPartId, String processStatus, Date resultDate, Date seedingDate, Date reseedingDate,
            Boolean isUrgent, String internalNote, String examNote, String footerInformNote, String samplingObservation,
            String samplingStatus, String samplingUser, Date samplingDate, String receptionObservation,
            String receptionStatus, String receptionUser, Date receptionDate, String user,
            List<HashMap<String, String>> events, HashMap<String, MBTest> tests,
            List<HashMap<String, Object>> optionalTests, String cultivo, String estado, String tipo_cultivo,String descripcionEstado, String codigo) {

        this.orderId = orderId;
        this.examId = examId;
        this.examName = examName;
        this.sample = sample;
        this.sampleId = sampleId;
        this.bodyPart = bodyPart;
        this.bodyPartId = bodyPartId;
        this.processStatus = processStatus;
        this.resultDate = resultDate;
        this.seedingDate = seedingDate;
        this.reseedingDate = reseedingDate;
        this.isUrgent = isUrgent;
        this.internalNote = internalNote;
        this.examNote = examNote;
        this.footerInformNote = footerInformNote;
        this.user = user;

        this.samplingObservation = samplingObservation;
        this.samplingStatus = samplingStatus;
        this.samplingUser = samplingUser;
        this.samplingDate = samplingDate;
        this.receptionObservation = receptionObservation;
        this.receptionStatus = receptionStatus;
        this.receptionUser = receptionUser;
        this.receptionDate = receptionDate;

        this.events = events;
        this.tests = tests;
        this.optionalTests = optionalTests;
        //Agregado x jan
        this.cultivo = cultivo;
        this.estado = estado;

        this.tipo_cultivo = tipo_cultivo;
        
        this.descripcionEstado  = descripcionEstado;
        this.codigo = codigo;
        
    }

    public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getipo_cultivo() {
        return this.tipo_cultivo;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getCultivo() {
        return this.cultivo;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getExamId() {
        return this.examId;
    }

    public String getExamName() {
        return this.examName;
    }

    public String getSample() {
        return this.sample;
    }

    public String getSampleId() {
        return this.sampleId;
    }

    public String getBodyPart() {
        return this.bodyPart;
    }

    public String getBodyPartId() {
        return this.bodyPartId;
    }

    public String getProcessStatus() {
        return this.processStatus;
    }

    public Date getResultDate() {
        return this.resultDate;
    }

    public Date getSeedingDate() {
        return this.seedingDate;
    }

    public Date getReseedingDate() {
        return this.reseedingDate;
    }

    public Boolean isUrgent() {
        return this.isUrgent;
    }

    public String getInternalNote() {
        return this.internalNote;
    }

    public void setInternalNote(String note) {
        this._setInternalNote(note, "-");
    }

    public void setInternalNote(String note, String username) {
        this._setInternalNote(note, username);
    }

    private void _setInternalNote(String note, String username) {
        if (!this.internalNote.equals(note)) {
            this.logEvent(username, "Nota Interna", this.internalNote, note);
            this.internalNote = note;
        }
    }

    private void logEvent(String username, String field, String oldValue, String newValue) {
        this.events.add(new HashMap<String, String>() {
            {
                put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                put("user", username);
                put("field", field);
                put("oldValue", oldValue);
                put("newValue", newValue);
            }
        });
    }

    public String getExamNote() {
        return this.examNote;
    }

    public void setExamNote(String note, String username) {
        this._setExamNote(note, username);
    }

    public void setExamNote(String note) {
        this._setExamNote(note, "-");
    }

    private void _setExamNote(String note, String username) {
        if (!this.examNote.equals(note)) {
            this.logEvent(username, "Nota Examen", this.examNote, note);
            this.examNote = note;
        }
    }

    public String getFooterInformNote() {
        return this.footerInformNote;
    }

    public void setFooterInformNote(String note, String username) {
        this._setFooterInformNote(note, username);
    }

    public void setFooterInformNote(String note) {
        this._setFooterInformNote(note, "-");
    }

    private void _setFooterInformNote(String note, String username) {
        if (!this.footerInformNote.equals(note)) {
            this.logEvent(username, "Nota Pie Informe", this.footerInformNote, note);
            this.footerInformNote = note;
        }
    }

    public String getSamplingObservation() {
        return this.samplingObservation;
    }

    public String getSamplingStatus() {
        return this.samplingStatus;
    }

    public String getSamplingUser() {
        return this.samplingUser;
    }

    public Date getSamplingDate() {
        return this.samplingDate;
    }

    public String getReceptionObservation() {
        return this.receptionObservation;
    }

    public String getReceptionStatus() {
        return this.receptionStatus;
    }

    public String getReceptionUser() {
        return this.receptionUser;
    }

    public Date getReceptionDate() {
        return this.receptionDate;
    }

    public String getUser() {
        return this.user;
    }

    public List<HashMap<String, String>> getEvents() {
        return this.events;
    }

    public List<HashMap<String, Object>> getTestList() {
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for (MBTest test : this.tests.values()) {
            result.add(new HashMap<String, Object>() {
                {
                    put("id", test.getId());
                    put("testId", test.getId());
                    put("examId", test.getExamId());
                    put("orderId", test.getOrderId());
                    put("test", test.getTestName());
                    put("result", test.getResult());
                    put("resultType", test.getResultType());
                    put("resultOptions", test.getResultOptions());
                    put("status", test.getStatus());
                    put("statusOptions", test.getStatusOptions());
                    put("microbiologyStatus", test.getMicrobiologyStatus());
                    put("microbiologyStatusOptions", test.getMicrobiologyStatusOptions());
                    put("isCulture", test.getIsCulture());
                    put("labelCode", test.getLabelCode());
                    put("sampleCode", test.getSampleCode());
                }
            });
        }
        return result;
    }

    public List<HashMap<String, Object>> getOptionalTests() {

        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

        for (HashMap<String, Object> optionalTest : this.optionalTests) {
            result.add(new HashMap<String, Object>() {
                {
                    put("id", optionalTest.get("id"));
                    put("name", optionalTest.get("name"));
                    put("selected", optionaTestIsSelected(optionalTest.get("id").toString()));
                }
            });
        }

        return result;
    }

    private Boolean optionaTestIsSelected(String optionalTestId) {
        for (HashMap<String, Object> test : this.getTestList()) {
            if (optionalTestId.equals(test.get("id").toString())) {
                return true;
            }
        }
        return false;
    }

    public MBTest getTest(String testId) {
        return this.tests.get(testId);
    }

    public void setResultDate(Date date, String username) {
        this._setResultDate(date, username);
    }

    public void setResultDate(Date date) {
        this._setResultDate(date, "-");
    }

    private void _setResultDate(Date date, String username) {
        if (!compare(date, this.getResultDate())) {
            this.logEvent(username, "Actualiza fecha de resultado",
                    this.getResultDate() == null ? "-"
                            : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.getResultDate()),
                    date == null ? "-" : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date));

            this.resultDate = date;
        }
    }

    public void setSeedingDate(Date date, String username) {
        this._setSeedingDate(date, username);
    }

    public void setSeedingDate(Date date) {
        this._setSeedingDate(date, "-");
    }

    private void _setSeedingDate(Date date, String username) {
        if (!compare(date, this.getSeedingDate())) {
            this.logEvent(username, "Actualiza fecha de siembra",
                    this.getSeedingDate() == null ? "-"
                            : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.getSeedingDate()),
                    date == null ? "" : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date));

            this.seedingDate = date;
        }
    }

    public void setReseedingDate(Date date, String username) {
        this._setReseedingDate(date, username);
    }

    public void setReseedingDate(Date date) {
        this._setReseedingDate(date, "-");
    }

    private void _setReseedingDate(Date date, String username) {
        if (!compare(date, this.getReseedingDate())) {
            this.logEvent(username, "Actualiza fecha de resiembra",
                    this.getReseedingDate() == null ? "-"
                            : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.getReseedingDate()),
                    date == null ? "-" : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date));
            this.reseedingDate = date;
        }
    }

    private static boolean compare(Date date1, Date date2) {
        return (date1 == null ? date2 == null : date1.equals(date2));
    }

    public HashMap<String, String> getSampleData() {
        HashMap<String, String> result = new HashMap<String, String>();

        MBTest firstTest = this.tests.get(this.getTestList().get(0).get("id"));
        result.put("sampleType", firstTest.getSampleType());
        result.put("samplePrefix", firstTest.getSamplePrefix());
        result.put("bodyPart", firstTest.getBodyPart());
        result.put("bodyPartId", firstTest.getBodyPartId());

        return result;
    }

    public void setOptionalTest(MBTest test, String username) {
        this._setOptionalTest(test, username);
    }

    public void setOptionalTest(MBTest test) {
        this._setOptionalTest(test, "-");
    }

    private void _setOptionalTest(MBTest test, String username) {
        for (HashMap<String, Object> optionalTestsData : this.getOptionalTests()) {
            if (optionalTestsData.get("name").equals(test.getTestName())) {
                if (!this.tests.containsKey(test.getId())) {
                    this.logEvent(username, "Agrega test opcional", "-", test.getTestName());
                    this.tests.put(test.getId(), test);
                }
            }
        }

    }

    public void deleteTest(String testName) {
        this._deleteTest(testName, "-");
    }

    public void deleteTest(String testName, String username) {
        this._deleteTest(testName, username);
    }

    private void _deleteTest(String testName, String username) {
        for (HashMap<String, Object> testData : this.getTestList()) {
            if (testData.get("test").equals(testName)) {
                this.logEvent(username, "Elimina test opcional", testName, "-");
                this.tests.remove(testData.get("id"));
            }
        }
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    
}
