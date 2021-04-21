package org.alexk.dao;

import lombok.Cleanup;
import lombok.val;
import org.alexk.connection.ConnectionFactory;
import org.alexk.models.Order;
import org.alexk.models.viewmodels.OrderViewModel;
import org.alexk.utils.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Clasa care se ocupa de operatii CRUD cu date despre comenzi
 */
public class OrderDAO extends AbstractDAO<Order> {
    /**
     * @param id id-ul comenzii pentru care se vor extrage datele pentru vizualizare
     * @return datele pentru vizualizare corespunzatoare comenzii cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public OrderViewModel findViewModelById(int id) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = "select * from vw_orderViewModel where `id` = ?";

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(OrderViewModel.class, res);
            return list.get(0);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findViewModelById");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @return o lista ce contine datele pentru vizualizare ale tuturor comenzilor
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<OrderViewModel> findAllViewModels() throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = "select * from vw_orderViewModel";

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(OrderViewModel.class, res);
            return list;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findAllViewModels");
            throw new SQLException(e.getMessage());
        }
    }
}
