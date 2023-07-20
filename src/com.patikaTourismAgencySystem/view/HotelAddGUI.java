package com.patikaTourismAgencySystem.view;

import com.patikaTourismAgencySystem.helper.Helper;
import com.patikaTourismAgencySystem.model.Hotel;
import com.patikaTourismAgencySystem.model.Season;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HotelAddGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JTextField fld_name;
    private JTextField fld_country;
    private JTextField fld_region;
    private JTextField fld_city;
    private JTextField fld_district;
    private JTextField fld_address;
    private JTextField fld_e_mail;
    private JTextField fld_phone;
    private JCheckBox cb_free_parking;
    private JCheckBox cb_free_wifi;
    private JCheckBox cb_swimming_pool;
    private JCheckBox cb_fitness;
    private JCheckBox cb_concierge;
    private JCheckBox cb_spa;
    private JCheckBox cb_room_service;
    private JComboBox cmb_stars;
    private JButton btn_new_hotel_add;
    private JButton btn_new_hotel_fields_clear;
    private JButton btn_new_hotel_cancel;
    private JTextField fld_season_start_date;
    private JTextField fld_season_end_date;
    private JButton addSeasonsButton;
    private JButton btn_clear_season1_label;
    private JButton btn_clear_season2_label;
    private JButton btn_clear_season3_label;
    private JLabel lbl_s1_start;
    private JLabel lbl_s1_end;
    private JLabel lbl_s2_start;
    private JLabel lbl_s2_end;
    private JLabel lbl_s3_start;
    private JLabel lbl_s3_end;

    public HotelAddGUI(JFrame previousFrame) {
        previousFrame.setEnabled(false);
        int guiWidth = 920, guiHeight = 680;

        setContentPane(wrapper);
        setTitle("Add A New Hotel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(guiWidth, guiHeight);
        setMinimumSize(new Dimension(guiWidth, guiHeight));
        setLocation((Helper.SCREEN_WIDTH - guiWidth) / 2, (Helper.SCREEN_HEIGHT - guiHeight) / 2);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                previousFrame.setEnabled(true);
            }
        });

        setSeasonLabelText(lbl_s1_start, lbl_s1_end, "-", "-");
        setSeasonLabelText(lbl_s2_start, lbl_s2_end, "-", "-");
        setSeasonLabelText(lbl_s3_start, lbl_s3_end, "-", "-");

        JDialog dateDialog = new JDialog();
        JCalendar calendar = new JCalendar();

        calendar.setMinSelectableDate(new Date());
        calendar.getDayChooser().setDay(0);

        dateDialog.add(calendar);
        dateDialog.setSize(400, 400);


        fld_season_start_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dateDialog.setVisible(true);
                dateDialog.setLocationRelativeTo(fld_season_start_date);
                setThisFrameEnableState(false);
            }
        });
        calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
            setThisFrameEnableState(true);
            Date selectedDate = calendar.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = dateFormat.format(selectedDate);
            fld_season_start_date.setText(formattedDate);
            dateDialog.setVisible(false);
        });


        dateDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setThisFrameEnableState(true);
                dateDialog.dispose();
            }
        });

        JDialog dateDialog2 = new JDialog();
        JCalendar calendar2 = new JCalendar();

        calendar2.setMinSelectableDate(new Date());
        calendar2.getDayChooser().setDay(0);

        dateDialog2.add(calendar2);
        dateDialog2.setSize(450, 300);

        fld_season_end_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dateDialog2.setVisible(true);
                dateDialog2.setLocationRelativeTo(fld_season_end_date);
                setThisFrameEnableState(false);
            }
        });
        calendar2.getDayChooser().addPropertyChangeListener("day", evt -> {
            setThisFrameEnableState(true);
            Date selectedDate = calendar2.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = dateFormat.format(selectedDate);
            fld_season_end_date.setText(formattedDate);
            dateDialog2.setVisible(false);
        });

        dateDialog2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setThisFrameEnableState(true);
                dateDialog2.dispose();
            }
        });

        addSeasonsButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_season_start_date, fld_season_end_date)) {
                Helper.showMessage("You have not entered the start and end dates.", "Missing Date Info");
            } else {
                String startDateText = fld_season_start_date.getText();
                String endDateText = fld_season_end_date.getText();
                DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate startDate = LocalDate.parse(startDateText, dtFormatter);
                LocalDate endDate = LocalDate.parse(endDateText, dtFormatter);
                if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
                    Helper.showMessage("The start date cannot be the same or later than the end date!", "Invalid Entry!");
                    return;
                } else if (endDate.isEqual(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
                    Helper.showMessage("Past seasons cannot be recorded!", "Invalid Entry!");
                }

                String[] startDates = {lbl_s1_start.getText(), lbl_s2_start.getText(), lbl_s3_start.getText()};
                String[] endDates = {lbl_s1_end.getText(), lbl_s2_end.getText(), lbl_s3_end.getText()};

                boolean datesAreAvailable = true;
                for (int i = 0; i < startDates.length; i++) {
                    if (!startDates[i].equals("-")) {
                        LocalDate labelStartDate = LocalDate.parse(startDates[i], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        LocalDate labelEndDate = LocalDate.parse(endDates[i], DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                        if (startDate.isEqual(labelStartDate) || startDate.isEqual(labelEndDate) || endDate.isEqual(labelStartDate) || endDate.isEqual(labelEndDate)) {
                            Helper.showMessage("Season start or end dates cannot be the same!", "Invalid Entry!");
                            datesAreAvailable = false;
                            break;
                        } else if ((startDate.isAfter(labelStartDate) && startDate.isBefore(labelEndDate))
                                || (endDate.isAfter(labelStartDate) && endDate.isBefore(labelEndDate))
                                || (startDate.isBefore(labelStartDate) && endDate.isAfter(labelEndDate))) {
                            Helper.showMessage("Seasons cannot contain the same days!", "Invalid Entry!");
                            datesAreAvailable = false;
                            break;
                        }
                    }
                }

                if (datesAreAvailable) {
                    if (lbl_s1_start.getText().equals("-")) {
                        setSeasonLabelText(lbl_s1_start, lbl_s1_end, startDateText, endDateText);
                    } else if (lbl_s2_start.getText().equals("-")) {
                        setSeasonLabelText(lbl_s2_start, lbl_s2_end, startDateText, endDateText);
                    } else if (lbl_s3_start.getText().equals("-")) {
                        setSeasonLabelText(lbl_s3_start, lbl_s3_end, startDateText, endDateText);
                    } else {
                        Helper.showMessage("No more than 3 seasons can be recorded!", "Season registrations are full!");
                    }
                }
            }
            sortSeasons();
        });

        btn_clear_season1_label.addActionListener(e -> {
            if (!lbl_s1_start.getText().equals("-")) {
                String message = "Season 1 (" + lbl_s1_start.getText() + "-" + lbl_s1_end.getText() + ") will be deleted!\n\nDo you approve?";
                if (Helper.confirm(message, "Attention!")) {
                    lbl_s1_start.setText("-");
                    lbl_s1_end.setText("-");
                    sortSeasons();
                }
            }
        });

        btn_clear_season2_label.addActionListener(e -> {
            if (!lbl_s2_start.getText().equals("-")) {
                String message = "Season 2 (" + lbl_s2_start.getText() + "-" + lbl_s2_end.getText() + ") will be deleted!\n\nDo you approve?";
                if (Helper.confirm(message, "Attention!")) {
                    lbl_s2_start.setText("-");
                    lbl_s2_end.setText("-");
                    sortSeasons();
                }
            }
        });

        btn_clear_season3_label.addActionListener(e -> {
            if (!lbl_s3_start.getText().equals("-")) {
                String message = "Season 3 (" + lbl_s3_start.getText() + "-" + lbl_s3_end.getText() + ") will be deleted!\n\nDo you approve?";
                if (Helper.confirm(message, "Attention!")) {
                    lbl_s3_start.setText("-");
                    lbl_s3_end.setText("-");
                    sortSeasons();
                }
            }
        });

        btn_new_hotel_cancel.addActionListener(e -> {
            if (Helper.confirm("Are you sure you want to cancel the transaction?", "Cancel Transaction!")) {
                previousFrame.setEnabled(true);
                dispose();
            }
        });

        btn_new_hotel_fields_clear.addActionListener(e -> {
            if (Helper.confirm("All the data you entered will be deleted!\nDo you agree?", "Attention!")) {
                fld_name.setText(null);
                fld_country.setText(null);
                fld_region.setText(null);
                fld_city.setText(null);
                fld_district.setText(null);
                fld_address.setText(null);
                fld_e_mail.setText(null);
                fld_phone.setText(null);

                cb_free_parking.setSelected(false);
                cb_free_wifi.setSelected(false);
                cb_fitness.setSelected(false);
                cb_room_service.setSelected(false);
                cb_swimming_pool.setSelected(false);
                cb_concierge.setSelected(false);
                cb_spa.setSelected(false);

                cmb_stars.setSelectedIndex(0);

                fld_season_start_date.setText(null);
                fld_season_end_date.setText(null);
                lbl_s1_start.setText("-");
                lbl_s2_start.setText("-");
                lbl_s3_start.setText("-");
                lbl_s1_end.setText("-");
                lbl_s2_end.setText("-");
                lbl_s3_end.setText("-");
            }
        });

        btn_new_hotel_add.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_name, fld_country, fld_region, fld_city, fld_district, fld_address, fld_e_mail, fld_phone)) {
                Helper.showMessage("Please fill in all fields.", "Incomplete Data Entry!");
                return;
            }

            String name = fld_name.getText();
            if (name.length() > 100) {
                Helper.showMessage("The hotel name can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String country = fld_country.getText();
            if (country.length() > 100) {
                Helper.showMessage("The country name can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String region = fld_region.getText();
            if (region.length() > 100) {
                Helper.showMessage("The region name can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String city = fld_city.getText();
            if (city.length() > 100) {
                Helper.showMessage("The city name can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String district = fld_district.getText();
            if (district.length() > 100) {
                Helper.showMessage("The district name can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String address = fld_address.getText();
            if (address.length() > 255) {
                Helper.showMessage("The address can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String eMail = fld_e_mail.getText();
            if (eMail.length() > 100) {
                Helper.showMessage("E-mail can be up to 100 characters.", "Name Size Error!");
                return;
            }

            String phone = fld_phone.getText();
            if (phone.length() > 20) {
                Helper.showMessage("Phone number can be up to 20 characters.", "Name Size Error!");
                return;
            }

            byte star;
            if (cmb_stars.getSelectedIndex() == 0) {
                Helper.showMessage("Select how many stars the hotel has.", "Hotel Star Error!");
                return;
            } else star = Byte.parseByte(cmb_stars.getSelectedItem().toString());

            ArrayList<Season> seasons = new ArrayList<>();
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            if (!lbl_s1_start.getText().equals("-"))
                seasons.add(new Season(LocalDate.parse(lbl_s1_start.getText(), dtFormatter), LocalDate.parse(lbl_s1_end.getText(), dtFormatter)));
            if (!lbl_s2_start.getText().equals("-"))
                seasons.add(new Season(LocalDate.parse(lbl_s2_start.getText(), dtFormatter), LocalDate.parse(lbl_s2_end.getText(), dtFormatter)));
            if (!lbl_s3_start.getText().equals("-"))
                seasons.add(new Season(LocalDate.parse(lbl_s3_start.getText(), dtFormatter), LocalDate.parse(lbl_s3_end.getText(), dtFormatter)));

            if (seasons.size() == 0) {
                Helper.showMessage("At least one season must be added!", "No Season Error!");
            } else {

                if (Hotel.add(star, name, country, region, city, district, address, eMail, phone,
                        cb_free_parking.isSelected(), cb_free_wifi.isSelected(), cb_swimming_pool.isSelected(),
                        cb_fitness.isSelected(), cb_concierge.isSelected(), cb_spa.isSelected(), cb_room_service.isSelected(), seasons)) {
                    Helper.showMessage("A new hotel has been added.", "Success");
                    previousFrame.setEnabled(true);
                    dispose();
                }
            }
        });
    }

    private void setThisFrameEnableState(boolean state) {
        this.setEnabled(state);
    }

    private void setSeasonLabelText(JLabel lblStart, JLabel lblEnd, String textStart, String textEnd) {
        lblStart.setText(textStart);
        lblEnd.setText(textEnd);
    }

    private void sortSeasons() {

        String s1s = lbl_s1_start.getText();
        String s2s = lbl_s2_start.getText();
        String s3s = lbl_s3_start.getText();
        String s1e = lbl_s1_end.getText();
        String s2e = lbl_s2_end.getText();
        String s3e = lbl_s3_end.getText();

        ArrayList<LocalDate> startDates = new ArrayList<>();
        ArrayList<LocalDate> endDates = new ArrayList<>();

        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (!s1s.equals("-") && !s1e.equals("-")) {
            startDates.add(LocalDate.parse(s1s, dtFormatter));
            endDates.add(LocalDate.parse(s1e, dtFormatter));
        }

        if (!s2s.equals("-") && !s2e.equals("-")) {
            startDates.add(LocalDate.parse(s2s, dtFormatter));
            endDates.add(LocalDate.parse(s2e, dtFormatter));
        }

        if (!s3s.equals("-") && !s3e.equals("-")) {
            startDates.add(LocalDate.parse(s3s, dtFormatter));
            endDates.add(LocalDate.parse(s3e, dtFormatter));
        }

        Collections.sort(startDates);
        Collections.sort(endDates);

        JLabel[] startLabels = {lbl_s1_start, lbl_s2_start, lbl_s3_start};
        JLabel[] endLabels = {lbl_s1_end, lbl_s2_end, lbl_s3_end};

        for (int i = 0; i < startLabels.length; i++) {
            if (i < startDates.size()) {
                startLabels[i].setText(startDates.get(i).format(dtFormatter));
            } else {
                startLabels[i].setText("-");
            }
        }

        for (int i = 0; i < endLabels.length; i++) {
            if (i < endDates.size()) {
                endLabels[i].setText(endDates.get(i).format(dtFormatter));
            } else {
                endLabels[i].setText("-");
            }
        }
    }
}
