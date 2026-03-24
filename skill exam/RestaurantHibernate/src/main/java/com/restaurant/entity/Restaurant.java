package com.restaurant.entity;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "status", length = 20)
    private String status; // e.g., "OPEN", "CLOSED", "UNDER_RENOVATION"

    @Column(name = "cuisine_type", length = 50)
    private String cuisineType;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "rating")
    private double rating;

    // ─── Constructors ─────────────────────────────────────────────────────────

    public Restaurant() {}

    public Restaurant(String name, String status, String cuisineType,
                      String location, double rating) {
        this.name        = name;
        this.status      = status;
        this.cuisineType = cuisineType;
        this.location    = location;
        this.rating      = rating;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getName()                  { return name; }
    public void setName(String name)         { this.name = name; }

    public String getStatus()                { return status; }
    public void setStatus(String status)     { this.status = status; }

    public String getCuisineType()           { return cuisineType; }
    public void setCuisineType(String t)     { this.cuisineType = t; }

    public String getLocation()              { return location; }
    public void setLocation(String l)        { this.location = l; }

    public double getRating()                { return rating; }
    public void setRating(double rating)     { this.rating = rating; }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Restaurant{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", status='" + status + '\'' +
               ", cuisineType='" + cuisineType + '\'' +
               ", location='" + location + '\'' +
               ", rating=" + rating +
               '}';
    }
}
