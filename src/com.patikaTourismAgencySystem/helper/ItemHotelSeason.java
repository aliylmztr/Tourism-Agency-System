package com.patikaTourismAgencySystem.helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemHotelSeason {
    private int id;
    private int hotelID;
    private int seasonID;

    public ItemHotelSeason(int hotelID, int seasonID) {
        this.hotelID = hotelID;
        this.seasonID = seasonID;
    }

    public static boolean add(int hotelID, int seasonID) {
        boolean result = false;
        String query = "INSERT INTO " + DBConstants.DB_TN_HOTEL_SEASON + " (hotel_id, season_id) VALUES (?, ?)";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, hotelID);
            ps.setInt(2, seasonID);

            result = (ps.executeUpdate() > 0);

            ps.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (!result) Helper.showMessage("Operation failed! There was an error adding an ıtem.", "Error");

        return result;
    }
}
