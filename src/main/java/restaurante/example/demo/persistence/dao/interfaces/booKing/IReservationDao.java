package restaurante.example.demo.persistence.dao.interfaces.booKing;

import java.util.Optional;
import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.booking.ReservationEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;

public interface IReservationDao extends ICrudDao<ReservationEntity> {
       Optional<ReservationEntity>  findByCustomer(CustomerEntity customer);  
}
