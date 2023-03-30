package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IInfoChangeAPI;
import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.dto.SkillDto;
import nl.itvitae.coachem.service.InfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class InfoChangeController implements IInfoChangeAPI {

    @Autowired
    private InfoChangeService infoChangeService;

    @Override
    public List<InfoChangeDto> getAllInfoChanges() {
        return infoChangeService.getAllInfoChanges();
    }

    @Override
    public  ResponseEntity<InfoChangeDto> getInfoChangeById(@PathVariable("id") Long id) {
        return infoChangeService.getInfoChangeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public InfoChangeDto addInfoChangeRequest(@RequestBody InfoChangeDto infoChange) {
        return infoChangeService.addInfoChangeRequest(infoChange);
    }

    @Override
    public ResponseEntity<Void> deleteInfoChangeById(@PathVariable("id") Long id) {
        if (infoChangeService.deleteInfoChangeById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
