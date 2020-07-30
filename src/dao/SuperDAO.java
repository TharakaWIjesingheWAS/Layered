package dao;

import java.util.List;

public interface SuperDAO {
    List<Object> findAll();

    Object find(Object key);

    boolean save(Object entity);

    boolean update(Object entity);

    boolean delete(Object key);
}
