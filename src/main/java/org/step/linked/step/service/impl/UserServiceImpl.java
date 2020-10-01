package org.step.linked.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.linked.step.dto.request.UpdateRequest;
import org.step.linked.step.entity.User;
import org.step.linked.step.repository.UserRepository;
import org.step.linked.step.service.CrudService;

import java.util.List;

@Service
public class UserServiceImpl implements CrudService<User, Long> {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        Long id = userRepository.findMaxId();
        if (id == null) {
            id = 1L;
        } else {
            ++id;
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User find(User user) {
        return userRepository.findById(user.getId()).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User update(Long aLong, UpdateRequest request) {
//        User user = userRepository.findById(aLong)
//                .orElseThrow(RuntimeException::new);
//
//        userRepository.updateUsername(request.getUsername(), aLong);
//
//        return user;
        User user = userRepository.findById(aLong)
                .orElseThrow(RuntimeException::new);

        user.setUsername(request.getUsername());

        userRepository.flush();

        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
