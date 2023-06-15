package DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> extends AutoCloseable {
    void guardar(T entity);

    List<T> getAll() throws SQLException ;

    void eliminar(int entity) throws SQLException;

    void actualizar(T entity) throws SQLException;
    void eliminar(String entity) throws SQLException;

    int getClientTotal();
}
