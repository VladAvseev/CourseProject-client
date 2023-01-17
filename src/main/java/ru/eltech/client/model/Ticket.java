package ru.eltech.client.model;

public class Ticket {

    protected int id;
    private String doctorName;
    private String patientName;
    private String diagnosis;

    public Ticket(int id, int doctorId, int patientId, String diagnosis, String doctorName, String patientName) {
        this.id = id;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
    }

    public Ticket() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDiagnosis() { return diagnosis; }

    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis;}

    public String toString() {
        return id + "," + doctorName + "," + patientName + "," + diagnosis;
    }
}
