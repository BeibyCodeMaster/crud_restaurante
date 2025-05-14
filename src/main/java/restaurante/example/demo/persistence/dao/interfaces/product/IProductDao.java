package restaurante.example.demo.persistence.dao.interfaces.product;

import java.util.List;
import java.util.Set;
import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.product.ProductEntity;

public interface IProductDao extends ICrudDao<ProductEntity> {
    List<ProductEntity> findAllById(List<Long> ids);    
}
