package org.step.linked.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.dto.ProfileDTOFull;
import org.step.linked.step.entity.Profile;
import org.step.linked.step.service.CrudService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final CrudService<Profile, Long> profileCrudService;

    @Autowired
    public ProfileController(CrudService<Profile, Long> profileCrudService) {
        this.profileCrudService = profileCrudService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProfileDTOFull>> getFullDescription() {
        List<ProfileDTOFull> profileDTOFulls = profileCrudService.findAll()
                .stream()
                .map(ProfileDTOFull::toProfileDTOFull)
                .collect(Collectors.toList());

        return ResponseEntity.ok(profileDTOFulls);
    }
}
