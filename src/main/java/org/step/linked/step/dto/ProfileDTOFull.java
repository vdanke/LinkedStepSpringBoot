package org.step.linked.step.dto;

import org.step.linked.step.entity.Profile;

public class ProfileDTOFull {

    public String description;

    private ProfileDTOFull(String description) {
        this.description = description;
    }

    public static ProfileDTOFull toProfileDTOFull(Profile profile) {
        return new ProfileDTOFull(profile.getDescription());
    }
}
