/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.presentation.controller.product;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityIllegalArgumentException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.presentation.dto.MessageResponseDto;
import restaurante.example.demo.presentation.dto.product.MenuDto;
import restaurante.example.demo.service.interfaces.product.IMenuService;


@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService; // Servicio para manejar la lógica de negocio relacionada con menús

    @GetMapping("/find/all")
    public ResponseEntity<?> getAllMenus() {
        List<MenuDto> menus = this.menuService.getAll();

        if (menus.isEmpty()) {
            return new ResponseEntity<>(MessageResponseDto.builder()
                    .message("No hay menús registrados.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("Se realizó la búsqueda con éxito.")
                .object(menus)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        MenuDto menuDto = this.menuService.getOneById(id);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("Se realizó la búsqueda con éxito")
                .object(menuDto)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMenu(@Valid @RequestBody MenuDto menuDto) throws EntityDataAccesException {
        MenuDto createdMenu = this.menuService.create(menuDto);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("El menú se registró correctamente.")
                .object(createdMenu)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable Long id, @Valid@RequestBody MenuDto menuDto) throws EntityDataAccesException, EntityNotFoundException {
        validateId(id);
        MenuDto updatedMenu = this.menuService.update(id, menuDto);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("El menú se actualizó correctamente.")
                .object(updatedMenu)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        String responseMessage = this.menuService.delete(id);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(responseMessage)
                .build(), HttpStatus.OK);
    }

    private void validateId(Long id) throws EntityIllegalArgumentException {
        if (id == null || id <= 0) {
            throw new EntityIllegalArgumentException("ID debe ser un número positivo y mayor que cero");
        }
    }
}

