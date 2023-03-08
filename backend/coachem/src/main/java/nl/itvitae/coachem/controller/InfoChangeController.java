package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.service.InfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/infochange")
public class InfoChangeController {
    @Autowired
    private InfoChangeService infoChangeService;
}
