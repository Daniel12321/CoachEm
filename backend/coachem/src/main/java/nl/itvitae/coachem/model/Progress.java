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
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private String time;

    @ManyToOne
    @JoinColumn(name="trainee_skill_id")
    private TraineeSkill traineeSkill;

    public Progress(String text, String time, TraineeSkill traineeSkill){
        this.text = text;
        this.time = time;
        this.traineeSkill = traineeSkill;
    }
}
