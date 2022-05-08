package io.github.jerrychin.testbackendjava.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class UserDTO {

    /**
     * user name, like Jerry Chin.
     */
    private String name;

    /**
     * date of birth.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    /**
     * user contact address
     */
    private String address;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
