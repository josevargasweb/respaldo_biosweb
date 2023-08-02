package com.grupobios.bioslis.microbiologia.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class MBTest {
    private String testId;
    private String examId;
    private String orderId;
    private String testName;
    private String result;
    private String resultType;
    private List<HashMap<String, Object>> resultOptions;
    private String status;
    private List<String> statusOptions;
    private String microbiologyStatus;
    private List<String> microbiologyStatusOptions;
    private String sampleType;
    private String sampleTypeId;
    private String sampleCode;
    private String testCode;
    private String samplePrefix;
    private String bodyPart;
    private String bodyPartId;

    private Date resultDate;
    private Date seedingDate;
    private Date reseedingDate;
    private String isCulture;
    private String patientId;
    private List<HashMap<String, String>> events;
    private List<HashMap<String, Object>> cultureMediums;
    private String labelCode;

    public MBTest() {
    }

    public MBTest(String testId, String examId, String orderId, String testName, String result, String resultType,
            List<HashMap<String, Object>> resultOptions, String status, List<String> statusOptions,
            String microbiologyStatus, List<String> microbiologyStatusOptions, String sampleType, String sampleTypeId,
            String sampleCode, String testCode, String samplePrefix, String bodyPart, String bodyPartId,
            Date resultDate, Date seedingDate, Date reseedingDate, List<HashMap<String, String>> events,
            String isCulture, List<HashMap<String, Object>> cultureMediums, String labelCode, String patientId) {
        this.testId = testId;
        this.examId = examId;
        this.orderId = orderId;
        this.testName = testName;
        this.result = result;
        this.resultType = resultType;
        this.resultOptions = resultOptions;
        this.status = status;
        this.statusOptions = statusOptions;
        this.microbiologyStatus = microbiologyStatus;
        this.microbiologyStatusOptions = microbiologyStatusOptions;
        this.sampleType = sampleType;
        this.sampleTypeId = sampleTypeId;
        this.sampleCode = sampleCode;
        this.testCode = testCode;
        this.samplePrefix = samplePrefix;
        this.bodyPart = bodyPart;
        this.bodyPartId = bodyPartId;
        this.resultDate = resultDate;
        this.seedingDate = seedingDate;
        this.reseedingDate = reseedingDate;
        this.events = events;
        this.isCulture = isCulture;
        this.cultureMediums = cultureMediums == null ? new ArrayList<HashMap<String, Object>>() : cultureMediums;
        this.labelCode = labelCode;
        this.patientId = patientId;
    }

    public String getId() {
        return this.testId;
    }

    public String getExamId() {
        return this.examId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getTestName() {
        return this.testName;
    }

    public String getResult() {
        return this.result;
    }

    public String getResultType() {
        return this.resultType;
    }

    public List<String> getResultOptions() {
        List<String> result = new ArrayList<String>();
        for (HashMap<String, Object> option : this.resultOptions) {
            result.add(option.get("name").toString());
        }
        return result;
    }

    public String getStatus() {
        return this.status;
    }

    public List<String> getStatusOptions() {
        return this.statusOptions;
    }

    public String getMicrobiologyStatus() {
        return this.microbiologyStatus;
    }

    public List<String> getMicrobiologyStatusOptions() {
        return this.microbiologyStatusOptions;
    }

    public String getSampleType() {
        return this.sampleType;
    }

    public String getSampleTypeId() {
        return this.sampleTypeId;
    }

    public String getSampleCode() {
        return this.sampleCode;
    }

    public String getTestCode() {
        return this.testCode;
    }

    public String getSamplePrefix() {
        return this.samplePrefix;
    }

    public String getBodyPart() {
        return this.bodyPart;
    }

    public String getBodyPartId() {
        return this.bodyPartId;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public String getCriticalValuesAsText() {
        List<String> result = new ArrayList<String>();
        for (HashMap<String, Object> option : this.resultOptions) {
            if ((Boolean) option.get("critical")) {
                result.add(option.get("name").toString());
            }

        }
        return String.join(", ", result);
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

    public List<HashMap<String, String>> getEvents() {
        return this.events;
    }

    public String getLabelCode() {
        return this.labelCode;
    }

    public void setResult(String result, String username) {
        this._setResult(result, username);
    }

    public void setResult(String result) {
        this._setResult(result, "-");
    }

    private void _setResult(String result, String username) {
        if (!this.getResult().equals(result)) {
            if (this.getResultType().equals("select")) {
                if (this.getResultOptions().contains(result)) {
                    this.logEvent(username, "Actualiza resultado test", this.getResult(), result);
                    this.resultDate = new Date();
                    this.status = "DIGITADO";
                    this.result = result;
                }
            } else {
                this.logEvent(username, "Actualiza resultado test", this.getResult(), result);
                this.resultDate = new Date();
                this.status = "DIGITADO";
                this.result = result;
            }
        }
    };

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

    public void setStatus(String status, String username) {
        this._setStatus(status, username);
    }

    public void setStatus(String status) {
        this._setStatus(status, "-");
    }

    private void _setStatus(String status, String username) {
        if (!this.getStatus().equals(status)) {
            if (this.getStatusOptions().contains(status)) {
                this.logEvent(username, "Actualiza estado test", "".equals(this.getStatus()) ? "-" : this.getStatus(),
                        status);
                this.status = status;
            }
        }
    };

    public void setMicrobiologyStatus(String status, String username) {
        this._setMicrobiologyStatus(status, username);
    }

    public void setMicrobiologyStatus(String status) {
        this._setMicrobiologyStatus(status, "-");
    }

    private void _setMicrobiologyStatus(String microbiologyStatus, String username) {
        if (!this.getMicrobiologyStatus().equals(microbiologyStatus)) {
            if (this.getMicrobiologyStatusOptions().contains(microbiologyStatus)) {
                this.logEvent(username, "Actualiza estado microbiologia",
                        "".equals(this.getMicrobiologyStatus()) ? "-" : this.getMicrobiologyStatus(),
                        microbiologyStatus);
                this.microbiologyStatus = microbiologyStatus;
            }
        }
    };

    public void setResultDate(Date date, String username) {
        this._setResultDate(date, username);
    }

    public void setResultDate(Date date) {
        this._setResultDate(date, "-");
    }

    public String getIsCulture() {
        return this.isCulture;
    }

    private void _setResultDate(Date date, String username) {
        if (!compare(date, this.getResultDate())) {
            this.logEvent(username, "Actualiza fecha resultado",
                    this.getResultDate() == null ? "-"
                            : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.getResultDate()),
                    date == null ? "-" : new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date));
            this.resultDate = date;
        }
    }

    public void setSampleType(String sampleType, String username) {
        this._setSampleType(sampleType, username);
    }

    public void setSampleType(String sampleType) {
        this._setSampleType(sampleType, "-");
    }

    private void _setSampleType(String sampleType, String username) {
        if (!this.getSampleType().equals(sampleType)) {
            this.logEvent(username, "Actualiza tipo muestra", this.getSampleType(), sampleType);
            this.sampleType = sampleType;
        }
    }

    public void setBodyPart(String bodyPart, String username) {
        this._setBodyPart(bodyPart, username);
    }

    public void setBodyPart(String bodyPart) {
        this._setBodyPart(bodyPart, "-");
    }

    private void _setBodyPart(String bodyPart, String username) {
        if (!this.getBodyPart().equals(bodyPart)) {
            this.logEvent(username, "Actualiza parte del cuerpo", this.getBodyPart(), bodyPart);
            this.bodyPart = bodyPart;
        }
    }

    public List<HashMap<String, Object>> getCultureMediums() {
        return this.cultureMediums;
    }

    public void setCultureMedium(HashMap<String, Object> cultureMedium, String username) {
        this._setCultureMedium(cultureMedium, username);
    }

    public void setCultureMedium(HashMap<String, Object> cultureMedium) {
        this._setCultureMedium(cultureMedium, "-");
    }

    private void _setCultureMedium(HashMap<String, Object> cultureMedium, String username) {
        for (int i = 0; i < this.cultureMediums.size(); i++) {
            HashMap<String, Object> medium = this.cultureMediums.get(i);
            if (medium.get("id").equals(cultureMedium.get("id"))) {
                if (cultureMedium.get("temperature") != null) {
                    if (!medium.get("temperature").equals(cultureMedium.get("temperature"))) {
                        this.logEvent(username, "DMS_DESCTEMPERATURA",
                                medium.get("temperature").toString(),
                                cultureMedium.get("temperature").toString());
                        medium.put("temperature", cultureMedium.get("temperature"));
                    }
                }
                if (cultureMedium.get("oxigenation") != null) {
                    if (!medium.get("oxigenation").equals(cultureMedium.get("oxigenation"))) {
                        this.logEvent(username, "DMS_DESCOXIGENACION",
                                medium.get("oxigenation").toString(),
                                cultureMedium.get("oxigenation").toString());
                        medium.put("oxigenation", cultureMedium.get("oxigenation"));
                    }
                }
                this.cultureMediums.set(i, medium);
                return;
            }
        }
        this.cultureMediums.add(new HashMap<String, Object>() {
            {
                put("id", "");
                put("sampleId", cultureMedium.get("sampleId").toString());
                put("cultureId", cultureMedium.get("cultureId").toString());
                put("patientId", cultureMedium.get("patientId").toString());
                put("temperature", "");
                put("oxigenation", "");
            }
        });
    }

    private static boolean compare(Date date1, Date date2) {
        return (date1 == null ? date2 == null : date1.equals(date2));
    }

    @Override
    public String toString() {
        return "MBTest [testId=" + testId + ", examId=" + examId + ", orderId=" + orderId + ", testName=" + testName
                + ", result=" + result + ", resultType=" + resultType + ", resultOptions=" + resultOptions + ", status="
                + status + ", statusOptions=" + statusOptions + ", microbiologyStatus=" + microbiologyStatus
                + ", microbiologyStatusOptions=" + microbiologyStatusOptions + ", sampleType=" + sampleType
                + ", sampleTypeId=" + sampleTypeId + ", sampleCode=" + sampleCode + ", testCode=" + testCode
                + ", samplePrefix=" + samplePrefix + ", bodyPart=" + bodyPart + ", bodyPartId=" + bodyPartId
                + ", resultDate=" + resultDate + ", seedingDate=" + seedingDate + ", reseedingDate=" + reseedingDate
                + ", isCulture=" + isCulture + ", patientId=" + patientId + ", events=" + events + ", cultureMediums="
                + cultureMediums + ", labelCode=" + labelCode + "]";
    }

    
}
