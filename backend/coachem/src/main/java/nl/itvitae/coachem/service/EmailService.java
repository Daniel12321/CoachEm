package nl.itvitae.coachem.service;

import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async("threadPoolTaskExecutor")
public class EmailService {

    @Value("${spring.mail.enabled}")
    private boolean enabled;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SimpleMailMessage template;

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
        mail.setText(this.getFeedbackText(trainee.getName(), person.getName(), feedback.getTraineeSkill().getSkill().getName(), feedback.getText()));
        send(mail);
    }

    public void send360InviteEmail(Person person, Person trainee) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation Invite");
        mail.setText(this.get360InviteText(person.getName(), trainee.getName()));
        send(mail);
    }

    public void send360InviteReminderEmail(Person person, Person trainee) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation Invite");
        mail.setText(this.get360InviteReminderText(person.getName(), trainee.getName()));
        send(mail);
    }

 /*   public void sendNewInfoChangeEmail() {}*/

    public void sendInfoChangeAcceptedEmail(Person person) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem InfoChange Accepted");
        mail.setText(this.getInfoChangeAcceptedText(person.getName()));
        send(mail);
    }

    public void sendInfoChangeRejectedEmail(Person person) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem InfoChange Rejected");
        mail.setText(this.getInfoChangeRejectedText(person.getName()));
        send(mail);

    }

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

    private String getFeedbackText(String name, String coachName, String skillName, String comment) {
        return String.format("""
                Hello %s,
                
                You have new feedback from %s on the skill '%s':
                `%s`
                
                To see all feedback on your skill, please check the website.
                
                - Team Coachem
                """, name, coachName, skillName, comment);
    }

    private String get360InviteText(String name, String traineeName) {
        return String.format("""
                Hello %s,
                
                You have been invited to a 360 degree meeting by %s.
                Check the website for more information.
                
                - Team Coachem
                """, name, traineeName);
    }

    private String get360InviteReminderText(String name, String traineeName) {
        return String.format("""
                Hello %s,
                
                You have not accepted or rejected a 360 degree meeting invite yet.
                We would like to remind you that %s is waiting for you.
                
                To accept or reject the invite, please check the website.
                
                - Team Coachem
                """, name, traineeName);
    }

    private String getInfoChangeAcceptedText(String name) {
        return String.format("""
                Hello %s,
                
                Your request to change your personal information has been accepted.
                When you log in to the website, you will now see the updated information.

                - Team Coachem
                """, name);
    }

    private String getInfoChangeRejectedText(String name) {
        return String.format("""
                Hello %s,
                
                Your request to change your personal information has been rejected.
                You can try again at any point.

                - Team Coachem
                """, name);
    }
}
