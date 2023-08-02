package com.grupobios.bioslis.microbiologia.bs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBAntibiogramDAO;
import com.grupobios.bioslis.microbiologia.dao.MBOrderDAO;
import com.grupobios.bioslis.microbiologia.entity.MBAntibiogram;
import com.grupobios.bioslis.microbiologia.entity.MBExam;
import com.grupobios.bioslis.microbiologia.entity.MBOrder;
import com.grupobios.bioslis.microbiologia.entity.MBTest;

public class TestSearcher {

 
    public HashMap<String, Object> searchTest(String orderId, String examId, String testId) {
        MBOrderDAO dao = (new DAOFactory()).getDAO("MBOrder");
        MBOrder order = dao.getById(Integer.parseInt(orderId));  
      
        MBExam exam = order.getExam(examId);      
        MBTest test = exam.getTest(testId) == null ? new MBTest() : exam.getTest(testId);        

        return new HashMap<String, Object>() {
            {
                put("data", new HashMap<String, String>() {
                    {
                        put("name", test.getTestName());
                        put("exam", exam.getExamName());
                        put("sample", test.getSampleType());
                        put("sampleId", test.getSampleTypeId());
                        put("sampleCode", test.getSampleCode());
                        put("resultType", test.getResultType().equals("select") ? "GLOSA" : "CAMPO LIBRE");
                        put("code", test.getTestCode());
                        put("prefix", test.getSamplePrefix());
                        put("result", test.getResult());
                        put("criticalValue", test.getCriticalValuesAsText());
                        put("bodyPart", test.getBodyPart());
                        put("bodyPartId", test.getBodyPartId());
                        put("status", test.getStatus());
                        put("labelCode", test.getLabelCode());
                        put("patientId", test.getPatientId());
                    }
                });

             // put("events", test.getEvents());
                put("notification", new HashMap<String, String>() {
                    {
                        put("result", test.getResult());
                        put("criticalResult", test.getCriticalValuesAsText());
                        put("physician", order.getPhysician() == null ? "" : order.getPhysician().getFullName());
                        put("patient", order.getPatient().getFullName());
                        put("patientPhone", order.getPatient().getPhoneNumber());
                        put("physicianPhone",
                                order.getPhysician() == null ? "" : order.getPhysician().getPhoneNumber());
                        put("patientEmail", order.getPatient().getEmail());
                        put("physicianEmail", order.getPhysician() == null ? "" : order.getPhysician().getEmail());
                    }
                });

                put("actions", new HashMap<String, String>() {
                    {
                        // "2021-12-01" "12:00"
                        put("sampleType", test.getSampleType());
                        put("bodyPart", test.getBodyPart());
                        put("seedingDate", exam.getSeedingDate() == null ? ""
                                : (new SimpleDateFormat("yyyy-MM-dd")).format(exam.getSeedingDate()));
                        put("seedingTime", exam.getSeedingDate() == null ? ""
                                : (new SimpleDateFormat("hh:mm")).format(exam.getSeedingDate()));
                        put("reseedingDate", exam.getReseedingDate() == null ? ""
                                : (new SimpleDateFormat("yyyy-MM-dd")).format(exam.getReseedingDate()));
                        put("reseedingTime", exam.getReseedingDate() == null ? ""
                                : (new SimpleDateFormat("hh:mm")).format(exam.getReseedingDate()));
                        put("resultDate", exam.getResultDate() == null ? ""
                                : (new SimpleDateFormat("yyyy-MM-dd")).format(exam.getResultDate()));
                        put("resultTime", exam.getResultDate() == null ? ""
                                : (new SimpleDateFormat("hh:mm")).format(exam.getResultDate()));
                        put("status", "");
                    }
                });
/*
                put("labels", new ArrayList<HashMap<String, Object>>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("id", "label_4");
                                put("name", "Eqtiqueta 4");
                                put("selected", false);
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("id", "label_2");
                                put("name", "Eqtiqueta 2");
                                put("selected", false);
                            }
                        });
                    }
                });
*/
               
                put("antibiogram", getAntibiogram(order.getId(), exam.getExamId(), test.getId()));
                put("cultureMedium", test.getCultureMediums());
             
            }
        };

    }

    private HashMap<String, Object> getAntibiogram(String orderId, String examId, String testId) { 
        MBAntibiogramDAO dao = (new DAOFactory()).getDAO("MBAntibiogram");
        MBAntibiogram antibiogram = dao.getAntibiogramByIds(Integer.parseInt(orderId), Integer.parseInt(examId),
                Integer.parseInt(testId));
        if (antibiogram == null) {
            return defaultAntibiogram();
        } else {
            return new HashMap<String, Object>() {
                {
                    put("colonyCount", antibiogram.getColonycount());
                    put("infectionType", antibiogram.getInfectionType());
                    put("antibioticLists", antibiogram.getAntibioticList());
                }
            };
        }
    }

    private HashMap<String, Object> defaultAntibiogram() {
        return new HashMap<String, Object>() {
            {
                put("colonyCount", "");
                put("infectionType", "");
                put("antibioticLists", new HashMap<Integer, Object>() {
                    {
                        put(1, new HashMap<String, Object>() {
                            {
                                put("microorganism", new HashMap<String, Object>());
                                put("antibiogram", new HashMap<String, Object>());
                                put("antibiotics", new ArrayList<String>());
                                put("methods", new ArrayList<String>());
                            }
                        });
                        put(2, new HashMap<String, Object>() {
                            {
                                put("microorganism", new HashMap<String, Object>());
                                put("antibiogram", new HashMap<String, Object>());
                                put("antibiotics", new ArrayList<String>());
                                put("methods", new ArrayList<String>());

                            }
                        });
                        put(3, new HashMap<String, Object>() {
                            {
                                put("microorganism", new HashMap<String, Object>());
                                put("antibiogram", new HashMap<String, Object>());
                                put("antibiotics", new ArrayList<String>());
                                put("methods", new ArrayList<String>());

                            }
                        });
                        put(4, new HashMap<String, Object>() {
                            {
                                put("microorganism", new HashMap<String, Object>());
                                put("antibiogram", new HashMap<String, Object>());
                                put("antibiotics", new ArrayList<String>());
                                put("methods", new ArrayList<String>());
                            }
                        });
                    }
                });
            }
        };
    }
}
