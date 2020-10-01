package org.step.linked.step.dto.request;

public class UpdateRequest {

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
