package org.alexk.dao;

import lombok.Cleanup;
import lombok.val;
import org.alexk.connection.ConnectionFactory;
import org.alexk.models.misc.BaseModel;
import org.alexk.utils.FieldRetriever;
import org.alexk.utils.ObjectFactory;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa generica care se ocupa de operatii CRUD pe modele
 * @param <T> tipul modelului folosit pentru efectuarea operatiilor CRUD
 */
public class AbstractDAO<T extends BaseModel> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    /**
     * Obiect care indica clasa corespondenta tipului modelului folosit pentru efectuarea operatiilor CRUD
     */
    protected final Class<T> type;

    /**
     * Creeaza o noua instanta de DAO
     */
    public AbstractDAO() {
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @return un string care reprezinta un query de inserare corespunzator tipului specificat
     */
    protected String createInsertQuery() {
        val sb = new StringBuilder("insert into `" + type.getSimpleName() + "` values (");

        boolean commaNeeded = false;
        for(val field : type.getDeclaredFields()) {
            if(commaNeeded) {
                sb.append(", ");
                commaNeeded = false;
            }

            sb.append("?");
            commaNeeded = true;
        }

        return sb.append(")").toString();
    }

    /**
     * @return un string care reprezinta un query de actualizare corespunzator tipului specificat
     */
    protected String createUpdateQuery() {
        val sb = new StringBuilder("update `" + type.getSimpleName() + "` set ");

        boolean commaNeeded = false;
        for(val field : type.getDeclaredFields()) {
            if(commaNeeded) {
                sb.append(", ");
                commaNeeded = false;
            }

            sb.append(field.getName() + " = ?");
            commaNeeded = true;
        }

        sb.append(" where id = ?");
        return sb.toString();
    }

    /**
     * @return un string care reprezinta un query de stergere corespunzator tipului specificat
     */
    protected String createDeleteQuery() {
        val sb = new StringBuilder();

        sb.append("delete from `");
        sb.append(type.getSimpleName());
        sb.append("` where id = ?");

        return sb.toString();
    }

    /**
     * @return un string care reprezinta un query de citire corespunzator tipului specificat
     */
    protected String createSelectQuery() {
        return createSelectQuery(null);
    }

    /**
     * @param field campul dupa care se doreste sa se faca filtrarea
     * @return un string care reprezinta un query de citire, filtrat dupa campul specificat, corespunzator tipului specificat
     */
    protected String createSelectQuery(String field) {
        val sb = new StringBuilder();

        sb.append("select * from `");
        sb.append(type.getSimpleName());
        sb.append("`");

        if(field != null) {
            sb.append(" where " + field + " = ?");
        }

        return sb.toString();
    }

    /**
     * @param obj obiectul care contine datele ce urmeaza sa fie salvate
     * @throws SQLException daca apare o eroare in timpul inserarii in baza de date
     */
    public void insert(T obj) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = createInsertQuery();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int cnt = 1;
            for(val field : type.getDeclaredFields()) {
                field.setAccessible(true);
                stmt.setObject(cnt++, field.get(obj));
            }

            stmt.executeUpdate();

            res = stmt.getGeneratedKeys();
            if(!res.next()) {
                throw new Exception("Id already exists.");
            }

            val newId = res.getInt(1);
            obj.setId(newId);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: insert");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @param obj obiectul care contine datele ce urmeaza sa fie actualizate
     * @throws SQLException daca apare o eroare in timpul actualizarii in baza de date
     */
    public void update(T obj) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        val query = createUpdateQuery();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            int cnt = 1;
            for(val field : type.getDeclaredFields()) {
                field.setAccessible(true);
                stmt.setObject(cnt, field.get(obj));
                cnt++;
            }
            stmt.setInt(cnt, obj.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: update");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @param obj obiectul care contine datele ce urmeaza sa fie sterse
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     */
    public void delete(T obj) throws SQLException {
        delete(obj.getId());
    }

    /**
     * @param id id-ul obiectului a carui date urmeaza sa fie sterse
     * @throws SQLException daca apare o eroare in timpul stergerii din baza de date
     */
    public void delete(int id) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        val query = createDeleteQuery();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: delete");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @param id id-ul obiectului a carui date urmeaza sa fie extrase
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public T findById(int id) throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = createSelectQuery("id");

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(type, res);
            return list.get(0);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findById");
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @throws SQLException daca apare o eroare in timpul citirii din baza de date
     */
    public List<T> findAll() throws SQLException {
        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement stmt = null;
        @Cleanup ResultSet res = null;
        val query = createSelectQuery();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            res = stmt.executeQuery();

            val list = ObjectFactory.createObjects(type, res);
            return list;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getSimpleName() + "DAO: findAll");
            throw new SQLException(e.getMessage());
        }
    }
}
