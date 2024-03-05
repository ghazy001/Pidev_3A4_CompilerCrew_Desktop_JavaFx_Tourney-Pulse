package tn.Esprit.models;


public class User {
    private int idU;
    private String NomU;
    private String PrenomU;
    private String EmailU;
    private String mdpU;
    private role roleU; // Utilisation de l'ENUM Role

    public User() {
    }

    public User(int idU, String nomU, String prenomU, String emailU, String mdpU, role roleU) {
        this.idU = idU;
        NomU = nomU;
        PrenomU = prenomU;
        EmailU = emailU;
        this.mdpU = mdpU;
        this.roleU = roleU;
    }

    public User(String nomU, String prenomU, String emailU, String mdpU, role roleU) {
        NomU = nomU;
        PrenomU = prenomU;
        EmailU = emailU;
        this.mdpU = mdpU;
        this.roleU = roleU;
    }
    public User(String nomU, String prenomU, String emailU, String mdpU) {
        NomU = nomU;
        PrenomU = prenomU;
        EmailU = emailU;
        this.mdpU = mdpU;
        this.roleU = role.utulisateur; // Définir le rôle par défaut
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNomU() {
        return NomU;
    }

    public void setNomU(String nomU) {
        NomU = nomU;
    }

    public String getPrenomU() {
        return PrenomU;
    }

    public void setPrenomU(String prenomU) {
        PrenomU = prenomU;
    }

    public String getEmailU() {
        return EmailU;
    }

    public void setEmailU(String emailU) {
        EmailU = emailU;
    }

    public String getMdpU() {
        return mdpU;
    }

    public void setMdpU(String mdpU) {
        this.mdpU = mdpU;
    }

    public role getRoleU() {
        return roleU;
    }

    public void setRoleU(role roleU) {
        this.roleU = roleU;
    }

    @Override
    public String toString() {
        return "User{" +
                "idU=" + idU +
                ", NomU='" + NomU + '\'' +
                ", PrenomU='" + PrenomU + '\'' +
                ", EmailU='" + EmailU + '\'' +
                ", mdpU='" + mdpU + '\'' +
                ", roleU=" + roleU +
                '}';
    }
}
