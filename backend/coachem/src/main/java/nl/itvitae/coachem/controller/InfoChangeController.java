package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.service.InfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/infochange")
public class InfoChangeController {
    @Autowired
    private InfoChangeService infoChangeService;

    @GetMapping("/all")
    public List<InfoChangeDto> getAllInfoChanges(){
        return infoChangeService.getAllInfoChanges();
    }

    @PostMapping("/new/{personid}")
    public InfoChangeDto addInfoChangeRequest(@PathVariable(value = "personid") Long id, @RequestBody InfoChangeDto infoChangeDto) {
        return infoChangeService.addInfoChangeRequest(id, infoChangeDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteInfoChangeById(@PathVariable(value = "id") long id){
        infoChangeService.deleteInfoChangeById(id);
    }
}
