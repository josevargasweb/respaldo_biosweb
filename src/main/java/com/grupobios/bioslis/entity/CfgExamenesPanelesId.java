/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.entity;

import java.io.Serializable;

/**
 *
 * @author Marco C
 */
public class CfgExamenesPanelesId implements Serializable {
    
    private short cepIdpanelexamenes;
    private long cepIdexamen;

    public CfgExamenesPanelesId() {
    }

    public CfgExamenesPanelesId(short cepIdpanelexamenes, long cepIdexamen) {
        this.cepIdpanelexamenes = cepIdpanelexamenes;
        this.cepIdexamen = cepIdexamen;
    }

    public short getCepIdpanelexamenes() {
        return cepIdpanelexamenes;
    }

    public void setCepIdpanelexamenes(short cepIdpanelexamenes) {
        this.cepIdpanelexamenes = cepIdpanelexamenes;
    }

    public long getCepIdexamen() {
        return cepIdexamen;
    }

    public void setCepIdexamen(long cepIdexamen) {
        this.cepIdexamen = cepIdexamen;
    }
    
}
