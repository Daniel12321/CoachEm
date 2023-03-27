package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.InfoChange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoChangeRepository extends CrudRepository<InfoChange, Long> {
}
