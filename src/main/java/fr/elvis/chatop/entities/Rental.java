package fr.elvis.chatop.entities;
import jakarta.persistence.*;

import java.util.Date;

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
    private int owner_id;
    private Date created_at;
    private Date updated_at;

    public Rental(Date created_at, String description, int id, String name, int owner_id, String picture, double price, double surface, Date updated_at) {
        this.created_at = created_at;
        this.description = description;
        this.id = id;
        this.name = name;
        this.owner_id = owner_id;
        this.picture = picture;
        this.price = price;
        this.surface = surface;
        this.updated_at = updated_at;
    }

    public Rental() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
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
                "created_at=" + created_at +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surface=" + surface +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", owner_id=" + owner_id +
                ", updated_at=" + updated_at +
                '}';
    }
}
