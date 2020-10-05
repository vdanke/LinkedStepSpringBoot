package org.step.linked.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.dto.ProfileDTO;
import org.step.linked.step.entity.Profile;
import org.step.linked.step.service.CrudService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private final CrudService<Profile, Long> profileCrudService;

    @Autowired
    public PublicController(CrudService<Profile, Long> profileCrudService) {
        this.profileCrudService = profileCrudService;
    }

    @GetMapping(value = "/profiles", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProfileDTO>> getPublicProfiles() {
        List<ProfileDTO> dtoProfileList = profileCrudService.findAll()
                .stream()
                .map(ProfileDTO::toProfileDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoProfileList);
    }
}
