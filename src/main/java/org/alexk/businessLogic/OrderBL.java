package org.alexk.businessLogic;

import lombok.val;
import org.alexk.businessLogic.validators.DateValidator;
import org.alexk.businessLogic.validators.Validator;
import org.alexk.dao.OrderDAO;
import org.alexk.exceptions.ModelException;
import org.alexk.models.Order;
import org.alexk.models.viewmodels.OrderViewModel;
import org.alexk.utils.PDFLogger;

import java.sql.SQLException;
import java.util.List;

/**
 * Clasa care se ocupa de logica necesara pentru procesarea unei comenzi
 */
public class OrderBL {
    /**
     * Obiect care faciliteaza lucrul cu date despre o comanda
     */
    private final OrderDAO dao;

    /**
     * Obiect care faciliteaza validarea unei date
     */
    private final Validator dateValidator;

    /**
     * Creeaza o noua instanta de logica necesara pentru procesarea unei comenzi
     */
    public OrderBL() {
        dao = new OrderDAO();
        dateValidator = new DateValidator();
    }

    /**
     * @param order datele despre comanda care urmeaza sa fie salvate
     * @throws SQLException daca apare o eroare in timpul incarcarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void insert(Order order) throws SQLException, ModelException {
        dateValidator.validate(order.getDate());
        dao.insert(order);
    }

    /**
     * @param order datele despre comanda care urmeaza sa fie actualizate
     * @throws SQLException daca apare o eroare in timpul actualizarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void update(Order order) throws SQLException, ModelException {
        dateValidator.validate(order.getDate());
        dao.update(order);
    }

    /**
     * @param order datele despre comanda care urmeaza sa fie sterse
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(Order order) throws SQLException, ModelException {
        delete(order.getId());
    }

    /**
     * @param id id-ul comenzii care urmeaza sa fie stearsa
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(int id) throws SQLException, ModelException {
        dao.delete(id);
    }

    /**
     * @param id id-ul comenzii ale carei date urmeaza sa fie extrase
     * @return datele despre comanda cu id-ul mentionat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public Order findById(int id) throws SQLException, ModelException {
        return dao.findById(id);
    }

    /**
     * @return o lista ce contine datele tuturor comenzilor
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<Order> findAll() throws SQLException, ModelException {
        return dao.findAll();
    }

    /**
     * @param id id-ul comenzii care urmeaza sa fie finalizate
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void finalizeOrder(int id) throws SQLException, ModelException {
        val order = dao.findViewModelById(id);

        if(order.getPrice().equals("-")) {
            throw new ModelException("Cannot finalize an empty order!");
        }

        PDFLogger.createBill(order);
    }

    // ViewModel methods

    /**
     * @param id id-ul comenzii pentru care se vor extrage datele pentru vizualizare
     * @return datele pentru vizualizare corespunzatoare comenzii cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public OrderViewModel findViewModelById(int id) throws SQLException, ModelException {
        return dao.findViewModelById(id);
    }

    /**
     * @return o lista ce contine datele pentru vizualizare ale tuturor comenzilor
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<OrderViewModel> findAllViewModels() throws SQLException, ModelException {
        return dao.findAllViewModels();
    }
}
