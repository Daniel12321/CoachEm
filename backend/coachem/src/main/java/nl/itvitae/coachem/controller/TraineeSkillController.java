package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.TraineeSkillDto;
import nl.itvitae.coachem.service.TraineeSkillReportService;
import nl.itvitae.coachem.service.TraineeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/traineeskill")
public class TraineeSkillController {

    @Autowired
    private TraineeSkillService traineeSkillService;

    @Autowired
    private TraineeSkillReportService reportService;

    @PostMapping("/new/{traineeid}/{skillid}")
    public ResponseEntity<TraineeSkillDto> newTraineeSkill(@RequestBody TraineeSkillDto traineeSkill,
                                           @PathVariable("traineeid") Long traineeId,
                                           @PathVariable("skillid") Long skillId) {
        return traineeSkillService.newTraineeSkill(traineeSkill, traineeId, skillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/upload/{id}")
    public void upload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        this.reportService.saveReport(id, file);
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") Long id) {
        Resource resource = this.reportService.loadReport(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Uploaded file found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TraineeSkillDto> getTraineeSkillById(@PathVariable("id") Long id) {
        return traineeSkillService.getTraineeSkillById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public List<TraineeSkillDto> getTraineeSkillByUser(@PathVariable("id") Long id) {
        return traineeSkillService.getTraineeSkillByUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TraineeSkillDto> updateTraineeSkillById(@PathVariable("id") Long id, @RequestBody TraineeSkillDto traineeSkill) {
        return traineeSkillService.updateTraineeSkillById(traineeSkill, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTraineeSkillById(@PathVariable("id") Long id) {
        if (traineeSkillService.deleteTraineeSkillById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
