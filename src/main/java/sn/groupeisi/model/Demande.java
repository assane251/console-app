package sn.groupeisi.model;

import jakarta.persistence.*;
import lombok.*;
import sn.groupeisi.enums.TypeDemande;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "demande")
@Getter
@Setter
@AllArgsConstructor
@NamedQuery(name = "Demande.findByDate", query = "SELECT d FROM Demande d WHERE d.date = :date")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Code invalide.")
    private String code;
    @NotEmpty(message = "date invalide.")
    @Temporal(TemporalType.DATE)
    private Date date;
    @NotEmpty(message = "Description invalide.")
    @Size(min = 1, max = 100)
    private String description;
    @NotEmpty(message = "Type Demande invalide.")
    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Client client;

    public Demande() {
        this.code = UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", typeDemande=" + typeDemande +
                '}';
    }
}
