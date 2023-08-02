package com.grupobios.bioslis.microbiologia.dao;

import java.util.List;

import com.grupobios.bioslis.microbiologia.entity.MBOrder;

import java.util.HashMap;

public interface MBOrderDAO{
    List<MBOrder> search(HashMap<String, String> queryData);
    public MBOrder getById(int orderId);
}
