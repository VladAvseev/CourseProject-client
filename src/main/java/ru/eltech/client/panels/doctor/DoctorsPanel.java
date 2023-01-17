package ru.eltech.client.panels.doctor;

import ru.eltech.client.api.DoctorRouter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DoctorsPanel extends JPanel{

    public DoctorsPanel(DoctorRouter router) throws IOException {
        setLayout(new BorderLayout());

        JPanel commandPanel = new JPanel();;

        commandPanel.setLayout(new GridLayout(3,1));

        DoctorTablePanel doctorTablePanel = new DoctorTablePanel(router);

        commandPanel.add(new DoctorAddPanel(router, doctorTablePanel));
        commandPanel.add(new DoctorUpdatePanel(router, doctorTablePanel));
        commandPanel.add(new DoctorDeletePanel(router, doctorTablePanel));

        add(commandPanel, BorderLayout.LINE_START);
        add(doctorTablePanel, BorderLayout.CENTER);
    }
}
