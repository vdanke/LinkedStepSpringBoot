package org.step.linked.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.dto.request.RegistrationRequest;
import org.step.linked.step.dto.response.RegistrationResponse;
import org.step.linked.step.entity.User;
import org.step.linked.step.service.CrudService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthController {

    private final CrudService<User, Long> userCrudService;

    @Autowired
    public AuthController(CrudService<User, Long> userCrudService) {
        this.userCrudService = userCrudService;
    }

    /*
    /registration/something - not valid by spring security
     */
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> saveNewUser(@Valid @RequestBody RegistrationRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .age(request.getAge())
                .build();

        User savedUser = userCrudService.save(user);

        RegistrationResponse registrationResponse = new RegistrationResponse(
                savedUser.getId(), savedUser.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "new user")
                .body(registrationResponse);
    }
}
