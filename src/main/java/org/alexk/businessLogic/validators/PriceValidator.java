package org.alexk.businessLogic.validators;

import org.alexk.exceptions.ModelException;

/**
 * Clasa care valideaza un pret
 */
public class PriceValidator implements Validator<Double> {
    /**
     * @param price pretul care urmeaza sa fie validat
     * @return true daca pretul este valid
     * @throws ModelException daca pretul lipseste sau este <= 0
     */
    @Override
    public boolean validate(Double price) throws ModelException {
        if(price == null) {
            throw new ModelException("Price is required!");
        }

        if(Double.compare(0, price) >= 0) {
            throw new ModelException("Price must be a positive value!");
        }

        return true;
    }
}
