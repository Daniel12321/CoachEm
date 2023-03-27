package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.CategoryDto;
import nl.itvitae.coachem.model.Category;
import nl.itvitae.coachem.repository.CategoryRepository;
import nl.itvitae.coachem.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDto.Mapper mapper;

    public List<CategoryDto> getAllCategories() {
        return ListUtil.toList(categoryRepository.findAll())
                .stream()
                .map(mapper::get)
                .toList();
    }

    public Optional<CategoryDto> addCategory(CategoryDto dto) {
        if (!dto.isValid())
            return Optional.empty();

        Category category = categoryRepository.save(mapper.post(dto));
        return Optional.of(mapper.get(category));
    }

    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }
}
