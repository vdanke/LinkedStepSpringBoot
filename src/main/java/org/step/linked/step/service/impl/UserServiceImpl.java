package org.step.linked.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.linked.step.entity.User;
import org.step.linked.step.repository.UserRepository;
import org.step.linked.step.service.CrudService;
import org.step.linked.step.util.DbHelper;

import java.util.List;

@Service
public class UserServiceImpl implements CrudService<User, Long> {

    private final UserRepository userRepository;
    private final DbHelper<User> userDbHelper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           DbHelper<User> userDbHelper) {
        this.userRepository = userRepository;
        this.userDbHelper = userDbHelper;
    }

    @Override
    @Transactional
    public User save(User user) {
        Long id = userDbHelper.generateId(userRepository);
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
    public User update(Long aLong, User request) {
        User user = userRepository.findById(aLong)
                .orElseThrow(RuntimeException::new);

        user.setUsername(request.getUsername());

        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
