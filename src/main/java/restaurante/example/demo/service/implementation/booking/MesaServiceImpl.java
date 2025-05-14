package restaurante.example.demo.service.implementation.booking;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.booKing.IMesaDao;
import restaurante.example.demo.persistence.enums.MesaLocationEnum;
import restaurante.example.demo.persistence.enums.MesaStateEnum;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.presentation.dto.booking.MesaDto;
import restaurante.example.demo.service.interfaces.booking.IMesaService;
import restaurante.example.demo.service.mapper.booking.IMesaMapper;


@Service
// Servicio encargado de implementar la lógica de negocio para la entidad Mesa.
public class MesaServiceImpl implements IMesaService {

    @Autowired
    private IMesaDao mesaDao; // DAO para acceder a la base de datos

    @Autowired
    private IMesaMapper mesaMapper; // Mapper para convertir entre entidad y DTO

    // Obtiene la lista de todas las mesas en forma de DTOs
    @Override
    public List<MesaDto> getAll() {
        return this.mesaDao.findAll().stream()
                .map(this.mesaMapper::entityToDto)
                .toList();
    }

    // Obtiene una mesa específica por su ID y la convierte en un DTO
    @Override
    public MesaDto getOneById(Long id) throws EntityNotFoundException {
        return this.mesaDao.findById(id)
                .map(this.mesaMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("La mesa con el id " + id + " no existe."));
    }

    // Crea una nueva mesa en la base de datos a partir de un DTO proporcionado
    @Override
    public MesaDto create(MesaDto MesaDto) throws EntityDataAccesException {
        MesaEntity mesaEntity = this.mesaMapper.dtoToEntity(MesaDto);
        try {
            this.mesaDao.save(mesaEntity); // Guarda la entidad en la base de datos
            return this.mesaMapper.entityToDto(mesaEntity); // Devuelve la entidad guardada como DTO
        } catch (DataAccessException e) {
            System.err.println("service/create :" + e.getMessage()); // Log de error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }

    // Actualiza una mesa existente, validando primero su existencia
    @Override
    public MesaDto update(Long id, MesaDto MesaDto) throws EntityNotFoundException, EntityDataAccesException {
        MesaEntity mesaEntity =  this.mesaDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La mesa no existe."));
        this.updateEntityFromDto(mesaEntity, MesaDto); // Actualiza los campos de la entidad
        try {
            this.mesaDao.save(mesaEntity); // Guarda los cambios en la base de datos
            return this.mesaMapper.entityToDto(mesaEntity); // Devuelve la entidad actualizada como DTO
        } catch (DataAccessException e) {
            System.err.println("service/update :" + e.getMessage()); // Log de error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }

    // Elimina una mesa específica por su ID, verificando su existencia
    @Override
    public String delete(Long id) throws EntityNotFoundException {
        Optional<MesaEntity> mesaOptional = this.mesaDao.findById(id);
        
        if (mesaOptional.isEmpty()) {
            throw new EntityNotFoundException("La mesa con el ID " + id + " no existe.");
        }
        this.mesaDao.delete(id);
        return "La mesa con el ID " + id + " fue eliminada.";
    }

    // Método auxiliar que actualiza los atributos de la entidad Mesa a partir de un DTO
    private void updateEntityFromDto(MesaEntity mesaEntity, MesaDto mesaDto){
        mesaEntity.setCode(mesaDto.getCode());
        mesaEntity.setCapacity(mesaDto.getCapacity());
        mesaEntity.setStatus(MesaStateEnum.valueOf(mesaDto.getStatus().name()));
        mesaEntity.setLocation(MesaLocationEnum.valueOf(mesaDto.getLocation().name()));
        mesaEntity.setDecoration(mesaDto.getDecoration());
        mesaEntity.setImage(mesaDto.getImage());
        mesaEntity.setImagePath(mesaDto.getImagePath());
    }

}
