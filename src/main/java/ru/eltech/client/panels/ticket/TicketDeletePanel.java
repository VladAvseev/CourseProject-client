package ru.eltech.client.panels.ticket;

import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.service.CustomGridBagConstraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TicketDeletePanel extends JPanel {
    public TicketDeletePanel(TicketRouter router, TicketTablePanel ticketTablePanel) {
        JLabel idLabel = new JLabel("Введите id");
        JTextField idText = new JTextField(20);
        JLabel idError = new JLabel("");

        JButton delete = new JButton("Удалить");
        delete.addActionListener(new TicketDeletePanel.TicketDeleteListener(router, ticketTablePanel, idText, idError));

        setLayout(new GridBagLayout());

        add(idLabel, new CustomGridBagConstraint(0, 0, 1, 1));
        add(idText, new CustomGridBagConstraint(1, 0, 1, 1));
        add(idError, new CustomGridBagConstraint(0, 1, 2, 1));

        add(delete, new CustomGridBagConstraint(0, 2, 2, 1));
    }

    private record TicketDeleteListener(TicketRouter router,
                                         TicketTablePanel ticketTable,
                                         JTextField idText,
                                         JLabel idError)
            implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int id = 0;
            if (idText.getText().trim().length() > 0) {
                id = Integer.parseInt(idText.getText().trim());
            }

            if(!ticketTable.getTtModel().findRecordByColumn(Integer.toString(id), 0)) {
                idError.setText("талон с таким id не найден");
                return;
            } else {
                idError.setText("");
            }

            try {
                router.deleteTicket(id);
                ticketTable.getTtModel().deleteRecord(id);
                ticketTable.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                idText.setText("");
            }
        }
    }
}