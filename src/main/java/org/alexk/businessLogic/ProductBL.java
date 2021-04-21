package org.alexk.businessLogic;

import org.alexk.businessLogic.validators.PriceValidator;
import org.alexk.businessLogic.validators.Validator;
import org.alexk.dao.ProductDAO;
import org.alexk.exceptions.ModelException;
import org.alexk.models.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Clasa care se ocupa de logica necesara pentru procesarea unui produs
 */
public class ProductBL {
    /**
     * Obiect care faciliteaza lucrul cu date despre un produs
     */
    private final ProductDAO dao;

    /**
     * Obiect care faciliteaza validarea unui pret
     */
    private final Validator priceValidator;

    /**
     * Creeaza o noua instanta de logica necesara pentru procesarea unui produs
     */
    public ProductBL() {
        dao = new ProductDAO();
        priceValidator = new PriceValidator();
    }

    /**
     * @param product datele despre produs care urmeaza sa fie salvate
     * @throws SQLException daca apare o eroare in timpul incarcarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void insert(Product product) throws SQLException, ModelException {
        priceValidator.validate(product.getPrice());
        dao.insert(product);
    }

    /**
     * @param product datele despre produs care urmeaza sa fie actualizate
     * @throws SQLException daca apare o eroare in timpul actualizarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void update(Product product) throws SQLException, ModelException {
        priceValidator.validate(product.getPrice());
        dao.update(product);
    }

    /**
     * @param product datele despre produs care urmeaza sa fie sterse
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(Product product) throws SQLException, ModelException {
        delete(product.getId());
    }

    /**
     * @param id id-ul produsului care urmeaza sa fie sters
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(int id) throws SQLException, ModelException {
        dao.delete(id);
    }

    /**
     * @param id id-ul produsului ale carui date urmeaza sa fie extrase
     * @return datele despre produsul cu id-ul mentionat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public Product findById(int id) throws SQLException, ModelException {
        return dao.findById(id);
    }

    /**
     * @return o lista ce contine datele tuturor produselor
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<Product> findAll() throws SQLException, ModelException {
        return dao.findAll();
    }

    /**
     * @return o lista ce contine datele tuturor produselor care sunt in stoc
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<Product> findAllSelectable() throws SQLException, ModelException {
        return dao.findAllSelectable();
    }

    /**
     * @param id id-ul comenzii pentru care se selecteaza produsele care sunt in stoc si nu exista deja in comanda
     * @return o lista ce contine datele tuturor produselor care sunt in stoc si nu exista deja in comanda cu id-ul specificat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<Product> findAllSelectableByOrderId(int id) throws SQLException, ModelException {
        return dao.findAllSelectableByOrderId(id);
    }
}
