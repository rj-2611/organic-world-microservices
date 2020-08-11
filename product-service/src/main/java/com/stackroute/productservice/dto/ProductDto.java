package com.stackroute.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Represents the Data Transfer object for Product Entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotEmpty(message = "{product.id.empty}")
    private String id;

    @NotEmpty(message = "{product.name.empty}")
    private String name;
    private String description;

    @NotEmpty(message = "{product.category.empty}")
    private String category;
    private double price;

    @JsonProperty("image")
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
