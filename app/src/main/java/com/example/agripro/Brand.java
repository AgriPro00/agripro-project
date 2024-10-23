package com.example.agripro;

public class Brand {

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImg() {
        return ProductImg;
    }

    public void setProductImg(String productImg) {
        ProductImg = productImg;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Long getPostNumber() {
        return PostNumber;
    }

    public void setPostNumber(Long postNumber) {
        PostNumber = postNumber;
    }

    public Brand() {
        // Required for Firestore deserialization
    }

    public Brand(String productName, String productImg, String category, Long postNumber) {
        ProductName = productName;
        ProductImg = productImg;
        Category = category;
        PostNumber = postNumber;
    }

    String ProductName;
    String ProductImg;
    String Category;
    Long PostNumber;
}
