package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.microbiologia.entity.Patient;

public interface PatientDAO {
    Patient getById(String id, int orderId);
}
