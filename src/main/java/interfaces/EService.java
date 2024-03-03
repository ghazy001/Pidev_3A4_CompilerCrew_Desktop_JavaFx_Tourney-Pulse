package interfaces;
import java.util.List;

public interface EService <T>{

    void addEntity(T t);
    void updateEntity(T t);
    void deleteEntity(T t);
    List<T> getAllData();
}
