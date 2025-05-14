/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.persistence.dao.implementation.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import restaurante.example.demo.persistence.dao.interfaces.user.ISuperDao;
import restaurante.example.demo.persistence.model.user.SuperEntity;
import restaurante.example.demo.persistence.repositoy.user.ISuperRepository;

@Component
// Implementación de la interfaz ISuperDao, que maneja las operaciones de acceso a datos para la entidad "Super".
public class SuperDaoImpl implements ISuperDao {

    @Autowired
    private ISuperRepository superRepository; // Repositorio de acceso a datos para la entidad "Super".

    @Override
    @Transactional(readOnly = true)
    public List<SuperEntity> findAll() {
        // Devuelve todos los registros de super desde la base de datos.
        return (List<SuperEntity>) this.superRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SuperEntity> findById(Long id) {
        // Devuelve un registro de super basado en su ID, si existe.
        return this.superRepository.findById(id);
    }

    @Override
    @Transactional
    public SuperEntity save(SuperEntity entity) {
        // Guarda o actualiza el super en la base de datos y retorna el objeto persistido.
        return this.superRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Elimina un registro de super basado en su ID.
        this.superRepository.deleteById(id);
    }
}
