package com.patikaTourismAgencySystem.model;

import com.patikaTourismAgencySystem.helper.DBConnector;
import com.patikaTourismAgencySystem.helper.DBConstants;
import com.patikaTourismAgencySystem.helper.Helper;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private byte star;
    private String name;
    private String country;
    private String region;
    private String city;
    private String district;
    private String address;
    private String email;
    private String phone;
    private boolean freeParking;
    private boolean freeWiFi;
    private boolean swimmingPool;
    private boolean fitness;
    private boolean concierge;
    private boolean spa;
    private boolean allTimeRoomService;
    private ArrayList<Season> seasons;

    public Hotel() {
    }

    public Hotel(byte star, String name, String country, String region, String city, String district, String address, String email, String phone, boolean freeParking, boolean freeWiFi, boolean swimmingPool, boolean fitness, boolean concierge, boolean spa, boolean allTimeRoomService, ArrayList<Season> seasons) {
        this.star = star;
        this.name = name;
        this.country = country;
        this.region = region;
        this.city = city;
        this.district = district;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.freeParking = freeParking;
        this.freeWiFi = freeWiFi;
        this.swimmingPool = swimmingPool;
        this.fitness = fitness;
        this.concierge = concierge;
        this.spa = spa;
        this.allTimeRoomService = allTimeRoomService;
        this.seasons = seasons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getStar() {
        return star;
    }

    public void setStar(byte star) {
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFreeParking() {
        return freeParking;
    }

    public void setFreeParking(boolean freeParking) {
        this.freeParking = freeParking;
    }

    public boolean isFreeWiFi() {
        return freeWiFi;
    }

    public void setFreeWiFi(boolean freeWiFi) {
        this.freeWiFi = freeWiFi;
    }

    public boolean isSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }

    public boolean isFitness() {
        return fitness;
    }

    public void setFitness(boolean fitness) {
        this.fitness = fitness;
    }

    public boolean isConcierge() {
        return concierge;
    }

    public void setConcierge(boolean concierge) {
        this.concierge = concierge;
    }

    public boolean isSpa() {
        return spa;
    }

    public void setSpa(boolean spa) {
        this.spa = spa;
    }

    public boolean isAllTimeRoomService() {
        return allTimeRoomService;
    }

    public void setAllTimeRoomService(boolean allTimeRoomService) {
        this.allTimeRoomService = allTimeRoomService;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public static boolean add(byte star, String name, String country, String region, String city, String district, String address, String eMail, String phone, boolean freeParking, boolean freeWiFi, boolean swimmingPool, boolean fitness, boolean concierge, boolean spa, boolean allTimeRoomService, ArrayList<Season> seasons) {
        boolean result = false;
        boolean resultAddingHotel;
        boolean resultAddingSeason = false;
        boolean resultAddingHotelSeason = false;

        if (fetchHotelFromDbByName(name) != null) {
            Helper.showMessage("Hotel : \"" + name + "\" is already exist.", "Hotel Name Error!");
            return false;
        }

        try {
            Connection connection = DBConnector.getInstance();
            connection.setAutoCommit(false);

            String queryHotel = "INSERT INTO " + DBConstants.DB_TN_HOTEL + " (star, name, country, region, city, district, address, email, phone, free_parking, free_wifi, swimming_pool, fitness, concierge, spa, all_time_room_service) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psHotel = connection.prepareStatement(queryHotel);
            int i = 1;

            psHotel.setByte(i++, star);

            psHotel.setString(i++, name);
            psHotel.setString(i++, country);
            psHotel.setString(i++, region);
            psHotel.setString(i++, city);
            psHotel.setString(i++, district);
            psHotel.setString(i++, address);
            psHotel.setString(i++, eMail);
            psHotel.setString(i++, phone);

            psHotel.setBoolean(i++, freeParking);
            psHotel.setBoolean(i++, freeWiFi);
            psHotel.setBoolean(i++, swimmingPool);
            psHotel.setBoolean(i++, fitness);
            psHotel.setBoolean(i++, concierge);
            psHotel.setBoolean(i++, spa);
            psHotel.setBoolean(i, allTimeRoomService);

            resultAddingHotel = (psHotel.executeUpdate() > 0);
            psHotel.close();

            if (!resultAddingHotel) {
                Helper.showMessage("There was an error, the hotel could not be added!", "Hotel Adding Error!");
                return false;
            }

            Hotel hotel = fetchHotelFromDbByName(name);

            for (Season season : seasons) {
                if (Season.fetchSeasonFromDbByStartAndEndDates(season.getStartDate(), season.getEndDate()) != null) {
                    resultAddingSeason = true;
                    continue;
                }

                String querySeason = "INSERT INTO " + DBConstants.DB_TN_SEASON + " (start_date, end_date) VALUES (?, ?)";
                PreparedStatement psSeason = connection.prepareStatement(querySeason);
                psSeason.setString(1, season.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                psSeason.setString(2, season.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

                resultAddingSeason = (psSeason.executeUpdate() > 0);

                if (!resultAddingSeason) {
                    psSeason.close();
                    break;
                }
                psSeason.close();
            }

            if (resultAddingSeason) {
                for (Season season : seasons) {
                    Season temp = Season.fetchSeasonFromDbByStartAndEndDates(season.getStartDate(), season.getEndDate());

                    if (temp != null) {

                        String queryItemHotelSeason = "INSERT INTO " + DBConstants.DB_TN_HOTEL_SEASON + " (hotel_id, season_id) VALUES (?, ?)";
                        PreparedStatement psItemHotelSeason = connection.prepareStatement(queryItemHotelSeason);
                        psItemHotelSeason.setInt(1, hotel.getId());
                        psItemHotelSeason.setInt(2, temp.getId());

                        resultAddingHotelSeason = (psItemHotelSeason.executeUpdate() > 0);

                        if (!resultAddingHotelSeason) {
                            psItemHotelSeason.close();
                            break;
                        }
                        psItemHotelSeason.close();
                    } else {
                        resultAddingHotelSeason = false;
                        break;
                    }
                }
            }

            if (!resultAddingSeason || !resultAddingHotelSeason) {
                Helper.showMessage("There was an error, the hotel season could not be added!", "Hotel Season Adding Error!");
                connection.rollback();
                return false;
            } else result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Hotel fetchHotelFromDbByName(String name) {
        Hotel hotel = null;
        String query = "SELECT * FROM " + DBConstants.DB_TN_HOTEL + " WHERE name = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                hotel = new Hotel();

                hotel.setId(resultSet.getInt("id"));

                hotel.setStar(resultSet.getByte("star"));

                hotel.setName(resultSet.getString("name"));
                hotel.setCountry(resultSet.getString("country"));
                hotel.setRegion(resultSet.getString("region"));
                hotel.setCity(resultSet.getString("city"));
                hotel.setDistrict(resultSet.getString("district"));
                hotel.setAddress(resultSet.getString("address"));
                hotel.setEmail(resultSet.getString("email"));
                hotel.setPhone(resultSet.getString("phone"));

                hotel.setFreeParking(resultSet.getBoolean("free_parking"));
                hotel.setFreeWiFi(resultSet.getBoolean("free_wifi"));
                hotel.setSwimmingPool(resultSet.getBoolean("swimming_pool"));
                hotel.setFitness(resultSet.getBoolean("fitness"));
                hotel.setConcierge(resultSet.getBoolean("concierge"));
                hotel.setSpa(resultSet.getBoolean("spa"));
                hotel.setAllTimeRoomService(resultSet.getBoolean("all_time_room_service"));

                hotel.setSeasons(Season.fetchSeasonsFromDbByHotelID(hotel.getId()));
            }
            ps.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hotel;
    }

    public String toStringForJLabel() {
        return "<html>Hotel: " + name + "<br>" +
                "\nstars: " + star + "<br>" +
                "\ncountry: " + country + "<br>" +
                "\nregion: " + region + "<br>" +
                "\ncity: " + city + "<br>" +
                "\ndistrict: " + district + "<br>" +
                "\naddress: " + address + "<br>" +
                "\nemail: " + email + "<br>" +
                "\nphone: " + phone +
                "</html>";
    }

    public static ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotels = new ArrayList<>();

        String query = "SELECT * FROM " + DBConstants.DB_TN_HOTEL;
        Hotel hotel;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                hotel = new Hotel();
                hotel.setId(rs.getInt("id"));
                hotel.setStar(rs.getByte("star"));

                hotel.setName(rs.getString("name"));
                hotel.setCountry(rs.getString("country"));
                hotel.setRegion(rs.getString("region"));
                hotel.setCity(rs.getString("city"));
                hotel.setDistrict(rs.getString("district"));
                hotel.setAddress(rs.getString("address"));
                hotel.setEmail(rs.getString("email"));
                hotel.setPhone(rs.getString("phone"));

                hotel.setFreeParking(rs.getBoolean("free_parking"));
                hotel.setFreeWiFi(rs.getBoolean("free_wifi"));
                hotel.setSwimmingPool(rs.getBoolean("swimming_pool"));
                hotel.setFitness(rs.getBoolean("fitness"));
                hotel.setConcierge(rs.getBoolean("concierge"));
                hotel.setSpa(rs.getBoolean("spa"));
                hotel.setAllTimeRoomService(rs.getBoolean("all_time_room_service"));

                hotels.add(hotel);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hotels;
    }

    public static Hotel fetchHotelByID(int id) {
        Hotel hotel = null;

        String query = "SELECT * FROM " + DBConstants.DB_TN_HOTEL + " WHERE id = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hotel = new Hotel();
                hotel.setId(rs.getInt("id"));
                hotel.setStar(rs.getByte("star"));

                hotel.setName(rs.getString("name"));
                hotel.setCountry(rs.getString("country"));
                hotel.setRegion(rs.getString("region"));
                hotel.setCity(rs.getString("city"));
                hotel.setDistrict(rs.getString("district"));
                hotel.setAddress(rs.getString("address"));
                hotel.setEmail(rs.getString("email"));
                hotel.setPhone(rs.getString("phone"));

                hotel.setFreeParking(rs.getBoolean("free_parking"));
                hotel.setFreeWiFi(rs.getBoolean("free_wifi"));
                hotel.setSwimmingPool(rs.getBoolean("swimming_pool"));
                hotel.setFitness(rs.getBoolean("fitness"));
                hotel.setConcierge(rs.getBoolean("concierge"));
                hotel.setSpa(rs.getBoolean("spa"));
                hotel.setAllTimeRoomService(rs.getBoolean("all_time_room_service"));
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hotel;
    }
}
