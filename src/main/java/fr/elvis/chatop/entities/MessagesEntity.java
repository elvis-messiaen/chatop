package fr.elvis.chatop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class MessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "message", length = 2000)
    private String message;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_id")
    @JsonBackReference
    private Rental rental;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    public MessagesEntity(Rental rental, UserEntity user, String message, Timestamp createdAt) {
        this.rental = rental;
        this.user = user;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }


    public MessagesEntity() {

    }
    public Rental getRental() { return rental; }

    public void setRental(Rental rental) { this.rental = rental; }

    public UserEntity getUser() { return user; }

    public void setUser(UserEntity user) { this.user = user; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


}
