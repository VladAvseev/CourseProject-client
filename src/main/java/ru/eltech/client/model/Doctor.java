package ru.eltech.client.model;

public class Doctor {
    private int id;
    private String name;
    private String license;

    private boolean is_archived;

    public Doctor(int id, String name, String license) {
        this.id = id;
        this.name = name;
        this.license = license;
    }

    public Doctor() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicense() {
        return license;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isIs_archived() { return is_archived; }

    public void setIs_archived(boolean is_archived) { this.is_archived = is_archived; }

    @Override
    public String toString() { return id + "," + name + "," + license; }
}
