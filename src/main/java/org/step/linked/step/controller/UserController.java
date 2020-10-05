package org.step.linked.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.dto.UserDTO;
import org.step.linked.step.dto.request.RegistrationRequest;
import org.step.linked.step.dto.request.UpdateRequest;
import org.step.linked.step.dto.response.RegistrationResponse;
import org.step.linked.step.entity.User;
import org.step.linked.step.service.CrudService;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;

/*
REST API
0. 1 url - 1 METHOD
1. 1 url - N METHODS
2. N url - 1 METHOD
3. N url - N METHODS
/users/{id}/username
/users/{id}/messages/{id}/date
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CrudService<User, Long> userCrudService;

    @Autowired
    public UserController(CrudService<User, Long> userCrudService) {
        this.userCrudService = userCrudService;
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> dtoList = userCrudService.findAll()
                .stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "token");

        ResponseEntity<List<UserDTO>> dtoListResponseEntity = new ResponseEntity<>(
                dtoList, headers, HttpStatus.OK
        );

        return dtoListResponseEntity;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> saveNewUser(@RequestBody RegistrationRequest request) {
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

    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable(name = "id") String id) {
        final long userId = Long.parseLong(id);
        User userById = userCrudService.findById(userId);
        return UserDTO.toDTO(userById);
    }

    /*
    http://localhost:8585/users/1 - PUT, DELETE, GET
    http://localhost:8585/users - POST, GET
    1 - {id}
     */
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable(name = "id") String id) {
        final long userId = Long.parseLong(id);
        userCrudService.deleteById(userId);
        return "User deleted";
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable(name = "id") String id,
                              @RequestBody UpdateRequest request) {
        final long userId = Long.parseLong(id);
        User toUpdate = User.builder().username(request.getUsername()).build();
        User user = userCrudService.update(userId, toUpdate);
        return UserDTO.toDTO(user);
    }

    @GetMapping("/cabinet")
    public UserDTO showUserInSecuritySession(@AuthenticationPrincipal UserDetails userDetails) {
//        Authentication authentication = getAuthentication();
//
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//
//        return new UserDTO(null, principal.getUsername());
        return new UserDTO(null, userDetails.getUsername());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}