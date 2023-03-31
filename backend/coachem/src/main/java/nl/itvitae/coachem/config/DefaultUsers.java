package nl.itvitae.coachem.config;

import jakarta.transaction.Transactional;
import nl.itvitae.coachem.model.*;
import nl.itvitae.coachem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultUsers {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private SkillRepository skillRepo;

    @Autowired
    private TraineeSkillRepository traineeSkillRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProgressRepository progressRepo;

    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private EvaluationRepository evaluationRepo;

    @Autowired
    private InviteRepository inviteRepo;

    @Autowired
    private InfoChangeRepository infoChangeRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EvaluationAttendeeRepository evaluationAttendeeRepo;

    @EventListener
    @Transactional
    public void onAppLaunch(ApplicationReadyEvent event) {
        User trainee = this.userRepo.save(new User("trainee@coachem.nl", encoder.encode("asd"), "TRAINEE"));
        User coach = this.userRepo.save(new User("coach@coachem.nl", encoder.encode("asd"), "COACH"));
        User manager = this.userRepo.save(new User("manager@coachem.nl", encoder.encode("asd"), "MANAGER"));
        User hr = this.userRepo.save(new User("hr@coachem.nl", encoder.encode("asd"), "HR"));
        Person traineeP = new Person("Joris", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        Person coachP = new Person("Coach Jansen", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        Person managerP = new Person("Manager Piet", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        Person hrP = new Person("Random HR Persoon ", "Kerkstraat 1", "Amersfoort", "7000AB", null);
        traineeP.setUser(trainee);
        coachP.setUser(coach);
        managerP.setUser(manager);
        hrP.setUser(hr);
        this.personRepo.save(traineeP);
        this.personRepo.save(coachP);
        this.personRepo.save(managerP);
        this.personRepo.save(hrP);

        Category category = this.categoryRepo.save(new Category("Javascript"));
        Category category2 = this.categoryRepo.save(new Category("React"));
        Category category3 = this.categoryRepo.save(new Category("SQL"));
        Category category4 = this.categoryRepo.save(new Category("Personal"));

        Skill skill = this.skillRepo.save(new Skill("Learn React: Basics", true, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 6, category));
        Skill skill2 = this.skillRepo.save(new Skill("Learn React: Advanced", true, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 5, category));
        Skill skill3 = this.skillRepo.save(new Skill("Learn React: Expert", false, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 5, category));
        Skill skill4 = this.skillRepo.save(new Skill("Learn Spring Boot", true, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 5, category3));
        Skill skill5 = this.skillRepo.save(new Skill("Learn Mapstruct", true, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 5, category3));
        Skill skill6 = this.skillRepo.save(new Skill("Learn Java Generics", false, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 5, category2));
        Skill skill7 = this.skillRepo.save(new Skill("Learn SQL basics", true, "we dont have a description yet but we will get one i promise", "2023-03-23T10:35:12.967Z", 5, category));

        TraineeSkill traineeskill = this.traineeSkillRepo.save(new TraineeSkill("", "2023-03-23T10:35:12.967Z", false, skill, trainee));
        TraineeSkill traineeskill2 = this.traineeSkillRepo.save(new TraineeSkill("", "2023-03-23T10:35:12.967Z", false, skill2, trainee));
        TraineeSkill traineeskill3 = this.traineeSkillRepo.save(new TraineeSkill("", "2023-03-23T10:35:12.967Z", false, skill3, trainee));

        progressRepo.save(new Progress("i made progress", "2023-03-23T10:35:12.967Z", traineeskill));
        progressRepo.save(new Progress("i made progress again", "2023-03-23T10:35:12.967Z", traineeskill));
        progressRepo.save(new Progress("and againnnnn", "2023-03-23T10:35:12.967Z", traineeskill));
        progressRepo.save(new Progress("i made progress dude", "2023-03-23T10:35:12.967Z", traineeskill2));

        feedbackRepo.save(new Feedback("do this differently", "2023-03-23T10:35:12.967Z", traineeskill, coachP));
        feedbackRepo.save(new Feedback("do this differently too", "2023-03-23T10:35:12.967Z", traineeskill, coachP));
        feedbackRepo.save(new Feedback("fix it", "2023-03-23T10:35:12.967Z", traineeskill, coachP));
        feedbackRepo.save(new Feedback("good work", "2023-03-23T10:35:12.967Z", traineeskill2, coachP));

        Evaluation evaluation = evaluationRepo.save(new Evaluation("2023-03-23T10:35:12.967Z", traineeP));
        EvaluationAttendee evaluationAttendee = evaluationAttendeeRepo.save(new EvaluationAttendee(evaluation, coachP));

        inviteRepo.save(new Invite(false, traineeP, coachP));
        inviteRepo.save(new Invite(false, traineeP, managerP));

        infoChangeRepo.save(new InfoChange(null, null, null, null, null, "4353234242", traineeP));
    }
}
