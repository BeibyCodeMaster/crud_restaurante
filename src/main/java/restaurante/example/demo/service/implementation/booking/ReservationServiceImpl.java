package restaurante.example.demo.service.implementation.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.booKing.IMesaDao;
import restaurante.example.demo.persistence.dao.interfaces.booKing.IReservationDao;
import restaurante.example.demo.persistence.dao.interfaces.user.ICustomerDao;
import restaurante.example.demo.persistence.enums.ReservationDetailsEnum;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.booking.ReservationEntity;
import restaurante.example.demo.persistence.model.booking.ReservationStatusEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.booking.ReservationDto;
import restaurante.example.demo.service.interfaces.booking.IReservationService;
import restaurante.example.demo.service.mapper.booking.IReservationMapper;

@Service // Indica que esta clase es un servicio de Spring y puede inyectarse en otras partes del sistema
public class ReservationServiceImpl implements IReservationService {

    // Inyección de dependencias para los DAOs y el mapper
    @Autowired
    private IReservationDao reservationDao; // DAO para acceder a la base de datos de reservas
    @Autowired
    private IReservationMapper reservationMapper; // Mapper para convertir entre entidad y DTO
    @Autowired
    private ICustomerDao customerDao; // DAO para gestionar clientes
    @Autowired
    private IMesaDao mesaDao; // DAO para gestionar mesas

    /**
     * Obtiene una lista de todas las reservas en la base de datos.
     *
     * @return Lista de objetos ReservationDto.
     */
    @Override
    public List<ReservationDto> getAll() {
        return this.reservationDao.findAll().stream()
                .map(reservationMapper::entityToDto) // Convierte cada entidad en un DTO
                .toList();
    }

    /**
     * Obtiene una reserva específica por su ID.
     *
     * @param id ID de la reserva.
     * @return Objeto ReservationDto de la reserva encontrada.
     * @throws EntityNotFoundException Si no se encuentra la reserva.
     */
    @Override
    public ReservationDto getOneById(Long id) throws EntityNotFoundException {
        return this.reservationDao.findById(id)
                .map(reservationMapper::entityToDto) // Convierte la entidad a un DTO si existe
                .orElseThrow(() -> new EntityNotFoundException("La reserva con el id " + id + " no existe."));
    }

    /**
     * Crea una nueva reserva en la base de datos.
     *
     * @param reservationDto DTO con los datos de la reserva.
     * @return DTO de la reserva creada.
     * @throws EntityDataAccesException Si no se encuentran el cliente o la mesa.
     */
    @Override
    public ReservationDto create(ReservationDto reservationDto) throws EntityDataAccesException {
        // Valida el cliente asociado a la reserva
        CustomerEntity customer = validateCustomer(reservationDto.getCustomer().getId());
        // Valida la mesa asociada a la reserva
        MesaEntity mesa = validateMesa(reservationDto.getIdMesa());

        // Crea una nueva reserva
        ReservationEntity reservationEntity = this.reservationMapper.dtoToEntity(reservationDto);
        reservationEntity.setCustomer(customer); // Asocia el cliente a la reserva
        // Configura las mesas y el estado inicial de la reserva
        reservationEntity.setMesas(Set.of(mesa));
        reservationEntity.setReservationStatus(
                ReservationStatusEntity.builder()
                        .status(ReservationDetailsEnum.CONFIRMADA) // Estado inicial de la reserva
                        .description("Se confirma la creación de la reserva")
                        .createdAt(LocalDateTime.now()) // Fecha de creación es la fecha actual
                        .build()
        );

        // Guarda la reserva en la base de datos
        ReservationEntity savedReservation = reservationDao.save(reservationEntity);
        return this.reservationMapper.entityToDto(savedReservation); // Devuelve la reserva como DTO
    }

    /**
     * Actualiza una reserva existente con nuevos datos.
     *
     * @param id            ID de la reserva a actualizar.
     * @param reservationDto DTO con los datos actualizados.
     * @return DTO de la reserva actualizada.
     * @throws EntityNotFoundException Si no se encuentra la reserva.
     * @throws EntityDataAccesException Si no se encuentra el cliente o la mesa.
     */
    @Override
    public ReservationDto update(Long id, ReservationDto reservationDto) throws EntityNotFoundException, EntityDataAccesException {
        // Valida el cliente y la mesa
        validateCustomer(reservationDto.getCustomer().getId());
        MesaEntity mesa = validateMesa(reservationDto.getIdMesa());

        // Busca la reserva por su ID
        ReservationEntity reservationEntity = this.reservationDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La reserva con el id " + id + " no existe."));

        // Actualiza las mesas asociadas a la reserva
        reservationEntity.setMesas(Set.of(mesa));
        reservationEntity.setDate(reservationDto.getDate());
        reservationEntity.setAvailableTime(reservationDto.getAvailableTime());
        reservationEntity.setUpdatedAt(LocalDateTime.now()); // Fecha de actualizacion
        ReservationEntity updatedReservation = this.reservationDao.save(reservationEntity);
        return this.reservationMapper.entityToDto(updatedReservation); // Devuelve la reserva actualizada como DTO
    }

    /**
     * Cancela una reserva existente.
     *
     * @param id     ID de la reserva a cancelar.
     * @param reason Razón de la cancelación.
     * @return DTO de la reserva cancelada.
     * @throws EntityNotFoundException Si no se encuentra la reserva.
     */
    @Override
    public ReservationDto cancel(Long id) throws EntityNotFoundException {
        // Busca la reserva por su ID
        ReservationEntity reservationEntity = this.reservationDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La reserva con el ID " + id + " no existe."));

        // Crea un nuevo estado de reserva "CANCELADA"
        ReservationStatusEntity reservationStatusEntity = ReservationStatusEntity.builder()
                .status(ReservationDetailsEnum.CANCELADA) // Estado de cancelación
                .description("Reserva cancelada por el usuario.") // Razón personalizada
                .build();

        // Actualiza el estado de la reserva
        reservationEntity.setReservationStatus(reservationStatusEntity);

        // Guarda los cambios en la base de datos
        this.reservationDao.save(reservationEntity);

        // Retorna la reserva actualizada como DTO
        return reservationMapper.entityToDto(reservationEntity);
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar.
     * @return Mensaje de confirmación de la eliminación.
     * @throws EntityNotFoundException Si no se encuentra la reserva.
     */
    @Override
    public String delete(Long id) throws EntityNotFoundException {
        this.reservationDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La reserva con el ID " + id + " no existe."));

        this.reservationDao.delete(id); // Elimina la reserva
        return "La reserva con el ID " + id + " fue eliminada.";
    }

    /**
     * Valida que un cliente exista en la base de datos.
     *
     * @param customerId ID del cliente.
     * @return Entidad del cliente.
     * @throws EntityDataAccesException Si el cliente no existe.
     */
    private CustomerEntity validateCustomer(Long customerId) throws EntityDataAccesException {
        return customerDao.findById(customerId)
                .orElseThrow(() -> new EntityDataAccesException("Cliente no encontrado."));
    }

    /**
     * Valida que una mesa exista en la base de datos.
     *
     * @param mesaId ID de la mesa.
     * @return Entidad de la mesa.
     * @throws EntityDataAccesException Si la mesa no existe.
     */
    private MesaEntity validateMesa(Long mesaId) throws EntityDataAccesException {
        return mesaDao.findById(mesaId)
                .orElseThrow(() -> new EntityDataAccesException("Mesa no encontrada."));
    }
}
