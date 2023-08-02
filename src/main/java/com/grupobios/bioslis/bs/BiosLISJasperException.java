/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.common.BiosLISException;
import java.io.FileNotFoundException;

/**
 *
 * @author eric.nicholls
 */
public class BiosLISJasperException extends BiosLISException {

    public BiosLISJasperException(String msg) {
        
    }

    BiosLISJasperException(Throwable ex) {

        super(ex);
    }
    
    
    
}
