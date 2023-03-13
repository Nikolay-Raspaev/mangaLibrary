package com.LabWork.app.student.service;

import com.LabWork.app.student.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User findUser(Long id) {
        final User user = em.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException(String.format("User with id [%s] is not found", id));
        }
        return user;
    }

    @Transactional
    public List<User> findAllUsers() {
        return em.createQuery("select c from User c", User.class).getResultList();
    }

    @Transactional
    public User addUser(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Customer's username or password is empty");
        }
        final User user = new User(username, password);
        em.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(Long id, String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Customer's username or password is empty");
        }
        final User user = findUser(id);
        user.setUserName(username);
        user.setHashedPassword(password);
        return em.merge(user);
    }

    @Transactional
    public User deleteUser(Long id) {
        final User currentCustomer = findUser(id);
        em.remove(currentCustomer);
        return currentCustomer;
    }

    @Transactional
    public void deleteAllUsers() {
        em.createQuery("delete from User").executeUpdate();
    }
}
