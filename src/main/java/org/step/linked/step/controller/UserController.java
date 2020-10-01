package org.step.linked.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.dto.UserDTO;
import org.step.linked.step.dto.request.RegistrationRequest;
import org.step.linked.step.dto.request.UpdateRequest;
import org.step.linked.step.dto.response.RegistrationResponse;
import org.step.linked.step.entity.User;
import org.step.linked.step.service.CrudService;

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
@RequestMapping("/users")
public class UserController {

    private final CrudService<User, Long> userCrudService;

    @Autowired
    public UserController(CrudService<User, Long> userCrudService) {
        this.userCrudService = userCrudService;
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userCrudService.findAll()
                .stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public RegistrationResponse saveNewUser(@RequestBody RegistrationRequest request) {
        User user = new User(null, request.getUsername(), request.getPassword());

        User savedUser = userCrudService.save(user);

        return new RegistrationResponse(savedUser.getId(), savedUser.getUsername());
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
        userCrudService.delete(userId);
        return "User deleted";
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable(name = "id") String id,
                              @RequestBody UpdateRequest request) {
        final long userId = Long.parseLong(id);
        User user = userCrudService.update(userId, request);
        return UserDTO.toDTO(user);
    }
}