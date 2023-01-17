package ru.eltech.client.panels.doctor;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.service.CustomGridBagConstraint;
import ru.eltech.client.request.DoctorCreateRequest;
import ru.eltech.client.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DoctorAddPanel extends JPanel {
    public DoctorAddPanel(DoctorRouter router, DoctorTablePanel doctorTable) {
        JLabel nameLabel = new JLabel("Введите имя");
        JTextField nameText = new JTextField(20);
        JLabel nameError = new JLabel();
        JLabel licenseLabel = new JLabel("Введите лицензию");
        JTextField licenseText = new JTextField(20);
        JLabel licenseError = new JLabel();

        JButton send = new JButton("Добавить");
        send.addActionListener(new DoctorAddListener(router, doctorTable, nameText, licenseText, nameError, licenseError));

        setLayout(new GridBagLayout());

        add(nameLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(nameText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(nameError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(licenseLabel, new CustomGridBagConstraint(0, 2, 1, 1));
        add(licenseText, new CustomGridBagConstraint(1, 2, 1, 1));
        add(licenseError, new CustomGridBagConstraint(0, 3, 2, 1));

        add(send, new CustomGridBagConstraint(0, 4, 2, 1));
    }

    private record DoctorAddListener(DoctorRouter router,
                                     DoctorTablePanel doctorTable,
                                     JTextField nameText,
                                     JTextField licenseText,
                                     JLabel nameError,
                                     JLabel licenseError)
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            DoctorCreateRequest request = new DoctorCreateRequest();

            String name = nameText.getText().trim();
            String license = licenseText.getText().trim();

            if (name.isEmpty() || name.length() > 50) {
                nameError.setText("Длина имени должна быть от 1 до 50 символов");
                return;
            } else {
                nameError.setText("");
            }

            if (!ValidationService.checkLicense(license)) {
                licenseError.setText("Неверный формат ввода лицензии (LNNNLLNNN)");
                return;
            } else {
                licenseError.setText("");
            }

            if(doctorTable.getDtModel().findRecordByColumn(license, 2)) {
                licenseError.setText("Доктор с такой лицензией уже существует");
                return;
            } else {
                licenseError.setText("");
            }

            request.setName(name);
            request.setLicense(license);

            try {
                doctorTable.getDtModel().addRecord(router.createDoctor(request));
                doctorTable.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                nameText.setText("");
                licenseText.setText("");
            }
        }
    }
}
