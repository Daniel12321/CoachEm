package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PersonRepository extends CrudRepository<Person, Long> {
}
