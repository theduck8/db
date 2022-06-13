package de.htw_berlin.imi.db.web;

import de.htw_berlin.imi.db.services.SeminarRaumEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(path = "/ui/seminarraeume")
public class SeminarRaumMvcController {

    @Autowired
    SeminarRaumEntityService seminarRaumEntityService;

    @GetMapping
    String findAll(final Model model) {
        model.addAttribute("seminarraeume", seminarRaumEntityService.findAll());
        return "seminarraeume";
    }

    @GetMapping("/{id}")
    String find(final Model model,
                @PathVariable("id") final long id) {
        model.addAttribute("seminarraeume",
                seminarRaumEntityService
                        .findById(id)
                        .orElseThrow(IllegalArgumentException::new));
        return "seminarraum-details";
    }

}

