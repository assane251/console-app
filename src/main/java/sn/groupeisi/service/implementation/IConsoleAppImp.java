package sn.groupeisi.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import sn.groupeisi.dtos.DemandeDTO;
import sn.groupeisi.model.Demande;
import sn.groupeisi.service.IConsoleApp;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Optional;

public class IConsoleAppImp implements IConsoleApp {

    private final EntityManager em;

    public IConsoleAppImp() {
        this.em = Persistence.createEntityManagerFactory("default").createEntityManager();
    }

    @Override
    public <T> T saveEntity(@Valid T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la sauvegarde de l'entité.", e.getCause());
        }
    }

    @Override
    public String findDemandeByDate(Date date) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Query query = em.createNamedQuery("Demande.findByDate")
                    .setParameter("date", date);
            Demande d = (Demande) query.getSingleResult();

            DemandeDTO demandeDTO = new DemandeDTO(d);

            return mapper.writeValueAsString(demandeDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return "Demande n'existe pas";
        } finally {
            em.close();
        }
    }
}