package tn.Esprit.models;

import java.sql.Timestamp;
import java.util.Arrays;

public class MarketPlace {
    private int idProd;
    private float Price;
    private int Quantity;

    public MarketPlace(int idProd, float price, int quantity, String prodName, String prodDescription, Timestamp dateProd, byte[] image) {
        this.idProd = idProd;
        Price = price;
        Quantity = quantity;
        ProdName = prodName;
        ProdDescription = prodDescription;
        DateProd = dateProd;
        this.image = image;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    private String ProdName;
   
    private String ProdDescription;
    private Timestamp DateProd;
    private byte[] image;
    
    private int userId; // Reference to the user who created the MakretPlace

    public MarketPlace() {}

    public MarketPlace(int idProd, String ProdName, String ProdDescription, Timestamp DateProd, int userId) {
        this.idProd = idProd;
        this.ProdName = ProdName;
        this.ProdDescription = ProdDescription;
        this.DateProd = DateProd;
       
     
        this.userId = userId; // Set the user who created the MakretPlace
    }


    // Getters and setters for user
    public int getUserId() {
        return userId;
    }

    public void setUserId(int user) {
        this.userId = userId;
    }

    // Constructor with Timestamp parameter
    public MarketPlace(int idProd, String ProdName, String ProdDescription, Timestamp DateProd,byte[] image) {
        this.idProd = idProd;
        this.ProdName = ProdName;
        this.ProdDescription = ProdDescription;
        this.DateProd = DateProd;
        this.image = image;
       
    }





    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String ProdName) {
        if (ProdName == null || ProdName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.ProdName = ProdName;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getProdDescription() {
        return ProdDescription;
    }

    public void setProdDescription(String ProdDescription) {
        if (ProdDescription == null || ProdDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be empty");
        }
        this.ProdDescription = ProdDescription;
    }

    public Timestamp getDateProd() {
        return DateProd;
    }

    @Override
    public String toString() {
        return "MarketPlace{" +
                "idProd=" + idProd +
                ", Price=" + Price +
                ", Quantity=" + Quantity +
                ", ProdName='" + ProdName + '\'' +
                ", ProdDescription='" + ProdDescription + '\'' +
                ", DateProd=" + DateProd +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public void setDateProd(Timestamp DateProd) {
        this.DateProd = DateProd;
    }


}
