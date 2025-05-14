package restaurante.example.demo.persistence.dao.implementation.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.product.IMenuDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.product.MenuEntity;
import restaurante.example.demo.persistence.repositoy.product.IMenuRepository;

import java.util.List;
import java.util.Optional;

@Component
public class MenuDaoImpl implements IMenuDao {

    @Autowired
    private IMenuRepository menuRepository;

    @Override
    public List<MenuEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<MenuEntity>) this.menuRepository.findAll();
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.menuRepository.findById(id);
    }

    @Override
    public MenuEntity save(MenuEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.menuRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.menuRepository.deleteById(id);
    }
}
