package tn.Esprit.iservices;

import tn.Esprit.models.User;

import java.util.ArrayList;

public interface IService <T> {

    public  void add(T t);
    public ArrayList<T> getAll();
    public void update(T t);
    public boolean delete(T t);

    User readById(int idDM);
    //  T readById(int idU);
}
