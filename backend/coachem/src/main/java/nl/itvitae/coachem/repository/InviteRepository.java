package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Invite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends CrudRepository<Invite, Long> {
}
