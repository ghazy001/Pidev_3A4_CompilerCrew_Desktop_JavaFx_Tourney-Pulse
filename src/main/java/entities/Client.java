package entities;


public class Client extends User {

    public Client(int id, String firstname, String lastname, String username, String email, String number, String password) {
        super(id, firstname, lastname, username, email, number, password, "User");
    }
}