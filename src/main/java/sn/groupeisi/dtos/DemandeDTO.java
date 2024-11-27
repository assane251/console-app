package sn.groupeisi.dtos;

import lombok.Getter;
import lombok.Setter;
import sn.groupeisi.model.Demande;

import java.sql.Date;

@Getter
@Setter
public class DemandeDTO {
    private Long id;
    private Date date;
    private String description;
    private String clientNom;

    public DemandeDTO(Demande demande) {
        this.id = demande.getId();
        this.date = demande.getDate();
        this.description = demande.getDescription();
        this.clientNom = demande.getClient() != null ? demande.getClient().getNom() : null;
    }
}

