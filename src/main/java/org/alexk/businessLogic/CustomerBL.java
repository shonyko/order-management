package org.alexk.businessLogic;

import org.alexk.businessLogic.validators.AgeValidator;
import org.alexk.businessLogic.validators.EmailValidator;
import org.alexk.businessLogic.validators.Validator;
import org.alexk.dao.CustomerDAO;
import org.alexk.exceptions.ModelException;
import org.alexk.models.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * Clasa care se ocupa de logica necesara pentru procesarea unui client
 */
public class CustomerBL {
    /**
     * Obiect care faciliteaza lucrul cu date despre un client
     */
    private final CustomerDAO dao;

    /**
     * Obiect care faciliteaza validarea unei varste
     */
    private final Validator ageValidator;
    /**
     * Obiect care faciliteaza validarea unui email
     */
    private final Validator emailValidator;

    /**
     * Creeaza o noua instanta de logica necesara pentru procesarea unui client
     */
    public CustomerBL() {
        dao = new CustomerDAO();
        ageValidator = new AgeValidator();
        emailValidator = new EmailValidator();
    }

    /**
     * @param customer datele despre client care urmeaza sa fie salvate
     * @throws SQLException daca apare o eroare in timpul incarcarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void insert(Customer customer) throws SQLException, ModelException {
        emailValidator  .validate(customer.getEmail());
        ageValidator    .validate(customer.getAge());
        dao.insert(customer);
    }

    /**
     * @param customer datele despre client care urmeaza sa fie actualizate
     * @throws SQLException daca apare o eroare in timpul actualizarii in baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void update(Customer customer) throws SQLException, ModelException {
        emailValidator  .validate(customer.getEmail());
        ageValidator    .validate(customer.getAge());

        dao.update(customer);
    }

    /**
     * @param customer datele despre client care urmeaza sa fie sterse
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(Customer customer) throws SQLException, ModelException {
        delete(customer.getId());
    }

    /**
     * @param id id-ul clientului care urmeaza sa fie sters
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public void delete(int id) throws SQLException, ModelException {
        dao.delete(id);
    }

    /**
     * @param id id-ul clientului ale carui date urmeaza sa fie extrase
     * @return datele despre clientul cu id-ul mentionat
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public Customer findById(int id) throws SQLException, ModelException {
        return dao.findById(id);
    }

    /**
     * @return o lista ce contine datele tuturor clientilor
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     * @throws ModelException daca datele specificate nu sunt valide
     */
    public List<Customer> findAll() throws SQLException, ModelException {
        return dao.findAll();
    }
}
