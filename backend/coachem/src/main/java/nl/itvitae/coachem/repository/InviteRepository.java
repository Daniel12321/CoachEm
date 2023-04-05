package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Invite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends CrudRepository<Invite, Long> {

    List<Invite> findByTraineeId(Long personId);
    List<Invite> findByInvitedId(Long personId);

    @Query("SELECT i FROM Invite i WHERE i.invited.id = ?1 AND i.accepted = 0")
    List<Invite> getAllUnseen(Long personId);

    @Query("SELECT i FROM Invite i WHERE i.accepted = 0")
    List<Invite> getAllUnaccepted();
}
