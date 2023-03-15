package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TraineeSkill")
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
    private List<Feedback> feedbacks = new ArrayList<Feedback>();

    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;


}
