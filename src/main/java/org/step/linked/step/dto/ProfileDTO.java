package org.step.linked.step.dto;

import org.step.linked.step.entity.Profile;

public class ProfileDTO {

    private String shortDescription;

    public ProfileDTO() {
    }

    public ProfileDTO(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public static ProfileDTO toProfileDTO(Profile profile) {
        final String description = profile.getDescription();
        return new ProfileDTO(description.substring(0, description.length() / 2));
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
