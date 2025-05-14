package restaurante.example.demo.persistence.dao.interfaces.common;


import java.util.List;
import java.util.Optional;

// Interfaz que define las operaciones básicas de acceso a datos.
public interface ICrudDao<T> {
    List<T> findAll();// Listar todos los registros.
    Optional<T> findById(Long id);// Buscar un registro por su ID.
    T save(T entity);// Guardar o actualizar un registro en la base de datos.
    void delete(Long id); // Eliminar un registro por su ID.
}
