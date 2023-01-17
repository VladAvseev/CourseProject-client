package ru.eltech.client.panels.doctor;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.service.CustomGridBagConstraint;
import ru.eltech.client.request.DoctorUpdateRequest;
import ru.eltech.client.service.ValidationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DoctorUpdatePanel extends JPanel {
    private final DoctorRouter router;

    public DoctorUpdatePanel(DoctorRouter router, DoctorTablePanel doctorTablePanel) {
        this.router = router;

        JLabel idLabel = new JLabel("Введите id");
        JTextField idText = new JTextField(20);
        JLabel idError = new JLabel("");
        JLabel nameLabel = new JLabel("Введите имя");
        JTextField nameText = new JTextField(20);
        JLabel nameError = new JLabel("");
        JLabel licenseLabel = new JLabel("Введите лицензию");
        JTextField licenseText = new JTextField(20);
        JLabel licenseError = new JLabel("");

        JButton edit = new JButton("Изменить");
        edit.addActionListener(new DoctorUpdateListener(router, doctorTablePanel, idText, nameText, licenseText, idError, nameError, licenseError));

        setLayout(new GridBagLayout());

        add(idLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(idText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(idError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(nameLabel, new CustomGridBagConstraint(0, 2, 1, 1));
        add(nameText, new CustomGridBagConstraint(1, 2, 1, 1));
        add(nameError, new CustomGridBagConstraint(0, 3, 2, 1));

        add(licenseLabel, new CustomGridBagConstraint(0, 4, 1, 1));
        add(licenseText, new CustomGridBagConstraint(1, 4, 1, 1));
        add(licenseError, new CustomGridBagConstraint(0, 5, 2, 1));

        add(edit, new CustomGridBagConstraint(0, 6, 2, 1));
    }

    private record DoctorUpdateListener(DoctorRouter router,
                                        DoctorTablePanel doctorTable,
                                        JTextField idText,
                                        JTextField nameText,
                                        JTextField licenseText,
                                        JLabel idError,
                                        JLabel nameError,
                                        JLabel licenseError)
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            DoctorUpdateRequest request = new DoctorUpdateRequest();

            int id = 0;
            if (idText.getText().trim().length() > 0) {
                id = Integer.parseInt(idText.getText().trim());
            }
            String name = nameText.getText().trim();
            String license = licenseText.getText().trim();

            if(!doctorTable.getDtModel().findRecordByColumn(Integer.toString(id), 0)) {
                idError.setText("Доктор с таким id не найден");
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

            request.setId(id);
            request.setName(name);
            request.setLicense(license);

            try {
                doctorTable.getDtModel().updateRecord(router.updateDoctor(request));
                doctorTable.update();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            }
            finally {
                idText.setText("");
                nameText.setText("");
                licenseText.setText("");
            }
        }
    }
}
