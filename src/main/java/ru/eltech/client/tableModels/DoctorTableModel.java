package ru.eltech.client.tableModels;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.model.Doctor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DoctorTableModel extends AbstractTableModel{
     private final int columnCount = 3;
     private List<String[]> doctorsRecords = new ArrayList<>();
     private final DoctorRouter router;

    public DoctorTableModel(DoctorRouter router){
        this.router = router;
    }

    @Override
    public int getRowCount() {
        return doctorsRecords.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return doctorsRecords.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {return "id";}
            case 1 -> {return "name";}
            case 2 -> {return "license";}
        }

        return "";
    }

    public void addRecord(Doctor doctor) {
        doctorsRecords.add(doctor.toString().split(","));
    }

    public List<String[]> getDoctorsRecords() {
        return doctorsRecords;
    }

    public void clearData() {
        doctorsRecords.clear();
    }

    public void setDoctorsRecords(List<String[]> data) {
        doctorsRecords = new ArrayList<>(data);
    }

    public void updateRecord(Doctor doctor) {
        var record = doctorsRecords.stream()
                .filter(item -> String.valueOf(doctor.getId()).equals(item[0]))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        record[1] = doctor.getName();
        record[2] = doctor.getLicense();
    }

    public void deleteRecord(int id) {
        var record = doctorsRecords.stream()
                .filter(item -> String.valueOf(id).equals(item[0]))
                .findFirst()
                .orElseThrow();

        doctorsRecords.remove(record);
    }

    public boolean findRecordByColumn(String value, int column) {
        try {
            var record = doctorsRecords.stream()
                    .filter(item -> String.valueOf(value).equals(item[column]))
                    .findFirst()
                    .orElseThrow();
            return true;
        } catch (Exception error) {
            return false;
        }
    }
}
