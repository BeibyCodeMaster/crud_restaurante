/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package restaurante.example.demo.persistence.repositoy.user;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.StatusEnum;
import restaurante.example.demo.persistence.enums.NameRoleEnum;


@SpringBootTest
public class IUsuarioRepositoryTest {
    
     
    
    @Autowired
    ICustomerRepository clienteRepository;



    @Test
    public void saveCliente(){

        RoleEntity roleEntity  = RoleEntity.builder()
                .name(NameRoleEnum.Cliente)
                .build();

        LocalDate fNacimientox = LocalDate.of(1972, Month.MAY, 23);
        LocalDate fechaNacimiento = LocalDate.of(1972, Month.MAY, 23);


        UserEntity usuario = UserEntity.builder()
                .firstName("Felipe")
                .lastName("lopez")
                .email("lopez@example.com")
                .address("Calle asalto")
                .phone("111111112")
                .password("12345678") // Asegúrate de encriptar en un entorno real
                .userName("felipe.micalisoft")
                .birthDate(fechaNacimiento)
                .roles(Set.of(roleEntity))
                .build();

        LocalDateTime fechaIngreso = LocalDateTime.now();

        DateTimeActive fechaTiempoActivo = DateTimeActive.builder()
                .state(StatusEnum.ACTIVE)
                .startDate(fechaIngreso)
                .build();


        CustomerEntity cliente = CustomerEntity.builder()
                .user(usuario) // Asociar el usuario al cliente
                .dateTimeActive(fechaTiempoActivo)// Usando la enum ActivoEnum
                .build();
        clienteRepository.save(cliente);

    }
        
  
    
    @Test
    public void getAllCustomer(){
        List<CustomerEntity>  customerlist = (List<CustomerEntity>) clienteRepository.findAll();
        System.out.println(customerlist);
    }

    @Test
    public void getOneById(){
     /*   CustomerEntity customerEntity = clienteRepository.findById(1L).orElseThrow();
        System.out.println("Este es el  customerEntity :" + customerEntity);
        CustomerDto customerDto = customerMapper.entityToDto(customerEntity);
        System.out.println("Este es el customerDto :" + customerDto);*/
    }
    
}
