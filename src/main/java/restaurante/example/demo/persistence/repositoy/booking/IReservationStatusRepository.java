package restaurante.example.demo.persistence.repositoy.booking;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.booking.ReservationStatusEntity;

public interface IReservationStatusRepository extends CrudRepository<ReservationStatusEntity,Long> {
}
