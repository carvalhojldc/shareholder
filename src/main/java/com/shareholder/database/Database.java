package com.shareholder.database;

import com.shareholder.config.SystemInstall;
import com.shareholder.config.log.Log;
import com.shareholder.ui.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class Database {
    static final String DB_NAME = "Shareholder;DB_CLOSE_ON_EXIT=TRUE";
    static final String DB_DRIVER = "jdbc:h2:file:";
    private static final Logger log = Log.getLogger(Class.class);
    private Connection conn;

    public Database() {

    }

    public boolean connect(String user, String password) {
        final String dbPath = DB_DRIVER + SystemInstall.getPath() + DB_NAME;

        try {
            conn = DriverManager.getConnection(dbPath, "admin", "admin");
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.info("The driver name is " + meta.getDriverName());
            } else {
                log.severe("Error in connecting DB");
            }
        } catch (SQLException e) {
            log.severe(e.getMessage());
            exit(2);
        }

        createTables();

        return true;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
    }

    private boolean createTables() {
        try {
            Statement stmt = conn.createStatement();

            final String shareList = "CREATE TABLE IF NOT EXISTS stock_list " + "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " + " name VARCHAR(255), " + " description VARCHAR(255), " + " PRIMARY KEY ( id ))";
            stmt.executeUpdate(shareList);

            final String shareData = "CREATE TABLE IF NOT EXISTS stock_data " + "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " + " name VARCHAR(255), " + " description VARCHAR(255), " + " amount BIGINT, " + " average_price DECIMAL(30,2), " + " total_price DECIMAL(30,2), " + " date DATETIME, " + " PRIMARY KEY ( id ))";
            stmt.executeUpdate(shareData);
        } catch (SQLException e) {
            log.severe(e.getMessage());
            exit(2);
        }

        return true;
    }

    /**
     * @param name Stock name
     * @return Stock ID if exists on table, or 0 if not
     */
    public Integer verifyIfStockExists(String name) {
        final String QUERY = "SELECT * FROM stock_list AS t " + "WHERE t.name = '" + name + "' ";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }

        return 0;
    }

    private boolean genericDelete(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            log.severe(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteStock(String name) {
        final String QUERY = "DELETE FROM stock_list WHERE name = '" + name + "'";
        return genericDelete(QUERY);
    }

    public boolean deleteStock(Integer id) {
        final String QUERY = "DELETE FROM stock_list WHERE id = " + id;
        return genericDelete(QUERY);
    }

    public List<Stock> readAllStocks() {
        final String QUERY = "SELECT * FROM stock_list";
        List<Stock> list = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                Stock s = new Stock();

                s.setId(rs.getInt("id"));
                s.setStockName(rs.getString("name"));
                s.setDescription(rs.getString("description"));

                list.add(s);
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
        }

        return list;
    }

    public Stock addNewStock(Stock data) {
        String SQL = "INSERT INTO stock_list (name, description) " + "VALUES(?,?)";

        try {
            PreparedStatement statement = conn.prepareStatement(SQL);

            statement.setString(1, data.getStockName());
            statement.setString(2, data.getDescription());
            statement.executeUpdate();

            Statement stmt = conn.createStatement();
            final String QUERY = "SELECT * FROM stock_list AS sd " + "WHERE sd.name = '" + data.getStockName() + "' ";
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) data.setId(rs.getInt("id"));

            log.info("Element inserted with ID: " + data.getId());
        } catch (SQLException e) {
            log.severe(e.getMessage());
            return null;
        }

        return data;
    }
}
