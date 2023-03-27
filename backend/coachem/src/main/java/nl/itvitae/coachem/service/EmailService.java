package nl.itvitae.coachem.service;

import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async("threadPoolTaskExecutor")
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SimpleMailMessage template;

    public void sendNewAccountEmail(Person person, String password) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("New Coachem Account");
        mail.setText(this.getNewAccountText(person.getName(), person.getUser().getEmail(), password));
        emailSender.send(mail);
    }

    public void sendEvaluationEmail(Person person, Evaluation evaluation) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation");
        mail.setText(this.getEvaluationText(person.getName(), person.getUser().getEmail(), evaluation.getTime()));
    }

    public void sendEvaluationAttendingEmail(Person person, Evaluation evaluation) {
        Person trainee = evaluation.getTrainee();

        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation Invite");
        mail.setText(this.getEvaluationAttendeeText(person.getName(), trainee.getName(), trainee.getUser().getEmail(), evaluation.getTime()));
    }

    public void sendFeedbackEmail() {

    }

    public void send360InviteEmail() {}

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
}
