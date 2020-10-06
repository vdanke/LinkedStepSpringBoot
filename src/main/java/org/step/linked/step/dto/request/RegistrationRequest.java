package org.step.linked.step.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistrationRequest {

    @Size(min = 3, max = 64, message = "Username is not valid")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @Min(value = 18, message = "Sorry, you are too young")
    @Max(value = 150, message = "Heaven is waiting for you")
    private Integer age;

    public RegistrationRequest() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
