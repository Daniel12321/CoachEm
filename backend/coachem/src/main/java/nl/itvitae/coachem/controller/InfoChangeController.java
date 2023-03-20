package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.service.InfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/infochange")
public class InfoChangeController {

    @Autowired
    private InfoChangeService infoChangeService;

    @GetMapping("/all")
    public List<InfoChangeDto> getAllInfoChanges() {
        return infoChangeService.getAllInfoChanges();
    }

    @PostMapping("/new")
    public InfoChangeDto addInfoChangeRequest(@RequestBody InfoChangeDto infoChange) {
        return infoChangeService.addInfoChangeRequest(infoChange);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInfoChangeById(@PathVariable("id") Long id) {
        if (infoChangeService.deleteInfoChangeById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
