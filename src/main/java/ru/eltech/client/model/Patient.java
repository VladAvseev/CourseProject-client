package ru.eltech.client.model;

public class Patient {
    private int id;
    private String name;
    private String phone;

    private boolean is_archived;

    public Patient(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Patient() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isIs_archived() { return is_archived; }

    public void setIs_archived(boolean is_archived) { this.is_archived = is_archived; }

    @Override
    public String toString() {
        return id + "," + name + ",+" + phone;
    }
}
