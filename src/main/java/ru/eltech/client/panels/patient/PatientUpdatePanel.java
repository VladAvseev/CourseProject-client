package ru.eltech.client.panels.patient;

import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.service.CustomGridBagConstraint;
import ru.eltech.client.request.PatientUpdateRequest;
import ru.eltech.client.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PatientUpdatePanel extends JPanel {
    private final PatientRouter router;

    public PatientUpdatePanel(PatientRouter router, PatientTablePanel patientTablePanel) {
        this.router = router;

        JLabel idLabel = new JLabel("Введите id");
        JTextField idText = new JTextField(20);
        JLabel idError = new JLabel("");
        JLabel nameLabel = new JLabel("Введите имя");
        JTextField nameText = new JTextField(20);
        JLabel nameError = new JLabel("");
        JLabel phoneLabel = new JLabel("Введите номер телефона");
        JTextField phoneText = new JTextField(20);
        JLabel phoneError = new JLabel("");
        JLabel emailLabel = new JLabel("Введите почту");
        JTextField emailText = new JTextField(20);
        JLabel emailError = new JLabel("");

        JButton edit = new JButton("Изменить");
        edit.addActionListener(new PatientUpdateListener(router, patientTablePanel, idText, nameText, phoneText, emailText, idError, nameError, phoneError, emailError));

        setLayout(new GridBagLayout());

        add(idLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(idText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(idError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(nameLabel, new CustomGridBagConstraint(0, 2, 1, 1));
        add(nameText, new CustomGridBagConstraint(1, 2, 1, 1));
        add(nameError, new CustomGridBagConstraint(0, 3, 2, 1));

        add(phoneLabel, new CustomGridBagConstraint(0, 4, 1, 1));
        add(phoneText, new CustomGridBagConstraint(1, 4, 1, 1));
        add(phoneError, new CustomGridBagConstraint(0, 5, 2, 1));

        add(emailLabel, new CustomGridBagConstraint(0, 6, 1, 1));
        add(emailText, new CustomGridBagConstraint(1, 6, 1, 1));
        add(emailError, new CustomGridBagConstraint(0, 7, 2, 1));

        add(edit, new CustomGridBagConstraint(0, 8, 2, 1));
    }

    private record PatientUpdateListener(PatientRouter router,
                                        PatientTablePanel patientTable,
                                        JTextField idText,
                                        JTextField nameText,
                                        JTextField phoneText,
                                        JTextField emailText,
                                         JLabel idError,
                                         JLabel nameError,
                                         JLabel phoneError,
                                         JLabel emailError)
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            PatientUpdateRequest request = new PatientUpdateRequest();

            int id = 0;
            if (idText.getText().trim().length() > 0) {
                id = Integer.parseInt(idText.getText().trim());
            }
            String name = nameText.getText().trim();
            String phone = phoneText.getText().trim();
            String email = emailText.getText().trim();

            if(!patientTable.getPtModel().findRecordByColumn(Integer.toString(id), 0)) {
                idError.setText("Пациент с таким id не найден");
                return;
            } else {
                idError.setText("");
            }

            if (name.isEmpty() || name.length() > 50) {
                nameError.setText("Длина имени должна быть от 1 до 50 символов");
                return;
            } else {
                nameError.setText("");
            }

            if (email.isEmpty() || email.length() > 50) {
                emailError.setText("Длина почты должна быть от 1 до 50 символов");
                return;
            } else {
                emailError.setText("");
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

            request.setId(id);
            request.setName(name);
            request.setPhone(phone);
            request.setEmail(email);

            try {
                patientTable.getPtModel().updateRecord(router.updatePatient(request));
                patientTable.update();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            }
            finally {
                idText.setText("");
                nameText.setText("");
                phoneText.setText("");
                emailText.setText("");
            }
        }
    }
}
