package restaurante.example.demo.persistence.dao.implementation.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.booKing.IReservationStatusDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.booking.ReservationStatusEntity;
import restaurante.example.demo.persistence.repositoy.booking.IReservationStatusRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationStatusDaoImpl implements IReservationStatusDao {

    @Autowired
    private IReservationStatusRepository reservationStatusRepository;

    @Override
    public List<ReservationStatusEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<ReservationStatusEntity>) this.reservationStatusRepository.findAll();
    }

    @Override
    public Optional<ReservationStatusEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.reservationStatusRepository.findById(id);
    }

    @Override
    public ReservationStatusEntity save(ReservationStatusEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.reservationStatusRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.reservationStatusRepository.deleteById(id);
    }
}
