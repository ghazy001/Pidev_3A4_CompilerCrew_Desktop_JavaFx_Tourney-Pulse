package interfaces;

import java.util.List;

public interface IService<T> {
    void Add(T item);
    void Update(T item);
    void delete(T item);
    List<T> Get();
}
