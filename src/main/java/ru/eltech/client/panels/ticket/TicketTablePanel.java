package ru.eltech.client.panels.ticket;

import ru.eltech.client.api.TicketRouter;
import ru.eltech.client.model.Ticket;
import ru.eltech.client.tableModels.TicketTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TicketTablePanel extends JPanel {
    private final TicketRouter router;
    private final TicketTableModel ttModel;
    private final JScrollPane scrollPane;
    private final JTable ticketTable;

    public TicketTablePanel(TicketRouter router) throws IOException {
        this.router = router;

        ttModel = new TicketTableModel(router);
        ticketTable = new JTable(ttModel);
        scrollPane = new JScrollPane(ticketTable);

        setLayout(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);

        update();
    }

    public TicketTableModel getTtModel() {
        return ttModel;
    }

    public void update() throws IOException {
        List<Ticket> tickets = router.getAllTickets();

        if (tickets.isEmpty()) {
            return;
        }

        var data = tickets.stream()
                .map(Ticket::toString)
                .map(record -> record.split(","))
                .toList();

        ttModel.clearData();
        ttModel.fireTableDataChanged();

        ttModel.setTicketsRecords(data);
        ttModel.fireTableDataChanged();
    }
}
