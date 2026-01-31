package com.example.MEDICINE.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class labtest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;
    private String description;
    private Double price;
    private String category;
    private String sampleType;
    private String preparation;
    private String resultTime;

    //getter and setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSampleType() {
        return sampleType;
    }
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }
    public String getPreparation() {
        return preparation;
    }
    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
    public String getResultTime() {
        return resultTime;
    }
    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

}
