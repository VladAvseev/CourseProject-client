package ru.eltech.client.request;

public class TicketCreateRequest {
    private int doctorId;
    private int patientId;
    private String diagnosis;

    public TicketCreateRequest() {}

    public TicketCreateRequest(int doctorId, int patientId, String diagnosis) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
