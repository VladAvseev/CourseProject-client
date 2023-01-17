package ru.eltech.client;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.frame.MainFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.IOException;

public class Client {

    private final DoctorRouter doctorRouter;
    private final PatientRouter patientRouter;
    private final TicketRouter ticketRouter;

    public Client(DoctorRouter doctorRouter, PatientRouter patientRouter, TicketRouter ticketRouter) {
        this.doctorRouter = doctorRouter;
        this.patientRouter = patientRouter;
        this.ticketRouter = ticketRouter;
    }

    public void run() throws IOException {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Error: NimbusLookAndFeel");
        }
        MainFrame frame = new MainFrame(doctorRouter, patientRouter, ticketRouter);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
