package org.alexk.dao;

import lombok.Cleanup;
import lombok.val;
import org.alexk.connection.ConnectionFactory;
import org.alexk.models.OrderItem;
import org.alexk.models.viewmodels.OrderItemViewModel;
import org.alexk.utils.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Clasa care se ocupa de operatii CRUD cu date despre produse dintr-o comanda
 */
public class OrderItemDAO extends AbstractDAO<OrderItem> {

    /**
     * @param id id-ul comenzii din care fac parte produsele ale caror date vor fi extrase
     * @return o lista ce contine datele tuturor produselor din comanda cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<OrderItem> findByOrderId(int id) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = createSelectQuery("orderId");

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            return ObjectFactory.createObjects(type, res);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findByOrderId");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @return o lista ce contine datele pentru vizualizare ale tuturor produselor dintr-o comanda
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<OrderItemViewModel> findAllViewModels() throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = "select * from vw_orderItemViewModel";

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(OrderItemViewModel.class, res);
            return list;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findAllViewModels");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @param id id-ul comenzii din care fac parte produsele ale caror datele pentru vizualizare vor fi extrase
     * @return o lista ce contine datele pentru vizualizare ale tuturor produselor din comanda cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<OrderItemViewModel> findAllViewModelsByOrderId(int id) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = "select * from vw_orderItemViewModel where `orderId` = ?";

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(OrderItemViewModel.class, res);
            return list;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findAllViewModelsByOrderId");
            throw new SQLException(e.getMessage());
        }
    }
}
