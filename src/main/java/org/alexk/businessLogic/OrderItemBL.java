package org.alexk.businessLogic;

import lombok.val;
import org.alexk.dao.OrderItemDAO;
import org.alexk.exceptions.ModelException;
import org.alexk.models.OrderItem;
import org.alexk.models.viewmodels.OrderItemViewModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Clasa care se ocupa de logica necesara pentru procesarea unui produs dintr-o comanda
 */
public class OrderItemBL {
    /**
     * Obiect care faciliteaza lucrul cu date despre un produs dintr-o comanda
     */
    private final OrderItemDAO dao;

    /**
     * Creeaza o noua instanta de logica necesara pentru procesarea unui produs dintr-o comanda
     */
    public OrderItemBL() {
        dao = new OrderItemDAO();
    }

    /**
     * Metoda care verifica daca mai exista destule produse in stoc
     * @param orderItem datele despre produsul dintr-o comanda
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    private void checkQuantity(OrderItem orderItem) throws SQLException, ModelException {
        val logic = new ProductBL();
        val prod = logic.findById(orderItem.getProductId());

        if(prod.getQuantity() < orderItem.getQuantity()) {
            throw new ModelException("Nu exista suficiente produse in stoc!");
        }
    }

    /**
     * @param orderItem datele despre produsul dintr-o comanda care urmeaza sa fie salvate
     * @throws SQLException daca apare o eroare in timpul incarcarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void insert(OrderItem orderItem) throws SQLException, ModelException {
        checkQuantity(orderItem);
        dao.insert(orderItem);
    }

    /**
     * @param orderItem datele despre produsul dintr-o comanda care urmeaza sa fie actualizate
     * @throws SQLException daca apare o eroare in timpul actualizarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void update(OrderItem orderItem) throws SQLException, ModelException {
        checkQuantity(orderItem);
        dao.update(orderItem);
    }

    /**
     * @param orderItem datele despre produsul dintr-o comanda care urmeaza sa fie sterse
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(OrderItem orderItem) throws SQLException, ModelException {
        delete(orderItem.getId());
    }

    /**
     * @param id id-ul produsului dintr-o comanda care urmeaza sa fie sters
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(int id) throws SQLException, ModelException {
        dao.delete(id);
    }

    /**
     * @param id id-ul produsului dintr-o comanda ale carui date urmeaza sa fie extrase
     * @return datele despre produsul cu id-ul mentionat dintr-o comanda
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public OrderItem findById(int id) throws SQLException, ModelException {
        return dao.findById(id);
    }

    /**
     * @return o lista ce contine datele tuturor produselor dintr-o comanda
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<OrderItem> findAll() throws SQLException, ModelException {
        return dao.findAll();
    }

    // ViewModel methods

    /**
     * @return o lista ce contine datele pentru vizualizare ale tuturor produselor dintr-o comanda
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<OrderItemViewModel> findAllViewModels() throws SQLException, ModelException {
        return dao.findAllViewModels();
    }

    /**
     * @param id id-ul comenzii din care fac parte produsele ale caror datele pentru vizualizare vor fi extrase
     * @return o lista ce contine datele pentru vizualizare ale tuturor produselor din comanda cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<OrderItemViewModel> findAllViewModelsByOrderId(int id) throws SQLException, ModelException {
        return dao.findAllViewModelsByOrderId(id);
    }
}
