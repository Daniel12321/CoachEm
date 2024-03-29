package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.itvitae.coachem.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean type;
    private String description;
    private String time;
    private Integer duration;

    @JsonIgnore
    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL)
    private List<TraineeSkill> traineeSkills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations = new ArrayList<>();

    public Skill(String name, Boolean type, String description, String time, Integer duration, Category category) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.time = time;
        this.duration = duration;
        this.category = category;
    }
}
