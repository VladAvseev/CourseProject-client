package ru.eltech.client.panels.ticket;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.api.TicketRouter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TicketsPanel extends JPanel {
    public TicketsPanel(TicketRouter router, DoctorRouter doctorRouter, PatientRouter patientRouter) throws IOException {
        setLayout(new BorderLayout());

        JPanel commandPanel = new JPanel();

        commandPanel.setLayout(new GridLayout(2, 1));

        TicketTablePanel ticketTablePanel = new TicketTablePanel(router);

        commandPanel.add(new TicketAddPanel(router, doctorRouter, patientRouter, ticketTablePanel));
        commandPanel.add(new TicketDeletePanel(router, ticketTablePanel));

        add(commandPanel, BorderLayout.LINE_START);
        add(ticketTablePanel, BorderLayout.CENTER);
    }
}