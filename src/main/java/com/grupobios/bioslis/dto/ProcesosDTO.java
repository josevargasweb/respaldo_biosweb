/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.LdvTipocomunicacion;
import com.grupobios.bioslis.entity.SigmaProcesos;
import com.grupobios.bioslis.entity.SigmaProcesosalarmas;

/**
 *
 * @author Marco Caracciolo
 */
public class ProcesosDTO {
    private SigmaProcesos sigmaProcesos;
    private LdvTipocomunicacion ldvTipocomunicacion;
    private ProcesosTestsDTO[] procesosTests;
    private SigmaProcesosalarmas[] procesosAlarmas;

    public SigmaProcesos getSigmaProcesos() {
        return sigmaProcesos;
    }

    public void setSigmaProcesos(SigmaProcesos sigmaProcesos) {
        this.sigmaProcesos = sigmaProcesos;
    }

    public LdvTipocomunicacion getLdvTipocomunicacion() {
        return ldvTipocomunicacion;
    }

    public void setLdvTipocomunicacion(LdvTipocomunicacion ldvTipocomunicacion) {
        this.ldvTipocomunicacion = ldvTipocomunicacion;
    }

    public ProcesosTestsDTO[] getProcesosTests() {
        return procesosTests;
    }

    public void setProcesosTests(ProcesosTestsDTO[] procesosTests) {
        this.procesosTests = procesosTests;
    }

    public SigmaProcesosalarmas[] getProcesosAlarmas() {
        return procesosAlarmas;
    }

    public void setProcesosAlarmas(SigmaProcesosalarmas[] procesosAlarmas) {
        this.procesosAlarmas = procesosAlarmas;
    }
    
}
