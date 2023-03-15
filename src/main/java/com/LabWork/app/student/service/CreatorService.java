package com.LabWork.app.student.service;

import com.LabWork.app.student.model.Creator;
import com.LabWork.app.student.model.Manga;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public Creator addCreator(String readername, String password) {
        if (!StringUtils.hasText(readername) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Creator's readername or password is empty");
        }
        final Creator creator = new Creator(readername, password);
        em.persist(creator);
        return creator;
    }

    @Transactional
    public Creator updateCreator(Long id, String readername, String password) {
        if (!StringUtils.hasText(readername) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Creator's readername or password is empty");
        }
        final Creator customer = findCreator(id);
        customer.setCreatorName(readername);
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
/*        List<Creator> creatorList = em.createQuery("select s from Creator s", Creator.class).getResultList();
        List<Manga> mangaList = new ArrayList<>();*/
/*        for (Creator creator: creatorList){
            mangaList.addAll(creator.getMangas());
        }*/
/*        for (Manga manga: mangaList){
            manga.getReaders().remove(manga);
        }*/
/*        for (Creator creator: creatorList){
            creator.getMangas().clear();
        }*/
    }
}
