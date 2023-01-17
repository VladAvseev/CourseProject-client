package ru.eltech.client.request;

public class PatientUpdateRequest extends PatientCreateRequest{
    private int id;

    public PatientUpdateRequest() {
    }

    public PatientUpdateRequest(int id, String name, String phone) {
        super(name, phone);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
