package ru.eltech.client.request;

public class PatientCreateRequest {
    private String name;
    private String phone;

    public PatientCreateRequest() {
    }

    public PatientCreateRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
