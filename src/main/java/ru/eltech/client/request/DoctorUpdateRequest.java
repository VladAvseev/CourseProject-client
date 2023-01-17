package ru.eltech.client.request;

public class DoctorUpdateRequest extends DoctorCreateRequest {
    private int id;

    public DoctorUpdateRequest() {
    }

    public DoctorUpdateRequest(int id, String name, String license) {
        super(name, license);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
