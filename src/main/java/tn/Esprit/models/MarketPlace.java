package tn.Esprit.models;

import java.sql.Timestamp;

public class MarketPlace {
    private int idProd;
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

    public void setDateProd(Timestamp DateProd) {
        this.DateProd = DateProd;
    }

 

  

    @Override
    public String toString() {
        return "MakretPlace{" +
                "idProd=" + idProd +
                ", ProdName='" + ProdName + '\'' +
                ", ProdDescription='" + ProdDescription + '\'' +
                ", DateProd=" + DateProd +
                '}';
    }
}
