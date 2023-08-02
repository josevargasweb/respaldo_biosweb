package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.microbiologia.entity.MBTest;

public interface MBTestDAO {
    public MBTest getTestByIds(int orderId, int examId, int testId);
    public void update(MBTest test);
    public void create(MBTest test);
    public void delete(String orderId, String examId, String testId);
}
