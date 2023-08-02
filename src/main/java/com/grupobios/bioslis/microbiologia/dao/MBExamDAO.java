package com.grupobios.bioslis.microbiologia.dao;

import com.grupobios.bioslis.microbiologia.entity.MBExam;

public interface MBExamDAO {

    public MBExam getExamByIds(int orderId, int examId);

    public MBExam get_examenes_por_orden(int orderId);

    public void update(MBExam exam);

}
