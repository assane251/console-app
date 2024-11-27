package sn.groupeisi.utils;

import javax.validation.constraints.*;

public class Input {
    @Positive(message = "Le choix doit être un nombre positif.")
    @Min(value = 1, message = "Le choix doit être au moins 1.")
    @Max(value = 4, message = "Le choix doit être au plus 4.")
    private int choix;

    @Pattern(regexp = "[a-zA-Z]+$", message = "Input contient des chiffres ou caractere speciaux.")
    @NotEmpty(message = "Input invalide.")
    private String input;

    public int getChoix() {
        return choix;
    }

    public void setChoix(int choix) {
        this.choix = choix;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
