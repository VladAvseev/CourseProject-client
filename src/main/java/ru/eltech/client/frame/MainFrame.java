package ru.eltech.client.frame;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.panels.doctor.DoctorsPanel;
import ru.eltech.client.panels.patient.PatientsPanel;
import ru.eltech.client.panels.ticket.TicketsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private final DoctorRouter doctorRouter;
    private final PatientRouter patientRouter;

    private final TicketRouter ticketRouter;
    public MainFrame(DoctorRouter doctorRouter, PatientRouter patientRouter, TicketRouter ticketRouter) throws HeadlessException, IOException {
        super("Patient Record System");
        this.doctorRouter = doctorRouter;
        this.patientRouter = patientRouter;
        this.ticketRouter = ticketRouter;

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Doctors", new DoctorsPanel(doctorRouter));
        tabbedPane.addTab("Patient", new PatientsPanel(patientRouter));
        tabbedPane.addTab("Tickets", new TicketsPanel(ticketRouter, doctorRouter, patientRouter));
        setLayout(new BorderLayout());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
