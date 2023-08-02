/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco.caracciolo
 */
public class ConfigPrefijosServiceImpl implements ConfigPrefijosService{

    @Override
    public String asignarPrefijo() throws BiosLISDAOException {
        CfgMuestrasDAO cmueDao = new CfgMuestrasDAO();
        List<String> prefijos = cmueDao.buscarPrefijos();
        
        String prefijoAsignado = "AA";
        String cadena ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        boolean yaExiste = true;
        
        for (String p: prefijos) {
            if (prefijoAsignado.equals(p)){
                char x0 = prefijoAsignado.charAt(0);
                char x1 = prefijoAsignado.charAt(1);
                int posicion0 = cadena.indexOf(x0);
                int posicion1 = cadena.indexOf(x1);
                char y0 = cadena.charAt(posicion0);
                char y1;
                if (posicion1>34){
                    //posicion1 = -1;
                    y0 = cadena.charAt(posicion0+1);
                    y1 = cadena.charAt(0);
                } else {
                    y1 = cadena.charAt(posicion1+1);
                }
                StringBuilder newPrefijo = new StringBuilder(prefijoAsignado);
                newPrefijo.setCharAt(0, y0);
                newPrefijo.setCharAt(1, y1);
                prefijoAsignado = newPrefijo.toString();
            } else {
                yaExiste = false;
            }
        }
        
        //Esto indica que no hay prefijos disponibles
        if (yaExiste){
            prefijoAsignado = "";
        }
        
        return prefijoAsignado;
    }
    
    @Override
    public List<String> generarPrefijos() throws BiosLISDAOException {
        String cadena ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        List<String> listaPrefijos = new ArrayList<>();
        
        for (int i=0; i<cadena.length(); i++){
            for (int j=0; j<cadena.length(); j++){
                StringBuilder newPrefijo = new StringBuilder();
                newPrefijo.setLength(2);
                newPrefijo.setCharAt(0, cadena.charAt(i));
                newPrefijo.setCharAt(1, cadena.charAt(j));
                listaPrefijos.add(newPrefijo.toString());
            }
        }
        
        return listaPrefijos;
    }
    
    @Override
    public List<Object[]> listarPrefijosMuestras() throws BiosLISDAOException{
        CfgMuestrasDAO cmueDao = new CfgMuestrasDAO();
        List<Object[]> listaPrefijosMuestras = cmueDao.getListaMuestras();
        
        List<String> prefijos = generarPrefijos();
        List<Object[]> lista = new ArrayList<>();
        if (listaPrefijosMuestras.isEmpty()) {
            for (String p: prefijos){
                Object[] obj = new Object[2];
                obj[0] = p; //prefijo almacenado
                obj[1] = "";
                lista.add(obj);
            }
		}else {
	        for (String p: prefijos){
	            Object[] obj = new Object[2];
	            obj[0] = p; //prefijo almacenado
	            obj[1] = "";
	            for (Object[] lpm: listaPrefijosMuestras){
	                if (lpm[1].toString().equals(p)){
	                    obj[1] = lpm[2]; //descripcion de muestra
	                }
	            }
	            lista.add(obj);
	        }
		}

        return lista;
    }
    
}
