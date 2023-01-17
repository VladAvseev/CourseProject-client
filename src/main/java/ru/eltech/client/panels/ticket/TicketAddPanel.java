package ru.eltech.client.panels.ticket;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.service.CustomGridBagConstraint;
import ru.eltech.client.model.Doctor;
import ru.eltech.client.model.Patient;
import ru.eltech.client.request.TicketCreateRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TicketAddPanel extends JPanel {
    public TicketAddPanel(TicketRouter router, DoctorRouter doctorRouter, PatientRouter patientRouter, TicketTablePanel ticketTable) {
        JLabel doctorIdLabel = new JLabel("Введите id доктора");
        JTextField doctorIdText = new JTextField(20);
        JLabel doctorIdError = new JLabel("");
        JLabel patientIdLabel = new JLabel("Введите id пациента");
        JTextField patientIdText = new JTextField(20);
        JLabel patientIdError = new JLabel("");
        JLabel diagnosisLabel = new JLabel("Диагноз");
        JTextArea diagnosisText = new JTextArea(6, 30);
        JLabel diagnosisError = new JLabel("");

        JButton send = new JButton("Создать");
        send.addActionListener(new TicketAddListener(
                router,
                doctorRouter,
                patientRouter,
                ticketTable,
                doctorIdText,
                patientIdText,
                diagnosisText,
                doctorIdError,
                patientIdError,
                diagnosisError
        ));

        setLayout(new GridBagLayout());

        add(doctorIdLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(doctorIdText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(doctorIdError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(patientIdLabel, new CustomGridBagConstraint(0, 2, 1, 1));
        add(patientIdText, new CustomGridBagConstraint(1, 2, 1, 1));
        add(patientIdError, new CustomGridBagConstraint(0, 3, 2, 1));

        add(diagnosisLabel, new CustomGridBagConstraint(0, 4, 2, 1));
        add(diagnosisText, new CustomGridBagConstraint(0, 5, 2, 6));
        add(diagnosisError, new CustomGridBagConstraint(0, 11, 2, 1));

        add(send, new CustomGridBagConstraint(0, 12, 2, 1));
    }

    private record TicketAddListener(TicketRouter router,
                                     DoctorRouter doctorRouter,
                                     PatientRouter patientRouter,
                                     TicketTablePanel ticketTable,
                                     JTextField doctorIdText,
                                     JTextField patientIdText,
                                     JTextArea diagnosisText,
                                     JLabel doctorIdError,
                                     JLabel patientIdError,
                                     JLabel diagnosisError)
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            TicketCreateRequest request = new TicketCreateRequest();

            int doctorId = 0;
            if (doctorIdText.getText().trim().length() > 0) {
                doctorId = Integer.parseInt(doctorIdText.getText().trim());
            }
            int patientId = 0;
            if (doctorIdText.getText().trim().length() > 0) {
                patientId = Integer.parseInt(patientIdText.getText().trim());
            }
            String diagnosis = diagnosisText.getText().trim();

            if (diagnosis.isEmpty()) {
                diagnosisError.setText("Это обязательное поле");
                return;
            }

            request.setDoctorId(doctorId);
            request.setPatientId(patientId);
            request.setDiagnosis(diagnosis);

            Doctor doctor;

            try {
                doctor = doctorRouter.getDoctorById(doctorId);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (doctor == null) {
                doctorIdError.setText("Доктор не найден");
                return;
            } else {
                doctorIdError.setText("");
            }

            Patient patient;

            try {
                patient = patientRouter.getPatientById(patientId);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (patient == null) {
                patientIdError.setText("Пациент не найден");
                return;
            } else {
                patientIdError.setText("");
            }

            try {
                ticketTable.getTtModel().addRecord(router.createTicket(request));
                ticketTable.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                doctorIdText.setText("");
                patientIdText.setText("");
                diagnosisText.setText("");

                patientIdError.setText("");
            }
        }
    }
}
