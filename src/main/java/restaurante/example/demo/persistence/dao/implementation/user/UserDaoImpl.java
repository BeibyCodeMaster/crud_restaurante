package restaurante.example.demo.persistence.dao.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import restaurante.example.demo.persistence.dao.interfaces.user.IUserDao;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.repositoy.user.IUserRepository;

import java.util.List;
import java.util.Optional;

@Component
// Implementación de la interfaz IUserDao, que maneja las operaciones de acceso a datos para la entidad "User".
public class UserDaoImpl implements IUserDao {

    @Autowired
    private IUserRepository userRepository; // Repositorio de acceso a datos para la entidad "User".

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        // Devuelve todos los registros de usuarios desde la base de datos.
        return (List<UserEntity>) this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        // Devuelve un registro de usuario basado en su ID, si existe.
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity entity) {
        // Guarda o actualiza el usuario en la base de datos y retorna el objeto persistido.
        return this.userRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Elimina un registro de usuario basado en su ID.
        this.userRepository.deleteById(id);
    }
}