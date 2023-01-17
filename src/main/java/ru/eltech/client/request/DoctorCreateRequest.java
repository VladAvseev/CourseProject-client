package ru.eltech.client.request;

public class DoctorCreateRequest {
    private String name;
    private String license;

    public DoctorCreateRequest() {}

    public DoctorCreateRequest(String name, String license) {
        this.name = name;
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
