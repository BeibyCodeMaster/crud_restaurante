package restaurante.example.demo.persistence.dao.implementation.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.product.IProductDao;
import restaurante.example.demo.persistence.model.product.ProductEntity;
import restaurante.example.demo.persistence.repositoy.product.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ProductDaoImpl implements IProductDao {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<ProductEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<ProductEntity>) this.productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.productRepository.findById(id);
    }

    @Override
    public ProductEntity save(ProductEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.productRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.productRepository.deleteById(id);
    }
    
    @Override
    public List<ProductEntity> findAllById(List<Long> ids){
       return (List<ProductEntity>)this.productRepository.findAllById(ids);
    }
}
