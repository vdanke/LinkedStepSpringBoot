package org.step.linked.step.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.step.linked.step.entity.Profile;
import org.step.linked.step.entity.User;
import org.step.linked.step.service.CrudService;
import org.step.linked.step.service.impl.UserServiceImpl;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = ProfileController.class)
//@AutoConfigureDataJpa
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean(name = "userServiceImpl")
//    private CrudService<User, Long> userCrudService;
//    @MockBean(name = "profileServiceImpl")
//    private CrudService<Profile, Long> profileCrudService;
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//    @MockBean(name = "userServiceImpl")
//    private UserDetailsService userDetailsService;

    @Test
    public void test() throws Exception {
//        Mockito.when(profileCrudService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/public/profiles"))
                .andExpect(status().isOk());
    }

//    @Configuration
//    @ComponentScan
//    public static class TestConfig {
//        @Bean
//        public PasswordEncoder testPasswordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Bean
//        public UserDetailsService userDetailsService(UserRepository userRepository,
//                                                     @Qualifier("testPasswordEncoder") PasswordEncoder passwordEncoder,
//                                                     DbHelper<User> dbHelper) {
//            return new UserServiceImpl(
//                    userRepository,
//                    dbHelper,
//                    passwordEncoder
//            );
//        }
//
//
//    }
}
