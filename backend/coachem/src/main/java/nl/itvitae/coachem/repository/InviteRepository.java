package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Invite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends CrudRepository<Invite, Long> {

    List<Invite> findByInviterId(Long personId);
    List<Invite> findByInvitedPersonId(Long personId);
}
