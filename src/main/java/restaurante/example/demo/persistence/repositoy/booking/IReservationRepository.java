package restaurante.example.demo.persistence.repositoy.booking;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.booking.ReservationEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;

public interface IReservationRepository extends CrudRepository<ReservationEntity,Long> {
   ReservationEntity  findByCustomer(CustomerEntity customer);    
}
