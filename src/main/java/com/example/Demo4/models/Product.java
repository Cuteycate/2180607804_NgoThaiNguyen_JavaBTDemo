package com.example.Demo4.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class Product {
    private int id;
    @NotBlank ( message = "tên sản phẩm không được để trống !!!")
    private String name;
    @Length(min =0,max =50, message="Tên hình ảnh không quá 50 ký tự")
    private String image;

    @NotNull( message = (" Gia san pham khong duoc de trong"))
    @Min( value =1, message ="Gia san pham khong duoc nho hon 1")
    @Max(value = 9999999, message = "GIa san pham khong duoc lon hon 999999999")
    private long price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = (" Gia san pham khong duoc de trong"))
    @Min(value = 1, message = "Gia san pham khong duoc nho hon 1")
    @Max(value = 9999999, message = "GIa san pham khong duoc lon hon 999999999")
    public long getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = (" Gia san pham khong duoc de trong")) @Min(value = 1, message = "Gia san pham khong duoc nho hon 1") @Max(value = 9999999, message = "GIa san pham khong duoc lon hon 999999999") long price) {
        this.price = price;
    }

    public Product(int id, String name, String image, long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product(){

    }
}
