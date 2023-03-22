package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
