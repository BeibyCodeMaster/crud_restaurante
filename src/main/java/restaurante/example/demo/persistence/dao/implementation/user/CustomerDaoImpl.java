package restaurante.example.demo.persistence.dao.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import restaurante.example.demo.persistence.dao.interfaces.user.ICustomerDao;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.persistence.repositoy.user.ICustomerRepository;

import java.util.List;
import java.util.Optional;

@Component
// Implementación de la interfaz ICustomerDao, que maneja las operaciones de acceso a datos para la entidad "Customer".
public class CustomerDaoImpl implements ICustomerDao {

    @Autowired
    private ICustomerRepository customerRepository; // Repositorio de acceso a datos para la entidad "Customer".

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findAll() {
        // Devuelve todos los registros de clientes desde la base de datos.
        return (List<CustomerEntity>) this.customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findById(Long id) {
        // Devuelve un registro de cliente basado en su ID, si existe.
        return this.customerRepository.findById(id);
    }

    @Override
    @Transactional
    public CustomerEntity save(CustomerEntity entity) {
        // Guarda o actualiza el cliente en la base de datos y retorna el objeto persistido.
        return this.customerRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Elimina un registro de cliente basado en su ID.
        this.customerRepository.deleteById(id);
    }
}