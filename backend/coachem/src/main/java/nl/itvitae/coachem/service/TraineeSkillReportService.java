package nl.itvitae.coachem.service;

import nl.itvitae.coachem.model.TraineeSkill;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.util.Optional;

@Service
public class TraineeSkillReportService extends FileStorageService {

    @Autowired
    private TraineeSkillRepository traineeSkillRepo;

    public void saveReport(Long traineeSkillId, MultipartFile file) {
        TraineeSkill ts = this.traineeSkillRepo.findById(traineeSkillId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TraineeSkill not found"));
        Path folder = this.root.resolve("traineeskill").resolve(traineeSkillId.toString());
        String fileName = ts.getReport();
        if (fileName != null && !fileName.isBlank())
            this.delete(folder.resolve(fileName));

        if (file.getOriginalFilename() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No original file name found");

        ts.setReport(file.getOriginalFilename());
        this.traineeSkillRepo.save(ts);

        this.save(folder, file);
    }

    public Optional<Resource> loadReport(Long traineeSkillId) {
        Path folder = this.root.resolve("traineeskill").resolve(traineeSkillId.toString());

        String fileName = this.traineeSkillRepo.findById(traineeSkillId).map(TraineeSkill::getReport).orElse(null);
        if (fileName == null || fileName.isBlank())
            return Optional.empty();

        return this.load(folder.resolve(fileName));
    }
}
