package com.grupobios.bioslis.microbiologia.entity;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class Patient {

    private String id;
    private String names;
    private String firstSurname;
    private String secondSurname;
    private Date dateOfBirth;
    private String gender;
    private List<String> pathologies;
    private String observation;
    private String phoneNumber;
    private List<String> ordersList;
    private String email;
    private String nDocument;
    private BigDecimal tipoDocumento; 

    public Patient(String id, String names, String firstSurname, String secondSurname, Date dateOfBirth, String gender,
            String observation, String phoneNumber, List<String> pathologies, List<String> ordersList, String email,
            String nDocument , BigDecimal tipoDocumento) {

        this.id = id;
        this.names = names;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.observation = observation;
        this.phoneNumber = phoneNumber;
        this.pathologies = pathologies;
        this.ordersList = ordersList;
        this.email = email;
        this.nDocument = nDocument;
        this.tipoDocumento = tipoDocumento;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return this.names + " " + this.firstSurname + " " + this.secondSurname;
    }

    public String getPathologiesASText() {
        return String.join(", ", this.pathologies);
    }

    public String getAgeAsText() {
        LocalDate now = LocalDate.now();
        LocalDate localDayOfBirth = this.dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period age = Period.between(localDayOfBirth, now);

        return Integer.toString(age.getYears()) + "a " + Integer.toString(age.getMonths()) + "m "
                + Integer.toString(age.getDays()) + "d";
    }

    public String getGender() {
        return this.gender;
    }

    public String getObservation() {
        return this.observation;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public List<String> getOrdersList() {
        return this.ordersList;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNDocument() {
        return this.nDocument;
    }

    public BigDecimal getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(BigDecimal tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
}
