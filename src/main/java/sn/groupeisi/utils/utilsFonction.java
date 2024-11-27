package sn.groupeisi.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class utilsFonction {
    public static boolean validerInput(String choix, Validator validator) {
        Input input = new Input();
        input.setInput(choix);

        Set<ConstraintViolation<String>> violations = validator.validate(input.getInput());

        if (violations.isEmpty()) {
            return true;
        } else {
            System.out.println("Erreur : " + violations.stream().findFirst().get().getMessage());
            return false;
        }
    }
}
