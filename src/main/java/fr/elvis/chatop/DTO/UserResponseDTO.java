package fr.elvis.chatop.DTO;

import java.util.Date;

public class UserResponseDTO {
    private String username;
    private String email;
    private Date created_at;
    private Date updated_at;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setUpdatedAt(Date updatedAt) {

    }

    public void setCreatedAt(Date createdAt) {

    }
}
