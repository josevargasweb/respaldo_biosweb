package com.grupobios.bioslis.microbiologia.bs;

import java.util.HashMap;
import java.text.SimpleDateFormat;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBExamDAO;
import com.grupobios.bioslis.microbiologia.dao.MBOrderDAO;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBOrder;

public class ExamSearcher {

    public HashMap<String, Object> searchExam(String orderId, String examId) {
        MBOrderDAO dao = (new DAOFactory()).getDAO("MBOrder");
        MBOrder order = dao.getById(Integer.parseInt(orderId));
        MBExam exam = order.getExam(examId);

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("observations", new HashMap<String, Object>() {
            {
                put("patient", new HashMap<String, String>() {
                    {
                        put("name", order.getPatient().getFullName());
                        put("observation", order.getPatient().getObservation());
                        put("age", order.getPatient().getAgeAsText());
                        put("nDocument", order.getPatient().getNDocument());
                    }
                });
                put("order", new HashMap<String, String>() {
                    {
                        put("id", order.getId());
                        put("observation", order.getObservation());
                        if (exam.getUser() != null) {
                            put("user", exam.getUser());
                        }
                        put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(order.getDate()));
                        put("bodyPartId", exam.getBodyPartId());
                        put("sampleId", exam.getSampleId());
                    }
                });
                put("sampling", new HashMap<String, String>() {
                    {
                        put("observation", exam.getSamplingObservation());
                        put("status", exam.getSamplingStatus());
                        put("user", exam.getSamplingUser());
                        put("date", exam.getSamplingDate() == null ? ""
                                : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(exam.getSamplingDate()));
                    }
                });
                put("reception", new HashMap<String, String>() {
                    {
                        put("observation", exam.getReceptionObservation());
                        put("status", exam.getReceptionStatus());
                        put("user", exam.getReceptionUser());
                        put("date", exam.getReceptionDate() == null ? ""
                                : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(exam.getReceptionDate()));
                    }
                });
            }
        });

        result.put("notes", new HashMap<String, String>() {
            {
                put("internal", exam.getInternalNote());
                put("exam", exam.getExamNote());
                put("footer", exam.getFooterInformNote());
            }
        });

        result.put("optionalTests", exam.getOptionalTests());
        result.put("events", exam.getEvents());
        result.put("testsList", exam.getTestList());

        return result;
    }

    public HashMap<String, Object> searchExam2(String orderId) {
        MBOrderDAO dao = (new DAOFactory()).getDAO("MBOrder");
        MBOrder order = dao.getById(Integer.parseInt(orderId));

        MBExamDAO dao_Exam = (new DAOFactory()).getDAO("MBExam");
        MBExam exam = dao_Exam.get_examenes_por_orden(Integer.parseInt(orderId));

        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("observations", new HashMap<String, Object>() {
            {
                put("resultOptions", "resultOptions");
                put("test", exam.getExamName());
                put("orderId", order.getId());
                put("testtt", exam.getTestList());
                put("microbiologyStatus", exam.getEstado());
                put("isCulture", "isCulture");
                put("result", "result");
                put("sampleCode", "result");
                put("examId", exam.getExamId());
                put("statusOptions", "result");
                put("id", "result");
                put("resultType", "result");
                put("labelCode", "result");
                put("status", "result");

            }
        });

        return result;
    }

}
