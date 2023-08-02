package com.grupobios.bioslis.microbiologia.dao;

import java.util.HashMap;
import java.util.List;

public interface MBConfigurationDAO {
    public HashMap<String, String> getMicroorganism(String id);

    public void updateMicroorganism(HashMap<String, String> microorganism , Long idUsuario);

    public void createMicroorganism(HashMap<String, String> microorganism);

    public void deleteMicroorganism(String id, String code);

    public List<HashMap<String, String>> getMOList(String code, String name, String active_only);

    public HashMap<String, String> getAntibiotic(String id);

    public void updateAntibiotic(HashMap<String, String> antibiotic, Long idUsuario);

    public void createAntibiotic(HashMap<String, String> antibiotic);

    public void deleteAntibiotic(String id, String code);

    public List<HashMap<String, String>> getABList(String code, String name, String class_, String active_only);

    public List<HashMap<String, String>> getStainingOptions();

    public List<HashMap<String, String>> getMorphologyOptions();

    public List<HashMap<String, String>> getGenderOptions();

    public List<HashMap<String, String>> getABClassOptions();

    public List<HashMap<String, String>> getABResistanceList(String id);

    public HashMap<String, String> updateABResistance(HashMap<String, String> resistance , Long idUsuario);

    public HashMap<String, String> getCultureMedium(String id);

    public void updateCultureMedium(HashMap<String, String> cultureMedium,  Long idUsuario);

    public void createCultureMedium(HashMap<String, String> cultureMedium);

    public List<HashMap<String, String>> getCMList(String code, String name, String active_only);

    public HashMap<String, String> getResistanceMethod(String id);

    public boolean updateResistanceMethod(HashMap<String, String> resistanceMethod, Long idUsuario);

    public boolean createResistanceMethod(HashMap<String, String> resistanceMethod);

    public List<HashMap<String, String>> getRMList(String code, String name, String active_only);

    public HashMap<String, String> getPruebaManual(String id);

    public void updatePruebaManual(HashMap<String, String> pruebaManual, Long idUsuario);

    public void createPruebaManual(HashMap<String, String> pruebaManual);

    public List<HashMap<String, String>> getPMList(String code, String name, String active_only);

    public HashMap<String, String> getBodyArea(String id);

    public boolean updateBodyArea(HashMap<String, String> bodyArea , Long idUsuario);

    public boolean createBodyArea(HashMap<String, String> bodyArea);

    public List<HashMap<String, String>> getBAList(String code, String name, String active_only);

    public HashMap<String, String> getColonyCount(String id);

    public boolean updateColonyCount(HashMap<String, String> colonyCount, Long idUsuario);

    public boolean createColonyCount(HashMap<String, String> colonyCount);

    public List<HashMap<String, String>> getCCList(String code, String name, String active_only);

    public HashMap<String, Object> getAntibiogram(String id);

    public boolean updateAntibiogram(HashMap<String, String> antibiogram, Long idUsuario);

    public boolean createAntibiogram(HashMap<String, String> antibiogram);

    public List<HashMap<String, String>> getAGList(String code, String name, String active_only);

    public void clearAntibiogramAntibiotics(String antibiogramId);

    public void updateAntibiogramAntibiotic(String antibiogramId, String antibioticId, String optional);
}
