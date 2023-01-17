package ru.eltech.client.panels.doctor;

import ru.eltech.client.api.DoctorRouter;
import ru.eltech.client.tableModels.DoctorTableModel;
import ru.eltech.client.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class DoctorTablePanel extends JPanel{
    private final DoctorRouter router;
    private final DoctorTableModel dtModel;
    private final JScrollPane scrollPane;
    private final JTable doctorTable;

    public DoctorTablePanel(DoctorRouter router) throws IOException {
        this.router = router;

        dtModel = new DoctorTableModel(router);
        doctorTable = new JTable(dtModel);
        scrollPane = new JScrollPane(doctorTable);

        setLayout(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);

        update();
    }

    public DoctorTableModel getDtModel() {
        return dtModel;
    }

    public void update() throws IOException {
       List<Doctor> doctors =  router.getAllDoctors();

       if (doctors.isEmpty()) {
           return;
       }

       var data = doctors.stream()
               .map(Doctor::toString)
               .map(record -> record.split(",")).toList();

       dtModel.clearData();
       dtModel.fireTableDataChanged();

       dtModel.setDoctorsRecords(data);
       dtModel.fireTableDataChanged();
    }
}
