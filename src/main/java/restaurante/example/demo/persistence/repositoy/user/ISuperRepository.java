/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.user.SuperEntity;

/**
 *
 * @author CARLOS
 */
public interface ISuperRepository  extends CrudRepository<SuperEntity, Long> {
    
}
