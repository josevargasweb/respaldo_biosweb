/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

/**
 *
 * @author marco caracciolo
 */
public class ConfigFactory {
    
    private static ConfigFactory instance = null;
    
    private ConfigFactory(){
        
    }
   /* 
    public static ConfigFactory getInstance(){
        if (instance==null){
            instance = new ConfigFactory();
        }
        return instance;
    }
    */
    public static ConfigPrefijosService create(){
        return new ConfigPrefijosServiceImpl();
    }
    
}
