import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sn.groupeisi.enums.TypeDemande;
import sn.groupeisi.model.Client;
import sn.groupeisi.model.Demande;
import sn.groupeisi.service.IConsoleApp;
import sn.groupeisi.service.implementation.IConsoleAppImp;

import java.sql.Date;

public class IConsoleTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }
    @Test
    public void clientTest() {
        IConsoleApp iConsoleApp = new IConsoleAppImp();
        Client client = new Client();
        client.setNom("DIALLO");
        client.setPrenom("Rougiatou");
        client.setTelephone("776543109");
        iConsoleApp.saveEntity(client);
    }

    @Test
    public void DemandeTest() {

        IConsoleApp iConsoleApp = new IConsoleAppImp();
        Client client = new Client();
        client.setNom("DIALLO");
        client.setPrenom("Rougiatou");
        client.setTelephone("776543109");
        iConsoleApp.saveEntity(client);

        Demande demande = new Demande();
        demande.setDescription("lorem ipsum");
        demande.setDate(Date.valueOf("2001-09-11"));
        demande.setTypeDemande(TypeDemande.ADMINISTRATEUR);
        demande.setClient(client);
        iConsoleApp.saveEntity(demande);
    }

    @Test
    public void findDemandeByDateTest() {
        IConsoleApp iConsoleApp = new IConsoleAppImp();

        Client client = new Client();
        client.setNom("DIALLO");
        client.setPrenom("Rougiatou");
        client.setTelephone("776543109");
        iConsoleApp.saveEntity(client);

        Demande demande = new Demande();
        demande.setDescription("lorem ipsum");
        demande.setDate(Date.valueOf("2001-09-11"));
        demande.setTypeDemande(TypeDemande.ADMINISTRATEUR);
        demande.setClient(client);

        iConsoleApp.findDemandeByDate(Date.valueOf("2001-09-11"));
    }
}
