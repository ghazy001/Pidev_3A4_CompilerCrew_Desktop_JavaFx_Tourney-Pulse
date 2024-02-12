package test;

import services.UserService;
import utiles.MyConnection;
import entities.user;


public class Main {

    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        user u = new user(1,"badi");
        UserService u1 = new UserService();
         // u1.addEntity(u);
        // user u2 = new user(1,"232");
        // u1.updateEntity(u2);
        // user u3 = new user(1);
        // u1.deleteEntity(u2);
        // System.out.println(u1.getAllData());
    }
}