package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.ICategoryAPI;
import nl.itvitae.coachem.dto.CategoryDto;
import nl.itvitae.coachem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CategoryController implements ICategoryAPI {

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Override
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto category) {
        return categoryService.addCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
