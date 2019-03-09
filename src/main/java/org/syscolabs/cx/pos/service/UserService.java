package org.syscolabs.cx.pos.service;

import org.syscolabs.cx.pos.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();

    void delete(long id);
}
