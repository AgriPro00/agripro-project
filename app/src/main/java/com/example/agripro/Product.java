package com.example.agripro;

public class Product {
    private String ProductName;
    private String Description;
    private String Category;
    private String Unit;
    private String UnitImg;
    private Long Quantity;

    private String RetailPrice;

    public Product() {

    }

    public Product(String productName, String description, String category, String unit, String unitImg, Long quantity, String retailPrice, String wholeSale) {
        ProductName = productName;
        Description = description;
        Category = category;
        Unit = unit;
        UnitImg = unitImg;
        Quantity = quantity;
        RetailPrice = retailPrice;
        WholeSale = wholeSale;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getUnitImg() {
        return UnitImg;
    }

    public void setUnitImg(String unitImg) {
        UnitImg = unitImg;
    }

    public Long getQuantity() {
        return Quantity;
    }

    public void setQuantity(Long quantity) {
        Quantity = quantity;
    }

    public String getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        RetailPrice = retailPrice;
    }

    public String getWholeSale() {
        return WholeSale;
    }

    public void setWholeSale(String wholeSale) {
        WholeSale = wholeSale;
    }

    private String WholeSale;
}
