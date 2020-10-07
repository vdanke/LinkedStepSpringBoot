package org.step.linked.step.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.step.linked.step.dto.ProfileDTO;
import org.step.linked.step.entity.Profile;
import org.step.linked.step.service.CrudService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PublicController.class)
@AutoConfigureDataJpa
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "profileServiceImpl")
    private CrudService<Profile, Long> profileCrudService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnAllProfilesPublic() throws Exception {
        List<Profile> profiles = Collections.singletonList(
                new Profile(1L, "desc")
        );

        List<ProfileDTO> profileDTOList = profiles.stream()
                .map(ProfileDTO::toProfileDTO)
                .collect(Collectors.toList());

        String json = objectMapper.writeValueAsString(
                profileDTOList
        );

        Mockito.when(profileCrudService.findAll()).thenReturn(profiles);

        mockMvc.perform(get("/api/v1/public/profiles"))
                .andExpect(MockMvcResultMatchers.content().json(json))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
