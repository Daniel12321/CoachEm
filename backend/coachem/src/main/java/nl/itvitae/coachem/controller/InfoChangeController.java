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

    @PostMapping("/new/{personid}")
    public InfoChangeDto addInfoChangeRequest(@PathVariable(value = "personid") Long personId, @RequestBody InfoChangeDto infoChangeDto) {
        return infoChangeService.addInfoChangeRequest(personId, infoChangeDto);
    }

    @DeleteMapping("/delete/{infochangeid}")
    public ResponseEntity<Void> deleteInfoChangeById(@PathVariable(value = "infochangeid") Long infoChangeId) {
        if (infoChangeService.deleteInfoChangeById(infoChangeId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
