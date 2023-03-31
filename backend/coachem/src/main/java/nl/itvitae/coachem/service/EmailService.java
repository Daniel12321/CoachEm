package nl.itvitae.coachem.service;

import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Async("threadPoolTaskExecutor")
@EnableScheduling

public class EmailService {

    @Value("${spring.mail.enabled}")
    private boolean enabled;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SimpleMailMessage template;

    @Autowired
    private InviteService inviteService;

    private void send(SimpleMailMessage mail) {
        if (enabled)
            emailSender.send(mail);
    }

    public void sendNewAccountEmail(Person person, String password) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("New Coachem Account");
        mail.setText(this.getNewAccountText(person.getName(), person.getUser().getEmail(), password));
        send(mail);
    }

    public void sendEvaluationEmail(Person person, Evaluation evaluation) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation");
        mail.setText(this.getEvaluationText(person.getName(), person.getUser().getEmail(), evaluation.getTime()));
        send(mail);
    }

    public void sendEvaluationAttendingEmail(Person person, Evaluation evaluation) {
        Person trainee = evaluation.getTrainee();

        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation Invite");
        mail.setText(this.getEvaluationAttendeeText(person.getName(), trainee.getName(), trainee.getUser().getEmail(), evaluation.getTime()));
        send(mail);
    }

    public void sendFeedbackEmail(Person trainee, Person person, Feedback feedback) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(trainee.getUser().getEmail());
        mail.setSubject("Coachem Feedback");
        mail.setText(this.getFeedbackText(trainee.getName(), person.getName(), feedback.getTraineeSkill().getSkill().getName(), feedback.getText(), feedback.getTraineeSkill().getId()));
        send(mail);
    }

    @Scheduled(cron = "0 0 8 * * *", zone= "Europe/Paris") //at 8 in the morning
    public void sentReminder(){
        List<Invite> invites = inviteService.getAllUnaccepted();
        for (Invite invite: invites) {
            if (!invite.getAccepted()){
                //send360InviteReminderEmail(invite.getInvited())
            }
        }
    }

    public void send360InviteEmail() {}

    public void send360InviteReminderEmail() {}

    public void sendNewInfoChangeEmail() {}

    public void sendInfoChangeAcceptedEmail() {}

    public void sendInfoChangeRejectedEmail() {}

    private String getNewAccountText(String name, String email, String password) {
        return String.format("""
                Hello %s,
                
                A new account has been created for you on the coachem website.
                Welcome to the coachem family.
                You can log in with the following user details:

                Username: %s
                Password: %s
                
                See you soon!
                
                - Team Coachem
                """, name, email, password);
    }

    private String getEvaluationText(String name, String email, String time) {
        return String.format("""
                Hello %1$s,

                A new evaluation has been created for you!
                Evaluation details:

                Trainee: %1$s (%2$s)
                Time: %3$s

                To see who is attending this evaluation, please check the website.

                - Team Coachem
                """, name, email, time);
    }

    private String getEvaluationAttendeeText(String name, String trainee, String traineeEmail, String time) {
        return String.format("""
                Hello %s,
                
                You have been invited to attend an evaluation.
                Evaluation Details:
                
                Trainee: %s (%s)
                Time: %s

                For a full list of all attendees, please check the website.

                - Team Coachem
                """, name, trainee, traineeEmail, time);
    }

    private String getFeedbackText(String name, String coachName, String skillName, String comment, Long id) {
        return String.format("""
                Hello %s,
                
                You have new feedback from %s on the skill '%s':
                `%s`
                
                Click here to see the feedback. (http://127.0.0.1:3000/skill/%s)
                
                - Team Coachem
                """, name, coachName, skillName, comment, id);
    }
}
