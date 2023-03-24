package com.example.MyBoxYoonho.dao;

import com.example.MyBoxYoonho.entity.User;

import java.util.List;

public interface UserDAO {
    void save(User theUser);

    User findById(Integer id);

    List<User> findAll();

    void update(User theUser);

    void delete(Integer theUserId);

    void deleteAll();
}
