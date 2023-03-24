package com.example.MyBoxYoonho.dao;

import com.example.MyBoxYoonho.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAOimpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOimpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        entityManager.persist(theUser);
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }


    // createquery에서 execute이거 안해도됨?
    @Override
    public List<User> findAll() {
        TypedQuery<User> theQuery = entityManager.createQuery("FROM User", User.class);
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void update(User theUser) {
        entityManager.merge(theUser);
    }

    @Override
    public void delete(Integer theUserId) {
        User theUser = entityManager.find(User.class, theUserId);
        entityManager.remove(theUser);
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM User").executeUpdate();
    }
}
