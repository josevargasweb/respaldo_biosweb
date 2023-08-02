/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.common;

/**
 *
 * @author eric.nicholls
 */
public class BiosLISException  extends Exception{
    
    public BiosLISException() {
        super();
    }
    
    public BiosLISException(String message) {
        super(message);
        
    }
    
    public BiosLISException(Throwable ex) {
        super(ex);
    }

    
}
