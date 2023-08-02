/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.SigmaProcesostest;

/**
 *
 * @author Marco Caracciolo
 */
public class ProcesosTestsDTO {
    private SigmaProcesostest sigmaProcesostest;
    private long idTest;
    private byte idSigmaTipoMuestra;
    private int idtiporesultado;
    private short cmueIdtipomuestra;
    private String prefijoTipoMuestra;
    private ProcesosTestsConversionDTO[] conversiones;

    public SigmaProcesostest getSigmaProcesostest() {
        return sigmaProcesostest;
    }

    public void setSigmaProcesostest(SigmaProcesostest sigmaProcesostest) {
        this.sigmaProcesostest = sigmaProcesostest;
    }

    public long getIdTest() {
        return idTest;
    }

    public void setIdTest(long idTest) {
        this.idTest = idTest;
    }

    public byte getIdSigmaTipoMuestra() {
        return idSigmaTipoMuestra;
    }

    public void setIdSigmaTipoMuestra(byte idSigmaTipoMuestra) {
        this.idSigmaTipoMuestra = idSigmaTipoMuestra;
    }

    public int getIdtiporesultado() {
        return idtiporesultado;
    }

    public void setIdtiporesultado(int idtiporesultado) {
        this.idtiporesultado = idtiporesultado;
    }

    public short getCmueIdtipomuestra() {
        return cmueIdtipomuestra;
    }

    public void setCmueIdtipomuestra(short cmueIdtipomuestra) {
        this.cmueIdtipomuestra = cmueIdtipomuestra;
    }

    public String getPrefijoTipoMuestra() {
        return prefijoTipoMuestra;
    }

    public void setPrefijoTipoMuestra(String prefijoTipoMuestra) {
        this.prefijoTipoMuestra = prefijoTipoMuestra;
    }

    public ProcesosTestsConversionDTO[] getConversiones() {
        return conversiones;
    }

    public void setConversiones(ProcesosTestsConversionDTO[] conversiones) {
        this.conversiones = conversiones;
    }
    
}
