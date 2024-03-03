package entities;

public class Admin extends User {

    public Admin(int id, String firstname, String lastname, String username, String email, String number, String password) {
        super(id, firstname, lastname, username, email, number, password, "admin");
    }
}

