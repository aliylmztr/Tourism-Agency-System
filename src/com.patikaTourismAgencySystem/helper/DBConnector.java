package com.patikaTourismAgencySystem.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection myConnection = null;

    private Connection connectDB() {
        try {
            this.myConnection = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.DB_USER_NAME, DBConstants.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myConnection;
    }

    public static Connection getInstance() {
        DBConnector DBConnector = new DBConnector();
        return DBConnector.connectDB();
    }
}
