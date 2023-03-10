package nl.itvitae.coachem.config;

import jakarta.transaction.Transactional;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUsers {

    @Autowired
    public PersonRepository repo;

    @Autowired
    public PasswordEncoder encoder;

    @EventListener
    @Transactional
    public void onAppLaunch(ApplicationReadyEvent event) {
        this.repo.save(new Person("trainee@coachem.nl", encoder.encode("asd"), "Joris", "TRAINEE"));
        this.repo.save(new Person("coach@coachem.nl", encoder.encode("asd"), "Ali B", "COACH"));
        this.repo.save(new Person("manager@coachem.nl", encoder.encode("asd"), "Scrum Manager", "MANAGER"));
        this.repo.save(new Person("hr@coachem.nl", encoder.encode("asd"), "Matthijs van Nieuwkerk", "HR"));
    }
}
