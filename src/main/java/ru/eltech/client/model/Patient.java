package ru.eltech.client.model;

public class Patient {
    private int id;
    private String name;
    private String phone;

    private String email;

    private boolean is_archived;

    public Patient(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
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

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean isIs_archived() { return is_archived; }

    public void setIs_archived(boolean is_archived) { this.is_archived = is_archived; }

    @Override
    public String toString() {
        return id + "," + name + ",+" + phone + "," + email;
    }
}
