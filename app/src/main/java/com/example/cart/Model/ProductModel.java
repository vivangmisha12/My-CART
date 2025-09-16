package com.example.cart.Model;

public class ProductModel {
    private int Id, ProId;

    private String Name;
    private String Details;
    private String MRP;
    private String SaleRate;
    private String PackSize;

    private String Picture;
    private String StockQty;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCatId() {
        return ProId;
    }

    public void setCatId(int proId) {
        ProId = proId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getSaleRate() {
        return SaleRate;
    }

    public void setSaleRate(String saleRate) {
        SaleRate = saleRate;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public void  setPicture(String picture){
        Picture=picture;
    }
    public String getPicture(){
        return Picture;
    }

    public String getStockQty() {
        return StockQty;
    }

    public void setStockQty(String stockQty) {
        StockQty = stockQty;
    }

}