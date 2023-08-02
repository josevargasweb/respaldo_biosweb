/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

/**
 *
 * @author marco.cortez
 */
public class ConfigSingleton {
    
    private static ConfigSingleton instance = null;
    
    private ConfigSingleton(){
        
    }
    
    public static ConfigSingleton getInstance(){
        if (instance==null){
            instance=new ConfigSingleton();
        } 
        return instance;
    }
    
    
            
}
