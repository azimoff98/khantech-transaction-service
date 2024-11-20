package com.khantech.ts.mapper;

import com.khantech.ts.dto.request.UserCreateRequest;
import com.khantech.ts.dto.response.UserResponse;
import com.khantech.ts.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreationMapper implements BaseMapper<UserCreateRequest, UserResponse, User> {

    @Override
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getBalance());
    }

    @Override
    public User toEntity(UserCreateRequest request) {
        return new User(request.username());
    }
}
