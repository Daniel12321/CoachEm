package nl.itvitae.coachem.config;

import jakarta.transaction.Transactional;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.User;
import nl.itvitae.coachem.repository.PersonRepository;
import nl.itvitae.coachem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUsers {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private PasswordEncoder encoder;

    @EventListener
    @Transactional
    public void onAppLaunch(ApplicationReadyEvent event) {
        User trainee = this.userRepo.save(new User("trainee@coachem.nl", encoder.encode("asd"), "TRAINEE"));
        User coach = this.userRepo.save(new User("coach@coachem.nl", encoder.encode("asd"), "COACH"));
        User manager = this.userRepo.save(new User("manager@coachem.nl", encoder.encode("asd"), "MANAGER"));
        User hr = this.userRepo.save(new User("hr@coachem.nl", encoder.encode("asd"), "HR"));
        Person traineeP = new Person("Joris", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        Person coachP = new Person("Ali B", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        Person managerP = new Person("Scrum Manager", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        Person hrP = new Person("Matthijs van Nieuwkerk ", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        traineeP.setUser(trainee);
        coachP.setUser(coach);
        managerP.setUser(manager);
        hrP.setUser(hr);
        this.personRepo.save(traineeP);
        this.personRepo.save(coachP);
        this.personRepo.save(managerP);
        this.personRepo.save(hrP);
    }
}
