package org.alexk.dao;

import lombok.Cleanup;
import lombok.val;
import org.alexk.connection.ConnectionFactory;
import org.alexk.models.Product;
import org.alexk.utils.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Clasa care se ocupa de operatii CRUD cu date despre produse
 */
public class ProductDAO extends AbstractDAO<Product> {
    /**
     * @return o lista ce contine datele tuturor produselor care sunt in stoc
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<Product> findAllSelectable() throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = "select * from vw_productSelectable";

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(Product.class, res);
            return list;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findAllSelectable");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @param id id-ul comenzii pentru care se selecteaza produsele care sunt in stoc si nu exista deja in comanda
     * @return o lista ce contine datele tuturor produselor care sunt in stoc si nu exista deja in comanda cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<Product> findAllSelectableByOrderId(int id) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = "select * from vw_productSelectable where `id` not in (select t.`productId` from `OrderItem` t where t.`orderId` = ?)";

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(Product.class, res);
            return list;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findAllSelectableByOrderId");
            throw new SQLException(e.getMessage());
        }
    }
}
