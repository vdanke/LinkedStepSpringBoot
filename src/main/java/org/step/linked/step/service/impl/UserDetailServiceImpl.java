package org.step.linked.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.linked.step.config.UserDetailsImpl;
import org.step.linked.step.entity.User;
import org.step.linked.step.repository.UserRepository;

@Service("userDetailsServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
