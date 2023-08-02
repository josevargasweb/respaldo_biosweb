package com.grupobios.bioslis.microbiologia.entity;

import java.util.HashMap;

public class MBAntibiogram {
    private String orderId;
    private String examId;
    private String testId;
    private String colonyCount;
    private String infectionType;
    private HashMap<Integer, Object> antibioticList;

    public MBAntibiogram(){}

    public MBAntibiogram(
        String orderId,
        String examId,
        String testId,
        String colonyCount,
        String infectionType,
        HashMap<Integer, Object> antibioticList){
            this.orderId = orderId;
            this.examId = examId;
            this.testId = testId;
            this.colonyCount = colonyCount;
            this.infectionType = infectionType;
            this.antibioticList = antibioticList;
        }

    public String getOrderId(){
        return this.orderId;
    }

    public String getExamId(){
        return this.examId;
    }

    public String getTestId(){
        return this.testId;
    }
    
    public String getColonycount(){
        return this.colonyCount;
    }

    public String getInfectionType(){
        return this.infectionType;
    }

    public HashMap<Integer, Object> getAntibioticList(){
        return this.antibioticList;
    }

    @Override
    public String toString() {
        return "MBAntibiogram [orderId=" + orderId + ", examId=" + examId + ", testId=" + testId + ", colonyCount="
                + colonyCount + ", infectionType=" + infectionType + ", antibioticList=" + antibioticList + "]";
    }


    
}
