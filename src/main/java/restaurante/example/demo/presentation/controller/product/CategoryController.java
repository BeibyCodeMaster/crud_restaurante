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
import restaurante.example.demo.presentation.dto.product.CategoryDto;
import restaurante.example.demo.service.interfaces.product.ICategoryService;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService; // Servicio para manejar la lógica de negocio relacionada con categorías

    @GetMapping("/find/all")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDto> categories = this.categoryService.getAll();

        if (categories.isEmpty()) {
            return new ResponseEntity<>(MessageResponseDto.builder()
                    .message("No hay categorías registradas.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("Se realizó la búsqueda con éxito.")
                .object(categories)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        CategoryDto categoryDto = this.categoryService.getOneById(id);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("Se realizó la búsqueda con éxito")
                .object(categoryDto)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) throws EntityDataAccesException {
        CategoryDto createdCategory = this.categoryService.create(categoryDto);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("La categoría se registró correctamente.")
                .object(createdCategory)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) throws EntityDataAccesException, EntityNotFoundException {
        validateId(id);
        CategoryDto updatedCategory = this.categoryService.update(id, categoryDto);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("La categoría se actualizó correctamente.")
                .object(updatedCategory)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        String responseMessage = this.categoryService.delete(id);
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

