package org.alexk.businessLogic.validators;

import org.alexk.exceptions.ModelException;

/**
 * @param <T> the type of the field to be validated
 */
public interface Validator<T> {
    /**
     * @param t the field to be validated
     * @return true if the field is valid
     * @throws ModelException if the field is either missing or not valid
     */
    boolean validate(T t) throws ModelException;
}
