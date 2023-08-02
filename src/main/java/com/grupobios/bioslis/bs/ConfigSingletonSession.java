/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

/**
 *
 * @author Marco Caracciolo
 */
public class ConfigSingletonSession {
    
    private static SessionService session = null;
    
    private ConfigSingletonSession() {
        
    }
    
    public static SessionService getInstance(){
        if (session==null){
            session = new SessionServiceImpl();
        }
        return session;
    }
    
}
