package fr.elvis.chatop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double surface;
    private double price;
    private String picture;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proprietaire_id")
    @JsonBackReference
    private UserEntity proprietaire;

    @OneToMany(mappedBy = "rental")
    private Set<MessagesEntity> messages;

    public Rental() {}

    public Rental(Date created_at, String description, int id, String name, String picture, double price, UserEntity proprietaire, double surface, Date updated_at) {
        this.created_at = created_at;
        this.description = description;
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.proprietaire = proprietaire;
        this.surface = surface;
        this.updated_at = updated_at;
    }

    @PrePersist
    public void onCreate() {
        created_at = new Date();
        updated_at = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updated_at = new Date();
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<MessagesEntity> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessagesEntity> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UserEntity getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(UserEntity proprietaire) {
        this.proprietaire = proprietaire;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surface=" + surface +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", proprietaire=" + proprietaire +
                '}';
    }
}
