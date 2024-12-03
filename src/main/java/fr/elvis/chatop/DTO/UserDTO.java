package fr.elvis.chatop.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Set;

public class UserDTO {
    private int id;

    @JsonProperty("name")
    private String username;

    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date updatedAt;

    private Set<RoleDTO> role;
    private Set<RentalDTO> rentals;
    private Set<MessageDTO> messages;

    public UserDTO() {}

    public UserDTO(int id, String username, String email, String password, Set<RoleDTO> role, Date createdAt, Date updatedAt, Set<RentalDTO> rentals, Set<MessageDTO> messages) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rentals = rentals;
        this.messages = messages;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<RoleDTO> getRole() {
        return role;
    }

    public void setRole(Set<RoleDTO> role) {
        this.role = role;
    }

    public Set<RentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(Set<RentalDTO> rentals) {
        this.rentals = rentals;
    }

    public Set<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageDTO> messages) {
        this.messages = messages;
    }
}