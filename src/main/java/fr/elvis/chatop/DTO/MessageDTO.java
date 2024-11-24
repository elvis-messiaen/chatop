package fr.elvis.chatop.DTO;

import java.util.Date;

public class MessageDTO {
    private int id;
    private String message;
    private Date createdAt;
    private Date updatedAt;
    private RentalDTO rental;

    public MessageDTO() {}

    public MessageDTO(int id, String message, Date createdAt, Date updatedAt, RentalDTO rental) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rental = rental;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RentalDTO getRental() {
        return rental;
    }

    public void setRental(RentalDTO rental) {
        this.rental = rental;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
