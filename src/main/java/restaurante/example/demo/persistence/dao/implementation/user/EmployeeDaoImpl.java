package restaurante.example.demo.persistence.dao.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.user.IEmployeeDao;
import restaurante.example.demo.persistence.model.user.EmployeeEntity;
import restaurante.example.demo.persistence.repositoy.user.IEmployeeRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Component
// Implementación de la interfaz IEmployeeDao, que maneja las operaciones de acceso a datos para la entidad "Employee".
public class EmployeeDaoImpl implements IEmployeeDao {

    @Autowired
    private IEmployeeRepository employeeRepository; // Repositorio de acceso a datos para la entidad "Employee".

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeEntity> findAll() {
        // Devuelve todos los registros de empleados desde la base de datos.
        return (List<EmployeeEntity>) this.employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeEntity> findById(Long id) {
        // Devuelve un registro de empleado basado en su ID, si existe.
        return this.employeeRepository.findById(id);
    }

    @Override
    @Transactional
    public EmployeeEntity save(EmployeeEntity entity) {
        // Guarda o actualiza el empleado en la base de datos y retorna el objeto persistido.
        return this.employeeRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Elimina un registro de empleado basado en su ID.
        this.employeeRepository.deleteById(id);
    }
}
