package restaurante.example.demo.service.interfaces.common;

import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;

import java.util.List;

// Interfaz genérica que define los métodos principales para los casos de uso CRUD en el sistema.
public interface IUseCases<DTO, ID> {
    List<DTO> getAll(); // Obtiene todos los registros del tipo DTO.
    DTO getOneById(ID id) throws EntityNotFoundException; // Obtiene un registro por su ID.
    DTO create(DTO entityDto) throws EntityDataAccesException; // Crea un nuevo registro a partir de un DTO.
    DTO update(ID id, DTO entityDto) throws EntityNotFoundException, EntityDataAccesException; // Actualiza un registro existente.
    String delete(ID id) throws EntityNotFoundException; // Elimina un registro por su ID.
}