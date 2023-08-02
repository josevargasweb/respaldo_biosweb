package com.grupobios.bioslis.microbiologia.bs;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBExamDAO;
import com.grupobios.bioslis.microbiologia.entity.MBExam;

public class ExamEditor {

    private String orderID;
    private String examId;
    private String username;

    private MBExamDAO dao;

    public ExamEditor(String orderId, String examId) {

        DAOFactory daoFactory = new DAOFactory();
        this.dao = daoFactory.getDAO("MBExam");

        this.orderID = orderId;
        this.examId = examId;
    }

    public ExamEditor(String orderId, String examId, String username) {

        DAOFactory daoFactory = new DAOFactory();
        this.dao = daoFactory.getDAO("MBExam");

        this.orderID = orderId;
        this.examId = examId;
        this.username = username;
    }

    public void updateNotes(String internalNote, String examNote, String footerNote,  String idUser) {
        MBExam exam = getExam();
        if (username == null) {
            exam.setInternalNote(internalNote);
            exam.setExamNote(examNote);
            exam.setFooterInformNote(footerNote);
            exam.setIdUsuario(idUser);
        } else {
            exam.setInternalNote(internalNote, this.username);
            exam.setExamNote(examNote, this.username);
            exam.setFooterInformNote(footerNote, this.username);
        }
        this.dao.update(exam);
    }

    private MBExam getExam() {
        return this.dao.getExamByIds(Integer.parseInt(this.orderID), Integer.parseInt(this.examId));
    }

    private MBExam get_examenes_orden() {
        return this.dao.get_examenes_por_orden(Integer.parseInt(this.orderID));
    }

}
