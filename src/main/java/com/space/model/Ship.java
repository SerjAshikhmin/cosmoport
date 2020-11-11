package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Ship {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    //@Size(max = 50)
    private String name;
    //@Size(max = 50)
    private String planet;
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    //@Min(2800)
    //@Max(3019)
    private Date prodDate;
    private Boolean isUsed;
    //@Min(0.01)
    //@Max(0.99)
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Ship() {
    }

    public Ship(Long id, String name, String planet, ShipType shipType, Date prodDate, boolean isUsed, double speed, Integer crewSize, double rating) {
        this.id = id;
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean isUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
