package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.ProgressDto;
import nl.itvitae.coachem.dto.RecommendationDto;
import nl.itvitae.coachem.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/trainee/{id}")
    public List<RecommendationDto> getRecommendationsByTraineeId(@PathVariable("id") Long id) {
        return recommendationService.getRecommendationsByTraineeId(id);
    }

    @PostMapping("/new/{personid}/{skillid}")
    public RecommendationDto newRecommendation(@PathVariable("personid") Long personId,
                                                         @PathVariable("skillid") Long skillId) {
        return recommendationService.newRecommendation(personId, skillId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecommendationById(@PathVariable("id") Long id) {
        if (recommendationService.deleteRecommendation(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
