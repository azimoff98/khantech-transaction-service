package com.khantec.ts.controller

import com.khantech.ts.controller.UserController
import com.khantech.ts.controller.constants.ApiCodes
import com.khantech.ts.dto.request.UserCreateRequest
import com.khantech.ts.dto.response.TransactionApiResponse
import com.khantech.ts.dto.response.UserResponse
import com.khantech.ts.entity.User
import com.khantech.ts.mapper.UserCreationMapper
import com.khantech.ts.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Unit tests for UserController")
class UserControllerTest extends Specification {

    def userService = Mock(UserService)
    def userCreationMapper = Mock(UserCreationMapper)

    @Subject
    UserController userController = new UserController(userService, userCreationMapper)

    def "should create a user and return the correct response"() {
        given: "A valid UserCreateRequest and corresponding mocked responses"

        def request = new UserCreateRequest("test-username")
        def userEntity = new User(1, "test-username", new BigDecimal(1000))
        def userResponse = new UserResponse(1, "test-username", new BigDecimal(1000))

        userCreationMapper.toEntity(request) >> userEntity
        userService.save(userEntity) >> userEntity
        userCreationMapper.toResponse(userEntity) >> userResponse

        when: "The create endpoint is called"
        ResponseEntity<TransactionApiResponse<UserResponse>> response = userController.create(request)

        then: "The response should be correct"
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.body.data == userResponse
        response.body.code == ApiCodes.USER_CREATED.code
    }
}
