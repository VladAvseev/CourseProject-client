package ru.eltech.client.tableModels;

import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.model.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PatientTableModel extends AbstractTableModel {
    private final int columnCount = 3;
    private List<String[]> patientsRecords = new ArrayList<>();
    private final PatientRouter router;

    public PatientTableModel(PatientRouter router){
        this.router = router;
    }

    @Override
    public int getRowCount() {
        return patientsRecords.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return patientsRecords.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {return "id";}
            case 1 -> {return "name";}
            case 2 -> {return "phone";}
        }

        return "";
    }

    public void addRecord(Patient patient) { patientsRecords.add(patient.toString().split(",")); }

    public List<String[]> getPatientsRecords() {
        return patientsRecords;
    }

    public void clearData() {
        patientsRecords.clear();
    }

    public void setPatientsRecords(List<String[]> data) {
        patientsRecords = new ArrayList<>(data);
    }

    public void updateRecord(Patient patient) {
        var record = patientsRecords.stream()
                .filter(item -> String.valueOf(patient.getId()).equals(item[0]))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        record[1] = patient.getName();
        record[2] = patient.getPhone();
    }

    public void deleteRecord(int id) {
        var record = patientsRecords.stream()
                .filter(item -> String.valueOf(id).equals(item[0]))
                .findFirst()
                .orElseThrow();

        patientsRecords.remove(record);
    }

    public boolean findRecordByColumn(String value, int column) {
        try {
            var record = patientsRecords.stream()
                    .filter(item -> String.valueOf(value).equals(item[column]))
                    .findFirst()
                    .orElseThrow();
            return true;
        } catch (Exception error) {
            return false;
        }
    }
}
