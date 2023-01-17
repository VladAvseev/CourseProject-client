package ru.eltech.client.tableModels;

import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.model.Ticket;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TicketTableModel extends AbstractTableModel {
    private final int columnCount = 4;

    private List<String[]> ticketRecords = new ArrayList<>();

    private final TicketRouter router;

    public TicketTableModel(TicketRouter router) { this.router = router;}

    @Override
    public int getRowCount() { return ticketRecords.size();}

    @Override
    public int getColumnCount() { return columnCount; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { return ticketRecords.get(rowIndex)[columnIndex]; }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {return "id";}
            case 1 -> {return "doctor_name";}
            case 2 -> {return "patient_name";}
            case 3 -> {return "diagnosis";}
        }
        return "";
    }

    public void addRecord(Ticket ticket) { ticketRecords.add(ticket.toString().split(",")); }

    public List<String[]> getTicketsRecords() {
        return ticketRecords;
    }

    public void clearData() {
        ticketRecords.clear();
    }

    public void setTicketsRecords(List<String[]> data) { ticketRecords = new ArrayList<>(data); }

    public void updateRecord(Ticket ticket) {
        var record = ticketRecords.stream()
                .filter(item -> String.valueOf(ticket.getId()).equals(item[0]))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        record[1] = ticket.getDoctorName();
        record[2] = ticket.getPatientName();
        record[3] = ticket.getDiagnosis();
    }

    public void deleteRecord(int id) {
        var record = ticketRecords.stream()
                .filter(item -> String.valueOf(id).equals(item[0]))
                .findFirst()
                .orElseThrow();

        ticketRecords.remove(record);
    }

    public boolean findRecordByColumn(String value, int column) {
        try {
            var record = ticketRecords.stream()
                    .filter(item -> String.valueOf(value).equals(item[column]))
                    .findFirst()
                    .orElseThrow();
            return true;
        } catch (Exception error) {
            return false;
        }
    }
}
