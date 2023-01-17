package ru.eltech.client.panels.patient;

import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.tableModels.PatientTableModel;
import ru.eltech.client.model.Patient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PatientTablePanel extends JPanel {
    private final PatientRouter router;
    private final PatientTableModel ptModel;
    private final JScrollPane scrollPane;
    private final JTable patientTable;

    public PatientTablePanel(PatientRouter router) throws IOException {
        this.router = router;

        ptModel = new PatientTableModel(router);
        patientTable = new JTable(ptModel);
        scrollPane = new JScrollPane(patientTable);

        setLayout(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);

        update();
    }

    public PatientTableModel getPtModel() {
        return ptModel;
    }

    public void update() throws IOException {
        List<Patient> patients = router.getAllPatients();

        if (patients.isEmpty()) {
            return;
        }

        var data = patients.stream()
                .map(Patient::toString)
                .map(record -> record.split(","))
                .toList();

        ptModel.clearData();
        ptModel.fireTableDataChanged();

        ptModel.setPatientsRecords(data);
        ptModel.fireTableDataChanged();
    }
}
