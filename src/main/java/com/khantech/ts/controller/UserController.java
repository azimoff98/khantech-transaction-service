package com.khantech.ts.controller;


import com.khantech.ts.controller.constants.ApiCodes;
import com.khantech.ts.controller.helper.TransactionApiBuilder;
import com.khantech.ts.dto.request.UserCreateRequest;
import com.khantech.ts.dto.response.TransactionApiResponse;
import com.khantech.ts.dto.response.UserResponse;
import com.khantech.ts.entity.User;
import com.khantech.ts.mapper.UserCreationMapper;
import com.khantech.ts.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements TransactionApiBuilder {

    private final UserService userService;
    private final UserCreationMapper mapper;


    @PostMapping
    public ResponseEntity<TransactionApiResponse<UserResponse>> create(@RequestBody UserCreateRequest request) {
        UserResponse created = mapper.toResponse(
                userService.save(
                        mapper.toEntity(request)
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(build(created, ApiCodes.USER_CREATED));
    }
}
