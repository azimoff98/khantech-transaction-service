package com.khantech.ts.service;

import com.khantech.ts.entity.User;

public interface UserService {

    User findById(long userId);
    User save(User user);
}
