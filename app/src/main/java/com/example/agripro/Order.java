package com.example.agripro;

public class Order {
    String OrderId;
    String ProductName;
    String Unit;
    String ProductImg;
    String Category;
    String Quantity;
    Long ProdQuantity;
    Long RetailPrice;
    Long WholesSale;

    public Order(){

    }

    public Order(String orderId ,String productName, String unit, String productImg, String category, String quantity, Long prodQuantity, Long retailPrice, Long wholesSale) {
        OrderId = orderId;
        ProductName = productName;
        Unit = unit;
        ProductImg = productImg;
        Category = category;
        Quantity = quantity;
        ProdQuantity = prodQuantity;
        RetailPrice = retailPrice;
        WholesSale = wholesSale;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public Long getProdQuantity() {
        return ProdQuantity;
    }

    public void setProdQuantity(Long prodQuantity) {
        ProdQuantity = prodQuantity;
    }

    public Long getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(Long retailPrice) {
        RetailPrice = retailPrice;
    }

    public Long getWholesSale() {
        return WholesSale;
    }

    public void setWholesSale(Long wholesSale) {
        WholesSale = wholesSale;
    }
}
