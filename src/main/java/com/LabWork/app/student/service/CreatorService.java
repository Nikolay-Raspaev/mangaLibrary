package com.LabWork.app.student.service;

import com.LabWork.app.student.model.Creator;
import com.LabWork.app.student.model.Manga;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CreatorService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Creator findCreator(Long id) {
        final Creator creator = em.find(Creator.class, id);
        if (creator == null) {
            throw new EntityNotFoundException(String.format("Creator with id [%s] is not found", id));
        }
        return creator;
    }

    @Transactional
    public List<Creator> findAllCreators() {
        return em.createQuery("select c from Creator c", Creator.class).getResultList();
    }

    @Transactional
    public Creator addCreator(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Creator's username or password is empty");
        }
        final Creator creator = new Creator(username, password);
        em.persist(creator);
        return creator;
    }

    @Transactional
    public Creator updateCreator(Long id, String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Creator's username or password is empty");
        }
        final Creator customer = findCreator(id);
        customer.setCreatorName(username);
        customer.setHashedPassword(password);
        return em.merge(customer);
    }

    @Transactional
    public Creator deleteCreator(Long id) {
        final Creator currentCustomer = findCreator(id);
        em.remove(currentCustomer);
        return currentCustomer;
    }

    @Transactional
    public void deleteAllCreators() {
        em.createQuery("delete from Creator").executeUpdate();
    }
}
