package restaurante.example.demo.persistence.dao.implementation.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.booKing.IReservationDao;
import restaurante.example.demo.persistence.model.booking.ReservationEntity;
import restaurante.example.demo.persistence.repositoy.booking.IReservationRepository;

import java.util.List;
import java.util.Optional;
import restaurante.example.demo.persistence.model.user.CustomerEntity;

@Component
public class ReservationDaoImpl  implements IReservationDao {

    @Autowired
    private IReservationRepository reservationRepository;

    @Override
    public List<ReservationEntity> findAll() {
        // Devuelve todos los registros de las reservas desde la base de datos.
        return  (List<ReservationEntity>) this.reservationRepository.findAll();
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        // Devuelve un registro de la reserva basado en su ID, si existe.
        return this.reservationRepository.findById(id);
    }

    @Override
    public ReservationEntity save(ReservationEntity entity) {
        // Guarda o actualiza la reserva en la base de datos y retorna el objeto persistido.
        return this.reservationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de la reserva basado en su ID.
        this.reservationRepository.deleteById(id);
    }

    @Override
    public Optional<ReservationEntity> findByCustomer(CustomerEntity customer) {
        return Optional.of(this.reservationRepository.findByCustomer(customer));
    }
}
