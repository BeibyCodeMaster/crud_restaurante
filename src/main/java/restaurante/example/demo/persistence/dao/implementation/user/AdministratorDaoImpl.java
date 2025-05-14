package restaurante.example.demo.persistence.dao.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.user.IAdministratorDao;
import restaurante.example.demo.persistence.model.user.AdministratorEntity;
import restaurante.example.demo.persistence.repositoy.user.IAdministratorRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Component
// Implementación de la interfaz IAdministratorDao, que maneja las operaciones de acceso a datos para la entidad "Administrator".
public class AdministratorDaoImpl implements IAdministratorDao {

    @Autowired
    private IAdministratorRepository administratorRepository; // Repositorio de acceso a datos para la entidad "Administrator".

    @Override
    @Transactional(readOnly = true)
    public List<AdministratorEntity> findAll() {
        // Devuelve todos los registros de administradores desde la base de datos.
        return (List<AdministratorEntity>) this.administratorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministratorEntity> findById(Long id) {
        // Devuelve un registro de administrador basado en su ID, si existe.
        return this.administratorRepository.findById(id);
    }

    @Override
    @Transactional
    public AdministratorEntity save(AdministratorEntity entity) {
        // Guarda o actualiza el administrador en la base de datos y retorna el objeto persistido.
        return this.administratorRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Elimina un registro de administrador basado en su ID.
        this.administratorRepository.deleteById(id);
    }
}