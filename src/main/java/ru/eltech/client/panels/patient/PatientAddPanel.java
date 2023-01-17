package ru.eltech.client.panels.patient;

import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.service.CustomGridBagConstraint;
import ru.eltech.client.request.PatientCreateRequest;
import ru.eltech.client.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PatientAddPanel extends JPanel {
    public PatientAddPanel(PatientRouter router, PatientTablePanel patientTable) {
        JLabel nameLabel = new JLabel("Введите имя");
        JTextField nameText = new JTextField(20);
        JLabel nameError = new JLabel();
        JLabel phoneLabel = new JLabel("Введите номер телефона");
        JTextField phoneText = new JTextField(20);
        JLabel phoneError = new JLabel();

        JButton send = new JButton("Добавить");
        send.addActionListener(new PatientAddPanel.PatientAddListener(router, patientTable, nameText, phoneText, nameError, phoneError));

        setLayout(new GridBagLayout());

        add(nameLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(nameText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(nameError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(phoneLabel, new CustomGridBagConstraint(0, 2, 1, 1));
        add(phoneText, new CustomGridBagConstraint(1, 2, 1, 1));
        add(phoneError, new CustomGridBagConstraint(0, 3, 2, 1));

        add(send, new CustomGridBagConstraint(0, 4, 2, 1));
    }

    private record PatientAddListener(PatientRouter router,
                                      PatientTablePanel patientTable,
                                      JTextField nameText,
                                      JTextField phoneText,
                                      JLabel nameError,
                                      JLabel phoneError)
            implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            PatientCreateRequest request = new PatientCreateRequest();

            String name = nameText.getText().trim();
            String phone = phoneText.getText().trim();

            if (name.isEmpty() || name.length() > 50) {
                nameError.setText("Длина имени должна быть от 1 до 50 символов");
                return;
            } else {
                nameError.setText("");
            }

            if (!ValidationService.checkPhone(phone)) {
                phoneError.setText("Неверный формат ввода телефона. Должен начинаться с 7");
                return;
            } else {
                phoneError.setText("");
            }

            if(patientTable.getPtModel().findRecordByColumn(phone, 2)) {
                phoneError.setText("Пациент с таким телефоном уже существует");
                return;
            } else {
                phoneError.setText("");
            }

            request.setName(name);
            request.setPhone(phone);

            try {
                patientTable.getPtModel().addRecord(router.createPatient(request));
                patientTable.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                nameText.setText("");
                phoneText.setText("");
            }
        }
    }
}
