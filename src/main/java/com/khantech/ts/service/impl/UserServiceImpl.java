package com.khantech.ts.service.impl;

import com.khantech.ts.entity.User;
import com.khantech.ts.exception.ErrorCodes;
import com.khantech.ts.exception.ResourceNotFoundException;
import com.khantech.ts.repository.UserRepository;
import com.khantech.ts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.RESOURCE_NOT_FOUND));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
