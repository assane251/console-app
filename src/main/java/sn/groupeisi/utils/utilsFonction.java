package sn.groupeisi.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class utilsFonction {
    public static boolean validerInput(String choix, Validator validator) {
        Input input = new Input();
        input.setInput(choix);

        Set<ConstraintViolation<String>> violations = validator.validate(input.getInput());

        return violations.isEmpty();
    }
}
