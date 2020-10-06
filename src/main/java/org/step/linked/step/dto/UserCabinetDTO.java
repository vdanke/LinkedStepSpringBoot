package org.step.linked.step.dto;

import org.step.linked.step.config.UserDetailsImpl;
import org.step.linked.step.entity.User;

public class UserCabinetDTO {

    public Long id;
    public String username;
    public Integer age;
    public ProfileDTOFull profile;

    private UserCabinetDTO(Long id, String username, Integer age, ProfileDTOFull profile) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.profile = profile;
    }

    public static UserCabinetDTO toUserCabinetDTO(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return new UserCabinetDTO(
                user.getId(),
                user.getUsername(),
                user.getAge(),
                ProfileDTOFull.toProfileDTOFull(user.getProfile())
        );
    }
}
