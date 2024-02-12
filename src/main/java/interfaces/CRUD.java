package interfaces;
import java.util.List;

public interface CRUD <T>{

    void addEntity(T t);
    void updateEntity(T t);
    void deleteEntity(T t);
    List<T> getAllData();
}
