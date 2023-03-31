package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Progress;
import nl.itvitae.coachem.model.Recommendation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
    List<Recommendation> findByPersonId(Long id);
}
