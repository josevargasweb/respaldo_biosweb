package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.microbiologia.entity.MBAntibiogram;

public interface MBAntibiogramDAO {
    public MBAntibiogram getAntibiogramByIds(int orderId, int examId, int testId);

    public void updateOrCreate(MBAntibiogram antibiogram);

    public String getAntibioticResistance(String antibioticId, String microorganismId, String method, String value);
}
