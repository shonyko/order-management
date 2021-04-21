package org.alexk.businessLogic.validators;

import org.alexk.exceptions.ModelException;


/**
 * Clasa care valideaza varsta de minim 18 ani
 */
public class AgeValidator implements Validator<Integer> {
    /**
     * @param age varsta care urmeaza sa fie validata
     * @return true daca varsta este valida
     * @throws ModelException daca varsta lipseste sau este mai mica de 18 ani
     */
    @Override
    public boolean validate(Integer age) throws ModelException {
        if(age == null) {
            throw new ModelException("Age is required!");
        }

        if(age < 18) {
            throw new ModelException("Age must be greater or equals to 18!");
        }

        return true;
    }
}
