package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.CategoryDto;
import nl.itvitae.coachem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/new")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto category){
        return categoryService.addCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
