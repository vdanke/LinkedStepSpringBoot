package org.step.linked.step.dto.request;

import javax.validation.constraints.Size;

public class UpdateRequest {

    @Size(min = 3, max = 64, message = "Update username with valid value")
    private String username;

    public UpdateRequest() {
    }

    public UpdateRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
