package com.example.searchservice.model;

import com.example.searchservice.listener.AccommodationEventListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Entity
@Table(name = "accommodation")
@Document(indexName = "accommodations")
@Setting(settingPath = "elasticsearch-settings.json")
@EntityListeners(AccommodationEventListener.class)
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "title", nullable = false, length = 255)
    @Field(type = FieldType.Text, analyzer = "custom_analyzer")
    private String title;
    
    @Column(name = "short_description", columnDefinition = "TEXT")
    @Field(type = FieldType.Text, analyzer = "custom_analyzer")
    private String shortDescription;
    
    @Column(name = "description", columnDefinition = "TEXT")
    @Field(type = FieldType.Text, analyzer = "custom_analyzer")
    private String description;
    
    @Column(name = "address", nullable = false, length = 255)
    @Field(type = FieldType.Text, analyzer = "custom_analyzer")
    private String address;
    
    @Column(name = "location_lat", nullable = false)
    @Field(type = FieldType.Double)
    private double locationLat;
    
    @Column(name = "location_lng", nullable = false)
    @Field(type = FieldType.Double)
    private double locationLng;
    
    @Column(name = "cover_img_src", length = 255)
    @Field(type = FieldType.Text)
    private String coverImgSrc;
    
    @Column(name = "price", nullable = false)
    @Field(type = FieldType.Integer)
    private int price;
    
    @Column(name = "num_of_bedroom", nullable = false)
    @Field(type = FieldType.Integer)
    private int numOfBedroom;
    
    @Column(name = "num_of_bed", nullable = false)
    @Field(type = FieldType.Integer)
    private int numOfBed;
    
    @Column(name = "max_guests", nullable = false)
    @Field(type = FieldType.Integer)
    private int maxGuests;

    public Accommodation() {
        // default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public String getCoverImgSrc() {
        return coverImgSrc;
    }

    public void setCoverImgSrc(String coverImgSrc) {
        this.coverImgSrc = coverImgSrc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumOfBedroom() {
        return numOfBedroom;
    }

    public void setNumOfBedroom(int numOfBedroom) {
        this.numOfBedroom = numOfBedroom;
    }

    public int getNumOfBed() {
        return numOfBed;
    }

    public void setNumOfBed(int numOfBed) {
        this.numOfBed = numOfBed;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
} 