package nl.itvitae.coachem.service;

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

    public void sendEvaluationEmail(Person person) {
        SimpleMailMessage mail = new SimpleMailMessage(this.template);
        mail.setTo(person.getUser().getEmail());
        mail.setSubject("Coachem Evaluation");
        mail.setText(this.getEvaluationText(person.getName()));
    }

    private String getNewAccountText(String name, String email, String password) {
        return """
                
                
                
                
                
                """;

        return "Hello " + name + "\n\n" +

                "A new account has been created for you on the coachem website.\n" +
                "Welcome to the coachem family.\n" +
                "You can log in with the following user details: \n\n" +

                "Username: " + email + "\n" +
                "Password: " + password + "\n\n" +

                "See you soon!\n\n" +

                "- Team Coachem";
    }

    private String getEvaluationText(String name) {
        return "Hello " + name + "\n\n" +

                "A new evaluation has been created for you!\n" +
                "Evaluation details\n\n" +

                "" +
                "" +
                "" +
                "" +
                "" +
                "To see who is attending this evaluation, check the website." +
                "" +
                "" +
                "";
    }
}
