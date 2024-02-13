package service;

public interface InterfaceServices<T> {
    void ajouter(T t);
    void modifier(int id,T t);
    void supprimer(int id);
    void afficher();

}
