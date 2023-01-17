package ru.eltech.client.service;

import java.awt.*;

public class CustomGridBagConstraint extends GridBagConstraints {
    public CustomGridBagConstraint(int gridx, int gridy, int gridwidth, int gridheight) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }
}
