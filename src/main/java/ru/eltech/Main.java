package ru.eltech;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DoctorRouter doctorRouter = new DoctorRouter();
        PatientRouter patientRouter = new PatientRouter();
        TicketRouter ticketRouter = new TicketRouter();

        Client client = new Client(doctorRouter, patientRouter, ticketRouter);
        client.run();
    }
}