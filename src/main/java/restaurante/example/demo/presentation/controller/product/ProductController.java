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
import restaurante.example.demo.presentation.dto.product.ProductDto;
import restaurante.example.demo.service.interfaces.product.IProductService;


@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private IProductService productService; // Servicio para manejar la lógica de negocio relacionada con productos

    @GetMapping("/find/all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = this.productService.getAll();

        if (products.isEmpty()) {
            return new ResponseEntity<>(MessageResponseDto.builder()
                    .message("No hay productos registrados.")
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("Se realizó la búsqueda con éxito.")
                .object(products)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        ProductDto productDto = this.productService.getOneById(id);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("Se realizó la búsqueda con éxito")
                .object(productDto)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) throws EntityDataAccesException {
        ProductDto createdProduct = this.productService.create(productDto);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("El producto se registró correctamente.")
                .object(createdProduct)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) throws EntityDataAccesException, EntityNotFoundException {
        validateId(id);
        ProductDto updatedProduct = this.productService.update(id, productDto);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message("El producto se actualizó correctamente.")
                .object(updatedProduct)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        String responseMessage = this.productService.delete(id);
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

