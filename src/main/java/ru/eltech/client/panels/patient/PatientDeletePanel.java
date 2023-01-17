package ru.eltech.client.panels.patient;

import ru.eltech.client.api.PatientRouter;
import ru.eltech.client.service.CustomGridBagConstraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PatientDeletePanel extends JPanel {
    public PatientDeletePanel(PatientRouter router, PatientTablePanel patientTablePanel) {
        JLabel idLabel = new JLabel("Введите id");
        JTextField idText = new JTextField(20);
        JLabel idError = new JLabel("");

        JButton delete = new JButton("Удалить");
        delete.addActionListener(new PatientDeletePanel.PatientDeleteListener(router, patientTablePanel, idText, idError));

        setLayout(new GridBagLayout());

        add(idLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(idText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(idError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(delete, new CustomGridBagConstraint(0, 2, 2, 1));
    }

    private record PatientDeleteListener(PatientRouter router,
                                        PatientTablePanel patientTable,
                                        JTextField idText,
                                         JLabel idError)
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int id = 0;
            if (idText.getText().trim().length() > 0) {
                id = Integer.parseInt(idText.getText().trim());
            }

            if(!patientTable.getPtModel().findRecordByColumn(Integer.toString(id), 0)) {
                idError.setText("Пациент с таким id не найден");
                return;
            } else {
                idError.setText("");
            }

            try {
                router.deletePatient(id);
                patientTable.getPtModel().deleteRecord(id);
                patientTable.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                idText.setText("");
            }
        }
    }
}
