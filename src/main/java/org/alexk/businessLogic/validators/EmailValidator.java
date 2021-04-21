package org.alexk.businessLogic.validators;

import org.alexk.exceptions.ModelException;

/**
 * Clasa care valideaza un email
 */
public class EmailValidator implements Validator<String> {
    /**
     * @param email email-ul care urmeaza sa fie validat
     * @return true daca email-ul este valid
     * @throws ModelException daca email-ul lipseste sau nu este valid
     */
    @Override
    public boolean validate(String email) throws ModelException {
        if(email == null) {
            throw new ModelException("Email is required!");
        }

        if(!email.matches("\\w{3,}@\\w{3,}\\.\\w{2,}")) {
            throw new ModelException("Email is not valid!");
        }

        return true;
    }
}
