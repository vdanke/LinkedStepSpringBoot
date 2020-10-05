package org.step.linked.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.step.linked.step.entity.Profile;
import org.step.linked.step.repository.ProfileRepository;
import org.step.linked.step.service.CrudService;

import java.util.List;

@Service
public class ProfileServiceImpl implements CrudService<Profile, Long> {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile findById(Long aLong) {
        return null;
    }

    @Override
    public Profile find(Profile profile) {
        return null;
    }

    @Override
    public void delete(Profile profile) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Profile update(Long aLong, Profile profile) {
        return null;
    }
}
