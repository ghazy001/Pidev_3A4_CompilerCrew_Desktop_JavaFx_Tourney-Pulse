package entities;

public class user {
    private int id;
    private String password;


    public user(int id, String password) {
        this.id = id;
        this.password = password;
    }


    public user(){
        this.id = id;
    }
    public user(int id){

    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}