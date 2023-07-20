package com.patikaTourismAgencySystem.helper;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class DatePicker extends JPanel {
    private final JDateChooser dateChooser;

    public DatePicker() {
        setLayout(new BorderLayout());
        dateChooser = new JDateChooser();
        add(dateChooser, BorderLayout.CENTER);
    }

    public String getDate() {
        return dateChooser.getDate().toString();
    }
}
