package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TraineeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String progress;
    private String report;
    private String time;
    private Boolean completed;

    @JsonIgnore
    @OneToMany(mappedBy = "traineeSkill", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skill skill;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    public TraineeSkill(String progress, String report, String time, Boolean completed, Skill skill, User user) {
        this.progress = progress;
        this.report = report;
        this.time = time;
        this.completed = completed;
        this.skill = skill;
        this.user = user;
    }
}
