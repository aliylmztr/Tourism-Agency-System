package com.patikaTourismAgencySystem.view;

import com.patikaTourismAgencySystem.helper.Helper;
import com.patikaTourismAgencySystem.model.Hotel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class HotelOperationsGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_center;
    private JPanel pnl_bottom;
    private JTextField fld_search_hotel_name;
    private JButton btn_hotel_search;
    private JPanel pnl_center_left;
    private JPanel pnl_center_right;
    private JLabel lbl_general_info;
    private JTable tbl_hotels;
    private JButton btn_hotel_add;
    private JButton btn_room_operations;
    private JButton btn_hotel_delete;
    private DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;

    public HotelOperationsGUI(JFrame previousFrame) {
        previousFrame.setEnabled(false);
        int guiWidth = 720, guiHeight = 480;

        setContentPane(wrapper);
        setTitle(Helper.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(guiWidth, guiHeight);
        setMinimumSize(new Dimension(guiWidth, guiHeight));
        //setResizable(false);
        setLocation((Helper.SCREEN_WIDTH - guiWidth) / 2, (Helper.SCREEN_HEIGHT - guiHeight) / 2);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                previousFrame.setEnabled(true);
            }
        });

        btn_hotel_add.addActionListener(e -> {
            HotelAddGUI hotelAddGUI = new HotelAddGUI(this);
        });

        mdl_hotel_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_hotel_list = {"ID", "Hotel Name"};
        mdl_hotel_list.setColumnIdentifiers(col_hotel_list);
        row_hotel_list = new Object[col_hotel_list.length];

        refreshHotelModel();

        tbl_hotels.setModel(mdl_hotel_list);
        tbl_hotels.getTableHeader().setReorderingAllowed(false);
        tbl_hotels.getColumnModel().getColumn(0).setMaxWidth(80);

        tbl_hotels.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tbl_hotels.getSelectedRow() != -1) {
                    String selectedHotelID = tbl_hotels.getValueAt(tbl_hotels.getSelectedRow(), 0).toString();
                    lbl_general_info.setText(Hotel.fetchHotelByID(Integer.parseInt(selectedHotelID)).toStringForJLabel());
                }
            }
        });
    }

    private void refreshHotelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotels.getModel();
        clearModel.setRowCount(0);

        for (Hotel hotel : Hotel.getList()) {
            int i = 0;
            row_hotel_list[i++] = hotel.getId();
            row_hotel_list[i] = hotel.getName();

            mdl_hotel_list.addRow(row_hotel_list);
        }
    }

    private void refreshHotelModel(ArrayList<Hotel> filteredHotels) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotels.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Hotel hotel : filteredHotels) {
            i = 0;
            row_hotel_list[i++] = hotel.getId();
            row_hotel_list[i] = hotel.getName();

            mdl_hotel_list.addRow(row_hotel_list);
        }
    }
}
