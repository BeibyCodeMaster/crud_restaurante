package restaurante.example.demo.persistence.dao.interfaces.booKing;


import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
// Interfaz que define las operaciones básicas de acceso a datos para la entidad "Administrador".
public interface IMesaDao  extends ICrudDao<MesaEntity> {
    // Aquí puedes definir operaciones personalizadas si es necesario.
    Iterable<MesaEntity> findAllById(Iterable<Long> ids);
}
