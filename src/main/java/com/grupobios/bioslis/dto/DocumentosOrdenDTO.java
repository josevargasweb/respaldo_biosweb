/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dto;

/**
 *
 * @author Marco Caracciolo
 */
public class DocumentosOrdenDTO {
    private long iddocumentoficha;
    private byte idtipodocumentoanexo;
    private String documento;
    private String tipoArchivo;

    public long getIddocumentoficha() {
        return iddocumentoficha;
    }

    public void setIddocumentoficha(long iddocumentoficha) {
        this.iddocumentoficha = iddocumentoficha;
    }

    public byte getIdtipodocumentoanexo() {
        return idtipodocumentoanexo;
    }

    public void setIdtipodocumentoanexo(byte idtipodocumentoanexo) {
        this.idtipodocumentoanexo = idtipodocumentoanexo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    
}
