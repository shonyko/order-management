package org.alexk.businessLogic.validators;

import lombok.val;
import org.alexk.exceptions.ModelException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clasa care valideaza o data dupa formatul MM/dd/yyyy hh:mm a
 */
public class DateValidator implements Validator<String>{
    /**
     * @param dateString data care urmeaza sa fie validata
     * @return true daca data respecta formatul MM/dd/yyyy hh:mm a
     * @throws ModelException daca data lipseste sau nu este valida
     */
    @Override
    public boolean validate(String dateString) throws ModelException {
        if(dateString == null) {
            throw new ModelException("Date is required!");
        }

        try {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
            val date = LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            throw new ModelException("Invalid date format!");
        }

        return true;
    }
}
