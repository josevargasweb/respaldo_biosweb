package com.grupobios.bioslis.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AntibiogramaMicrobiologiaDTO {

    private BigDecimal ID;
    private BigDecimal  ORDERID;
    private BigDecimal EXAMNID;
    private BigDecimal TESTID;
    private String TEST;
    private String COLONYCOUNT;
    private String INFECTIONTYPE;
    
    private HashMap<Integer , AntibioticoMicrobiologiaDTO> antibioticLists = new HashMap<>();

    
    
    
    public AntibiogramaMicrobiologiaDTO() {
                
    }

   
    public BigDecimal getID() {
        return ID;
    }

    public void setID(BigDecimal iD) {
        ID = iD;
    }

    public BigDecimal getORDERID() {
        return ORDERID;
    }

    public void setORDERID(BigDecimal oRDERID) {
        ORDERID = oRDERID;
    }

    public BigDecimal getEXAMNID() {
        return EXAMNID;
    }

    public void setEXAMNID(BigDecimal eXAMNID) {
        EXAMNID = eXAMNID;
    }

    public BigDecimal getTESTID() {
        return TESTID;
    }

    public void setTESTID(BigDecimal tESTID) {
        TESTID = tESTID;
    }

    public String getTEST() {
        return TEST;
    }

    public void setTEST(String tEST) {
        TEST = tEST;
    }

    public String getCOLONYCOUNT() {
        return COLONYCOUNT;
    }

    public void setCOLONYCOUNT(String cOLONYCOUNT) {
        COLONYCOUNT = cOLONYCOUNT;
    }

    public String getINFECTIONTYPE() {
        return INFECTIONTYPE;
    }

    public void setINFECTIONTYPE(String iNFECTIONTYPE) {
        INFECTIONTYPE = iNFECTIONTYPE;
    }


    public HashMap<Integer, AntibioticoMicrobiologiaDTO> getAntibioticLists() {
        return antibioticLists;
    }


    public void setAntibioticLists( AntibioticoMicrobiologiaDTO antibioticLists , Integer indice) {
        this.antibioticLists.put(indice ,antibioticLists);
    }



  

   
    
}
