package ru.eltech.client.panels.patient;

import ru.eltech.client.api.PatientRouter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PatientsPanel extends JPanel {
    public PatientsPanel(PatientRouter router) throws IOException {
        setLayout(new BorderLayout());

        JPanel commandPanel = new JPanel();

        commandPanel.setLayout(new GridLayout(3, 1));

        PatientTablePanel patientTablePanel = new PatientTablePanel(router);

        commandPanel.add(new PatientAddPanel(router, patientTablePanel));
        commandPanel.add(new PatientUpdatePanel(router, patientTablePanel));
        commandPanel.add(new PatientDeletePanel(router, patientTablePanel));

        add(commandPanel, BorderLayout.LINE_START);
        add(patientTablePanel, BorderLayout.CENTER);
    }
}
