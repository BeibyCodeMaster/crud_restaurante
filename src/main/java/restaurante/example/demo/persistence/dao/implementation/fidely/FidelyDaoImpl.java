package restaurante.example.demo.persistence.dao.implementation.fidely;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.fidelity.IFidelityDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.fidelity.FidelityEntity;
import restaurante.example.demo.persistence.repositoy.fidelity.IFidelityRepository;

import java.util.List;
import java.util.Optional;

@Component
public class FidelyDaoImpl implements IFidelityDao {

    @Autowired
    private IFidelityRepository fidelityRepository;

    @Override
    public List<FidelityEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<FidelityEntity>) this.fidelityRepository.findAll();    }

    @Override
    public Optional<FidelityEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.fidelityRepository.findById(id);
    }

    @Override
    public FidelityEntity save(FidelityEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.fidelityRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.fidelityRepository.deleteById(id);
    }
}
