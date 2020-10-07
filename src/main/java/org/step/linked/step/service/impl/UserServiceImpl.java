package org.step.linked.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.linked.step.config.UserDetailsImpl;
import org.step.linked.step.entity.User;
import org.step.linked.step.exception.UserNotFoundException;
import org.step.linked.step.repository.UserRepository;
import org.step.linked.step.service.CrudService;
import org.step.linked.step.util.DbHelper;

import java.util.List;

@Service
public class UserServiceImpl implements CrudService<User, Long>, UserDetailsService {

    private final UserRepository userRepository;
    private final DbHelper<User> userDbHelper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           DbHelper<User> userDbHelper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDbHelper = userDbHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User save(User user) {
        final Long id = userDbHelper.generateId(userRepository);
        final String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %d not found", id), id
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public User find(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %d not found", user.getId()), user.getId()
        ));
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User update(Long aLong, User request) {
        /*
        Hibernate вытаскивае сущность по id и ложит ее в PersistenceContext
        Hibernate валидирует изменения и производит update только по изменившимся полям
         */
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

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username %s doesn't exists", username))
                );

//        Set<SimpleGrantedAuthority> authorities = user.getRoles()
//                .stream()
//                .map(Role::name)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());

//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                true, // аккаунт активный
//                true, // аккаунт не просрочен
//                true, // пароль от аккаунта не просрочен
//                true, // аккаунт не заблокирован
//                user.getRoles()
//        );
        return new UserDetailsImpl(user);
    }
}
