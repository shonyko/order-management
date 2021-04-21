package org.alexk.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clasa care se ocupa de crearea de conexiuni cu baza de date
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /**
     * String care indica pachetul in care se gaseste clasa DriverManager
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * String care indica adresa bazei de date
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/OrderManagement";
    /**
     * String care indica utilizatorul cu care se face logarea la baza de date
     */
    private static final String USER = "root";
    /**
     * String care indica parola cu care se face logarea la baza de date
     */
    private static final String PASS = "toor";

    /**
     * Obiect de tip singleton care creeaza conexiuni cu baza de date
     */
    private static final ConnectionFactory instance = new ConnectionFactory();


    /**
     * Initializeaza obiectul de tip singleton
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return o conexiune cu baza de date
     * @throws SQLException daca apare o eroare la conectarea cu baza de date
     */
    private Connection createConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while creating a connection with the database");
            throw e;
        }
    }

    /**
     * @return o conexiune cu baza de date
     * @throws SQLException daca apare o eroare la conectarea cu baza de date
     */
    public static Connection getConnection() throws SQLException {
        return instance.createConnection();
    }
}
